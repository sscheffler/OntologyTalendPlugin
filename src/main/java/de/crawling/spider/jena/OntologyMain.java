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

import java.util.List;


import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.talend.commons.ui.runtime.image.ImageUtils.ICON_SIZE;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.process.EConnectionType;
import org.talend.core.model.process.IConnection;
import org.talend.core.ui.images.CoreImageProvider;

import de.crawling.spider.jena.i18n.Messages;
import de.crawling.spider.jena.log.Log;
import de.crawling.spider.jena.managers.OntologyManager;
import de.crawling.spider.jena.ui.OntologyUI;

/**
 * This class is responsible for contacting the RowGeneratorComponent and FOXUI. <br/>
 * 
 * $Id: FOXMain.java,v 1.1 2007/06/12 07:20:38 gke Exp $
 * 
 */
public class OntologyMain {
	
	

    private boolean standAloneMode = false;

    private OntologyUI generatorUI;

    private OntologyLoadComponent connector;

    private OntologyManager ontologyManager;

    public OntologyMain(OntologyLoadComponent connector) {
    	Log.info("Starting plugin");
        this.connector = connector;
        this.ontologyManager = new OntologyManager(connector);
    }
    
    

    /**
     * create UI".
     * 
     * @param parent
     * @return
     */
    public void createUI(Composite parent) {
        generatorUI = new OntologyUI(parent, ontologyManager);
        generatorUI.init();
        
        

    }

    /**
     * qzhang Comment method "createUI".
     * 
     * @param display
     * @return
     */
    public Shell createUI(Display display) {
        Shell shell = new Shell(display, SWT.APPLICATION_MODAL | SWT.BORDER | SWT.RESIZE | SWT.CLOSE | SWT.MIN | SWT.MAX
                | SWT.TITLE);
        IComponent component = connector.getComponent();
        Image createImage = CoreImageProvider.getComponentIcon(component, ICON_SIZE.ICON_32);
        shell.setImage(createImage);
        shell.setText(connector.getUniqueName());
        Rectangle boundsRG = new Rectangle(50, 50, 800, 600);
        shell.setBounds(boundsRG);
        createUI(shell);
        if (!shell.isDisposed()) {
            shell.open();
        }
        return shell;
    }

    /**
     * Getter for standAloneMode.
     * 
     * @return the standAloneMode
     */
    public boolean isStandAloneMode() {
        return this.standAloneMode;
    }

    /**
     * Sets the standAloneMode.
     * 
     * @param standAloneMode the standAloneMode to set
     */
    public void setStandAloneMode(boolean standAloneMode) {
        this.standAloneMode = standAloneMode;
    }

    public OntologyManager getFoxManager() {
        return ontologyManager;
    }

}
