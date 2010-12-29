
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import javax.swing.UIManager;
 

public class MainFrame extends JFrame{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static String INDEXDIR = "/home/mesutcan/Belgeler";


	public MainFrame(){
		
		final CheckTree tree = new CheckTree();
		
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Dosya");
		file.setMnemonic(KeyEvent.VK_D);
		JMenuItem yolBelirle = new JMenuItem("Kaynak Dizin");
		yolBelirle.setMnemonic(KeyEvent.VK_K);
		yolBelirle.setToolTipText("Icerik Kontrolu icin Klasor secimi");
		yolBelirle.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event) {
		          INDEXDIR = JOptionPane.showInputDialog(null, "Icerik Kontrolu yapmnak istediginiz dizini seciniz : ", 
		        		  "Kaynak Dizin", 1);
		          tree.setroot(INDEXDIR);
		      }
		});
		file.add(yolBelirle);
		
		JMenuItem closeWindow = new JMenuItem("Cikis");
		closeWindow.setMnemonic(KeyEvent.VK_C);
		closeWindow.setToolTipText("Programdan Cik");
		closeWindow.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event) {
		    	  System.exit(0);
		      }
		});
		file.add(closeWindow);
		menuBar.add(file);
		
		JMenu search = new JMenu("Arama");
		file.setMnemonic(KeyEvent.VK_S);
		JMenuItem searchElement = new JMenuItem("Arama");
		searchElement.setToolTipText("Icerik Arama");
		searchElement.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event) {
		          String nameForSearch = JOptionPane.showInputDialog(null, "Aramak istediginiz icerigin adini giriniz : ", 
		        		  "Arama", 1);
		          Database data = new Database();
		          data.startConnection();
		          String query = "select `mediaID`, `filePath` from `genel` where `fileName`='"+nameForSearch+"'";
		          System.out.println(query);
		          data.query(query);
		      
		          data.printLastQueryResult();
		      }
		});
		search.add(searchElement);
		menuBar.add(search);
		setJMenuBar(menuBar);
		
		

		JPanel treePanel = new JPanel();
		treePanel.setLayout(new BorderLayout());
		
		JButton saveDatabase = new JButton("Veritabanina Kaydet");
		JPanel selectionAna = new JPanel();
		selectionAna.setLayout(new BorderLayout());
		SelectionPanel selectionPanel = new SelectionPanel();
		JScrollPane selectionPane = new JScrollPane(selectionPanel);
		selectionAna.add(selectionPane);
		selectionAna.add(saveDatabase, BorderLayout.SOUTH);
		saveDatabase.addActionListener(new DatabaseSaveListener(selectionPanel));
		
		// secilebilir agaci buraya koy.
		
		System.out.println("tree eleman sayisi :"+tree.getComponentCount());
		JScrollPane sp = new JScrollPane(tree);
		treePanel.add(sp);
		getContentPane().add(treePanel,    BorderLayout.WEST);
		JButton listeGetir = new JButton("Liste Getir");
		listeGetir.addActionListener(new ListeCekmeDugmeDinleme(tree, selectionPanel));
		treePanel.add(listeGetir, BorderLayout.SOUTH );
		
		

//		JScrollPane selectionPane = new JScrollPane();


		getContentPane().add(selectionAna, BorderLayout.CENTER);
		
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
		frame.setTitle("Medya indexleme");
		frame.setVisible(true);
		
		Database data = new Database();
		data.startConnection();
	}

}
