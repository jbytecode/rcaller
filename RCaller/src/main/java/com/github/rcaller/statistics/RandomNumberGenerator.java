package com.github.rcaller.statistics;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class RandomNumberGenerator {

  private RCaller caller;
  private RCode code;

  public RandomNumberGenerator(RCaller rcaller) {
    this.caller = rcaller;
    this.code = RCode.create();
  }

  private double[] generateRandom(String strcode) throws Exception {
    code.clear();
    code.getCode().append(strcode).append("\n");
    caller.setRCode(code);
    caller.runAndReturnResult("rcaller_data");
    return (caller.getParser().getAsDoubleArray("rcaller_data"));
  }

  public double[] randomNormal(int n, double mean, double standardDeviation) throws Exception {
    return (generateRandom("rcaller_data<-rnorm(" + n + "," + mean + "," + standardDeviation + ")"));
  }

  public double[] randomLogNormal(int n, double logmean, double logStandardDeviation) throws Exception {
    return (generateRandom("rcaller_data<-rlnorm(" + n + "," + logmean + "," + logStandardDeviation + ")"));
  }

  public double[] randomUniform(int n, double min, double max) throws Exception {
    return (generateRandom("rcaller_data<-runif(" + n + "," + min + "," + max + ")"));
  }

  public double[] randomBeta(int n, double shape1, double shape2) throws Exception {
    return (generateRandom("rcaller_data<-rbeta(" + n + "," + shape1 + "," + shape2 + ")"));
  }

  public double[] randomCauchy(int n, double location, double scale) throws Exception {
    return (generateRandom("rcaller_data<-rcauchy(" + n + "," + location + "," + scale + ")"));
  }

  public double[] randomT(int n, int df) throws Exception {
    return (generateRandom("rcaller_data<-rt(" + n + "," + df + ")"));
  }

  public double[] randomChisqare(int n, int df) throws Exception {
    return (generateRandom("rcaller_data<-rchisq(" + n + "," + df + ")"));
  }

  public double[] randomF(int n, int df1, int df2) throws Exception {
    return (generateRandom("rcaller_data<-rf(" + n + "," + df1 + "," + df2 + ")"));
  }

  public double[] randomPoisson(int n, double lambda) throws Exception {
    return (generateRandom("rcaller_data<-rpois(" + n + "," + lambda + ")"));
  }

  public double[] randomBinom(int n, int size, double p) throws Exception {
    return (generateRandom("rcaller_data<-rbinom(" + n + "," + size + "," + p + ")"));
  }

  public double[] randomNegativeBinom(int n, int size, double p) throws Exception {
    return (generateRandom("rcaller_data<-rnbinom(" + n + "," + size + "," + p + ")"));
  }

  public double[] randomMultinomial(int n, int size, double p) throws Exception {
    return (generateRandom("rcaller_data<-rmultinom(" + n + "," + size + "," + p + ")"));
  }

  public double[] randomGeometric(int n, double p) throws Exception {
    return (generateRandom("rcaller_data<-rgeom(" + n + "," + p + ")"));
  }

  public double[] randomWeibull(int n, double shape, double scale) throws Exception {
    return (generateRandom("rcaller_data<-rweibull(" + n + "," + shape + "," + scale + ")"));
  }

  public double[] randomHyperGeometric(int amount, int n, int m, int k) throws Exception {
    return (generateRandom("rcaller_data<-rhyper(" + amount + "," + n + "," + m + "," + k + ")"));
  }

  public double[] randomExponential(int n, double theta) throws Exception {
    return (generateRandom("rcaller_data<-rexp(" + n + "," + theta + ")"));
  }

  public double[] randomGamma(int n, double shape, double rate, double scale) throws Exception {
    return (generateRandom("rcaller_data<-rgamma(" + n + "," + shape + "," + rate + "," + scale + ")"));
  }
}
