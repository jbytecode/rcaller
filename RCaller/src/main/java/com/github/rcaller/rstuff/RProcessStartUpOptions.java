package com.github.rcaller.rstuff;

import java.lang.reflect.Field;

/**
 * this class contains the startup options of the R process.
 * for documentation read: http://linux.die.net/man/1/r
 */
public class RProcessStartUpOptions {


    private boolean save;
    private boolean noSave;
    private boolean noEnviron;
    private boolean noSiteFile;
    private boolean noInitFile;
    private boolean restore;
    private boolean noRestoreData;
    private boolean noRestoreHistory;
    private boolean noRestore;
    private boolean vanilla;
    private boolean noReadLine;
    private boolean quiet;
    private boolean silent;
    private boolean slave;
    private boolean interactive;
    private boolean verbose;

    private Integer maxPPSize;
    private Integer minNSize;
    private Integer minVSize;

    private String debugger;
    private String debuggerArgs;
    private String gui;
    private String arch;
    private String args;
    private String file;

    private RProcessStartUpOptions(
            boolean save,
            boolean noSave,
            boolean noEnviron,
            boolean noSiteFile,
            boolean noInitFile,
            boolean restore,
            boolean noRestoreData,
            boolean noRestoreHistory,
            boolean noRestore,
            boolean vanilla,
            boolean noReadLine,
            boolean quiet,
            boolean silent,
            boolean slave,
            boolean interactive,
            boolean verbose,
            Integer maxPPSize,
            Integer minNSize,
            Integer minVSize,
            String debugger,
            String debuggerArgs,
            String gui,
            String arch,
            String args,
            String file) {
        this.save = save;
        this.noSave = noSave;
        this.noEnviron = noEnviron;
        this.noSiteFile = noSiteFile;
        this.noInitFile = noInitFile;
        this.restore = restore;
        this.noRestoreData = noRestoreData;
        this.noRestoreHistory = noRestoreHistory;
        this.noRestore = noRestore;
        this.vanilla = vanilla;
        this.noReadLine = noReadLine;
        this.quiet = quiet;
        this.silent = silent;
        this.slave = slave;
        this.interactive = interactive;
        this.verbose = verbose;
        this.maxPPSize = maxPPSize;
        this.minNSize = minNSize;
        this.minVSize = minVSize;
        this.debugger = debugger;
        this.debuggerArgs = debuggerArgs;
        this.gui = gui;
        this.arch = arch;
        this.args = args;
        this.file = file;
    }

    /**
     *
     * @return the default RProcessStartUpOptions object
     */
    public static RProcessStartUpOptions create() {
        return new RProcessStartUpOptions(false, false, false, false, false,
                false, false, false, false, true, false, false,
                false, false, false, false, null, null,
                null, null, null, null, null, null, null);
    }

    public static RProcessStartUpOptions create(
            boolean save,
            boolean noSave,
            boolean noEnviron,
            boolean noSiteFile,
            boolean noInitFile,
            boolean restore,
            boolean noRestoreData,
            boolean noRestoreHistory,
            boolean noRestore,
            boolean vanilla,
            boolean noReadLine,
            boolean quiet,
            boolean silent,
            boolean slave,
            boolean interactive,
            boolean verbose,
            Integer maxPPSize,
            Integer minNSize,
            Integer minVSize,
            String debugger,
            String debuggerArgs,
            String gui,
            String arch,
            String args,
            String file) {
        return new RProcessStartUpOptions(save, noSave, noEnviron, noSiteFile,
                noInitFile, restore, noRestoreData, noRestoreHistory, noRestore, vanilla,
                noReadLine, quiet, silent, slave, interactive, verbose, maxPPSize, minNSize,
                minVSize, debugger, debuggerArgs, gui, arch, args, file);
    }

    public String getStartUpOptionsAsCommand() {
        Field[] fields = this.getClass().getDeclaredFields();
        String command = " ";

        for (Field field : fields) {
            if (field.getType().equals(boolean.class)) {
                command = command.concat(getCommandForBooleanField(field.getName()));
            } else if (field.getType().equals(Integer.class)) {
                command = command.concat(getCommandForIntegerField(field.getName()));
            } else if (field.getType().equals(String.class)) {
                if (field.getName().equals("args")) {
                    String tempCommand = getCommandForStringField(field.getName());
                    if (!tempCommand.isEmpty()) {
                        return tempCommand;
                    }
                }
                command = command.concat(getCommandForStringField(field.getName()));
            } else {
                throw new IllegalArgumentException("Unknown field type " + field.getType());
            }
        }

        return command;
    }

    private String getCommandForBooleanField(String fieldName) {
        switch (fieldName) {
            case "save": return save ? "--save " : "";
            case "noSave": return noSave ? "--no-save " : "";
            case "noEnviron": return noEnviron ? "--no-environ " : "";
            case "noSiteFile": return noSiteFile ? "--no-site-file " : "";
            case "noInitFile": return noInitFile ? "--no-init-file " : "";
            case "restore": return restore ? "--restore " : "";
            case "noRestoreData" : return noRestoreData ? "--no-restore-data " : "";
            case "noRestoreHistory": return noRestoreHistory ? "--no-restore-history " : "";
            case "noRestore": return noRestore ? "--no-restore " : "";
            case "vanilla": return vanilla ? "--vanilla " : "";
            case "noReadLine": return noReadLine ? "--no-readline " : "";
            case "quiet": return quiet ? "--quiet " : "";
            case "silent": return silent ? "--silent " : "";
            case "slave": return slave ? "--slave " : "";
            case "interactive": return interactive ? "--interactive " : "";
            case "verbose": return verbose ? "--verbose ": "";
            default: throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
    }

    private String getCommandForIntegerField(String fieldName) {
        switch (fieldName) {
            case "maxPPSize": return maxPPSize != null && maxPPSize >= 0 ? "--max-ppsize=" + maxPPSize + " " : "";
            case "minNSize": return minNSize !=  null && minNSize >= 0 ? "--min-nsize=" + minNSize + " " : "";
            case "minVSize": return minVSize != null && minVSize >= 0 ? "--min-vsize=" + minNSize + " " : "";
            default: throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
    }

    private String getCommandForStringField(String fieldName) {
        switch (fieldName) {
            case "debugger": return debugger != null && !debugger.isEmpty() ? "--debugger=".concat(debugger).concat(" ") : "";
            case "debuggerArgs": return debuggerArgs != null && !debuggerArgs.isEmpty() ? "--debugger-args=".concat(debuggerArgs).concat(" ") : "";
            case "gui": return gui != null && !gui.isEmpty() ? "--gui=".concat(gui).concat(" ") : "";
            case "arch": return arch != null && !arch.isEmpty() ? "--arch=".concat(arch).concat(" ") : "";
            case "args": return args != null && !args.isEmpty() ? args : "";
            case "file": return file != null && !file.isEmpty() ? "--file=".concat(file).concat(" ") : "";
            default: throw new IllegalArgumentException("Unknown field: " + fieldName);
        }
    }

    public void setSave(boolean save) {
        this.save = save;
    }

    public void setNoSave(boolean noSave) {
        this.noSave = noSave;
    }

    public void setNoEnviron(boolean noEnviron) {
        this.noEnviron = noEnviron;
    }

    public void setNoSiteFile(boolean noSiteFile) {
        this.noSiteFile = noSiteFile;
    }

    public void setNoInitFile(boolean noInitFile) {
        this.noInitFile = noInitFile;
    }

    public void setRestore(boolean restore) {
        this.restore = restore;
    }

    public void setNoRestoreData(boolean noRestoreData) {
        this.noRestoreData = noRestoreData;
    }

    public void setNoRestoreHistory(boolean noRestoreHistory) {
        this.noRestoreHistory = noRestoreHistory;
    }

    public void setNoRestore(boolean noRestore) {
        this.noRestore = noRestore;
    }

    public void setVanilla(boolean vanilla) {
        this.vanilla = vanilla;
    }

    public void setNoReadLine(boolean noReadLine) {
        this.noReadLine = noReadLine;
    }

    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

    public void setSilent(boolean silent) {
        this.silent = silent;
    }

    public void setSlave(boolean slave) {
        this.slave = slave;
    }

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void setMaxPPSize(Integer maxPPSize) {
        this.maxPPSize = maxPPSize;
    }

    public void setMinNSize(Integer minNSize) {
        this.minNSize = minNSize;
    }

    public void setMinVSize(Integer minVSize) {
        this.minVSize = minVSize;
    }

    public void setDebugger(String debugger) {
        this.debugger = debugger;
    }

    public void setGui(String gui) {
        this.gui = gui;
    }

    public void setArch(String arch) {
        this.arch = arch;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
