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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
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
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.MetadataColumn;
import org.talend.core.model.metadata.MetadataTable;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IExternalNode;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.INodeConnector;
import org.talend.designer.core.model.components.NodeConnector;
import org.talend.designer.core.ui.AbstractMultiPageTalendEditor;
import org.talend.designer.core.ui.editor.cmd.ChangeMetadataCommand;
import org.talend.designer.core.ui.editor.cmd.ConnectionCreateCommand;
import org.talend.designer.core.ui.editor.cmd.ExternalNodeChangeCommand;
import org.talend.designer.core.ui.editor.nodes.Node;
import org.talend.designer.core.ui.editor.properties.controllers.ColumnListController;
import org.talend.designer.core.ui.editor.properties.controllers.generator.ColumnListGenerator;

import ca.uhn.hl7v2.conf.spec.MetaData;

import com.hp.hpl.jena.ontology.DatatypeProperty;
import com.hp.hpl.jena.ontology.OntClass;
import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
import com.hp.hpl.jena.ontology.OntResource;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.NsIterator;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.tdb.TDBFactory;
import com.hp.hpl.jena.util.iterator.ExtendedIterator;

import de.crawling.spider.jena.OntologyLoadComponent;
import de.crawling.spider.jena.commands.SetMetaDataCommand;


/**
 * amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: FooterComposite.java,v 1.1 2007/06/12 07:20:39 gke Exp $
 * 
 */
public class LoadOntologyComposite extends AbstractComposite {

    private Button loadOntologyButton;
    private Button loadTDBButton;
    private Button finishButton;
    
    private Button ontologyRadioButton;
    private Button tdbRadioButton;

    private FormData loadFromFsFormData;
    private FormData loadTDBFormData;
    
    private FormData ontologyNameSpaceFormData;
    private FormData ontologyClassFormData;
    private FormData finishFormData;
    
    private FormData ontologyRadioFormdata;
    private FormData tdbRadioFormData;
    private FormData tdbPathFormData;
    private FormData propertyTableFormData;
    
    private Table propertyTable;
    private Text tdbPath;
    
    private Combo ontologyClassCombo;
    private Combo nameSpaceClassCombo;
    private LoadOntologyComposite thisClass= this;
    
    private final static String[] FILTERS = {"*.rdf", "*.owl", ".*"};
    private final static String FILE_PROTOCOL = "file://";
    
    private Model defaultModel;
    private OntModel ontModel;
    
    private String pathString="";
    private Composite parent = null;
    
    private OntologyLoadComponent externalNode = null;

    public LoadOntologyComposite(Composite parent) {
        super(parent);
        this.parent = parent;
//        createComponents();
//        this.composite = parent;
        defaultModel= ModelFactory.createDefaultModel();
    }
    
    public void setExternalNode(OntologyLoadComponent node){
    	this.externalNode = node;
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
    
    
    
    private void addRadioSelection(){
    	ontologyRadioButton = new Button(this, SWT.RADIO);
    	tdbRadioButton = new Button(this, SWT.RADIO);
    	ontologyRadioFormdata = new FormData(250,50);
    	tdbRadioFormData = new FormData(250,50);
    	
    	ontologyRadioButton.setText("Load Ontology");
    	tdbRadioButton.setText("Load TDB");
//    	ontologyRadioButton.to.setSelection(true);
    	tdbRadioButton.setSelection(true);
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
    	/*this.nameSpaceClassCombo.setVisible(toggle);
    	this.ontologyClassCombo.setVisible(toggle);*/
    	this.loadOntologyButton.setVisible(toggle);
    	
    }
    
    private void toggleLoadTdb(boolean toggle){
    	//this.tdbPath.setVisible(toggle);
    	this.loadTDBButton.setVisible(toggle);
    }
    
    private void addLoadFromFsButton(){
    	loadOntologyButton = new Button(this, SWT.NONE);
    	
    	loadOntologyButton.setText(/*Messages.getString("OntologyComposite.loadFromFs")*/"Load");
    	loadOntologyButton.setVisible(false);
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
    
    private void addPropertyTable(){
    	String [] titles={"Property", "Schema"};
    	propertyTable = new Table(this, SWT.MULTI | SWT.BORDER | SWT.FULL_SELECTION);
    	propertyTable.setEnabled(false);
    	propertyTable.setLinesVisible(true);
    	propertyTable.setHeaderVisible(true);
    	
    	propertyTableFormData = new FormData(600,300);
    	
    	propertyTable.setLayoutData(propertyTableFormData);
    	
    	for (int i=0; i<titles.length; i++) {
    		TableColumn column = new TableColumn (propertyTable, SWT.NONE);
    		column.setText (titles [i]);
    	}
    }
    
    private void addOntologyCombo(){
    	this.ontologyClassCombo = new Combo(this,STYLE);
    	this.ontologyClassCombo.setEnabled(false);
    	this.ontologyClassFormData = new FormData();
    	this.ontologyClassFormData.width = 300;
    	this.ontologyClassCombo.setVisibleItemCount(10);
    	ontologyClassCombo.setLayoutData(ontologyClassFormData);
    	
    	this.ontologyClassCombo.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				propertyTable.removeAll();
				
				int nsIndex = nameSpaceClassCombo.getSelectionIndex();
				int ontIndex = ontologyClassCombo.getSelectionIndex();
				
				String nsItem = nameSpaceClassCombo.getItem(nsIndex);
				String ontItem = ontologyClassCombo.getItem(ontIndex);
				String itemName = nsItem+ontItem;
				
				TableItem item1 = new TableItem(propertyTable, SWT.NONE);
				
				OntResource res = ontModel.getOntResource(itemName);
				StmtIterator it = res.listProperties();
				ExtendedIterator<DatatypeProperty> dataProps = ontModel.listDatatypeProperties();
				
				while(dataProps.hasNext()){
					DatatypeProperty p = dataProps.next();
					if(("".equals(p.getLocalName().trim()))||p.getLocalName()==null){
						continue;
					}
					
					
					ExtendedIterator<? extends OntResource> resources = p.listDomain();
					
					while(resources.hasNext()){
						OntResource checkRes = resources.next();
						if(res.equals(checkRes)){
							TableEditor editor = new TableEditor(propertyTable);
							editor.horizontalAlignment=SWT.LEFT;
							
							TableItem item = new TableItem(propertyTable, SWT.NONE);
							item.setText(0, p.getLocalName());
							Button b = new Button(propertyTable, SWT.CHECK);
							editor.minimumWidth = b.getSize ().x;
							b.pack();
							editor.setEditor(b, item, 1);
							
						}
					};
				}
				
				propertyTable.setEnabled(true);
				propertyTable.getColumn(0).pack();
				propertyTable.getColumn(1).pack();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
    }
    
    private void addTDBComponents(){
    	this.tdbPath = new Text(this, SWT.SINGLE);
    	this.loadTDBButton = new Button(this,SWT.NONE);
    	this.loadTDBButton.setText("Load TDB");
    	this.loadTDBButton.setVisible(true);
    	this.tdbPath.setVisible(false);
    	this.tdbPath.setEditable(false);
    	
    	this.tdbPathFormData = new FormData();
    	this.loadTDBFormData = new FormData();
    	this.tdbPathFormData.width=300;
    	this.loadTDBFormData.width=100;
    	
    	this.tdbPath.setLayoutData(this.tdbPathFormData);
    	this.loadTDBButton.setLayoutData(this.loadTDBFormData);
    	
    	loadTDBButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DirectoryDialog dialog = new DirectoryDialog(composite.getShell());
				if(!pathString.equals(""))dialog.setFilterPath(pathString);
				
				String result = dialog.open();
				pathString = result;
				
				if(result != null){
					defaultModel = TDBFactory.createDataset(result).getDefaultModel();
					ontModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_DL_MEM_RDFS_INF, defaultModel);
					fillNameSpaceList();
				}
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
    	
    }
    
    private void addFinishButton(){
    	this.finishButton = new Button(this, SWT.NONE);
    	this.finishFormData = new FormData();
    	this.finishButton.setText("finish");
    	this.finishButton.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {				
				SetMetaDataCommand cl= new SetMetaDataCommand(externalNode, propertyTable.getItems());
				cl.execute();

				
				
//				ChangeMetadataCommand cmd = new ChangeMetadataCommand(externalNode, null, null, t);
//				cmd.execute();
			//	 IWorkbenchPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

		            /*if (externalNode != null && (part instanceof AbstractMultiPageTalendEditor)) {
		                INode node = externalNode.getOriginalNode();
		                if (node != null && node instanceof Node) {
		                    Command cmd = new ExternalNodeChangeCommand((Node) node, externalNode);
		                    CommandStack cmdStack = (CommandStack) part.getAdapter(CommandStack.class);
		                    cmdStack.execute(cmd);
		                }}*/
				
				parent.setVisible(false);
				try {
					throw new Exception("test");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});
    	this.finishButton.setLayoutData(this.finishFormData);
    }
    
    @Override
    protected void adjustForms(){
    	this.ontologyRadioFormdata.left = new FormAttachment(0,5);
    	this.tdbRadioFormData.left = new FormAttachment(this.ontologyRadioButton,5);
    	
    	/*this.tdbPathFormData.top= new FormAttachment(this.ontologyRadioButton,5);
    	this.tdbPathFormData.left = new FormAttachment(0,5);*/
    	
    	this.loadTDBFormData.left=new FormAttachment(this.ontologyClassCombo, 5);
    	this.loadTDBFormData.top = new FormAttachment(this.ontologyRadioButton, 5);
    	
    	this.ontologyNameSpaceFormData.top= new FormAttachment(this.ontologyRadioButton,5);
    	this.ontologyClassFormData.top= new FormAttachment(this.ontologyRadioButton,5);
    	this.loadFromFsFormData.top= new FormAttachment(this.ontologyRadioButton,5);
    	
    	this.ontologyClassFormData.left = new FormAttachment(this.nameSpaceClassCombo,5);
    	this.loadFromFsFormData.left = new FormAttachment(this.ontologyClassCombo, 5);
    	
    	this.propertyTableFormData.top = new FormAttachment(this.nameSpaceClassCombo, 5);
    	this.finishFormData.top=new FormAttachment(this.propertyTable, 5);
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
    	
    	this.addPropertyTable();
    	this.addRadioSelection();
        this.addLoadFromFsButton();
        this.addOntologyCombo();
        this.addNameSpaceCombo();
        this.addFinishButton();
        this.addRadioSelection();
        this.adjustForms();
    	
    }
}
