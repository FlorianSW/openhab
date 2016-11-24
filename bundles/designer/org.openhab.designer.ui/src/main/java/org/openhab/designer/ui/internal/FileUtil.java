package org.openhab.designer.ui.internal;

import org.eclipse.core.resources.IFile;
import org.eclipse.ui.IEditorDescriptor;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.FileEditorInput;
import org.openhab.designer.core.config.ConfigurationFolderProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileUtil {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationFolderProvider.class);

	/**
	 * Opens an editor on the given file resource.
	 *
	 * @param file
	 *            the file resource
	 */
	public static void openFile(IFile file) {
		IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

		FileEditorInput editorInput = null;

		if (editorInput == null) {
			editorInput = new FileEditorInput(file);
		}

		IEditorDescriptor editor = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
		String editorId = editor == null ? "org.eclipse.ui.DefaultTextEditor" : editor.getId();
		try {
			activePage.openEditor(editorInput, editorId);
		} catch (PartInitException e) {
			logger.info(e.getMessage(), e);
		}
	}
}
