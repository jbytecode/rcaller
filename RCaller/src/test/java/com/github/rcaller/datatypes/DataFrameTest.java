package com.github.rcaller.datatypes;

import com.github.rcaller.rstuff.RCaller;
import com.github.rcaller.rstuff.RCode;
import com.github.rcaller.util.DataFrameUtil;
import org.junit.Test;

import java.util.Arrays;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

public class DataFrameTest {

    @Test
    public void createDefaultDataFrameTest() {
        DataFrame dataFrame = DataFrame.create(4, 3);

        assertEquals(dataFrame.getNumberOfColumns(), 4);
        assertEquals(dataFrame.getNumberOfRows(), 3);
        assertEquals(dataFrame.getNames().length, 4);
        assertEquals(dataFrame.getNames()[0], "var0");
        assertEquals(dataFrame.getNames()[1], "var1");
        assertEquals(dataFrame.getNames()[2], "var2");
        assertEquals(dataFrame.getNames()[3], "var3");
    }

    @Test(expected = IllegalArgumentException.class)
    public void createDataFrameTest_shouldFail() {
        Object[][] objects = new Object[][]{{1,2,3}, {"a", "b", "c"}};
        String[] names = new String[] {"numbers"};

        DataFrame.create(objects, names);
    }

    @Test
    public void createDataFrameTest_shouldSucceed() {
        Object[][] objects = new Object[][]{{1,2,3}, {"a", "b", "c"}};
        String[] names = new String[] {"numbers", "letters"};

        DataFrame dataFrame = DataFrame.create(objects, names);

        assertTrue(Arrays.equals(dataFrame.getRow(0), new Object[]{1, "a"}));
        assertTrue(Arrays.equals(dataFrame.getColumn(1), new Object[]{"a", "b", "c"}));
        assertTrue(dataFrame.getObject(0,2).equals(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getRowTest_shouldFail_1() {
        Object[][] objects = new Object[][]{{1,2,3}, {"a", "b", "c"}};
        String[] names = new String[] {"numbers", "letters"};

        DataFrame dataFrame = DataFrame.create(objects, names);

        assertTrue(Arrays.equals(dataFrame.getRow(3), new Object[]{1, "a"}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getRowTest_shouldFail_2() {
        Object[][] objects = new Object[][]{{1,2,3}, {"a", "b", "c"}};
        String[] names = new String[] {"numbers", "letters"};

        DataFrame dataFrame = DataFrame.create(objects, names);

        assertTrue(Arrays.equals(dataFrame.getRow(-3), new Object[]{1, "a"}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getColumnTest_shouldFail_1() {
        Object[][] objects = new Object[][]{{1,2,3}, {"a", "b", "c"}};
        String[] names = new String[] {"numbers", "letters"};

        DataFrame dataFrame = DataFrame.create(objects, names);

        assertTrue(Arrays.equals(dataFrame.getColumn(7), new Object[]{"a", "b", "c"}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getColumnTest_shouldFail_2() {
        Object[][] objects = new Object[][]{{1,2,3}, {"a", "b", "c"}};
        String[] names = new String[] {"numbers", "letters"};

        DataFrame dataFrame = DataFrame.create(objects, names);

        assertTrue(Arrays.equals(dataFrame.getColumn(-7), new Object[]{"a", "b", "c"}));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getObjectTest_shouldFail_1() {
        Object[][] objects = new Object[][]{{1,2,3}, {"a", "b", "c"}};
        String[] names = new String[] {"numbers", "letters"};

        DataFrame dataFrame = DataFrame.create(objects, names);

        assertTrue(dataFrame.getObject(-1,2).equals(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getObjectTest_shouldFail_2() {
        Object[][] objects = new Object[][]{{1,2,3}, {"a", "b", "c"}};
        String[] names = new String[] {"numbers", "letters"};

        DataFrame dataFrame = DataFrame.create(objects, names);

        assertTrue(dataFrame.getObject(0,-2).equals(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getObjectTest_shouldFail_3() {
        Object[][] objects = new Object[][]{{1,2,3}, {"a", "b", "c"}};
        String[] names = new String[] {"numbers", "letters"};

        DataFrame dataFrame = DataFrame.create(objects, names);

        assertTrue(dataFrame.getObject(-0,5).equals(3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void getObjectTest_shouldFail_4() {
        Object[][] objects = new Object[][]{{1,2,3}, {"a", "b", "c"}};
        String[] names = new String[] {"numbers", "letters"};

        DataFrame dataFrame = DataFrame.create(objects, names);

        assertTrue(dataFrame.getObject(8,2).equals(3));
    }

    @Test
    public void writeDataFrameToRAndGetMean() {
        Object[][] objects = new Object[][]{{1, 2, 3, 4}, {2, 3, 4, 5}, {3, 4, 5, 6}};

        DataFrame dataFrame = DataFrame.create(objects, DataFrameUtil.createDefaultNamesArray(3));

        RCaller rCaller = RCaller.create();
        RCode rCode = RCode.create();

        rCode.addDataFrame("df", dataFrame);
        rCode.addRCode("result <- c(colMeans(df), rowMeans(df))");
        rCaller.setRCode(rCode);
        rCaller.runAndReturnResult("result");

        double[] result = rCaller.getParser().getAsDoubleArray("result");

        assertEquals(result[0], 2.5);
        assertEquals(result[1], 3.5);
        assertEquals(result[2], 4.5);
        assertEquals(result[3], 2.0);
        assertEquals(result[4], 3.0);
        assertEquals(result[5], 4.0);
        assertEquals(result[6], 5.0);
    }

    @Test
    public void writeBigDataFrame() {
        RCaller rCaller = RCaller.create();
        RCode rCode = RCode.create();

        rCode.addDataFrame("df", DataFrame.create(DataFrameUtil.createObjectsMatrix(2000, 3000, 2), DataFrameUtil.createDefaultNamesArray(2000)));
        rCode.addRCode("result <- c(nrow(df), ncol(df), df[2,2])");
        rCaller.setRCode(rCode);
        rCaller.runAndReturnResult("result");

        double[] result = rCaller.getParser().getAsDoubleArray("result");
        assertEquals(result[0], 3000d);
        assertEquals(result[1], 2000d);
        assertEquals(result[2], 2d);
    }

}
