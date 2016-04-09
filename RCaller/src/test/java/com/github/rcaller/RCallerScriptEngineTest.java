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
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;


public class RCallerScriptEngineTest {
    
    RCallerScriptEngine engine = null;
    
    @Before
    @Test
    public void init(){
        Globals.detect_current_rscript();
        engine = new RCallerScriptEngine();
        assertNotNull(engine);
    }
    
}
