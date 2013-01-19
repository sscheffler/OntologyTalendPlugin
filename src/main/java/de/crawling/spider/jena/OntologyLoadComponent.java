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
package de.crawling.spider.jena;

import java.beans.PropertyChangeSupport;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.talend.commons.exception.SystemException;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.commons.utils.system.EnvironmentUtils;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.components.IODataComponent;
import org.talend.core.model.metadata.ColumnNameChanged;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.MetadataColumn;
import org.talend.core.model.process.AbstractExternalNode;
import org.talend.core.model.process.EParameterFieldType;
import org.talend.core.model.process.IComponentDocumentation;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IDataConnection;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.IExternalData;
import org.talend.core.model.process.INode;
import org.talend.core.model.temp.ECodePart;
import org.talend.designer.codegen.ICodeGeneratorService;
import org.talend.repository.ui.utils.DataConnection;

/**
 * DOC bqian class global comment. Detailled comment <br/>
 * 
 * $Id: FileOutputXMLComponent.java,v 1.1 2007/06/12 07:20:37 gke Exp $
 * 
 */
public class OntologyLoadComponent extends AbstractExternalNode {

    public static final String ROOT = "ROOT"; //$NON-NLS-1$

    public static final String GROUP = "GROUP"; //$NON-NLS-1$

    public static final String LOOP = "LOOP"; //$NON-NLS-1$

    public static final String PATH = "PATH"; //$NON-NLS-1$

    public static final String VALUE = "VALUE"; //$NON-NLS-1$

    public static final String TYPE = "TYPE"; //$NON-NLS-1$

    public static final String COLUMN = "COLUMN"; //$NON-NLS-1$

    public static final String ATTRIBUTE = "ATTRIBUTE"; //$NON-NLS-1$s

    public static final String ORDER = "ORDER"; //$NON-NLS-1$

    private OntologyMain foxmain;
    
    protected PropertyChangeSupport listeners = new PropertyChangeSupport(this);

    public OntologyLoadComponent() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IExternalNode#initialize()
     */
    public void initialize() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IExternalNode#getComponentDocumentation(java.lang.String, java.lang.String)
     */
    public IComponentDocumentation getComponentDocumentation(String componentName, String tempFolderPath) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IExternalNode#getExternalData()
     */
    public IExternalData getExternalData() {
    	
    	
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IExternalNode#open(org.eclipse.swt.widgets.Composite)
     */
    public int open(Composite parent) {
    	int i = open(parent.getDisplay());
        return i;
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IExternalNode#open(org.eclipse.swt.widgets.Display)
     */
    public int open(Display display) {
        foxmain = new OntologyMain(this);
        Shell shell = foxmain.createUI(display);
        Exception e;
        List<Map<String,String>> l=(List<Map>String,String>>)ElementParameterParser.getObjectValue("__PARAMS__");
        StringBuilder sb = new StringBuilder();
        for( int i= 0;i<l.size().size();i++){
        	Map<String, String> m = l.get(i);
        	sb.append(m.get("COMMAND"));
        	sb.append(" ");
        }
        Process p = new ProcessBuilder("").start();
        p.
        IConnection c = this.getOutgoingConnections().get(0);
        
        IElementParameter ele = this.getElementParameter("SCHEMA");
        
        String s = this.getMetadataList().size()+" - ";
        Map<String, IElementParameter> map = ele.getChildParameters();
        IElementParameter type = map.get("SCHEMA_TYPE");
        
        IMetadataColumn col = this.getMetadataTable().getListColumns().get(1);
        MetadataColumn ownCol = new MetadataColumn();
        ownCol.setLabel("freakstyle");
        this.getMetadataTable().getListColumns().add(ownCol);
//        this.getComponent().
        
     Map<String, Object> obMap = new HashMap<String, Object>();
     obMap.put("SCHEMA", type);
//        this.reloadComponent(this.getComponent(), obMap);
     listeners.firePropertyChange("SCHEMA", null, type);
        
        c.setName(""+this.getMetadataTable().getListColumns().get(3).getLabel()+this.getMetadataTable().getListColumns().size());
        
        
//        c.setName(this.getJobletNode().getElementName());
        
        
//        col.setLabel("wehaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaabäääääääääääääääääääääänhungr");
        /*for(IMetadataTable e:type.ge){
        	s = s+e.getComment()+" - ";
        }*/
        
//        c.setName(col.getLabel()+col.getComment());
        
        
        
        
        
        
        while (!shell.isDisposed()) {
            try {
                if (!display.readAndDispatch()) {
                    display.sleep();
                }
            } catch (Throwable e) {
                if (foxmain.isStandAloneMode()) {
                    e.printStackTrace();
                } else {
                    ExceptionHandler.process(e);
                }
            }
        }
        if (foxmain.isStandAloneMode()) {
            display.dispose();
        }
        return foxmain.getFoxManager().getUiManager().getUiResponse();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IExternalNode#setExternalData(org.talend.core.model.process.IExternalData)
     */
    public void setExternalData(IExternalData persistentData) {
        // do nothing here
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.AbstractExternalNode#renameMetadataColumnName(java.lang.String,
     * java.lang.String, java.lang.String)
     */
    @Override
    protected void renameMetadataColumnName(String conectionName, String oldColumnName, String newColumnName) {
    }

    @Override
    public void metadataInputChanged(IODataComponent dataComponent, String connectionToApply) {
        List<Map<String, String>> listRoot = (List<Map<String, String>>) this.getElementParameter(ROOT).getValue();
        boolean flagRoot = false;

        List<Map<String, String>> listGroup = (List<Map<String, String>>) this.getElementParameter(GROUP).getValue();
        boolean flagGroup = false;

        List<Map<String, String>> listLoop = (List<Map<String, String>>) this.getElementParameter(LOOP).getValue();
        boolean flagLoop = false;

        // add by wzhang. column show with schema name added for mutiSchema
        String schemaId = ""; //$NON-NLS-1$
        if (istFileOutputMSXML()) {
            schemaId = dataComponent.getConnection().getMetadataTable().getLabel() + ":"; //$NON-NLS-1$
        }

        for (ColumnNameChanged col : dataComponent.getColumnNameChanged()) {
            for (Map<String, String> map : listRoot) {
                if (map.get(COLUMN).equals(schemaId + col.getOldName())) {
                    map.put(COLUMN, schemaId + col.getNewName());
                    flagRoot = true;
                }
            }
            for (Map<String, String> map : listGroup) {
                if (map.get(COLUMN).equals(schemaId + col.getOldName())) {
                    map.put(COLUMN, schemaId + col.getNewName());
                    flagGroup = true;
                }
            }
            for (Map<String, String> map : listLoop) {
                if (map.get(COLUMN).equals(schemaId + col.getOldName())) {
                    map.put(COLUMN, schemaId + col.getNewName());
                    flagLoop = true;
                }
            }
        }

        if (flagRoot) {
            this.getElementParameter(ROOT).setValue(listRoot);
        }
        if (flagGroup) {
            this.getElementParameter(GROUP).setValue(listGroup);
        }
        if (flagLoop) {
            this.getElementParameter(LOOP).setValue(listLoop);
        }
    }

    @Override
    public void metadataOutputChanged(IODataComponent dataComponent, String connectionToApply) {
        List<Map<String, String>> listRoot = (List<Map<String, String>>) this.getElementParameter(ROOT).getValue();
        boolean flagRoot = false;

        List<Map<String, String>> listGroup = (List<Map<String, String>>) this.getElementParameter(GROUP).getValue();
        boolean flagGroup = false;

        List<Map<String, String>> listLoop = (List<Map<String, String>>) this.getElementParameter(LOOP).getValue();
        boolean flagLoop = false;

        // add by wzhang. column show with schema name added for mutiSchema
        String schemaId = ""; //$NON-NLS-1$
        if (istFileOutputMSXML()) {
            return;
        }
        for (ColumnNameChanged col : dataComponent.getColumnNameChanged()) {
            for (Map<String, String> map : listRoot) {
                if (map.get(COLUMN).equals(col.getOldName())) {
                    map.put(COLUMN, col.getNewName());
                    flagRoot = true;
                }
            }
            for (Map<String, String> map : listGroup) {
                if (map.get(COLUMN).equals(col.getOldName())) {
                    map.put(COLUMN, col.getNewName());
                    flagGroup = true;
                }
            }
            for (Map<String, String> map : listLoop) {
                if (map.get(COLUMN).equals(col.getOldName())) {
                    map.put(COLUMN, col.getNewName());
                    flagLoop = true;
                }
            }
        }

        if (flagRoot) {
            this.getElementParameter(ROOT).setValue(listRoot);
        }
        if (flagGroup) {
            this.getElementParameter(GROUP).setValue(listGroup);
        }
        if (flagLoop) {
            this.getElementParameter(LOOP).setValue(listLoop);
        }
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public boolean setTableElementParameter(List<Map<String, String>> epsl, String paraName) {
        List<IElementParameter> eps = (List<IElementParameter>) this.getElementParameters();
        boolean result = true;
        for (int i = 0; i < eps.size(); i++) {
            IElementParameter parameter = eps.get(i);
            if (parameter.getFieldType() == EParameterFieldType.TABLE && parameter.getName().equals(paraName)) {
                List<Map<String, String>> newValues = new ArrayList<Map<String, String>>();
                for (Map<String, String> map : epsl) {
                    Map<String, String> newMap = new HashMap<String, String>();
                    newMap.putAll(map);
                    newValues.add(newMap);
                }
                // List<Map<String, String>> oldValues = (List<Map<String, String>>) parameter.getValue();
                //
                // if (oldValues.size() != newValues.size()) {
                // result = true;
                // } else {
                // for (int k = 0; k < oldValues.size(); k++) {
                // if (!oldValues.get(k).get(COLUMN).equals(newValues.get(k).get(COLUMN))) {
                // result = true;
                // break;
                // }
                // if (!oldValues.get(k).get(ATTRIBUTE).equals(newValues.get(k).get(ATTRIBUTE))) {
                // result = true;
                // break;
                // }
                // if (!oldValues.get(k).get(PATH).equals(newValues.get(k).get(PATH))) {
                // result = true;
                // break;
                // }
                // if (!oldValues.get(k).get(VALUE).equals(newValues.get(k).get(VALUE))) {
                // result = true;
                // break;
                // }
                // }
                // }

                if (result) {
                    parameter.setValue(newValues);
                }
                break;
            }
        }
        return result;
    }

    @SuppressWarnings("unchecked")//$NON-NLS-1$
    public List<Map<String, String>> getTableList(String paraName) {
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        List<IElementParameter> eps = (List<IElementParameter>) this.getElementParameters();
        if (eps == null) {
            return list;
        }
        for (int i = 0; i < eps.size(); i++) {
            IElementParameter parameter = eps.get(i);
            if (parameter.getFieldType() == EParameterFieldType.TABLE && parameter.getName().equals(paraName)) {
                list = (List<Map<String, String>>) parameter.getValue();
                break;
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.INode#getGeneratedCode()
     */
    public String getGeneratedCode() {
        try {
            ICodeGeneratorService service = (ICodeGeneratorService) GlobalServiceRegister.getDefault().getService(
                    ICodeGeneratorService.class);

            return service.createCodeGenerator().generateComponentCode(this, ECodePart.MAIN);
        } catch (SystemException e) {
            ExceptionHandler.process(e);
        }
        return ""; //$NON-NLS-1$
    }

    public void renameInputConnection(String oldName, String newName) {

    }

    public void renameOutputConnection(String oldName, String newName) {

    }

    /**
     * 
     * wzhang Comment method "istWriteXMLField".
     * 
     * @return
     */
    public boolean istWriteXMLField() {
        return getComponent().getName().equals("tWriteXMLField") || getComponent().getName().equals("tBRMS"); //$NON-NLS-1$
    }

    /**
     * 
     * wzhangComment method "isMDMOutput".
     * 
     * @return
     */
    public boolean istMDMOutput() {
        return getComponent().getName().equals("tMDMOutput"); //$NON-NLS-1$
    }

    /**
     * 
     * wzhang Comment method "istFileOutputMSXML".
     * 
     * @return
     */
    public boolean istFileOutputMSXML() {
        return getComponent().getName().equals("tFileOutputMSXML") || getComponent().getName().equals("tInGESTCoreXMLOutput"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /**
     * DOC ycbai Comment method "istWriteJSONField".
     * 
     * @return
     */
    public boolean istWriteJSONField() {
        return getComponent().getName().equals("tWriteJSONField"); //$NON-NLS-1$
    }

    /**
     * DOC gke Comment method "getMetadataTable".
     * 
     * @return
     */
    public IMetadataTable getMetadataTable() {
        try {
            return getMetadataList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.process.IExternalNode#getTMapExternalData()
     */
    public IExternalData getTMapExternalData() {
        // TODO Auto-generated method stub
        return null;
    }

}
