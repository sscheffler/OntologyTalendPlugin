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
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NsIterator;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;


/**
 * amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: FooterComposite.java,v 1.1 2007/06/12 07:20:39 gke Exp $
 * 
 */
public class LoadOntologyComposite extends AbstractComposite {

    private Button loadOntologyButton;
    private Button loadTDBButton;
    
    private Button ontologyRadioButton;
    private Button tdbRadioButton;

    private FormData loadFromFsFormData;
    private FormData loadTDBFormData;
    
    private FormData ontologyNameSpaceFormData;
    private FormData ontologyClassFormData;
    private FormData ontologyRadioFormdata;
    private FormData tdbRadioFormData;
    private FormData tdbPathFormData;
    
    private Text tdbPath;
    private Combo ontologyClassCombo;
    private Combo nameSpaceClassCombo;
    private LoadOntologyComposite thisClass= this;
    
    private final static String[] FILTERS = {"*.rdf", "*.owl", ".*"};
    private final static String FILE_PROTOCOL = "file://";
    
    private Model defaultModel;
    private OntModel ontModel;

    public LoadOntologyComposite(Composite parent) {
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
    
    /*Listener listener = new Listener () {
		public void handleEvent (Event e) {
			Control [] children = shell.getChildren ();
			for (int i=0; i<children.length; i++) {
				Control child = children [i];
				if (e.widget != child && child instanceof Button && (child.getStyle () & SWT.TOGGLE) != 0) {
					((Button) child).setSelection (false);
				}
			}
			((Button) e.widget).setSelection (true);
		}
	};
	for (int i=0; i<20; i++) {
		Button button = new Button (shell, SWT.TOGGLE);
		button.setText ("B" + i);
		button.addListener (SWT.Selection, listener);
		if (i == 0) button.setSelection (true);
	}*/
    
    private void addRadioSelection(){
    	ontologyRadioButton = new Button(this, SWT.RADIO);
    	tdbRadioButton = new Button(this, SWT.RADIO);
    	ontologyRadioFormdata = new FormData(250,50);
    	tdbRadioFormData = new FormData(250,50);
    	
    	ontologyRadioButton.setText("Load Ontology");
    	tdbRadioButton.setText("Load TDB");
    	ontologyRadioButton.setSelection(true);
    	
    	ontologyRadioButton.setLayoutData(ontologyRadioFormdata);
        tdbRadioButton.setLayoutData(tdbRadioFormData);
        
        SelectionListener listener = new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if(ontologyRadioButton.getSelection()){
    				tdbRadioButton.setSelection(false);
    				thisClass.toggleLoadOntology(true);
    				thisClass.toggleLoadTdb(false);
    			}
    			
    			if(tdbRadioButton.getSelection()){
    				ontologyRadioButton.setSelection(false);
    				thisClass.toggleLoadOntology(false);
    				thisClass.toggleLoadTdb(true);
    			}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		};
    	ontologyRadioButton.addSelectionListener(listener);
    	tdbRadioButton.addSelectionListener(listener);
        
    }
    
    private void toggleLoadOntology(boolean toggle){
    	this.nameSpaceClassCombo.setVisible(toggle);
    	this.ontologyClassCombo.setVisible(toggle);
    	this.loadOntologyButton.setVisible(toggle);
    	
    }
    
    private void toggleLoadTdb(boolean toggle){
    	this.tdbPath.setVisible(toggle);
    	this.loadTDBButton.setVisible(toggle);
    }
    
    private void addLoadFromFsButton(){
    	loadOntologyButton = new Button(this, SWT.NONE);
    	
    	loadOntologyButton.setText(/*Messages.getString("OntologyComposite.loadFromFs")*/"Load");
    	loadFromFsFormData = new FormData();
        Point minSize = loadOntologyButton.computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
        loadFromFsFormData.width = Math.max(IDialogConstants.BUTTON_WIDTH, minSize.x);

        loadOntologyButton.setLayoutData(loadFromFsFormData);
        loadOntologyButton.addSelectionListener(new SelectionListener() {
			
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
    
    private void addTDBComponents(){
    	this.tdbPath = new Text(this, SWT.SINGLE);
    	this.loadTDBButton = new Button(this,SWT.NONE);
    	this.loadTDBButton.setText("Load TDB");
    	this.loadTDBButton.setVisible(false);
    	this.tdbPath.setVisible(false);
    	this.tdbPath.setEditable(false);
    	
    	this.tdbPathFormData = new FormData();
    	this.loadTDBFormData = new FormData();
    	this.tdbPathFormData.width=300;
    	this.loadTDBFormData.width=100;
    	
    	this.tdbPath.setLayoutData(this.tdbPathFormData);
    	this.loadTDBButton.setLayoutData(this.loadTDBFormData);
    	
    }
    
    @Override
    protected void adjustForms(){
    	this.ontologyRadioFormdata.left = new FormAttachment(0,5);
    	this.tdbRadioFormData.left = new FormAttachment(this.ontologyRadioButton,5);
    	
    	this.tdbPathFormData.top= new FormAttachment(this.ontologyRadioButton,5);
    	this.tdbPathFormData.left = new FormAttachment(0,5);
    	
    	this.loadTDBFormData.left=new FormAttachment(this.tdbPath, 5);
    	this.loadTDBFormData.top = new FormAttachment(this.ontologyRadioButton, 5);
    	
    	this.ontologyNameSpaceFormData.top= new FormAttachment(this.ontologyRadioButton,5);
    	this.ontologyClassFormData.top= new FormAttachment(this.ontologyRadioButton,5);
    	this.loadFromFsFormData.top= new FormAttachment(this.ontologyRadioButton,5);
    	
    	this.ontologyClassFormData.left = new FormAttachment(this.nameSpaceClassCombo,5);
    	this.loadFromFsFormData.left = new FormAttachment(this.ontologyClassCombo, 5);
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
    	this.addTDBComponents();
    	
    	this.addRadioSelection();
        this.addLoadFromFsButton();
        this.addOntologyCombo();
        this.addNameSpaceCombo();
        this.adjustForms();
    	
    }
}
