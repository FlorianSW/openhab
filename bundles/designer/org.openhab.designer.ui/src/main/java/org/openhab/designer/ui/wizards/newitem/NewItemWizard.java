package org.openhab.designer.ui.wizards.newitem;

import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;

public class NewItemWizard extends Wizard implements INewWizard {

	private NewItemWizardCreationPage mainPage;

	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setWindowTitle("Create new Item");
	}

	@Override
	public void addPages() {
		mainPage = new NewItemWizardCreationPage("Create new Item");
		addPage(mainPage);
	}

	@Override
	public boolean performFinish() {
		return mainPage.handleFinish();
	}

}
