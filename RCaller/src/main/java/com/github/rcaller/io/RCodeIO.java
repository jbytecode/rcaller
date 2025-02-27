package com.github.rcaller.io;

import com.github.rcaller.exception.ExecutionException;
import com.github.rcaller.rstuff.RCallerOptions;
import com.github.rcaller.util.RCodeUtils;
import java.io.File;
import java.net.URI;

public class RCodeIO {

    private interface RCodeIOGenerator {
        String getInterprocessDependencies();
        String getVariableExporting(String variableName, URI target);
    }

    private static class RCodeIOGeneratorArrow implements RCodeIOGenerator {
        @Override
        public String getInterprocessDependencies() {
            var generatingScript = new StringBuilder();
            RCodeUtils.addResourceScript(generatingScript, "runiversal.R");
            RCodeUtils.addResourceScript(generatingScript, "arrow_bridge.R");
            return generatingScript.toString();
        }

        @Override
        public String getVariableExporting(String variableName, URI target) {
            return "send_by_arrow(obj=" + variableName + ", name=\"" + variableName +
                    "\", uri=\"" + new File(target).toString().replace("\\", "/") + "\")\n";
        }
    }
    private static final RCodeIOGenerator rCodeIOGeneratorArrow = new RCodeIOGeneratorArrow();

    private static class RCodeIOGeneratorXML implements RCodeIOGenerator {
        @Override
        public String getInterprocessDependencies() {
            var generatingScript = new StringBuilder();
            RCodeUtils.addResourceScript(generatingScript, "runiversal.R");
            RCodeUtils.addResourceScript(generatingScript, "xml_exporting.R");
            return generatingScript.toString();
        }

        @Override
        public String getVariableExporting(String variableName, URI target) {
            return "cat(makexml(obj=" + variableName + ", name=\"" + variableName +
                    "\"), file=\"" + new File(target).toString().replace("\\", "/") + "\")\n";
        }
    }
    private static final RCodeIOGenerator rCodeIOGeneratorXML = new RCodeIOGeneratorXML();

    private static RCodeIOGenerator getRCodeIOGenerator(RCallerOptions rCallerOptions) {
        if (rCallerOptions.useArrowIfAvailable()) {
            if (ArrowBridge.isArrowAvailable(rCallerOptions)) {
                //Use Arrow by default if enabled
                return rCodeIOGeneratorArrow;
            } else {
                if (rCallerOptions.failIfArrowNotAvailable()) {
                    throw new ExecutionException("Arrow is enabled but not available");
                } else {
                    //Use XML if Arrow is enabled but not available and we should not fail
                    return rCodeIOGeneratorXML;
                }
            }
        } else {
            //Use XML if Arrow is disabled
            return rCodeIOGeneratorXML;
        }
    }

    /**
     * Prepare R script with functions or/and module loadings that would be used in following IPC
     * @param rCallerOptions Current parameters for selecting implementation
     * @return R script with functions or/and module loadings to be added to user's code
     */
    public static String getInterprocessDependencies(RCallerOptions rCallerOptions)  {
        return getRCodeIOGenerator(rCallerOptions).getInterprocessDependencies();
    }

    /**
     * Prepare R script for exporting variable from top level R environment to file or another IPC channel
     * @param rCallerOptions Current parameters for selecting implementation
     * @param variableName R variable to be exported
     * @param target file or socket where export should be saved
     * @return R script for exporting variable to be added to user's code
     */
    public static String getVariableExporting(RCallerOptions rCallerOptions, String variableName, URI target) {
        return getRCodeIOGenerator(rCallerOptions).getVariableExporting(variableName, target);
    }
}
