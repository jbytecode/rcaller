/*
 *
RCaller, A solution for calling R from Java
S
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
package rcaller.statistics;

import rcaller.RCaller;
import rcaller.RCode;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class RandomNumberGenerator {

  RCaller caller;
  RCode code;

  public RandomNumberGenerator(RCaller rcaller) {
    this.caller = rcaller;
    this.code = new RCode();
  }

  private double[] generateRandom(String strcode) throws Exception {
    code.clear();
    code.getCode().append(strcode).append("\n");
    caller.setRCode(code);
    caller.runAndReturnResult("rcaller_data");
    return (caller.getParser().getAsDoubleArray("rcaller_data"));
  }

  public double[] randomNormal(int n, double mean, double standardDeviation) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rnorm(").append(n).append(",").append(mean).append(",").append(standardDeviation).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomLogNormal(int n, double logmean, double logStandardDeviation) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rlnorm(").append(n).append(",").append(logmean).append(",").append(logStandardDeviation).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomUniform(int n, double min, double max) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-runif(").append(n).append(",").append(min).append(",").append(max).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomBeta(int n, double shape1, double shape2) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rbeta(").append(n).append(",").append(shape1).append(",").append(shape2).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomCauchy(int n, double location, double scale) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rcauchy(").append(n).append(",").append(location).append(",").append(scale).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomT(int n, int df) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rt(").append(n).append(",").append(df).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomChisqare(int n, int df) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rchisq(").append(n).append(",").append(df).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomF(int n, int df1, int df2) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rf(").append(n).append(",").append(df1).append(",").append(df2).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomPoisson(int n, double lambda) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rpois(").append(n).append(",").append(lambda).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomBinom(int n, int size, double p) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rbinom(").append(n).append(",").append(size).append(",").append(p).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomNegativeBinom(int n, int size, double p) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rnbinom(").append(n).append(",").append(size).append(",").append(p).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomMultinomial(int n, int size, double p) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rmultinom(").append(n).append(",").append(size).append(",").append(p).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomGeometric(int n, double p) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rgeom(").append(n).append(",").append(p).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomWeibull(int n, double shape, double scale) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rweibull(").append(n).append(",").append(shape).append(",").append(scale).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomHyperGeometric(int amount, int n, int m, int k) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rhyper(").append(amount).append(",").append(n).append(",").append(m).append(",").append(k).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomExponential(int n, double theta) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rexp(").append(n).append(",").append(theta).append(")");
    return (generateRandom(code.toString()));
  }

  public double[] randomGamma(int n, double shape, double rate, double scale) throws Exception {
    StringBuilder code = new StringBuilder();
    code.append("rcaller_data<-rgamma(").append(n).append(",").append(shape).append(",").append(rate).append(",").append(scale).append(")");
    return (generateRandom(code.toString()));
  }
}
