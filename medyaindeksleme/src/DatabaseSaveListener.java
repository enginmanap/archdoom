import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;


public class DatabaseSaveListener implements ActionListener {
	private int mediaID;
	private SelectionPanel panel;
	
	public DatabaseSaveListener(SelectionPanel panel){
		this.panel = panel;
	}
	
	public void actionPerformed(ActionEvent ev) {
		
		mediaID = Integer.parseInt(JOptionPane.showInputDialog("Media ID giriniz"));
		
		System.out.println("mediaID: " + mediaID);
		
		
		
		List<JTextField> listName = panel.getListName();
		List<JTextField> listPath = panel.getListPath();
		Database data = new Database();
		data.startConnection();
		
		boolean success = false;
		
		for (int i=0;i<listName.size();i++){
			success = data.update("INSERT INTO genel " +
					"(FileName, filePath, mediaID) " +
					"values ('"+listName.get(i).getText()+"', '"+listPath.get(i).getText()+"', '"+mediaID+"')");
		}
		
		if (success)
			JOptionPane.showMessageDialog(null,"Basari ile kaydedildi!. Medya ID: " + mediaID, "Basarili", JOptionPane.INFORMATION_MESSAGE);
		

	}
}