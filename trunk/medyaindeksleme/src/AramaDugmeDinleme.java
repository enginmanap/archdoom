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


public class AramaDugmeDinleme implements ActionListener{
	private String searchType="";
	private final List<JLabel> finalExactList = new ArrayList<JLabel>();
	private final List<JLabel> finalLikeList = new ArrayList<JLabel>();
	
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
//    	System.out.println("query1: "+ query);
//        System.out.println(query);
        ResultSet rs = data.query(query);
        JFrame resultFrame = new JFrame();
//        JScrollPane resultPane = new JScrollPane(resultFrame);
        resultFrame.setLayout(new GridLayout(0,3));
        List<JLabel> labelListExact = new ArrayList<JLabel>();
        List<JLabel> labelList = new ArrayList<JLabel>();
        JLabel filePath = new JLabel("Dosya Yolu:");
        JLabel fileName = new JLabel("Dosya Adi:");
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
//			System.out.println("query2: "+ query);
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
			finalExactList = labelListExact;
			finalLikeList = labelList;
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
				JOptionPane.showMessageDialog(null,"Dosya Bulunamadi! DosyaAdi: " + nameForSearch, " Dosya yok", JOptionPane.INFORMATION_MESSAGE);
			}
			else{
			
			JButton printResult = new JButton("Sonuclari yazdir");
			printResult.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					String outputString="";
					int sayac = 0; 
					while (sayac*3 < finalExactList.size()){
						outputString +=finalExactList.get(sayac);
						for (int i=0;i <Printer.NAMELENGHT - finalExactList.get(sayac).getName().length();i++)
							outputString +=" ";
						outputString +=finalExactList.get(sayac+1);
						for (int i=0;i <Printer.NAMELENGHT - finalExactList.get(sayac+1).getName().length();i++)
							outputString +=" ";
						outputString +=finalExactList.get(sayac+2);
						outputString +="\n";
						if (MainFrame.DEBUG)
							System.out.println("yazilacak satir \""+ outputString +"\"");
						sayac++;
					}
					if (searchType == MainFrame.FILESEARCH){
						outputString += "Yaklasik Sonuclar :\n";
						while (sayac*3 < finalLikeList.size()){
							outputString +=finalLikeList.get(sayac);
							for (int i=0;i <Printer.NAMELENGHT - finalLikeList.get(sayac).getName().length();i++)
								outputString +=" ";
							outputString +=finalLikeList.get(sayac+1);
							for (int i=0;i <Printer.NAMELENGHT - finalLikeList.get(sayac+1).getName().length();i++)
								outputString +=" ";
							outputString +=finalLikeList.get(sayac+2);
							outputString +="\n";
							if (MainFrame.DEBUG)
								System.out.println("yazilacak satir \""+ outputString +"\"");
							sayac++;
						}
					}
					Printer.printString(outputString);
				}
			});
			resultFrame.add(printResult);
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
}
