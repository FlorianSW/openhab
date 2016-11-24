package org.openhab.designer.ui.wizards.newitem;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;
import org.openhab.core.items.GroupItem;
import org.openhab.core.items.Item;
import org.openhab.core.items.ItemFactory;
import org.openhab.core.items.ItemRegistry;
import org.openhab.designer.core.DesignerCoreConstants;
import org.openhab.designer.core.config.ConfigurationFolderProvider;
import org.openhab.designer.ui.UIActivator;
import org.openhab.designer.ui.internal.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NewItemWizardCreationPage extends WizardPage implements Listener {

	private static final Logger logger = LoggerFactory.getLogger(ConfigurationFolderProvider.class);
	private static final String GENERAL_FILENAME_FORMAT = "%s.items";
	private static final String GENERAL_ITEM_SYNTAX = "%s %s  \"%s\"";
	private static final String ITEM_GROUP_SYNTAX = " (%s)";

	private IFolder wsItemsFolder = ResourcesPlugin.getWorkspace().getRoot()
			.getProject(DesignerCoreConstants.CONFIGURATION_PROJECT_NAME)
			.getFolder(DesignerCoreConstants.CONFIGURATION_FOLDER_NAME).getFolder("items");
	private Text itemNameInput;
	private Text itemDisplayNameInput;
	private Combo itemGroupInput;
	private Combo itemType;
	private Text itemPreview;

	protected NewItemWizardCreationPage(String pageName) {
		super(pageName);
		setTitle(pageName);
		setDescription("Creates a new OpenHAB item descriptor file.");
		setPageComplete(true);
	}

	@Override
	public void handleEvent(Event event) {
		setPageComplete(validateInputAndShowPreview());
	}

	private boolean validateInputAndShowPreview() {
		if (itemPreview != null)
			itemPreview.setText("");

		this.setErrorMessage(null);

		if (itemNameInput.getText().isEmpty()) {
			this.setErrorMessage("The item name is mandatory.");
			return false;
		}

		if (doesItemExistAlready(itemNameInput.getText())) {
			this.setErrorMessage("An item file with this name already exists.");
			return false;
		}

		if (itemType.getText().isEmpty()) {
			this.setErrorMessage("The item type is mandatory.");
			return false;
		}

		String displayName = itemNameInput.getText();
		if (!itemDisplayNameInput.getText().isEmpty()) {
			displayName = itemDisplayNameInput.getText();
		}

		String previewText = String.format(GENERAL_ITEM_SYNTAX, itemType.getText(), itemNameInput.getText(),
				displayName);

		if (!itemGroupInput.getText().isEmpty()) {
			for (int i = 0; i < itemGroupInput.getText().length(); i++) {
				char c = itemGroupInput.getText().charAt(i);
				if (!Character.isLetterOrDigit(c)) {
					this.setErrorMessage("Only letters and digits are allowed as group names.");
					return false;
				}
			}
			previewText = previewText.concat(String.format(ITEM_GROUP_SYNTAX, itemGroupInput.getText()));
		}

		previewText = previewText.concat(" {}");
		itemPreview.setText(previewText);

		return true;
	}

	private boolean doesItemExistAlready(String itemName) {
		return wsItemsFolder.getFile(String.format(GENERAL_FILENAME_FORMAT, itemName)).exists();
	}

	public boolean handleFinish() {
		IFile itemsFile = wsItemsFolder.getFile(String.format(GENERAL_FILENAME_FORMAT, itemNameInput.getText()));
		try {
			itemsFile.create(new ByteArrayInputStream(itemPreview.getText().getBytes()), IResource.NONE, null);
		} catch (CoreException e) {
			logger.info(e.getMessage(), e);
			this.setErrorMessage("Unable to create the item file for an unknown reason.");
			return false;
		}
		FileUtil.openFile(itemsFile);
		return true;
	}

	public void createControl(Composite parent) {
		Composite topLevel = new Composite(parent, SWT.NONE);

		GridLayout layout = new GridLayout();
		layout.numColumns = 4;
		layout.marginWidth = 0;
		topLevel.setLayout(layout);
		topLevel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		GridData defaultGridData = new GridData(SWT.FILL, SWT.FILL, true, false);

		// item name input box
		Label itemNameLabel = new Label(topLevel, SWT.NONE);
		itemNameLabel.setText("Name");
		itemNameInput = new Text(topLevel, SWT.BORDER);
		itemNameInput.setFocus();
		itemNameInput.addListener(SWT.Modify, this);
		itemNameInput.setLayoutData(defaultGridData);

		// item type combo
		Label itemTypeLabel = new Label(topLevel, SWT.NONE);
		itemTypeLabel.setText("Type");
		itemType = new Combo(topLevel, SWT.DROP_DOWN | SWT.READ_ONLY);
		itemType.addListener(SWT.Modify, this);
		itemType.setLayoutData(defaultGridData);
		itemType.setItems(getAllSupportedItemTypes());

		// item disply name
		Label itemDisplayNameLabel = new Label(topLevel, SWT.NONE);
		itemDisplayNameLabel.setText("Display name");
		itemDisplayNameInput = new Text(topLevel, SWT.BORDER);
		itemDisplayNameInput.addListener(SWT.Modify, this);
		itemDisplayNameInput.setLayoutData(defaultGridData);

		// item group
		Label itemGroupLabel = new Label(topLevel, SWT.NONE);
		itemGroupLabel.setText("Group");
		itemGroupInput = new Combo(topLevel, SWT.BORDER);
		itemGroupInput.setItems(getAllItemGroups());
		itemGroupInput.addListener(SWT.Modify, this);
		itemGroupInput.setLayoutData(defaultGridData);

		// item descriptor preview
		Label itemPreviewLabel = new Label(topLevel, SWT.NONE);
		itemPreviewLabel.setText("Preview");
		itemPreview = new Text(topLevel, SWT.MULTI | SWT.BORDER | SWT.WRAP | SWT.V_SCROLL);
		itemPreview.setEditable(false);
		GridData layoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		layoutData.horizontalSpan = 4;
		itemPreview.setLayoutData(layoutData);

		setControl(topLevel);
		setPageComplete(validateInputAndShowPreview());
	}

	private String[] getAllSupportedItemTypes() {
		List<String> allTypes = new ArrayList<String>();
		for (Object service : UIActivator.itemFactoryTracker.getServices()) {
			for (String type : ((ItemFactory) service).getSupportedItemTypes()) {
				allTypes.add(type);
			}
		}
		
		return allTypes.toArray(new String[allTypes.size()]);
	}

	private String[] getAllItemGroups() {
		ItemRegistry registry = (ItemRegistry) UIActivator.itemRegistryTracker.getService();
		List<String> allGroups = new ArrayList<String>();
		for (Item item : registry.getItems()) {
			if (item instanceof GroupItem) {
				allGroups.add(((GroupItem) item).getName());
			}
		}

		return allGroups.toArray(new String[allGroups.size()]);
	}

}
