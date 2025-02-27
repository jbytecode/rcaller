package com.github.rcaller.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

public class FileStatusWatcher {

    public static void waitUntilFileChanged(File file) throws IOException,InterruptedException {
        WatchService watchService = FileSystems.getDefault().newWatchService();
        Path folder = Paths.get(file.getParent());
        folder.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        while (true) {
            WatchKey key = watchService.take();
            for (WatchEvent<?> watchEvent : key.pollEvents()) {
                final WatchEvent.Kind<?> kind = watchEvent.kind();
                // Entry_modify event
                if (StandardWatchEventKinds.ENTRY_MODIFY == kind) {
                    if(watchEvent.context().toString().equals(file.getName())){
                        watchService.close();
                        return;
                    }
                }
            }
            key.reset();
        }
    }
}
