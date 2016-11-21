package org.openhab.designer.core.config;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.CoreException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigurationFolderWatcher {
	private final Logger logger = LoggerFactory.getLogger(ConfigurationFolderWatcher.class);

	private WatchService service;
	private Thread thread;

	private void createNewWatchService() throws IOException {
		if (service != null) {
			logger.info("Closing existing WatchService");
			try {
				thread.interrupt();
				while (thread.isAlive()) {
				}
				service.close();
			} catch (IOException e) {
				logger.info("Unable to close WatchService.", e);
			}
		}
		service = FileSystems.getDefault().newWatchService();
		logger.info("Successfully created new WatchService.");
	}

	public void watchNew(Path folder) throws IOException {
		createNewWatchService();
		ConfigurationFolderWatcherRunnable runnable = new ConfigurationFolderWatcherRunnable(service);
		thread = new Thread(runnable, "ConfigurationFolderWatcherRunnable");
		registerAllDirectories(folder);
		thread.start();
	}

	private void registerAllDirectories(Path folder) throws IOException {
		Files.walkFileTree(folder, new SimpleFileVisitor<Path>() {
			@Override
			public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
				logger.info("Added " + dir.toString() + " to watched folders.");
				dir.register(service, ENTRY_CREATE, ENTRY_DELETE);
				return FileVisitResult.CONTINUE;
			}
		});
	}

	private static class ConfigurationFolderWatcherRunnable implements Runnable {

		private final Logger logger = LoggerFactory.getLogger(ConfigurationFolderWatcherRunnable.class);

		private WatchService service;

		public ConfigurationFolderWatcherRunnable(WatchService service) {
			this.service = service;
		}

		@Override
		public void run() {
			logger.info("Started watching file changes.");
			while (true) {
				WatchKey wk;
				try {
					wk = service.take();
				} catch (InterruptedException e) {
					logger.info(e.getMessage(), e);
					return;
				}
				for (WatchEvent<?> event : wk.pollEvents()) {
					handleWatchEvent(wk, event);
				}

				// reset the key
				boolean valid = wk.reset();
				if (!valid || Thread.currentThread().isInterrupted()) {
					logger.info("WatchService key has been unregistered.");
					break;
				}
			}
		}

		private void handleWatchEvent(WatchKey wk, WatchEvent<?> event) {
			// not what we want
			if (event.kind() == OVERFLOW) {
				return;
			}
			try {
				logger.info("File changes in configuration folder detected, reloading.");
				if (ConfigurationFolderProvider.getRootConfigurationFolder() == null) {
					wk.cancel();
					return;
				}
				ConfigurationFolderProvider.getRootConfigurationFolder().refreshLocal(IFolder.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				logger.info("Configuration folder not available, ending watch service.", e);
				wk.cancel();
			}
		}

	}
}
