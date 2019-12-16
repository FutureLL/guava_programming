package com.lilei.guava.eventbus.monitor;

import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.*;

/**
 * @description:
 * @author: Mr.Li
 * @date: Created in 2019/12/14 12:09
 * @version: 1.0
 * @modified By:
 */
public class DirectoryTargetMonitor implements TargetMonitor {

    private final static Logger LOGGER = LoggerFactory.getLogger(DirectoryTargetMonitor.class);

    /**
     * java.nio.file.WatchService
     * A watch service that watches registered objects for changes and events.
     * For example a file manager may use a watch service to monitor a directory for changes
     * so that it can update its display of the list of files when files are created or deleted.
     */
    private WatchService watchService;

    /**
     * java.nio.file.Path
     * An object that may be used to locate a file in a file system. It will typically represent a system dependent file path.
     */
    private final Path path;

    private final EventBus eventBus;

    // 开关
    private volatile boolean start = false;

    // 两个构造方法
    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath) {
        this(eventBus, targetPath, "");
    }

    public DirectoryTargetMonitor(final EventBus eventBus, final String targetPath, final String... morePaths) {
        this.eventBus = eventBus;
        this.path = Paths.get(targetPath, morePaths);
    }


    @Override
    public void startMonitor() throws Exception {
        // 初始化watchService对象
        this.watchService = FileSystems.getDefault().newWatchService();
        // 增加感兴趣的事件 [MODIFY修改,DELETE删除,CREATE创建]
        this.path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY
                , StandardWatchEventKinds.ENTRY_DELETE
                , StandardWatchEventKinds.ENTRY_CREATE);
        LOGGER.info("The directory [{}] is monitoring... ", path);

        // 开关为true
        this.start = true;
        while (start) {
            /**
             * java.nio.file.WatchKey
             * A token representing the registration of a watchable object with a WatchService.
             */
            WatchKey watchKey = null;
            try {
                watchKey = watchService.take();
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    WatchEvent.Kind<?> kind = event.kind();
                    Path path = (Path) event.context();
                    Path child = DirectoryTargetMonitor.this.path.resolve(path);
                    eventBus.post(new FileChangeEvent(child, kind));
                }
            } catch (Exception e) {
                this.start = false;
            } finally {
                if (watchKey != null) {
                    watchKey.reset();
                }
            }
        }
    }

    @Override
    public void stopMonitor() throws Exception {
        LOGGER.info("The directory [{}] monitor will be stop...", path);
        Thread.currentThread().interrupt();
        this.start = false;
        this.watchService.close();
        LOGGER.info("The directory [{}] monitor will be stop done.", path);
    }
}
