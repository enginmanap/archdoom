import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class SelectionPanel extends JPanel {
	private List<JTextField> listPath = new ArrayList<JTextField>();
	private List<JTextField> listName = new ArrayList<JTextField>();
	public List<JTextField> getListPath() {
		return listPath;
	}

	public List<JTextField> getListName() {
		return listName;
	}

	private static final long serialVersionUID = 1L;

	public SelectionPanel(){
		super();
		this.setLayout(new GridLayout(0, 2));
		this.setAutoscrolls(true);
		this.setSize(200, 300);
		JTextArea pathText = new JTextArea("Yol:");
		pathText.setFont(getFont());
		JTextArea nameText = new JTextArea("Isim:");
		nameText.setFont(getFont());
		this.add(pathText);
		this.add(nameText);

	}
	
	public void addList(List<String> listPath){
		
		for (int j=0;j<this.listPath.size();j++){
			this.remove(this.listPath.get(j));
			this.remove(this.listName.get(j));
		}
		
		this.listPath = new ArrayList<JTextField>();
		this.listName = new ArrayList<JTextField>();
		JTextField tf=null;
		JTextField tf2=null;
		for (int i=0;i<listPath.size();i++){
			tf = new JTextField(listPath.get(i));

			tf.setEditable(false);
			this.listPath.add(tf);
			this.add(tf);
			
			tf2 = new JTextField(listPath.get(i).substring(listPath.get(i).lastIndexOf(File.separatorChar)+1));
			this.listName.add(tf2);
			this.add(tf2);
			this.setVisible(false);
		}		
		this.setVisible(true);
	}
}
