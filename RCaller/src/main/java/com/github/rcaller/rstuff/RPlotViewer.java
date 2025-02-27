package com.github.rcaller.rstuff;

import com.github.rcaller.util.Globals;
import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class RPlotViewer extends JFrame {

  private ImageIcon img;

  public ImageIcon getImg() {
    return img;
  }

  public void setImg(ImageIcon img) {
    this.img = img;
  }

  public RPlotViewer(ImageIcon img) {
    this.img = img;
    this.setSize(img.getIconWidth() + 20, img.getIconHeight() + 60);
    this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    this.setTitle(Globals.version + " - Generated Plot");
    repaint();
  }

  @Override
  public void paint(Graphics g) {
    super.paint(g);
    if (img != null) {
      g.drawImage(this.img.getImage(), 10, 30, this);
    }
  }
}
