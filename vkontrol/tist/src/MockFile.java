import java.io.File;

import javax.swing.tree.DefaultMutableTreeNode;


public class MockFile extends File {
	DefaultMutableTreeNode expectedTree = new DefaultMutableTreeNode("root");
	/**
	 * 
	 */
	private static final long serialVersionUID = -6703435246200960723L;
	DefaultMutableTreeNode root;
	public MockFile(String pathname) {
		super("");
		root = new DefaultMutableTreeNode(pathname);
	}

	public void addExpectedNode(String DosyaAdi, int nereye){
		expectedTree.insert(new DefaultMutableTreeNode(DosyaAdi), nereye);
	}

}
