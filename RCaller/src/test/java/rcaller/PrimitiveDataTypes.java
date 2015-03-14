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

package rcaller;


import org.junit.Test;
import static org.junit.Assert.*;

public class PrimitiveDataTypes {
    
    @Test
    public void testDouble(){
        RCode code = new RCode();
        code.addDouble("d", 3.141592);
        System.out.println(code);
        assertEquals(true, code.toString().contains("d<-3.141592\n"));
    }
    
    @Test
    public void testInt(){
        RCode code = new RCode();
        code.addInt("d", 3);
        System.out.println(code);
        assertEquals(true, code.toString().contains("d<-3\n"));
    }
    
    @Test
    public void testLong(){
        RCode code = new RCode();
        code.addLong("d", 3);
        System.out.println(code);
        assertEquals(true, code.toString().contains("d<-3\n"));
    }
    
    @Test
    public void testFloat(){
        RCode code = new RCode();
        code.addFloat("d", 3.141592f);
        System.out.println(code);
        assertEquals(true, code.toString().contains("d<-3.141592\n"));
    }

    @Test
    public void testShort(){
        RCode code = new RCode();
        code.addShort("d", (short)3);
        System.out.println(code);
        assertEquals(true, code.toString().contains("d<-3\n"));
    }
    
    @Test
    public void testBoolean(){
        RCode code = new RCode();
        code.addBoolean("d", true);
        System.out.println(code);
        assertEquals(true, code.toString().contains("d<-TRUE\n"));
    }
    
}
