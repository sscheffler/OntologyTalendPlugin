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
package de.crawling.spider.jena.ui.comp;




import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Sash;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NsIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import de.crawling.spider.jena.i18n.Messages;


/**
 * amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: FooterComposite.java,v 1.1 2007/06/12 07:20:39 gke Exp $
 * 
 */
public class ShowOntologyClassComposite extends AbstractComposite {

    private Button loadButton;

    private Sash sash;
    
    private FormData loadFromFsFormData;
    private FormData ontologyNameSpaceFormData;
    private FormData ontologyClassFormData;
    private FormData sashFormData;
    
    private Combo ontologyClassCombo;
    private Combo nameSpaceClassCombo;
    
    private final static String[] FILTERS = {"*.rdf", "*.owl", ".*"};
    private final static String FILE_PROTOCOL = "file://";
    
    private Model defaultModel;
    private OntModel ontModel;

    public ShowOntologyClassComposite(Composite parent) {
        super(parent);
//        createComponents();
//        this.composite = parent;
        defaultModel= ModelFactory.createDefaultModel();
    }
    
    private void fillNameSpaceList() {
    	
    	nameSpaceClassCombo.removeAll();
    	NsIterator it = ontModel.listNameSpaces();
    	while(it.hasNext()){
    		String space = it.nextNs();
    		nameSpaceClassCombo.add(space);
    	}
    	
    	if(nameSpaceClassCombo.getItemCount() >0){
    		nameSpaceClassCombo.setEnabled(true);
    		nameSpaceClassCombo.select(0);
    	}
    	else{
    		nameSpaceClassCombo.setEnabled(false);
    	}
    }
    
    private void fillOntologyList(String ns){
    	
    	ontologyClassCombo.removeAll();
		ExtendedIterator<OntClass> it  =ontModel.listNamedClasses();
		
		while(it.hasNext()){
			OntClass ontClass = it.next();
			
			
			if(ontClass.getURI().startsWith(ns)){
				ontologyClassCombo.add(ontClass.getLocalName());
			}
		}
		
		if(ontologyClassCombo.getItemCount() >0){
    		ontologyClassCombo.setEnabled(true);
    		ontologyClassCombo.select(0);
    	}
    	else{
    		ontologyClassCombo.setEnabled(false);
    	}
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
				dialog.setFilterExtensions(FILTERS);
				String result = dialog.open();
				if(result != null){
					defaultModel.read(FILE_PROTOCOL+result);
					ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF, defaultModel);
					
					fillNameSpaceList();
					nameSpaceClassCombo.setEnabled(true);
					
					fillOntologyList(nameSpaceClassCombo.getItem(0));
					
					
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
    }
    
    private void addNameSpaceCombo(){
    	nameSpaceClassCombo = new Combo(this, STYLE);
    	nameSpaceClassCombo.setEnabled(false);
    	ontologyNameSpaceFormData = new FormData();
    	this.ontologyNameSpaceFormData.width = 400;
    	nameSpaceClassCombo.setLayoutData(ontologyNameSpaceFormData);
    	nameSpaceClassCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				int index = nameSpaceClassCombo.getSelectionIndex();
				String item = nameSpaceClassCombo.getItem(index);
				fillOntologyList(item);
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
    }
    
    private void addOntologyCombo(){
    	this.ontologyClassCombo = new Combo(this,STYLE);
    	this.ontologyClassCombo.setEnabled(false);
    	this.ontologyClassFormData = new FormData();
    	this.ontologyClassFormData.width = 300;
    	this.ontologyClassCombo.setVisibleItemCount(10);
    	ontologyClassCombo.setLayoutData(ontologyClassFormData);
    }
    
    private void addSash(){
    	this.sash = new Sash(this,SWT.BORDER | SWT.VERTICAL);
    	Rectangle rect = this.getClientArea();
    	sash.setBounds(180, 500, 32, 300);
    	this.sashFormData = new FormData();
    	
    }
    
    @Override
    protected void adjustForms(){
    	/*this.ontologyNameSpaceFormData.left= new FormAttachment(0,5);
    	this.ontologyClassFormData.left = new FormAttachment(this.nameSpaceClassCombo,5);*/
    	this.sashFormData.left = new FormAttachment (50, 0);
    	this.sashFormData.top = new FormAttachment(0, 0);
    	this.sashFormData.bottom = new FormAttachment(100,0);
    	
    	sash.setLayoutData(sashFormData);
    	
    	
    	
    	loadFromFsFormData.left = new FormAttachment (sash, 0);
    	loadFromFsFormData.right = new FormAttachment (100, 0);
    	loadFromFsFormData.top = new FormAttachment (0, 0);
    	loadFromFsFormData.bottom = new FormAttachment (100, 0);
    	
    }
    

    /**
     * amaumont Comment method "createComponents".
     */
    @Override
    protected void createComponents() {
    	GridData data = new GridData(GridData.FILL_HORIZONTAL);
    	
    	this.setLayoutData(data);
    	FormLayout formLayout = new FormLayout();
    	
    	this.setLayout(formLayout);
    	this.addSash();
        this.addLoadFromFsButton();
        
        /*this.addOntologyCombo();
        this.addNameSpaceCombo();*/
        this.adjustForms();
    	
    }
}
