package de.crawling.spider.jena.ui.comp;

import org.eclipse.swt.widgets.Composite;

import de.crawling.spider.jena.ui.OntologyUI;

public abstract class AbstractComposite extends Composite{
	
	protected static final int STYLE = OntologyUI.STYLE;
	protected Composite composite;
	
	protected AbstractComposite(Composite comp) {
		super(comp, STYLE);
		createComponents();
        this.composite = comp;
	}

	protected abstract void createComponents();
	protected abstract void adjustForms();
}
