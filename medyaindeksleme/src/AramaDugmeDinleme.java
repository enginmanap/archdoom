import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class AramaDugmeDinleme implements ActionListener{
	private String searchType="";
	
	public AramaDugmeDinleme(String searchFor) {
		super();
		this.searchType = searchFor;
	}



	public void actionPerformed(ActionEvent event) {
		String nameForSearch="";
		if (this.searchType == MainFrame.FILESEARCH )
			nameForSearch = JOptionPane.showInputDialog(null, "Aramak istediginiz icerigin adini giriniz : ", "Arama", 1);
		if (this.searchType == MainFrame.MEDIASEARCH)
			nameForSearch = JOptionPane.showInputDialog(null, "Aramak istediginiz Medya ID sini giriniz : ", "Arama", 1);
		Database data = new Database();
        data.startConnection();
        String query = "";
        if (this.searchType == MainFrame.FILESEARCH )
        	query = "select `mediaID`, `fileName`, `filePath` from `genel` where `fileName` = '"+nameForSearch+"'";
        if (this.searchType == MainFrame.MEDIASEARCH )
        	query = "select `mediaID`, `fileName`, `filePath` from `genel` where `mediaID` = '"+Integer.parseInt(nameForSearch)+"'";
        if (MainFrame.DEBUG)
        	System.out.println("query exact: "+ query);
        ResultSet rs = data.query(query);
        JFrame resultFrame = new JFrame();
        JPanel resultPanel = new JPanel();
        resultFrame.setLayout(new BorderLayout());
        JScrollPane resultPane = new JScrollPane(resultPanel);
        resultFrame.add(resultPane);
        
        
        

        resultPanel.setLayout(new GridLayout(0,3));
        List<JLabel> labelListExact = new ArrayList<JLabel>();
        List<JLabel> labelList = new ArrayList<JLabel>();
        JLabel filePath = new JLabel("Dosya Yolu:");
        JLabel fileName = new JLabel("Dosya Adi:");
        JLabel mediaID = new JLabel("Medya ID:");
        resultPanel.add(mediaID);
        resultPanel.add(filePath);
        resultPanel.add(fileName);
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
			resultPanel.add(error);
			e.printStackTrace();
		}
		
		
			JLabel exactResult = new JLabel();
			JLabel exactResult1;
			if (this.searchType == MainFrame.FILESEARCH )
				exactResult1 = new JLabel("Tam Sonuclar:");
			else
				exactResult1 = new JLabel();
			JLabel exactResult2 = new JLabel();
			resultPanel.add(exactResult);
			resultPanel.add(exactResult1);
			resultPanel.add(exactResult2);
		
		
		for (JLabel lab: labelListExact){
			resultPanel.add(lab);
		}
		
			
		if (rsCount < 1){
			JLabel empty = new JLabel();
			JLabel empty1 = new JLabel("Sonuc Bulunamadi!");
			JLabel empty2 = new JLabel();
			resultPanel.add(empty);
			resultPanel.add(empty1);
			resultPanel.add(empty2);
		}

		
		if (this.searchType == MainFrame.FILESEARCH ){
				query = "select `mediaID`, `fileName`, `filePath` from `genel` where `fileName` LIKE '"+"%"+nameForSearch+"%"+"' AND `fileName` != "+"'"+nameForSearch+"'";
				if (MainFrame.DEBUG)
					System.out.println("query like: "+ query);
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
					resultPanel.add(error);
					e.printStackTrace();
				}
	
				if (rsCount > 0)
				{
					JLabel result = new JLabel();
					JLabel result1 = new JLabel("Yaklasik Sonuclar:");
					JLabel result2 = new JLabel();
					resultPanel.add(result);
					resultPanel.add(result1);
					resultPanel.add(result2);
				}
				
				for (JLabel lab: labelList){
					resultPanel.add(lab);
				}
			}
			if (rsCount < 1){
				JOptionPane.showMessageDialog(null,"Dosya Bulunamadi! DosyaAdi: " + nameForSearch, " Dosya yok", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
			
			JButton printResult = new JButton("Sonuclari yazdir");
			printResult.addActionListener(new PrintButtonListener(labelListExact,labelList));
			resultPanel.add(printResult);
			resultFrame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		
			resultFrame.setSize(800, 480);
			resultFrame.setTitle("\""+nameForSearch + "\" aramasi sonuclari");
			resultFrame.setVisible(true);
		    Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		    
		    // Determine the new location of the window
		    int w = resultFrame.getSize().width;
		    int h = resultFrame.getSize().height;
		    int x = (dim.width-w)/2 + 200;
		    int y = (dim.height-h)/2 + 100;
		    
		    // Move the window
		    resultFrame.setLocation(x, y);
			

		}
        
    }
}
