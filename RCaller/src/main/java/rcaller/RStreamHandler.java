package rcaller;

import java.io.BufferedReader;
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
    private Thread consumerThread;
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
