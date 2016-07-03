/*
 *
RCaller, A solution for calling R from Java
Copyright (C) 2010-2016  Mehmet Hakan Satman

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

package com.github.rcaller.io;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;




public class RSerializer {

    ByteArrayOutputStream byteOut;
    DataOutputStream dataOut;
    
    public RSerializer(){
        byteOut = new ByteArrayOutputStream();
        dataOut = new DataOutputStream(byteOut);
    }
    
    public void writeVector(double[] v) throws IOException{
        dataOut.write("A\n".getBytes());            /* version */
        dataOut.write("2\n".getBytes());
        dataOut.write("197214\n".getBytes());
        dataOut.write("131840\n".getBytes());
        dataOut.write("14\n".getBytes());           /* Vector Type */
        dataOut.write( (String.valueOf(v.length) + "\n").getBytes() );
        for (int i=0;i<v.length;i++){
            dataOut.write(String.valueOf(v[i]).getBytes());
            dataOut.write("\n".getBytes());
        }
        dataOut.flush();
        byteOut.flush();
        dataOut.close();
        byteOut.close();
    }
    
    public void save(String filename) throws IOException{
        FileWriter writer = new FileWriter(filename);
        writer.write(byteOut.toString());
        writer.close();
    }
}
