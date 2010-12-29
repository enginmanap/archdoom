import java.awt.GridLayout;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;


public class SelectionPanel extends JPanel {
	private List<String> listPath = new ArrayList<String>();
	public List<String> getListPath() {
		return listPath;
	}

	public List<String> getListName() {
		return listName;
	}

	private List<String> listName = new ArrayList<String>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SelectionPanel(){
		super();
		this.setLayout(new GridLayout(0, 2, 5, 5));
		this.setAutoscrolls(true);
		this.setSize(200, 300);
		JTextArea pathText = new JTextArea("Yol:");
		JTextArea nameText = new JTextArea("İsim:");
		this.add(pathText);
		this.add(nameText);
		
		/* TEST
		List<String> a = new ArrayList<String>();
		a.add("i/a");
		a.add("k/b");
		a.add("l/c");
		a.add("m/d");
		a.add("n/e");
		a.add("o/f");
		a.add("p/g");
		a.add("q/h");
		a.add("r/z");
		a.add("s/t");
		

		addList(a);
		    TEST */

	}
	
	public void addList(List<String> listPath){
		JTextField tf=null;
		JTextField tf2=null;
		for (int i=0;i<listPath.size();i++){
			tf = new JTextField(listPath.get(i));


			this.listPath.add(listPath.get(i));
			this.add(tf);

			
			tf2 = new JTextField(listPath.get(i).substring(listPath.get(i).lastIndexOf(File.separatorChar)+1));

			this.listName.add(listPath.get(i).substring(listPath.get(i).lastIndexOf(File.separatorChar)+1));
			this.add(tf2);
			this.setVisible(false);

		}
		
		this.setVisible(true);


	}

}
