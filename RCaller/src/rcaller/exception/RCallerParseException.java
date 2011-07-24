/*
 *
    RCaller, A solution for calling R from Java
    Copyright (C) 2010,2011  Mehmet Hakan Satman

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
 * Google code projec: http://code.google.com/p/rcaller/
 *
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
