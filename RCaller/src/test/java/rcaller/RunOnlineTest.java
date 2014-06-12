package rcaller;

import rcaller.exception.ExecutionException;

import org.junit.Test;
import static org.junit.Assert.*;

public class RunOnlineTest {

    /*
     * Set this to 200 for enhanced test.
     * We set it to 20 for faster tests.
     */
    private int numTrials = 20;

    @Test
    public void testMatrixOnline() {
        RCaller rcaller = new RCaller();
        RCode code = new RCode();

        Globals.detect_current_rscript();
        rcaller.setRscriptExecutable(Globals.Rscript_current);
        rcaller.setRExecutable(Globals.R_current);

        code.addDoubleArray("x", new double[]{1.0, 2.0, 3.0, 4.0, 50.0});
        code.addRCode("result <- mean(x)");

        rcaller.setRCode(code);
        rcaller.runAndReturnResultOnline("result");
        double mean = rcaller.getParser().getAsDoubleArray("result")[0];
        System.out.println("mean: " + mean);

        code.clear();
        code.addRCode("result <- sd(x)");
        rcaller.runAndReturnResultOnline("result");
        double sd = rcaller.getParser().getAsDoubleArray("result")[0];
        System.out.println("sd: " + sd);

        code.clear();
        code.addRCode("result <- mad(x)");
        rcaller.runAndReturnResultOnline("result");
        double mad = rcaller.getParser().getAsDoubleArray("result")[0];
        System.out.println("mad: " + mad);

        rcaller.deleteTempFiles();
        rcaller.StopRCallerOnline();
    }

    @Test
    public void onlineCalculationTest() {
        RCaller rcaller = new RCaller();
        RCode code = new RCode();

        Globals.detect_current_rscript();
        rcaller.setRscriptExecutable(Globals.Rscript_current);
        rcaller.setRExecutable(Globals.R_current);

        double[] arr = {15417.0258, -16702.8374, -12278.0589, 7113.1799, 2724.1958, -6901.4409, 4618.2502, 4739.5719, 1401.5055, -942.2477, 5695.3249, 3010.1360, -9810.1831, -4145.3832, 4298.8821, -4348.9022, -5309.6303, 397.6447, -5321.3184, 1052.7197, -3057.3954, 948.0684, 1197.8204, -968.3039, -2923.1719, -3798.3165, -5855.8911, 859.3472, 4533.1969, -499.4634, 432.4027, -4516.3900, -631.8379, -3809.4734, 4864.8372, -6852.7206, -6991.0532, 452.7993, 2224.9119, 2246.6802, 5099.6117, 397.3032, 1576.9757, -85.5898, -800.8109, 69.6130, 1026.2853, -332.3653, 406.8292, -270.9119, -1017.2446, -4257.9015, 1436.8735, -930.0522, 1560.6135, 3017.7634, 2338.1001, 1308.8552, 5850.4506, 133.2165, 2927.1318, 1153.0644, -627.8444, 1979.2928, 3513.0251, 2481.6440, 4656.5110, 2095.5622, 4291.3661, 681.6946, -3075.5939, -1742.7691, 1470.1612, -1719.8986, -2584.8800, -1980.7716, -1104.5914, 1296.7185, 165.3335, 162.6103, 3288.7827, 1044.9453, -1937.3671, -852.5246, -1467.9217, -2849.6604, 740.7345, -1526.6975, -169.2024, 1157.7624, 4235.4573, -519.5547, 1280.2302, 2413.8581, -4437.2706, -3998.9926, -1229.1639, 504.9951, -3122.1370, -5194.1457, -2755.3479, -1148.4945, 2610.9400, 82.4462, -2548.0511, 1082.7906, 3400.3856, -559.6267, -2522.5761, -1766.9749, -489.6912, -1010.5460, 489.8533, 831.2105, 1195.5911, -845.9388, -628.2652, 1440.6815, -2577.7789, 1000.5335, 1425.9856, -1273.0955, 2082.1441, -729.5981, 1393.8735, 1117.8440, 961.2421, 2556.2797, 492.2107, -2012.4984, -1160.5490, -786.1731, -391.5831, 662.4742, -2656.1474, -1048.8036, 89.5009, -1155.2024, -2839.6841, -489.6955, 880.6003, -1058.5520, -833.2617, 252.6280, 1018.7616, -1383.1382, 4906.7096, 2574.6833, -520.2743, -991.9216, 1241.4528, 3452.4856, -1142.6240, -1389.3175, -1189.6124, -1990.6767, -141.9305, -1708.8201, -835.8583, 173.6941, 263.8833, -2847.0565, 1302.1570, -95.9897, -60.8207, 664.5335, 2658.8889, -1446.3477, -1572.2909, 1567.3002, 701.0554, -475.0002, -746.5112, 1920.3498, -2125.8352, 845.8621, -542.9336, -170.1608, 330.7092, -1203.2594, 167.5217, 92.0326, 749.1877, -441.4419, -2748.0570, 1804.7733, -1128.7319, 1675.2106, -420.9231, -2460.9562, -150.9814, -1974.9060, 404.6211, 1064.4121, 719.4778, -2002.3254, 1642.4301, -1436.6522, -2152.3570, -1989.2740, -19.8922, 742.8214, -708.7313, -1117.1665, 1669.8513, -1364.6196, 2225.2467, -832.7106, 1638.1193, 349.9394, -2003.6369, 1014.3934, 222.2482, -1385.0711, 482.2928, 270.7888, -906.7582, 1493.5475, 12.8172, -88.1739, 1361.7173, 588.0990, 285.5661, 1570.9689, -853.5588, -299.8580, 1660.3175, 898.1994, 1744.0548, -2041.9264, 1655.6419, -794.0836, 573.3254, 174.8690, 330.3106, -456.5721, -490.4052, -2826.7622, 1246.6433, 1030.2496, 2120.8744, 166.8055, -17.0710, -1158.9432, 270.5223, 628.2171, -125.4914, -549.9167, 788.4990, -3439.2461, 1399.8009, 1710.5828, -352.1875, -530.0253, 2511.0867, 928.5471, 2201.4299, 309.8721, -1585.2081, -2719.8596, -596.5809, -856.0828, 1053.5595, 1228.0468, 1898.2744, 1232.3738, 527.6361, 1395.3925, 270.7453, -1499.2688, -610.0412, 1020.7133, -1376.7431, -2136.7905, 178.5940, 1957.2654, 1562.5924, 1553.0375, -485.4381, -1090.9498, 443.4093, 1911.5011, 850.2295, 198.2293, -359.3781, 2588.3401, 412.2649, 244.8575, 894.6444, 1791.0471, -2033.9945, 1216.5256, 1708.9881, -2198.0570, 2482.4999, 329.7832, 1329.1384, -393.7075, -477.9517, 580.9775};

        for (int i = 0; i < numTrials; i++) {
            System.out.println(i);
            code.clear();
            code.addRCode("a<-1:10");
            for (int j = 0; j < 50; j++) {
                code.addDoubleArray("largeArray" + j, arr);
            }

            code.addRCode("write(largeArray0, stdout())");
            code.addRCode("write(largeArray0, stdout())");
            code.addRCode("write(largeArray0, stderr())");
            code.addRCode("write(largeArray0, stderr())");

            rcaller.setRCode(code);
            rcaller.runAndReturnResultOnline("a");
            assertEquals(rcaller.getParser().getAsIntArray("a")[0], 1);

            code.clear();
            code.addRCode("b<-1:10");
            code.addRCode("m<-mean(b)");

            rcaller.runAndReturnResultOnline("m");
            assertEquals(rcaller.getParser().getAsDoubleArray("m")[0], 5.5, 0.000001);

            code.clear();
            code.addRCode("a<-1:99");
            code.addRCode("k<-median(a)");

            rcaller.runAndReturnResultOnline("k");
            assertEquals(rcaller.getParser().getAsDoubleArray("k")[0], 50.0, 0.000001);

            //Test mean of b is still alive
            rcaller.runAndReturnResultOnline("m");
            assertEquals(rcaller.getParser().getAsDoubleArray("m")[0], 5.5, 0.000001);
        }
        System.out.println("done");
        assertTrue(rcaller.stopStreamConsumers());
        //both consumer threads are dead, program must terminate now
        rcaller.deleteTempFiles();
        rcaller.StopRCallerOnline();
    }

    /**
     * Same as RunOnlineTest#onlineCalculationTest, except a problem is
     * deliberately caused Expected behaviour: RCaller must retry
     */
    @Test(expected = rcaller.exception.ExecutionException.class)
    public void errorHandlerTest() {
        RCaller rcaller = new RCaller();
        RCode code = new RCode();

        Globals.detect_current_rscript();

//     executables deliberately not set!
//    rcaller.setRscriptExecutable(Globals.Rscript_current);
//    rcaller.setRExecutable(Globals.R_current);
        double[] arr = {15417.0258, -16702.8374, -12278.0589, 7113.1799, 2724.1958, -6901.4409, 4618.2502, 4739.5719, 1401.5055, -942.2477, 5695.3249, 3010.1360, -9810.1831, -4145.3832, 4298.8821, -4348.9022, -5309.6303, 397.6447, -5321.3184, 1052.7197, -3057.3954, 948.0684, 1197.8204, -968.3039, -2923.1719, -3798.3165, -5855.8911, 859.3472, 4533.1969, -499.4634, 432.4027, -4516.3900, -631.8379, -3809.4734, 4864.8372, -6852.7206, -6991.0532, 452.7993, 2224.9119, 2246.6802, 5099.6117, 397.3032, 1576.9757, -85.5898, -800.8109, 69.6130, 1026.2853, -332.3653, 406.8292, -270.9119, -1017.2446, -4257.9015, 1436.8735, -930.0522, 1560.6135, 3017.7634, 2338.1001, 1308.8552, 5850.4506, 133.2165, 2927.1318, 1153.0644, -627.8444, 1979.2928, 3513.0251, 2481.6440, 4656.5110, 2095.5622, 4291.3661, 681.6946, -3075.5939, -1742.7691, 1470.1612, -1719.8986, -2584.8800, -1980.7716, -1104.5914, 1296.7185, 165.3335, 162.6103, 3288.7827, 1044.9453, -1937.3671, -852.5246, -1467.9217, -2849.6604, 740.7345, -1526.6975, -169.2024, 1157.7624, 4235.4573, -519.5547, 1280.2302, 2413.8581, -4437.2706, -3998.9926, -1229.1639, 504.9951, -3122.1370, -5194.1457, -2755.3479, -1148.4945, 2610.9400, 82.4462, -2548.0511, 1082.7906, 3400.3856, -559.6267, -2522.5761, -1766.9749, -489.6912, -1010.5460, 489.8533, 831.2105, 1195.5911, -845.9388, -628.2652, 1440.6815, -2577.7789, 1000.5335, 1425.9856, -1273.0955, 2082.1441, -729.5981, 1393.8735, 1117.8440, 961.2421, 2556.2797, 492.2107, -2012.4984, -1160.5490, -786.1731, -391.5831, 662.4742, -2656.1474, -1048.8036, 89.5009, -1155.2024, -2839.6841, -489.6955, 880.6003, -1058.5520, -833.2617, 252.6280, 1018.7616, -1383.1382, 4906.7096, 2574.6833, -520.2743, -991.9216, 1241.4528, 3452.4856, -1142.6240, -1389.3175, -1189.6124, -1990.6767, -141.9305, -1708.8201, -835.8583, 173.6941, 263.8833, -2847.0565, 1302.1570, -95.9897, -60.8207, 664.5335, 2658.8889, -1446.3477, -1572.2909, 1567.3002, 701.0554, -475.0002, -746.5112, 1920.3498, -2125.8352, 845.8621, -542.9336, -170.1608, 330.7092, -1203.2594, 167.5217, 92.0326, 749.1877, -441.4419, -2748.0570, 1804.7733, -1128.7319, 1675.2106, -420.9231, -2460.9562, -150.9814, -1974.9060, 404.6211, 1064.4121, 719.4778, -2002.3254, 1642.4301, -1436.6522, -2152.3570, -1989.2740, -19.8922, 742.8214, -708.7313, -1117.1665, 1669.8513, -1364.6196, 2225.2467, -832.7106, 1638.1193, 349.9394, -2003.6369, 1014.3934, 222.2482, -1385.0711, 482.2928, 270.7888, -906.7582, 1493.5475, 12.8172, -88.1739, 1361.7173, 588.0990, 285.5661, 1570.9689, -853.5588, -299.8580, 1660.3175, 898.1994, 1744.0548, -2041.9264, 1655.6419, -794.0836, 573.3254, 174.8690, 330.3106, -456.5721, -490.4052, -2826.7622, 1246.6433, 1030.2496, 2120.8744, 166.8055, -17.0710, -1158.9432, 270.5223, 628.2171, -125.4914, -549.9167, 788.4990, -3439.2461, 1399.8009, 1710.5828, -352.1875, -530.0253, 2511.0867, 928.5471, 2201.4299, 309.8721, -1585.2081, -2719.8596, -596.5809, -856.0828, 1053.5595, 1228.0468, 1898.2744, 1232.3738, 527.6361, 1395.3925, 270.7453, -1499.2688, -610.0412, 1020.7133, -1376.7431, -2136.7905, 178.5940, 1957.2654, 1562.5924, 1553.0375, -485.4381, -1090.9498, 443.4093, 1911.5011, 850.2295, 198.2293, -359.3781, 2588.3401, 412.2649, 244.8575, 894.6444, 1791.0471, -2033.9945, 1216.5256, 1708.9881, -2198.0570, 2482.4999, 329.7832, 1329.1384, -393.7075, -477.9517, 580.9775};

        for (int i = 0; i < numTrials; i++) {
            System.out.println(i);
            code.clear();
            code.addRCode("a<-1:10");
            for (int j = 0; j < 50; j++) {
                code.addDoubleArray("largeArray" + j, arr);
            }

            code.addRCode("write(largeArray0, stdout())");
            code.addRCode("write(largeArray0, stdout())");
            code.addRCode("write(largeArray0, stderr())");
            code.addRCode("write(largeArray0, stderr())");

            rcaller.setRCode(code);
            rcaller.runAndReturnResultOnline("a");
            assertEquals(rcaller.getParser().getAsIntArray("a")[0], 1);

        }
        System.out.println("done");
        assertTrue(rcaller.stopStreamConsumers());
        //both consumer threads are dead, program must terminate now
        rcaller.StopRCallerOnline();
        rcaller.deleteTempFiles();
    }

    @Test
    public void timeoutTest() {
        System.out.println("TIMEOUT TEST");
        RCaller rcaller = new RCaller();
        RCode code = new RCode();

        //do not retry
        rcaller.setFailurePolicy(RCaller.FailurePolicy.CONTINUE);

        rcaller.setMaxWaitTime(100);

        Globals.detect_current_rscript();
        rcaller.setRscriptExecutable(Globals.Rscript_current);
        rcaller.setRExecutable(Globals.R_current);

        code.clear();
        code.addRCode("a<-1:10");

        //ensures R will not terminate within the specified timeout
        //This line is passive because an R instance is stay in the memory
        //while Sys.sleep operates. 
        //code.addRCode("Sys.sleep(100)");
        rcaller.setRCode(code);

        boolean exceptionThrown = false;
        try {
            rcaller.runAndReturnResultOnline("a");
        } catch (ExecutionException ex) {
//            ex.printStackTrace();
            exceptionThrown = true;
            rcaller.StopRCallerOnline();
        }
        assertTrue(exceptionThrown);

        //must not have retried because the policy is CONTINUE
        assertEquals(rcaller.getRetries(), 0);

        //both consumer threads are dead, program must terminate now
        System.out.println("done");
        rcaller.deleteTempFiles();
        rcaller.StopRCallerOnline();
    }

}
