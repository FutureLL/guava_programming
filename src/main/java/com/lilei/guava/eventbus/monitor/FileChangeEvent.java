package com.lilei.guava.eventbus.monitor;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/14 12:09
 * @version: 1.0
 * @modified By:
 */
public class FileChangeEvent {

    private final Path path;

    private final WatchEvent.Kind<?> kind;

    public FileChangeEvent(Path path, WatchEvent.Kind<?> kind) {
        this.path = path;
        this.kind = kind;
    }

    public Path getPath() {
        return path;
    }

    public WatchEvent.Kind<?> getKind() {
        return kind;
    }
}
