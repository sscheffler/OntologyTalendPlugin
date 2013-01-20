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

import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.utils.NodeUtil;

import de.crawling.spider.jena.OntologyLoadComponent;
import de.crawling.spider.jena.managers.OntologyManager;
import de.crawling.spider.jena.ui.comp.LoadOntologyComposite;


/**
 * DOC bqian class global comment. Detailled comment <br/>
 * 
 * $Id: FOXUI.java,v 1.1 2007/06/12 07:20:38 gke Exp $
 * 
 */
public class OntologyUI {

    protected OntologyManager ontManager;

    private Composite foxUIParent;
    public static final int STYLE = SWT.NONE;
    protected OntologyLoadComponent externalNode;
    private LoadOntologyComposite loadComp = null;

    protected TableViewer schemaViewer;

    public OntologyUI(Composite parent, OntologyManager ontologyManager) {
        this.ontManager = ontologyManager;
        this.ontManager.getUiManager().setFoxUI(this);
        externalNode = ontologyManager.getFoxComponent();

        // add listeners.
        this.foxUIParent = parent;
        foxUIParent.setLayout(new GridLayout());
    }

    /**
     * bqian Comment method "init".
     */
    public void init() {
    	
        createContent(foxUIParent);
    }

    /**
     * Comment method "createContent".
     * 
     * @param child
     */
    private void createContent(Composite mainComposite) {
        this.loadComp = new LoadOntologyComposite(mainComposite);
        this.loadComp.setExternalNode(externalNode);
    }

    /**
     * 
     * wzhang Comment method "createCombo".
     * 
     * @param mainComposite
     */
    protected void createCombo(Composite mainComposite) {

    }

    /**
     * 
     * wzhang Comment method "getConnection".
     * 
     * @return
     */
    public IConnection getConnection() {
        List<? extends IConnection> incomingConnections = NodeUtil.getIncomingConnections(ontManager.getFoxComponent(),
                IConnectionCategory.FLOW);
        if (incomingConnections.size() > 0) {
            return incomingConnections.get(0);
        }
        return null;
    }
    
    public Composite getFoxUIParent(){
    	return this.foxUIParent;
    }
    
    
  
    
    

    
}
