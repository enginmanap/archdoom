import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterJob;
import java.util.List;
import java.util.StringTokenizer;

import javax.swing.JOptionPane;


public class Printer {
	
	public static int NAMELENGHT = 15;

	public static void printString(final String text) {
		PageFormat pageFormat = new PageFormat();
		PrinterJob printerJob = PrinterJob.getPrinterJob();
		printerJob.setPrintable(new Printable() {
			public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
				Graphics2D graphics2D = (Graphics2D)graphics;
				graphics2D.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
				StringTokenizer stringTokenizer = new StringTokenizer(text, "\n\r");
				Font font = new Font("Monospaced", Font.BOLD, 10);
				graphics2D.setFont(font);
				int pageWidth = graphics2D.getClipBounds().width;
				FontMetrics fontMetrics = graphics2D.getFontMetrics(font);
				int position = fontMetrics.getHeight();
				int height = fontMetrics.getAscent();
				int sayac = 0;
				String name = "";
				while (stringTokenizer.hasMoreTokens()) {
					String line = stringTokenizer.nextToken().trim();
					int lineWidth = fontMetrics.stringWidth(line);
					while (lineWidth > pageWidth) {
						String lineCopy = line;
						String firstPart = "";
						while (lineWidth > pageWidth) {
							int index = lineCopy.lastIndexOf(' ');
							firstPart = lineCopy.substring(0, index);
							lineWidth =	fontMetrics.stringWidth(firstPart);
							lineCopy = firstPart;
						}
						graphics2D.drawString(firstPart, 0, position);
						position += height;
						line = line.substring(firstPart.length(), line.length());
						lineWidth = fontMetrics.stringWidth(line);
					}
					graphics2D.drawString(line, 0, position);
					if(MainFrame.DEBUG)
						System.out.println("yazilacak satir \""+line+ "\"");
					position += height;
					sayac++;
				}
				return (pageIndex == 0 ? PAGE_EXISTS : NO_SUCH_PAGE);
			}
		}, pageFormat);
		if (printerJob.printDialog()) {
			try {
				printerJob.print();
			} catch (Throwable throwable) {
				throwable.printStackTrace();
			}
		}
	}
	
	public String mergeTableToString(List<String> names, List<String> paths){
		if (names.size()!=paths.size()){
			JOptionPane.showInternalMessageDialog(null,"isim ve yol sayilari esit olmalidir");
			System.exit(0);
		}
		String outputString="";
		int sayac = 0; 
		while (sayac < names.size()){
			outputString +=names.get(sayac);
			for (int i=0;i <NAMELENGHT - names.get(sayac).length();i++)
				outputString +=" ";
			outputString += paths.get(sayac); 
			if(MainFrame.DEBUG)
				System.out.println("yazilacak satir \""+ outputString +"\"");
			sayac++;
		}
		return outputString;
	}
}
