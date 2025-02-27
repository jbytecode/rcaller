package com.github.rcaller;

public class MessageSaver implements EventHandler {
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
