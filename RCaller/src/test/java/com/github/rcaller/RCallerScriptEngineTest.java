/*
 *
 RCaller, A solution for calling R from Java
 Copyright (C) 2016  Mehmet Hakan Satman

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

package com.github.rcaller;

import com.github.rcaller.scriptengine.RCallerScriptEngine;
import com.github.rcaller.util.Globals;
import javax.script.ScriptException;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;


public class RCallerScriptEngineTest{
    
    RCallerScriptEngine engine = null;
    double delta = 1/100000;
    
    @Before
    @Test
    public void init(){
        Globals.detect_current_rscript();
        engine = new RCallerScriptEngine();
        assertNotNull(engine);
    }
    
    @Test
    public void sendCommandBasicTest() throws ScriptException {
        engine.eval("a <- 5");
        engine.eval("b <- 3");
        engine.eval("d <- a+b");
        double[] result = (double[]) engine.get("d");
        assertEquals(1, result.length);
        assertEquals(8.0, result[0], delta);
    }
    
    @Test
    public void sendReceiveMatrixTest() throws ScriptException {
        engine.eval("m <- matrix(1:9, nrow = 3, ncol = 3)");
        double[][] result = (double[][])engine.get("m");
        assertEquals(1, result[0][0], delta);
        assertEquals(2, result[0][1], delta);
        assertEquals(3, result[0][2], delta);
        assertEquals(4, result[1][0], delta);
        assertEquals(5, result[1][1], delta);
        assertEquals(6, result[1][2], delta);
        assertEquals(7, result[2][0], delta);
        assertEquals(8, result[2][1], delta);
        assertEquals(9, result[2][2], delta);       
    }
    
    @Test
    public void longTimeProcessTest() throws ScriptException {
        engine.eval("a <- 1:100");
        engine.eval("s <- sum(a)");
        engine.eval("Sys.sleep(1)");
        double[] result = (double[])engine.get("s");
        assertEquals((100.0 * 101.0)/2, result[0],delta);
    }
    
    @Test
    public void longVectorResultTest() throws ScriptException{
        engine.eval("a <- 1:1000");
        double[] result = (double[])engine.get("a");
        assertEquals(1000, result.length);
        assertEquals(1000.0, result[result.length-1],delta);
    }
    
    @Test
    public void getPutTest() throws ScriptException{
        double[] a = new double[]{19.0, 17.0, 23.0};
        engine.put("a", a);
        engine.eval("a <- sort(a)");
        double[] result = (double[])engine.get("a");
        assertEquals(result[0], 17.0, delta);
        assertEquals(result[1], 19.0, delta);
        assertEquals(result[2], 23.0, delta);
    }

    
}
