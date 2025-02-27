package com.github.rcaller.rstuff;

import com.github.rcaller.EventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A class to handle R's standard output and error streams
 *
 * @author <a href="mailto:mbatchkarov@gmail.com">Miroslav Batchkarov</a>
 */
public class RStreamHandler implements Runnable {
    private AtomicBoolean stillReading = new AtomicBoolean();
    private InputStream stream = null;
    private BufferedReader reader = null;
    private final Thread consumerThread;
    private String name = null;
    private ArrayList<EventHandler> eventHandlers = null;
    private boolean OKAYdetected = false;
    
    public RStreamHandler(InputStream stream, String name) {
        this.stream = stream;
        this.name = name;
        consumerThread = new Thread(this, name + "ProcessorThread");
        this.eventHandlers = new ArrayList<>();
    }

    public boolean isOKAYdetected() {
        return OKAYdetected;
    }

    public void setOKAYdetected(boolean OKAYdetected) {
        this.OKAYdetected = OKAYdetected;
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
        this.consumerThread.start();
    }

    public void stop() {
        try {
            while (true) {
                stillReading.set(false);
                this.consumerThread.join(100);
                if (!this.consumerThread.isAlive()) {
                    break;
                } else if (!stillReading.get()) {
                    this.consumerThread.interrupt();
                    break;
                }
            }
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean isAlive() {
        return this.consumerThread.isAlive();
    }

    public void run() {
        if (reader == null) {
            Charset charset = Charset.forName(System.getProperty("sun.jnu.encoding", Charset.defaultCharset().name()));
            reader = new BufferedReader(new InputStreamReader(stream, charset));
        }
        try {
            while (true) {
                String s = reader.readLine();
                //System.out.println(name+": "+s);
                OKAYdetected = false;
                stillReading.set(true);
                if (s == null){
                    break;
                }
                if(s.contains("OKAY!")){
                    this.OKAYdetected = true;
                }
                for (EventHandler eventHandler : eventHandlers) {
                    eventHandler.messageReceived(name, s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        stillReading.set(false);
    }
}
