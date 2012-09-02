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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.MetadataTable;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IConnectionCategory;
import org.talend.core.model.utils.NodeUtil;
import org.talend.repository.ui.wizards.metadata.connection.files.xml.treeNode.Attribute;
import org.talend.repository.ui.wizards.metadata.connection.files.xml.treeNode.Element;
import org.talend.repository.ui.wizards.metadata.connection.files.xml.treeNode.FOXTreeNode;
import org.talend.repository.ui.wizards.metadata.connection.files.xml.treeNode.NameSpaceNode;
import org.talend.repository.ui.wizards.metadata.connection.files.xml.util.TreeUtil;

import de.crawling.spider.jena.OntologyLoadComponent;

/**
 * DOC ke class global comment. Detailled comment <br/>
 * 
 */
public class OntologyManager {

    protected OntologyLoadComponent ontologyComponent;

    protected UIManager uiManager;

    // add by wzhang. for multifoxmanager to record all schema
    protected Map<String, List<FOXTreeNode>> contents = new HashMap<String, List<FOXTreeNode>>();

    protected String currentSchema;

    protected int order = 1;

    protected Map<String, Integer> orderMap = new HashMap<String, Integer>();

    /**
     * 
     * wzhang Comment method "getCurrentSchema".
     * 
     * @return
     */
    public String getCurrentSchema() {
        return this.currentSchema;
    }

    /**
     * 
     * wzhang Comment method "setCurrentSchema".
     * 
     * @param currentSchema
     */
    public void setCurrentSchema(String currentSchema) {
        this.currentSchema = currentSchema;
    }

    /**
     * constructor.
     */
    public OntologyManager(OntologyLoadComponent ontologyComponent) {
        this.ontologyComponent = ontologyComponent;
        this.uiManager = new UIManager(this);
        initModel();
    }

    /**
     * Getter for k.
     * 
     * @return the foxComponent
     */
    public OntologyLoadComponent getFoxComponent() {
        return this.ontologyComponent;
    }

    /**
     * Sets the foxComponent.
     * 
     * @param foxComponent the foxComponent to set
     */
    public void setOntologyComponent(OntologyLoadComponent ontologyComponent) {
        this.ontologyComponent = ontologyComponent;
    }

    /**
     * Getter for uiManager.
     * 
     * @return the uiManager
     */
    public UIManager getUiManager() {
        return this.uiManager;
    }

    /**
     * Sets the uiManager.
     * 
     * @param uiManager the uiManager to set
     */
    public void setUiManager(UIManager uiManager) {
        this.uiManager = uiManager;
    }

    public void initModel() {
        IMetadataTable metadataTable = ontologyComponent.getMetadataTable();
        if (metadataTable == null) {
            metadataTable = new MetadataTable();
        }
        IConnection inConn = null;
        for (IConnection conn : ontologyComponent.getIncomingConnections()) {
            if ((conn.getLineStyle().equals(EConnectionType.FLOW_MAIN)) || (conn.getLineStyle().equals(EConnectionType.FLOW_REF))) {
                inConn = conn;
                break;
            }
        }
        if (inConn != null) {
            metadataTable = inConn.getMetadataTable();
        }
    }
}
