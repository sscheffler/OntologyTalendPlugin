// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package de.crawling.spider.jena.ui;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Text;

import de.crawling.spider.jena.i18n.Messages;


/**
 * amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: FooterComposite.java,v 1.1 2007/06/12 07:20:39 gke Exp $
 * 
 */
public class OntologyComposite extends Composite {


    private Composite composite;
    private Button loadButton;
    private Button loadOntologyButton;
    private Text ontologyPathField;
    private static final int STYLE = SWT.NONE;

    private FormData loadFromFsFormData;
    private FormData loadOntologyFormData;
    private FormData ontologyTextFormData;
    

    public OntologyComposite(Composite parent) {
        super(parent, STYLE);
        createComponents();
        this.composite = parent;
    }
    
    
    private void addLoadFromFsButton(){
    	loadButton = new Button(this, SWT.NONE);
    	loadButton.setText(Messages.getString("OntologyComposite.loadFromFs"));
    	loadFromFsFormData = new FormData();
        Point minSize = loadButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
        loadFromFsFormData.width = Math.max(IDialogConstants.BUTTON_WIDTH, minSize.x);

        loadButton.setLayoutData(loadFromFsFormData);
        loadButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				FileDialog dialog = new FileDialog(composite.getShell());
				String result = dialog.open();
				ontologyPathField.setText(result);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
    }
    
    private void addLoadOntologyButton(){
    	loadButton = new Button(this, STYLE);
    	loadButton.setText(Messages.getString("OntologyComposite.loadOntology"));
    	loadOntologyFormData = new FormData();
        Point minSize = loadButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
        loadOntologyFormData.width = Math.max(IDialogConstants.BUTTON_WIDTH, minSize.x);

        loadButton.setLayoutData(loadOntologyFormData);
    }
    
    private void addOntologyTextField(){
    	ontologyPathField = new Text(this, STYLE);
    	ontologyTextFormData = new FormData();
    	this.ontologyTextFormData.width = 500;
    	ontologyPathField.setLayoutData(ontologyTextFormData);
    }
    
    
    private void adjustForms(){
    	this.ontologyTextFormData.left = new FormAttachment(0,5);
    	this.loadFromFsFormData.left = new FormAttachment(this.ontologyPathField, 5);
    	this.loadOntologyFormData.right = new FormAttachment(100,-5);
    }
    
    /*OntologyComposite.loadFromFs=...
	OntologyComposite.loadOntology=Load*/

    /**
     * amaumont Comment method "createComponents".
     */
    private void createComponents() {
    	GridData data = new GridData(GridData.FILL_HORIZONTAL);
    	this.setLayoutData(data);
    	
    	FormLayout formLayout = new FormLayout();
    	this.setLayout(formLayout);
        this.addLoadFromFsButton();
        this.addLoadOntologyButton();
        this.addOntologyTextField();
        this.adjustForms();
    	
    	
    	

      /*  GridData footerCompositeGridData = new GridData(GridData.FILL_HORIZONTAL);
        this.setLayoutData(footerCompositeGridData);

        FormLayout formLayout = new FormLayout();
        this.setLayout(formLayout);

        Button okButton = new Button(this, SWT.NONE);
        okButton.setText(Messages.getString("FooterComposite.0")); //$NON-NLS-1$
        FormData okFormData = new FormData();
        Point minSize = okButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
        okFormData.width = Math.max(IDialogConstants.BUTTON_WIDTH, minSize.x);

        okButton.setLayoutData(okFormData);
        okButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                if (foxManager.getUiManager().validateRootElement()) {
                    foxManager.getUiManager().closeFOX(SWT.OK);
                } else {
                    MessageDialog warningMessageDialog = new MessageDialog(composite.getShell(), Messages
                            .getString("FooterComposite.RootElementError.Title"), null, //$NON-NLS-1$
                            Messages.getString("FooterComposite.RootElementError.Message"), MessageDialog.ERROR, //$NON-NLS-1$
                            new String[] { Messages.getString("FooterComposite.0") }, 0); //$NON-NLS-1$
                    warningMessageDialog.open();
                }
            }

        });

        Button cancelButton = new Button(this, SWT.NONE);
        cancelButton.setText(Messages.getString("FooterComposite.1")); //$NON-NLS-1$
        cancelButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                foxManager.getUiManager().closeFOX(SWT.CANCEL);
            }

        });

        FormData cancelFormData = new FormData();
        minSize = cancelButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
        cancelFormData.width = Math.max(IDialogConstants.BUTTON_WIDTH, minSize.x);
        cancelButton.setLayoutData(cancelFormData);

        Button autoMapButton = new Button(this, SWT.NONE);
        // see bug 7087
        if (foxManager != null) {
            boolean canModify = foxManager.getFoxComponent().getProcess().isReadOnly();
            if (foxManager.getFoxComponent().getOriginalNode().getJobletNode() != null) {
                canModify = foxManager.getFoxComponent().isReadOnly();
            }
            if (canModify) {
                autoMapButton.setEnabled(false);
            }
        }
        autoMapButton.setToolTipText(Messages.getString("FooterComposite.AutoMapTip")); //$NON-NLS-1$
        autoMapButton.setText(Messages.getString("FooterComposite.AutoMap")); //$NON-NLS-1$
        autoMapButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                foxManager.getUiManager().autoMap();
            }

        });
        FormData autoMapFormData = new FormData();
        minSize = autoMapButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
        autoMapFormData.width = Math.max(IDialogConstants.BUTTON_WIDTH, minSize.x);
        autoMapButton.setLayoutData(autoMapFormData);

        cancelFormData.right = new FormAttachment(100, -5);
        okFormData.right = new FormAttachment(cancelButton, -5);
        autoMapFormData.left = new FormAttachment(0, 5);

        // tree operation buttons
        FormData treeNodeData = new FormData();
        MoveDownTreeNodeButton moveDown = new MoveDownTreeNodeButton(this, foxManager);
        treeNodeData.right = new FormAttachment(okButton, -5);
        moveDown.getButton().setLayoutData(treeNodeData);

        MoveUpTreeNodeButton moveUpBtn = new MoveUpTreeNodeButton(this, foxManager);
        treeNodeData = new FormData();
        treeNodeData.right = new FormAttachment(moveDown.getButton(), -5);
        moveUpBtn.getButton().setLayoutData(treeNodeData);

        RemoveTreeNodeButton removeNodeBtn = new RemoveTreeNodeButton(this, foxManager);
        treeNodeData = new FormData();
        treeNodeData.right = new FormAttachment(moveUpBtn.getButton(), -5);
        removeNodeBtn.getButton().setLayoutData(treeNodeData);

        AddTreeNodeButton addNodeBtn = new AddTreeNodeButton(this, foxManager);
        treeNodeData = new FormData();
        treeNodeData.right = new FormAttachment(removeNodeBtn.getButton(), -5);
        addNodeBtn.getButton().setLayoutData(treeNodeData);*/

    }
}
