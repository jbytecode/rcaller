package graphics;

import java.awt.Color;

public abstract class GraphicsTheme {

  Color bgColor = Color.WHITE;
  Color plotColor = Color.BLACK;
  Color labelColor = Color.BLACK;
  Color headerColor = Color.BLACK;
  Color subheaderColor = Color.BLACK;
  Color axisColor = Color.BLACK;
  String pointChar = "o";
  int lwd = 1;

  public int getLineWidth() {
    return lwd;
  }

  public void setLineWidth(int lwd) {
    this.lwd = lwd;
  }

  public Color getBackgroundColor() {
    return this.bgColor;
  }

  public void setBackgroundColor(Color col) {
    bgColor = col;
  }

  public Color getPlotColor() {
    return this.plotColor;
  }

  public void setPlotColor(Color col) {
    plotColor = col;
  }

  public Color getLabelColor() {
    return this.labelColor;
  }

  public void setLabelColor(Color col) {
    this.labelColor = col;
  }

  public Color getHeaderColor() {
    return this.headerColor;
  }

  public void setHeaderColor(Color col) {
    this.headerColor = col;
  }

  public Color getSubHeaderColor() {
    return this.subheaderColor;
  }

  public void setSubHeaderColor(Color col) {
    this.subheaderColor = col;
  }

  public String getPointChar() {
    return this.pointChar;
  }

  public Color getAxisColor() {
    return this.axisColor;
  }

  public void setAxisColor(Color col) {
    this.axisColor = col;
  }

  public void setPointChar(String pch) {
    this.pointChar = pch;
  }

  public String generateRCode() {
    StringBuffer buf = new StringBuffer();
    buf.append("par(bg=").append("\"").append(getStringColor(bgColor)).append("\"").append(")").append("\n");
    buf.append("par(col=").append("\"").append(getStringColor(plotColor)).append("\"").append(")").append("\n");
    buf.append("par(col.lab=").append("\"").append(getStringColor(labelColor)).append("\"").append(")").append("\n");
    buf.append("par(col.main=").append("\"").append(getStringColor(headerColor)).append("\"").append(")").append("\n");
    buf.append("par(col.sub=").append("\"").append(getStringColor(subheaderColor)).append("\"").append(")").append("\n");
    buf.append("par(col.axis=").append("\"").append(getStringColor(axisColor)).append("\"").append(")").append("\n");
    buf.append("par(pch=").append("\"").append(this.pointChar).append("\"").append(")").append("\n");
    buf.append("par(lwd=").append(this.lwd).append(")").append("\n");
    return (buf.toString());
  }

  private String getHex(int a) {
    String result = Integer.toHexString(a);
    if (result.length() == 2) {
      return (result);
    } else {
      return ("0" + result);
    }
  }

  public String getStringColor(Color col) {
    StringBuilder buf = new StringBuilder(7);
    buf.append("#");
    buf.append(getHex(col.getRed()));
    buf.append(getHex(col.getGreen()));
    buf.append(getHex(col.getBlue()));
    return (buf.toString());
  }
}
