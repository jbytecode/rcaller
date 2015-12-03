package org.expr.rcaller;


public class RService {

    public static String type_String = "[Ljava.lang.String;";
    public static String type_double = "[Ljava.lang.double;";
    public static String type_Integer = "[Ljava.lang.Integer;";
    private RCaller rcaller = null;
    private RCode rcode = null;

    public RService(String pathToR){
        rcaller = new RCaller();
        rcaller.setRExecutable(pathToR);
        rcode = new RCode();
        rcaller.setRCode(rcode);
    }
    
    public RCaller getRCaller(){
        return(this.rcaller);
    }
    
    public RCode getRCode(){
        return(this.rcode);
    }
    
    public Object[] get(String var, String command, String type){
        rcode.addRCode(var + " <- " +command);
        rcaller.runAndReturnResultOnline(var);
        if (type.equals(type_String)) {
            return(rcaller.getParser().getAsStringArray(var));
        } else if (type.equals(type_Integer)) {
            int[] res = rcaller.getParser().getAsIntArray(var);
            Integer[] ints = new Integer[res.length];
            for (int i=0;i<res.length;i++){
                ints[i] = res[i];
            }
            return(ints);
        } else if (type.equals(type_double)) {
            double[] res = rcaller.getParser().getAsDoubleArray(var);
            Double[] dbls = new Double[res.length];
            for (int i=0;i<res.length;i++){
                dbls[i] = res[i];
            }
            return(dbls);
        } else {
            return(null);
        }
    }
        
    
    public String version() {
        Object[] result;
        result = this.get("version_string", "version", RService.type_String);
        return result[0].toString();
    }
    
    public String minor() {
        Object[] result;
        result = this.get("minor", "version", type_String);
        return result[0].toString();
    }
     
    public String major() {
        Object[] result;
        result = this.get("major", "version", type_String);
        return result[0].toString();
    }
}
