import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;


public class PrintButtonListener implements ActionListener{
	List<JLabel> exactMatchList = new ArrayList<JLabel>();
	List<JLabel> likeMatchList = new ArrayList<JLabel>();
	public PrintButtonListener(List<JLabel> exactMatches, List<JLabel> closeMatches) {
		super();
		exactMatchList = exactMatches;
		likeMatchList = closeMatches;
	}

	public void actionPerformed(ActionEvent e) {
		String outputString="";
		int sayac = 0; 
		while (sayac*3 < exactMatchList.size()){
			outputString +=exactMatchList.get(sayac);
			for (int i=0;i <Printer.NAMELENGHT - exactMatchList.get(sayac).getName().length();i++)
				outputString +=" ";
			outputString +=exactMatchList.get(sayac+1);
			for (int i=0;i <Printer.NAMELENGHT - exactMatchList.get(sayac+1).getName().length();i++)
				outputString +=" ";
			outputString +=exactMatchList.get(sayac+2);
			outputString +="\n";
			if (MainFrame.DEBUG)
				System.out.println("yazilacak satir \""+ outputString +"\"");
			sayac++;
		}
		if (likeMatchList.size()>0){
			outputString += "Yaklasik Sonuclar :\n";
			while (sayac*3 < likeMatchList.size()){
				outputString +=likeMatchList.get(sayac);
				for (int i=0;i <Printer.NAMELENGHT - likeMatchList.get(sayac).getName().length();i++)
					outputString +=" ";
				outputString +=likeMatchList.get(sayac+1);
				for (int i=0;i <Printer.NAMELENGHT - likeMatchList.get(sayac+1).getName().length();i++)
					outputString +=" ";
				outputString +=likeMatchList.get(sayac+2);
				outputString +="\n";
				if (MainFrame.DEBUG)
					System.out.println("yazilacak satir \""+ outputString +"\"");
				sayac++;
			}
		}
		Printer.printString(outputString);
	}
}

