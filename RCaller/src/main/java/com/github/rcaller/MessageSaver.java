package com.github.rcaller;

/**
 * Date: 25.08.2015
 * Time: 14:08
 */
public class MessageSaver extends EventHandler {
    private final StringBuilder errorBuffer = new StringBuilder();

    @Override
    public void messageReceived(String senderName, String msg) {
        errorBuffer.append(msg).append("\n");
    }

    public String getMessage() {
        return errorBuffer.toString();
    }

    public void resetMessage() {
        errorBuffer.setLength(0);
    }
}
