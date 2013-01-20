package de.crawling.spider.jena.commands;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.TableItem;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.core.model.metadata.IMetadataTable;
import org.talend.core.model.metadata.MetadataColumn;
import org.talend.core.model.process.IConnection;

import de.crawling.spider.jena.OntologyLoadComponent;

public class SetMetaDataCommand extends Command{
	
	private OntologyLoadComponent externalNode ;
	private TableItem[] items;

	public SetMetaDataCommand(OntologyLoadComponent comp, TableItem[] items) {
		this.externalNode=comp;
		this.items  =items;
	}
	
	@Override
	public void execute(){
		IConnection c = externalNode.getOutgoingConnections().get(0);
   
	    IMetadataTable t = c.getMetadataTable();
	    List<IMetadataColumn>  columns=new ArrayList<IMetadataColumn>(); 
    	for(TableItem item : items){
	    	MetadataColumn ownCol = new MetadataColumn();
	    	ownCol.setLabel(item.getText(0));
	    	ownCol.setComment(c.getConnectorName());
	        ownCol.setTalendType("id_String");
	        ownCol.setNullable(true);
	        ownCol.setType("String");
	        columns.add(ownCol);
    	}
    	t.setListColumns(columns);
	        
	        /*externalNode.setPropertyValue(EParameterName.UPDATE_COMPONENTS.getName(), Boolean.TRUE);
	        ((Process) externalNode.getProcess()).checkProcess();
	        
	        IWorkbenchPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();

            if (externalNode != null && (part instanceof AbstractMultiPageTalendEditor)) {
                INode node = externalNode.getOriginalNode();
                if (node != null && node instanceof Node) {
                    Command cmd = new ExternalNodeChangeCommand((Node) node, externalNode);
                    CommandStack cmdStack = (CommandStack) part.getAdapter(CommandStack.class);
                    cmdStack.execute(cmd);
                }
            }*/
	        
	        
	        
	    //}
		
	}

}
