/*
 *
RCaller, A solution for calling R from Java
Copyright (C) 2010-2014  Mehmet Hakan Satman

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Mehmet Hakan Satman - mhsatman@yahoo.com
 * http://www.mhsatman.com
 * Google code project: https://github.com/jbytecode/rcaller
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */


package org.expr.rcaller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * A class to handle R's standard output and error streams
 * @author <a href="mailto:mbatchkarov@gmail.com">Miroslav Batchkarov</a>
 */
public class RStreamHandler implements Runnable {

    private InputStream stream = null;
    private BufferedReader reader = null;
    private final Thread consumerThread;
    private String name = null;
    private ArrayList<EventHandler> eventHandlers = null;

    public RStreamHandler(InputStream stream, String name) {
        this.stream = stream;
        this.name = name;
        consumerThread = new Thread(this, name + "ProcessorThread");
        this.eventHandlers = new ArrayList<EventHandler>();
    }

    public void setStream(InputStream stream) {
        this.stream = stream;
    }

    public void addEventHandler(EventHandler eh) {
        this.eventHandlers.add(eh);
    }

    public void removeEventHandler(EventHandler eh) {
        this.eventHandlers.remove(eh);
    }

    public void start() {
//        this.consumerThread = new Thread(this);
//        this.consumerThread.setName(this.name);
        this.consumerThread.start();
    }

    public void stop() {
        try {
            this.consumerThread.join();
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean isAlive() {
        return this.consumerThread.isAlive();
    }

    public void run() {
        if (reader == null) {
            //HACK: OS encoding may differ from default encoding (especially on Windows system) and it
            //results in invalid output messages encoding
            Charset charset = Charset.forName(System.getProperty("sun.jnu.encoding", Charset.defaultCharset().name()));
            reader = new BufferedReader(new InputStreamReader(stream, charset));
        }
        try {
            String s = reader.readLine();
            while (s != null) {
                for (EventHandler eventHandler : eventHandlers) {
                    eventHandler.messageReceived(name, s);
                }
                s = reader.readLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
