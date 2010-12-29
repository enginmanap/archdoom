import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JTree;


public class ListeCekmeDugmeDinleme implements ActionListener {
	CheckTree tree;
	List<String>donusListesi;
	
	ListeCekmeDugmeDinleme(final CheckTree tree,List<String>DonusListesi){
		this.tree = tree;
	}
	
	public void actionPerformed(ActionEvent ev) {
		donusListesi = tree.getSelectedList();
		System.out.println("Donus list :"+ donusListesi);
	}
}
