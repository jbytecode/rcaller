/*
 *
RCaller, A solution for calling R from Java
Copyright (C) 2010-2014  Mehmet Hakan Satman

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
 * Google code project: http://code.google.com/p/rcaller/
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */

package rcaller;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class RPlotViewer extends JFrame {

  ImageIcon img = null;

  public ImageIcon getImg() {
    return img;
  }

  public void setImg(ImageIcon img) {
    this.img = img;
  }

  public RPlotViewer(ImageIcon img) {
    this.img = img;
    this.setSize(img.getIconWidth() + 20, img.getIconHeight() + 60);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("RCaller 2.0 - Generated Plot");
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
