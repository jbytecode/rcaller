package com.github.rcaller.exception;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class ExecutionException extends RuntimeException {

  /**
   * Creates a new instance of <code>RCallerExecutionException</code> without detail message.
   */
  public ExecutionException() {
  }

  /**
   * Constructs an instance of <code>RCallerExecutionException</code> with the specified detail message.
   * @param msg the detail message.
   */
  public ExecutionException(String msg) {
    super(msg);
  }
  /**
   * Constructs an instance of <code>RCallerExecutionException</code> with the specified detail message and cause.
   * @param msg the detail message.
   * @param cause the cause exception.
   */
  public ExecutionException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
