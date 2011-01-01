
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
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
	
	public static Boolean DEBUG = true;
	public static String INDEXDIR = System.getProperty("user.dir");


	public MainFrame(){
		
		final CheckTree tree = new CheckTree();
		final JPanel selectionAna = new JPanel();
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu("Dosya");
		file.setMnemonic(KeyEvent.VK_D);
		JMenuItem yolBelirle = new JMenuItem("Kaynak Dizin");
		yolBelirle.setMnemonic(KeyEvent.VK_K);
		yolBelirle.setToolTipText("Icerik Kontrolu icin Klasor secimi");
		yolBelirle.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event) {
//		          INDEXDIR = JOptionPane.showInputDialog(null, "Icerik Kontrolu yapmnak istediginiz dizini seciniz : ", 
//		        		  "Kaynak Dizin", 1);
//		    	  
//		          tree.setroot(INDEXDIR);
		    	    JFileChooser chooser = new JFileChooser(); 
		    	    chooser.setCurrentDirectory(new File("."));
		    	    chooser.setDialogTitle("Dizin Seciniz");
		    	    chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		    	    chooser.setAcceptAllFileFilterUsed(false);
		    	    //    
		    	    if (chooser.showOpenDialog(selectionAna) == JFileChooser.APPROVE_OPTION) { 
		    	    	INDEXDIR=chooser.getSelectedFile().getAbsolutePath();
		    	    	if (MainFrame.DEBUG)
		    	    		System.out.println(this.getClass().getName()+": Dizin agaci icin indexDir degistiriliyor, yeni indexDir = "+INDEXDIR );
		    	    	tree.setroot(INDEXDIR);
		    	         
		    	      }
		    	   
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
		JMenuItem searchMedia = new JMenuItem("Medya ara");
		searchMedia.setToolTipText("Medya nin icerigini getir");
		searchMedia.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event) {
		          String nameForSearch = JOptionPane.showInputDialog(null, "Aramak istediginiz Medya ID sini giriniz : ", 
		        		  "Arama", 1);
		          Database data = new Database();
		          data.startConnection();
		          String query = "select `mediaID`, `fileName`, `filePath` from `genel` where `mediaID` = '"+Integer.parseInt(nameForSearch)+"'";
//		      	System.out.println("query1: "+ query);
//		          System.out.println(query);
		          ResultSet rs = data.query(query);
		          JFrame resultFrame = new JFrame();
//		          JScrollPane resultPane = new JScrollPane(resultFrame);
		          resultFrame.setLayout(new GridLayout(0,3));
		          List<JLabel> labelListExact = new ArrayList<JLabel>();
		          JLabel filePath = new JLabel("Dosya Yolu:");
		          JLabel fileName = new JLabel("Dosya Adı:");
		          JLabel mediaID = new JLabel("Medya ID:");
		          resultFrame.add(mediaID);
		          resultFrame.add(filePath);
		          resultFrame.add(fileName);
		          int rsCount=0;

		          try {
					while(rs.next()){
						rsCount++;
								labelListExact.add(new JLabel(rs.getString(1)));
								labelListExact.add(new JLabel(rs.getString(3)));
								labelListExact.add(new JLabel(rs.getString(2)));
								
						}
				} catch (SQLException e) {
					JLabel error = new JLabel("Veritabani baglantisinda hata!");
					resultFrame.add(error);
					e.printStackTrace();
				}
				
		
				
				for (JLabel lab: labelListExact){
					resultFrame.add(lab);
				}
				
					

				if (rsCount < 1){
					JOptionPane.showMessageDialog(null,"Medya Bulunamadi!. Medya ID: " + nameForSearch, "Medya Yok", JOptionPane.INFORMATION_MESSAGE);
				}
				else{
				
				
				resultFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
			
				resultFrame.setSize(750, 200);
				resultFrame.setTitle("Medya ID: \""+nameForSearch + "\" aramasi sonuclari");
				resultFrame.setVisible(true);
			    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				    
				    // Determine the new location of the window
				    int w = resultFrame.getSize().width;
				    int h = resultFrame.getSize().height;
				    int x = (dim.width-w)/2;
				    int y = (dim.height-h)/2;
				    
				    // Move the window
				    resultFrame.setLocation(x, y);
					

				}
		          
		      }
		});
		searchElement.setToolTipText("Icerik Arama");
		searchElement.addActionListener(new ActionListener() {
		      public void actionPerformed(ActionEvent event) {
		          String nameForSearch = JOptionPane.showInputDialog(null, "Aramak istediginiz icerigin adini giriniz : ", 
		        		  "Arama", 1);
		          Database data = new Database();
		          data.startConnection();
		          String query = "select `mediaID`, `fileName`, `filePath` from `genel` where `fileName` = '"+nameForSearch+"'";
//		      	System.out.println("query1: "+ query);
//		          System.out.println(query);
		          ResultSet rs = data.query(query);
		          JFrame resultFrame = new JFrame();
//		          JScrollPane resultPane = new JScrollPane(resultFrame);
		          resultFrame.setLayout(new GridLayout(0,3));
		          List<JLabel> labelListExact = new ArrayList<JLabel>();
		          List<JLabel> labelList = new ArrayList<JLabel>();
		          JLabel filePath = new JLabel("Dosya Yolu:");
		          JLabel fileName = new JLabel("Dosya Adı:");
		          JLabel mediaID = new JLabel("Medya ID:");
		          resultFrame.add(mediaID);
		          resultFrame.add(filePath);
		          resultFrame.add(fileName);
		          int rsCount=0;

		          try {
					while(rs.next()){
						rsCount++;
								labelListExact.add(new JLabel(rs.getString(1)));
								labelListExact.add(new JLabel(rs.getString(3)));
								labelListExact.add(new JLabel(rs.getString(2)));
								
						}
				} catch (SQLException e) {
					JLabel error = new JLabel("Veritabani baglantisinda hata!");
					resultFrame.add(error);
					e.printStackTrace();
				}
				
				
					JLabel exactResult = new JLabel();
					JLabel exactResult1 = new JLabel("Tam Sonuclar:");
					JLabel exactResult2 = new JLabel();
					resultFrame.add(exactResult);
					resultFrame.add(exactResult1);
					resultFrame.add(exactResult2);
				
				
				for (JLabel lab: labelListExact){
					resultFrame.add(lab);
				}
				
					
				if (rsCount < 1){
					JLabel empty = new JLabel();
					JLabel empty1 = new JLabel("Sonuc Bulunamadi!");
					JLabel empty2 = new JLabel();
					resultFrame.add(empty);
					resultFrame.add(empty1);
					resultFrame.add(empty2);
				}
	
				
					
					query = "select `mediaID`, `fileName`, `filePath` from `genel` where `fileName` LIKE '"+"%"+nameForSearch+"%"+"' AND `fileName` != "+"'"+nameForSearch+"'";
//					System.out.println("query2: "+ query);
					rs = data.query(query);
					rsCount = 0;
					
					try {
						while(rs.next()){
							rsCount++;
									labelList.add(new JLabel(rs.getString(1)));
									labelList.add(new JLabel(rs.getString(3)));
									labelList.add(new JLabel(rs.getString(2)));
									
							}
					} catch (SQLException e) {
						JLabel error = new JLabel("Veritabani baglantisinda hata!");
						resultFrame.add(error);
						e.printStackTrace();
					}
					
					if (rsCount > 0)
					{
						JLabel result = new JLabel();
						JLabel result1 = new JLabel("Yaklasik Sonuclar:");
						JLabel result2 = new JLabel();
						resultFrame.add(result);
						resultFrame.add(result1);
						resultFrame.add(result2);
					}
					
					for (JLabel lab: labelList){
						resultFrame.add(lab);
					}
					
					if (rsCount < 1){
						JOptionPane.showMessageDialog(null,"Dosya Bulunamadi!. DosyaAdi: " + nameForSearch, "Dosya yok", JOptionPane.INFORMATION_MESSAGE);
					}
					else{
					
					
					resultFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
				
					resultFrame.setSize(750, 200);
					resultFrame.setTitle("\""+nameForSearch + "\" aramasi sonuclari");
					resultFrame.setVisible(true);
				    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				    
				    // Determine the new location of the window
				    int w = resultFrame.getSize().width;
				    int h = resultFrame.getSize().height;
				    int x = (dim.width-w)/2;
				    int y = (dim.height-h)/2;
				    
				    // Move the window
				    resultFrame.setLocation(x, y);
					

				}
		          
		      }
		});
		search.add(searchElement);
		search.add(searchMedia);
		menuBar.add(search);
		setJMenuBar(menuBar);
		
		

		JPanel treePanel = new JPanel();
		treePanel.setLayout(new BorderLayout());
		
		JButton saveDatabase = new JButton("Veritabanina Kaydet");
		
		selectionAna.setLayout(new BorderLayout());
		SelectionPanel selectionPanel = new SelectionPanel();
		JScrollPane selectionPane = new JScrollPane(selectionPanel);
		selectionAna.add(selectionPane);
		selectionAna.add(saveDatabase, BorderLayout.SOUTH);
		saveDatabase.addActionListener(new DatabaseSaveListener(selectionPanel));
		
		// secilebilir agaci buraya koy.
		
//		System.out.println("tree eleman sayisi :"+tree.getComponentCount());
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
		frame.setSize(600, 400);
		frame.setTitle("Medya indexleme");
		frame.setVisible(true);
	    // Get the size of the screen
	    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	    
	    // Determine the new location of the window
	    int w = frame.getSize().width;
	    int h = frame.getSize().height;
	    int x = (dim.width-w)/2;
	    int y = (dim.height-h)/2;
	    
	    // Move the window
	    frame.setLocation(x, y);



	}

}
