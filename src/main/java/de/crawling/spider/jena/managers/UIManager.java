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
package de.crawling.spider.jena.managers;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.repository.ui.wizards.metadata.connection.files.xml.treeNode.Element;
import org.talend.repository.ui.wizards.metadata.connection.files.xml.treeNode.FOXTreeNode;

import de.crawling.spider.jena.ui.OntologyUI;

/**
 * UI Manager<br/>
 * 
 * $Id: UIManager.java,v 1.1 2007/06/12 07:20:38 gke Exp $
 * 
 */
public class UIManager {

    protected OntologyUI ontologyUI;

    private int uiResponse = SWT.NONE;

    protected OntologyManager ontologyManager;

    /**
     * UIManager constructor .
     * 
     * @param foxManager
     */
    public UIManager(OntologyManager ontologyManager) {
        this.ontologyManager = ontologyManager;
    }

    /**
     * Getter for foxManager.
     * 
     * @return the foxManager
     */
    public OntologyManager getOntologyManager() {
        return this.ontologyManager;
    }

    /**
     * Sets the foxManager.
     * 
     * @param foxManager the foxManager to set
     */
    public void setFoxManager(OntologyManager foxManager) {
        this.ontologyManager = foxManager;
    }

    /**
     * Getter for foxUI.
     * 
     * @return the foxUI
     */
    public OntologyUI getFoxUI() {
        return this.ontologyUI;
    }

    /**
     * Sets the foxUI.
     * 
     * @param foxUI the foxUI to set
     */
    public void setFoxUI(OntologyUI foxUI) {
        this.ontologyUI = foxUI;
    }

    /**
     * Getter for uiResponse.
     * 
     * @return the uiResponse
     */
    public int getUiResponse() {
        return this.uiResponse;
    }

    /**
     * Sets the uiResponse.
     * 
     * @param uiResponse the uiResponse to set
     */
    public void setUiResponse(int uiResponse) {
        this.uiResponse = uiResponse;
    }

    /**
     * DOC qiang.zhang Comment method "closeFOX".
     * 
     * @param reponse
     */
    public void closeOntology(int response) {

        Composite parent = ontologyUI.getFoxUIParent();
        if (parent instanceof Shell) {
            ((Shell) parent).close();
        }

    }

    

}
