package rcaller;

import java.io.OutputStream;

public abstract class EventHandler {

    private OutputStream o;

    public EventHandler(OutputStream o) {
        this.o = o;
    }

    public abstract void messageReceived(String senderName, String msg);
}
