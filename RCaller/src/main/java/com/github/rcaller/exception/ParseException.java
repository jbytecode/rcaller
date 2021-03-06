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
 * Google code project: https://github.com/jbytecode/rcaller
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */


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
