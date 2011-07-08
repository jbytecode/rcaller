/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rcaller.exception;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class RCallerParseException extends Exception {

    /**
     * Creates a new instance of <code>RCallerParseException</code> without detail message.
     */
    public RCallerParseException() {
    }

    /**
     * Constructs an instance of <code>RCallerParseException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RCallerParseException(String msg) {
        super(msg);
    }
}
