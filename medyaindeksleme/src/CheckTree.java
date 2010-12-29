import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

public class CheckTree extends JTree {
    /**
	 * 
	 */
	private static final long serialVersionUID = -6786413930969879954L;
	CheckNode[] nodes;
    
	public CheckTree() {
		//nodes = createTreeNodes();
		//super(createTreeNodes()[0]);
		
		File de = new File(MainFrame.INDEXDIR);
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		model.setRoot(createTreeNodes(de));
		//this.getComponentCount();
		this.setCellRenderer(new CheckRenderer());
	    this.getSelectionModel().setSelectionMode(
	      TreeSelectionModel.SINGLE_TREE_SELECTION
	    );
	    this.putClientProperty("JTree.lineStyle", "Angled");
	    this.addMouseListener(new NodeSelectionListener(this));
	    
	}
	public void setroot(String root){
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		File de = new File(MainFrame.INDEXDIR);
		model.setRoot(createTreeNodes(de));
		this.repaint();
	}
	
	public List<String> getSelectedList(){
		List<String> selectedList= new ArrayList<String>();
		CheckNode root = (CheckNode) this.getModel().getRoot();
		@SuppressWarnings("rawtypes")
		Enumeration e = root.breadthFirstEnumeration();
	    while (e.hasMoreElements()) {
	    	CheckNode node = (CheckNode) e.nextElement();
	    	if (node.isSelected()) {
	    		TreeNode[] nodes = node.getPath();
	    		String path = "";
	    		path = MainFrame.INDEXDIR;
	    		if (path.charAt(path.length()-1) ==  File.separatorChar)
	    			path = path.substring(0, path.length()-2);
	    		for (int i = 1; i < nodes.length; i++) {
	    			path += File.separatorChar + nodes[i].toString();
	    		}
	    		selectedList.add(path);
	    	}
	    }
	    return selectedList;
	}

	private CheckNode createTreeNodes(File eklenenKlasor) {
		CheckNode nodes = new CheckNode(eklenenKlasor.getName());
				File[] files = eklenenKlasor.listFiles();


	  
			 for(int i=0; i < files.length; i++){
				 //if the file is directory, call the function recursively
					 if(files[i].isDirectory()){
						 nodes.add(createTreeNodes(files[i]));
						 continue;
					 }else{
						 nodes.add(new CheckNode(files[i].getName()));
					 }
			 }
			 
		return nodes;
	}
}
