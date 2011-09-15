
package rcaller.statistics;

import rcaller.RCaller;


public class LinearRegression {
  
  RCaller rcaller;
  double[] residuals;
  double[] fittedResponse;
  double[] coefficients;
  
  public LinearRegression(RCaller rcaller){
    this.rcaller = rcaller;
  }
  
  public void calculate(String model, double[] y, double[][] x){
    rcaller.cleanRCode();
    rcaller.addRCode("ols<-lm("+model+")");
  }
  
}
