import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class ListeCekmeDugmeDinleme implements ActionListener {
	CheckTree tree;
	List<String>donusListesi;
	SelectionPanel panel;
	
	ListeCekmeDugmeDinleme(final CheckTree tree, SelectionPanel panel){
		this.tree = tree;
		this.panel = panel;
	}
	
	public void actionPerformed(ActionEvent ev) {
		donusListesi = tree.getSelectedList();
//		System.out.println("Donus list :"+ donusListesi);
		panel.addList(donusListesi);

	}
}
