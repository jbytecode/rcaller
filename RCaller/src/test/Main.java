/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import rcaller.RCaller;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class Main {
    
    public Main(){
        RCaller r = new RCaller();
        double[] d = new double[]{1,2.9, 3.1};
        r.addDoubleArray("a", d);
    }
    
    public static void main(String[] args){
        new Main();
    }
}
