package rcaller.statistics;

import rcaller.RCaller;

/**
 *
 * @author Mehmet Hakan Satman
 */
public class RandomNumberGenerator {

    RCaller caller;
    String codeBuffer;

    public RandomNumberGenerator(RCaller rcaller) {
        this.caller = rcaller;
        this.codeBuffer = rcaller.getRCode().toString();
    }

    public double[] randomNormal(int n, double mean, double standardDeviation) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rnorm(").append(n).append(",").append(mean).append(",").append(standardDeviation).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomLogNormal(int n, double logmean, double logStandardDeviation) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rlnorm(").append(n).append(",").append(logmean).append(",").append(logStandardDeviation).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomUniform(int n, double min, double max) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-runif(").append(n).append(",").append(min).append(",").append(max).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomBeta(int n, double shape1, double shape2) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rbeta(").append(n).append(",").append(shape1).append(",").append(shape2).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomCauchy(int n, double location, double scale) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rcauchy(").append(n).append(",").append(location).append(",").append(scale).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomT(int n, int df) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rt(").append(n).append(",").append(df).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomChisqyare(int n, int df) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rchisq(").append(n).append(",").append(df).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomF(int n, int df1, int df2) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rf(").append(n).append(",").append(df1).append(",").append(df2).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomPoisson(int n, double lambda) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rpois(").append(n).append(",").append(lambda).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomBinom(int n, int size, double p) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rbinom(").append(n).append(",").append(size).append(",").append(p).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomNegativeBinom(int n, int size, double p) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rnbinom(").append(n).append(",").append(size).append(",").append(p).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomMultinomial(int n, int size, double p) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rmultinom(").append(n).append(",").append(size).append(",").append(p).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomGeometric(int n, double p) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rgeom(").append(n).append(",").append(p).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomWeibull(int n, double shape, double scale) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rweibull(").append(n).append(",").append(shape).append(",").append(scale).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomHyperGeometric(int amount, int n, int m, int k) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rhyper(").append(amount).append(",").append(n).append(",").append(m).append(",").append(k).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomExponential(int n, double theta) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rexp(").append(n).append(",").append(theta).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }

    public double[] randomGamma(int n, double shape, double rate, double scale) throws Exception {
        caller.cleanRCode();
        StringBuilder code = new StringBuilder();
        code.append("rcaller_data<-rgamma(").append(n).append(",").append(shape).append(",").append(rate).append(",").append(scale).append(")");
        caller.addRCode(code.toString());
        caller.runAndReturnResult("rcaller_data");
        caller.setRCode(new StringBuffer(this.codeBuffer));
        return (caller.getParser().getAsDoubleArray("rcaller_data"));
    }
}
