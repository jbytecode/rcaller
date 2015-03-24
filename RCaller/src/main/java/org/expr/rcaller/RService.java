package org.expr.rcaller;


public class RService {

    public static String type_String = "[Ljava.lang.String;";
    public static String type_double = "[Ljava.lang.double;";
    public static String type_Integer = "[Ljava.lang.Integer;";
    RCaller rcaller;
    RCode rcode;

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
        Object[] returnObject = null;
        rcode.clear();
        rcode.addRCode(var + " <- " +command);
        rcaller.runAndReturnResultOnline(var);
        if(type.equals(type_String)){
            returnObject = rcaller.getParser().getAsStringArray(var);
        }
        return(returnObject);
    }
    
    
    public String version(){
        Object[] result;
        result = this.get("version_string", "version", RService.type_String);
        return(result[0].toString());
    }
    
    public String minor(){
        Object[] result;
        result = this.get("minor", "version", type_String);
        return(result[0].toString());
    }
     
    public String major(){
        Object[] result;
        result = this.get("major", "version", type_String);
        return(result[0].toString());
    }
}
