/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rcaller.exception;

/**
 *
 * @author hako
 */
public class RCallerExecutionException extends Exception {

    /**
     * Creates a new instance of <code>RCallerExecutionException</code> without detail message.
     */
    public RCallerExecutionException() {
    }

    /**
     * Constructs an instance of <code>RCallerExecutionException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public RCallerExecutionException(String msg) {
        super(msg);
    }
}
