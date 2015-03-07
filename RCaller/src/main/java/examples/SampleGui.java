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
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import rcaller.Globals;
import rcaller.RCaller;
import rcaller.RCode;

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
  JTextArea textResult, inputStreamResult, errorStreamResult;
  
  
  public SampleGui() {
    super("RCaller Sample GUI");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(640, 480);
    this.setLayout(null);

    Globals.detect_current_rscript();
    this.RScriptExecutable = Globals.Rscript_current;
    
    textArea = new JTextArea("myvar<-c(1,2,3,4,5)\nothervar<-runif(10,0,1)\ncat(\"Hello World!\")\nfor(i in 1:5){\nprint(i)\n}\n");
    textArea.setSize(350, 200);
    textArea.setLocation(10, 20);
    textArea.setBorder(new javax.swing.border.TitledBorder("R Code"));
    this.add(textArea);
    
    inputStreamResult = new JTextArea();
    inputStreamResult.setSize(350,70);
    inputStreamResult.setLocation(10, 230);
    inputStreamResult.setBorder(new javax.swing.border.TitledBorder("Output by R"));
    this.add(inputStreamResult);
    
    errorStreamResult = new JTextArea();
    errorStreamResult.setSize(350,70);
    errorStreamResult.setLocation(10, 310);
    errorStreamResult.setBorder(new javax.swing.border.TitledBorder("Error by R"));
    this.add(errorStreamResult);
    
    
    textField = new JTextField("othervar");
    textField.setSize(200, 50);
    textField.setLocation(380, 20);
    textField.setBorder(new javax.swing.border.TitledBorder("Handle this by Java"));
    this.add(textField);
    
    button = new JButton("Run R Code");
    button.setSize(150, 40);
    button.setLocation(380, 90);
    button.addActionListener(this);
    this.add(button);
    
    textResult = new JTextArea();
    textResult.setSize(200, 250);
    textResult.setLocation(380, 140);
    textResult.setBorder(new javax.swing.border.TitledBorder("Result as Java object"));
    this.add(textResult);
    
    
    this.setVisible(true);
  }
  
  public static void main(String[] args) {
    new SampleGui();
  }
  
  public void actionPerformed(ActionEvent e) {
    if (e.getActionCommand().equals("Run R Code")) {
      
      this.inputStreamResult.setText("");
      this.errorStreamResult.setText("");
      
      RCaller caller = new RCaller();
      caller.setRscriptExecutable(RScriptExecutable);
      
      RCode code = new RCode();
      code.clear();
      code.addRCode(textArea.getText());
      
//      caller.addEventHandler(new EventHandler() {
//
//        public void messageReceived(String threadName, String msg) {
//          if(threadName.equals("Output")){
//            inputStreamResult.append(msg+"\n");
//          }else{
//            errorStreamResult.append(msg+"\n");
//          }
//        }
//      });
      
      
      try {
        caller.setRCode(code);
        caller.runAndReturnResult(textField.getText());
        double[] result = caller.getParser().getAsDoubleArray(textField.getText());
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < result.length; i++) {
          buf.append(result[i] + "\n");
        }
        textResult.setText(buf.toString());
      } catch (Exception ex) {
        JOptionPane.showMessageDialog(this, ex.toString());
      }
      
      
      
    }
  }
}
