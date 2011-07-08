/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package rcaller;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author hako
 */
public class RPlotViewer extends JFrame{
    
    ImageIcon img = null;

    public ImageIcon getImg() {
        return img;
    }

    public void setImg(ImageIcon img) {
        this.img = img;
    }
    
    public RPlotViewer(ImageIcon img){
        this.img = img;
        this.setSize(img.getIconWidth() +20 , img.getIconHeight()+60);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("RCaller 2.0 - Generated Plot");
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if(img!=null) g.drawImage(this.img.getImage(), 10, 30, this);
    }
    
    
    
    
}
