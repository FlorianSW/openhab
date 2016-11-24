/**
 * Copyright (c) 2010-2016 by the respective copyright holders.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */
package org.openhab.designer.ui.internal.actions;

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.openhab.designer.ui.UIActivator;
import org.openhab.designer.ui.internal.FileUtil;

public class OpenFileAction extends Action {

    /**
     * The id of this action.
     */
    public static final String ID = UIActivator.PLUGIN_ID + ".OpenFileAction";//$NON-NLS-1$

    private Viewer viewer;
    
    public OpenFileAction(TreeViewer viewer) {
    	this.viewer = viewer;
    }

    public void run() {
		ISelection selection = viewer.getSelection();
		Object obj = ((IStructuredSelection)selection).getFirstElement();
		if(obj instanceof IFile) {
			FileUtil.openFile((IFile) obj);
		}
    }

}
