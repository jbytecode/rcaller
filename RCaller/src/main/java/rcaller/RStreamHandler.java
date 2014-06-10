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
 * Google code project: http://code.google.com/p/rcaller/
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */


package rcaller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * A class to handle R's standard output and error streams
 * @author Miroslav Batchkarov <mbatchkarov@gmail.com>
 */
public class RStreamHandler implements Runnable {

    private InputStream stream = null;
    private BufferedReader reader = null;
    private final Thread consumerThread;
    private boolean closeSignal = false;
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

    public void setCloseSignal(boolean closeSignal) {
        this.closeSignal = closeSignal;
    }

    public void start() {
        closeSignal = false;
//        this.consumerThread = new Thread(this);
//        this.consumerThread.setName(this.name);
        this.consumerThread.start();
    }

    public boolean isAlive() {
        return this.consumerThread.isAlive();
    }

    public void run() {
        if (reader == null) {
            reader = new BufferedReader(new InputStreamReader(stream));
        }
        String s;
        while (!closeSignal) {
            try {
                s = reader.readLine();
                if (s != null) {
                    for (EventHandler eventHandler : eventHandlers) {
                        eventHandler.messageReceived(name, s);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
