/*
 *
 RCaller, A solution for calling R from Java
 Copyright (C) 2010-2015  Mehmet Hakan Satman

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

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class HandlingAllVariablesTest {

    private final static double delta = 1.0 / 1000.0;

    @Test
    public void GetAllVariablesInEnvironmentTest() {
        RCaller caller = RCaller.create();
        RCode code = RCode.create();

        code.addDouble("x", 5.65);
        code.addDouble("y", 8.96);
        code.addRCode("result <- as.list(.GlobalEnv)");

        caller.setRCode(code);

        caller.runAndReturnResult("result");

        ArrayList<String> names = caller.getParser().getNames();

        Assert.assertEquals(caller.getParser().getAsDoubleArray("x")[0], 5.65, delta);
        Assert.assertEquals(caller.getParser().getAsDoubleArray("y")[0], 8.96, delta);
    }
    
    @Test
    public void GetAllVectorsInEnvironmentTest() {
        RCaller caller = RCaller.create();
        RCode code = RCode.create();

        code.addDoubleArray("x", new double[]{1,2,3,4,5});
        code.addDoubleArray("y", new double[]{2,4,6,8,10});
        code.addRCode("result <- as.list(.GlobalEnv)");

        caller.setRCode(code);

        caller.runAndReturnResult("result");

        ArrayList<String> names = caller.getParser().getNames();
        //System.out.println("Names : " + names);

        //System.out.println("x[4] is " + caller.getParser().getAsDoubleArray("x")[4]);
        //System.out.println("y[4] is " + caller.getParser().getAsDoubleArray("y")[4]);

        Assert.assertEquals(caller.getParser().getAsDoubleArray("x")[4], 5, delta);
        Assert.assertEquals(caller.getParser().getAsDoubleArray("y")[4], 10, delta);
    }
}
