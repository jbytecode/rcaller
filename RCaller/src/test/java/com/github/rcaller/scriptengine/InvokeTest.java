package com.github.rcaller.scriptengine;

import com.github.rcaller.util.Globals;
import static com.github.rcaller.scriptengine.NamedArgument.*;
import java.util.ArrayList;
import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class InvokeTest {

    static ScriptEngine engine = null;
    double delta = 1 / 100000;
    
    public static void message(String text) {
        System.out.println("* " + text); 
    }

    @BeforeClass
    public static void setUpClass() {
        Globals.detect_current_rscript();
        ScriptEngineManager manager = new ScriptEngineManager();
        engine = manager.getEngineByName("RCaller");
    }

    @AfterClass
    public static void tearDownClass() {
        ((RCallerScriptEngine) engine).close();
    }

    @Test
    public void EngineIsNullTest() {
        assertNotNull(engine);
    }

    @Test
    public void InvokeRunifTest() throws ScriptException, NoSuchMethodException {
        message("Invoke 'runif' ...");
        Invocable invocable = (Invocable) engine;
        Object result = invocable.invokeFunction("runif",
                Named("n", 5),
                Named("min", 0),
                Named("max", 100)
        );
        ArrayList<NamedArgument> allresults = (ArrayList<NamedArgument>) result;
        assertEquals(1, allresults.size());

        double[] dresult = (double[]) allresults.get(0).getObj();
        assertTrue(dresult[0] > 0 && dresult[0] < 100);
        assertTrue(dresult[1] > 0 && dresult[0] < 100);
        assertTrue(dresult[2] > 0 && dresult[0] < 100);
        assertTrue(dresult[3] > 0 && dresult[0] < 100);
        assertTrue(dresult[4] > 0 && dresult[0] < 100);
    }

    @Test
    public void InvokeSqrtTest() throws ScriptException, NoSuchMethodException {
        message("Invoke 'sqrt' ...");
        assertNotNull(engine);
        Invocable invocable = (Invocable) engine;
        ArrayList<NamedArgument> allresults = (ArrayList<NamedArgument>) invocable.invokeFunction("sqrt", Named("", 25.0));
        double[] dresult = (double[]) allresults.get(0).getObj();
        assertEquals(5.0, dresult[0], delta);
    }

    @Test
    public void InvokeRNormWithoutNamesTest() throws ScriptException, NoSuchMethodException {
        message("Invoke 'rnorm' without argument names...");
        Invocable invocable = (Invocable) engine;
        ArrayList<NamedArgument> allresults
                = (ArrayList<NamedArgument>) invocable.invokeFunction(
                        "rnorm", // function name
                        Named("", 100), // for n
                        Named("", 0), // for mean
                        Named("", 2));  // for standard deviation

        double[] dresult = (double[]) allresults.get(0).getObj();
        assertEquals(100, dresult.length);
        assertTrue(dresult[0] < 0 + 2 * 5 && dresult[0] > 0 - 2 * 5);
        assertTrue(dresult[1] < 0 + 2 * 5 && dresult[1] > 0 - 2 * 5);
    }

    @Test
    public void InvokePassDoubleArrayToRFunctionTest() throws ScriptException, NoSuchMethodException {
        message("Invoke 'mean' on a Java double[] without argument names");
        Invocable invocable = (Invocable) engine;
        double[] x = new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        ArrayList<NamedArgument> allresults
                = (ArrayList<NamedArgument>) invocable.invokeFunction(
                        "mean", // function name
                        Named("", x)
                );
        double[] dresult = (double[]) allresults.get(0).getObj();
        assertEquals(1, dresult.length);
        assertEquals(5.0, dresult[0], delta);
    }

    @Test
    public void InvokeUserDefinedFunctionOnAVectorTest() throws ScriptException, NoSuchMethodException {
        message("Invoke user defined R function on a Java double[] array...");
        Invocable invocable = (Invocable) engine;
        double[] x = new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        engine.eval("f <- function(a){return(a^2)}");
        ArrayList<NamedArgument> allresults = (ArrayList<NamedArgument>) invocable.invokeFunction("f", Named("a", x));
        double[] dresult = (double[]) allresults.get(0).getObj();
        assertEquals(9, dresult.length);
        assertEquals(1.0, dresult[0], delta);
        assertEquals(4.0, dresult[1], delta);
        assertEquals(9.0, dresult[2], delta);
        assertEquals(16.0, dresult[3], delta);
        assertEquals(25.0, dresult[4], delta);
        assertEquals(36.0, dresult[5], delta);
        assertEquals(49.0, dresult[6], delta);
        assertEquals(64.0, dresult[7], delta);
        assertEquals(81.0, dresult[8], delta);
    }

    @Test
    public void InvokeUserDefinedFunctionOnAMatrixTest() throws ScriptException, NoSuchMethodException {
        message("Invoke user defined R function on a Java double[][] array...");
        Invocable invocable = (Invocable) engine;
        double[][] x = new double[][]{{1.0, 2.0, 3.0}, {4.0, 5.0, 6.0}, {7.0, 8.0, 9.0}};
        engine.eval("mydet <- function(a){round(det(a))}");
        ArrayList<NamedArgument> allresults
                = (ArrayList<NamedArgument>) invocable.invokeFunction("mydet", Named("a", x));
        double[] dresult = (double[]) allresults.get(0).getObj();
        assertEquals(1, dresult.length);
        assertEquals(0.0, dresult[0], delta);
    }

    @Test
    public void InvokeLmTest() throws ScriptException, NoSuchMethodException {
        message("Invoke 'lm()' on Java arrays x[] and y[]...");
        Invocable invocable = (Invocable) engine;
        double[] x = new double[]{1.0, 2.0, 3.0, 4.0, 5.0, 6.0, 7.0, 8.0, 9.0};
        double[] y = new double[]{2.0, 4.0, 6.0, 8.0, 10.0, 12.0, 14.0, 16.0, 18.0};
        engine.put("x", x);
        engine.put("y", y);
        ArrayList<NamedArgument> allresults
                = (ArrayList<NamedArgument>) invocable.invokeFunction("lm", Named("formula", "y~x"));
        assertEquals("coefficients", allresults.get(0).getName());
        assertArrayEquals(new double[]{2.368475785867E-15, 2.0}, (double[]) allresults.get(0).getObj(), delta);
    }

    @Test
    public void NumericalIntegrationTest() throws ScriptException, NoSuchMethodException {
        message("Invoke 'integrate' for Numerical integration...");
        Invocable invocable = (Invocable) engine;
        engine.eval("f <- function(x) { return(1/(1+x)^2) }");
        ArrayList<NamedArgument> results
                = (ArrayList<NamedArgument>) invocable.invokeFunction("integrate",
                        Named("f", new LanguageElement("f")),
                        Named("lower", 0.0),
                        Named("upper", new LanguageElement("Inf")));
        Object current;
        current = NamedArgument.find(results, "value");

        assertEquals(1.0, ((double[]) current)[0], delta);
        current = NamedArgument.find(results, "message");
        assertEquals("OK", ((String[]) current)[0]);
        current = NamedArgument.find(results, "abs_error");
        assertTrue(((double[]) current)[0] < 0.001);
    }

    @Test
    public void PDQFunctionsTests() throws ScriptException, NoSuchMethodException {
        message("Invoke p, d, q probability functions...");
        Invocable invocable = (Invocable) engine;
        ArrayList<NamedArgument> args = (ArrayList< NamedArgument>) invocable.invokeFunction("qnorm", Named("p", 0.05 / 2));
        assertEquals(-1.9599, ((double[]) args.get(0).getObj())[0], 1.0 / 10.0);

        args = (ArrayList< NamedArgument>) invocable.invokeFunction("pnorm", Named("q", -1.95996398));
        assertEquals(0.05 / 2, ((double[]) args.get(0).getObj())[0], 1.0 / 10.0);

        args = (ArrayList< NamedArgument>) invocable.invokeFunction("dnorm", Named("x", 0));
        assertEquals(1 / Math.sqrt(2 * Math.PI), ((double[]) args.get(0).getObj())[0], 1.0 / 10.0);
    }

    @Test
    public void OptimizeTest() throws ScriptException, NoSuchMethodException {
        message("Invoke optimize() test...");
        
        Invocable invocable = (Invocable) engine;
        
        ArrayList<NamedArgument> args;

        engine.eval("f <- function(x){"
                + "return(3*x^2)"
                + "}"
        );
        args = (ArrayList<NamedArgument>) invocable.invokeFunction("optimize",
                Named("f", new LanguageElement("f")),
                Named("lower", -10.0),
                Named("upper", 10.0));

        double value = ((double[]) NamedArgument.find(args, "objective"))[0];
        double x = ((double[]) NamedArgument.find(args, "minimum"))[0];
        assertEquals(0.0, value, delta);
        assertEquals(0.0, x, delta);
    }
    
}
