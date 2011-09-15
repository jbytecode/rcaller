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
package examples;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import rcaller.RCaller;

/**
 *
 * @author Mehmet Hakan Satman
 */

public class SampleGui extends JFrame implements ActionListener {
    
    //Please change this variable if the Rscript is in an other folder.
    String RScriptExecutable = "/usr/bin/Rscript";
    
    JTextArea textArea;
    JButton button;
    JTextField textField;
    JTextArea textResult;
    
    public SampleGui(){
        super("RCaller Sample GUI");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(640, 480);
        this.setLayout(null);
        
        textArea = new JTextArea("myvar<-c(1,2,3,4,5)\nothervar<-runif(10,0,1)");
        textArea.setSize(350,400);
        textArea.setLocation(10,20);
        this.add(textArea);
        
        JLabel label = new JLabel("Return this variable:");
        label.setSize(150, 20);
        label.setLocation(380, 20);
        this.add(label);
        
        
        textField = new JTextField("othervar");
        textField.setSize(100,30);
        textField.setLocation(380, 40);
        this.add(textField);
        
        button = new JButton("Run R Code");
        button.setSize(150, 40);
        button.setLocation(380, 80);
        button.addActionListener(this);
        this.add(button);
        
        textResult = new JTextArea();
        textResult.setSize(200,250);
        textResult.setLocation(380, 150);
        this.add(textResult);
        
        this.setVisible(true);
    }
    
    public static void main(String[] args){
        new SampleGui();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Run R Code")){
            RCaller caller = new RCaller();
            caller.setRscriptExecutable(RScriptExecutable);
            caller.cleanRCode();
            caller.addRCode(textArea.getText());
            try{
            caller.runAndReturnResult(textField.getText());
            double[] result = caller.getParser().getAsDoubleArray(textField.getText());
            StringBuffer buf = new StringBuffer();
            for (int i=0;i<result.length;i++){
                buf.append(result[i]+"\n");
            }
            textResult.setText(buf.toString());
            }catch(Exception ex){
                JOptionPane.showMessageDialog(this, ex.toString());
            }
            
            
            
        }
    }
}
