
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	public MainFrame(){
		JPanel treePanel = new JPanel();
		treePanel.setLayout(new BorderLayout());
		
		// secilebilir agaci buraya koy.
		CheckTree tree = new CheckTree();
		System.out.println("tree eleman sayisi :"+tree.getComponentCount());
		JScrollPane sp = new JScrollPane(tree);
		treePanel.add(sp);
		getContentPane().add(treePanel,    BorderLayout.CENTER);
		//JPanel ListPanel = new JPanel();
		//ListPanel.setLayout(new FlowLayout());
				
		// secilmislerin listesini buraya koy.
	}
	
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 250);

		frame.setVisible(true);
		Database data = new Database();
		data.startConnection();
	}

}
