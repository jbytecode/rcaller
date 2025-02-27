package com.github.rcaller.exception;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class ParseException extends ExecutionException {

  /**
   * Creates a new instance of <code>RCallerParseException</code> without detail message.
   */
  public ParseException() {
  }

  /**
   * Constructs an instance of <code>RCallerParseException</code> with the specified detail message.
   * @param msg the detail message.
   */
  public ParseException(String msg) {
    super(msg);
  }

  /**
   * Constructs an instance of <code>RCallerParseException</code> with the specified detail message and cause.
   * @param msg the detail message.
   * @param cause the cause exception.
   */
  public ParseException(String msg, Throwable cause) {
    super(msg, cause);
  }
}
