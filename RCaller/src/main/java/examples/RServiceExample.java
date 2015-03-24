
package examples;

import org.expr.rcaller.RService;

public class RServiceExample {

    public static void main(String[] args){
        new RServiceExample();
    }
    
    public RServiceExample(){
        /* Creating R Service */
        RService service = new RService("/usr/bin/R");
        service.getRCode().clear();
        
        /* Defining some variables in R side */
        service.getRCode().addRCode("x <- 4");
        service.getRCode().addRCode("y <- 6");
        
        
        /* Performing some operations */
        Object[] result = service.get("z", "list(z=x+y)", RService.type_double);
        System.out.println(result[0]);
        
        /* Performing some other operations */
        result = service.get("product", "list(product=x*y)", RService.type_double);
        System.out.println(result[0]);        
        
        service.getRCaller().StopRCallerOnline();
    }
}
