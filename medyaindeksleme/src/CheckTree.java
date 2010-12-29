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
		DefaultTreeModel model = (DefaultTreeModel) this.getModel();
		System.out.println("root is :"+model.getRoot());
		model.setRoot(createTreeNodes()[0]);
		System.out.println("root is :"+model.getRoot()+ " count : "+model.getChildCount(model.getRoot()));
		System.out.println("count"+this.getComponentCount());		
		//this.getComponentCount();
		this.setCellRenderer(new CheckRenderer());
	    this.getSelectionModel().setSelectionMode(
	      TreeSelectionModel.SINGLE_TREE_SELECTION
	    );
	    this.putClientProperty("JTree.lineStyle", "Angled");
	    this.addMouseListener(new NodeSelectionListener(this));
	    
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
	    		selectedList.add(nodes[0].toString());
	    		for (int i = 1; i < nodes.length; i++) {
	    			selectedList.add(nodes[i].toString());
	    		}
	    	}
	    }
	    return selectedList;
	}
	
	private static CheckNode[] createTreeNodes() {
		String[] strs = {"swing",     // 0
	         "platf",     // 1
	         "basic",     // 2
	         "metal",     // 3
	         "JTree",    // 4
	    "platf2",     // 1
	    "basic2",     // 2
	    "metal2",     // 3
	    "JTree2",    // 4
	    "platf2",     // 1
	    "basic2",     // 2
	    "metal2",     // 3
	    "JTree2"};    // 4
	                                             
	    CheckNode[] nodes = new CheckNode[strs.length];
	    for (int i=0;i<strs.length;i++) {
	      nodes[i] = new CheckNode(strs[i]); 
	    }
	    nodes[0].add(nodes[1]);
	    nodes[1].add(nodes[2]);
	    nodes[1].add(nodes[3]);
	    nodes[0].add(nodes[4]);
	    nodes[0].add(nodes[5]);
	    nodes[2].add(nodes[6]);
	    nodes[2].add(nodes[7]);
	    nodes[2].add(nodes[8]);
	    nodes[1].add(nodes[9]);
	    nodes[1].add(nodes[10]);
	    nodes[4].add(nodes[11]);
	    nodes[4].add(nodes[12]);
	    nodes[3].setSelected(true);
		return nodes;
	}
}
