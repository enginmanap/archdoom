
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;



public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;




	public MainFrame(){
		List<String> SeciliEleman = new ArrayList<String>();
		JPanel treePanel = new JPanel();
		treePanel.setLayout(new BorderLayout());
		
		// secilebilir agaci buraya koy.
		CheckTree tree = new CheckTree();
		System.out.println("tree eleman sayisi :"+tree.getComponentCount());
		JScrollPane sp = new JScrollPane(tree);
		treePanel.add(sp);
		getContentPane().add(treePanel,    BorderLayout.CENTER);
		JButton listeGetir = new JButton("Liste Getir");
		listeGetir.addActionListener(new ListeCekmeDugmeDinleme(tree, SeciliEleman));
		treePanel.add(listeGetir, BorderLayout.SOUTH );
		//JPanel ListPanel = new JPanel();
		//ListPanel.setLayout(new FlowLayout());
		
		// secilmislerin listesini buraya koy.
	}
	
	
	
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			e.printStackTrace();
		} 
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 250);
		frame.setVisible(true);
		
		Database data = new Database();
		data.startConnection();
	}

}
