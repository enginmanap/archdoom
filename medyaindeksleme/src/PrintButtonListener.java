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
		while (sayac < exactMatchList.size()){
			outputString +=exactMatchList.get(sayac).getText();
			for (int i=0;i <Printer.NAMELENGTH - exactMatchList.get(sayac).getText().length();i++)
				outputString +=" ";
			outputString +=exactMatchList.get(sayac+1).getText();
			for (int i=0;i <Printer.PATHLENGTH - exactMatchList.get(sayac+1).getText().length();i++)
				outputString +=" ";
			outputString +=exactMatchList.get(sayac+2).getText();
			outputString +="\n";
			if (MainFrame.DEBUG)
				System.out.println("yazilacak satir :\""+ outputString +"\"");
			sayac += 3;
		}
		sayac=0;
		if (likeMatchList.size()>0){
			outputString += "Yaklasik Sonuclar :\n";
			while (sayac < likeMatchList.size()){
				outputString +=likeMatchList.get(sayac).getText();
				for (int i=0;i <7 - likeMatchList.get(sayac).getText().length();i++)
					outputString +=" ";
				outputString +=likeMatchList.get(sayac+1).getText();
				for (int i=0;i <Printer.PATHLENGTH - likeMatchList.get(sayac+1).getText().length();i++)
					outputString +=" ";
				outputString +=likeMatchList.get(sayac+2).getText();
				outputString +="\n";
				if (MainFrame.DEBUG)
					System.out.println("yazilacak yaklasik satir \""+ outputString +"\"");
				sayac += 3;
			}
		}
		Printer.printString(outputString);
	}
}

