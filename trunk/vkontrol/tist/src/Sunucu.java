import java.util.LinkedList;
import java.util.List;

import difflib.DiffUtils;
import difflib.Patch;


public class Sunucu {

	
	public List<String> dosyadanSatira(String filename) {
        List<String> lines = new LinkedList<String>();
        
        OkunacakMetinDosya dosya = new OkunacakMetinDosya(filename);
        while(true){
        	String newLine = dosya.satirOku();
        	if (newLine != null)
        		lines.add(newLine);
        	else
        		break;
		}
        return lines;
	}

	public Patch farkAl(String dosya1, String dosya2) {
		List<String> ilkDosya = dosyadanSatira(dosya1);
		List<String> ikinciDosya = dosyadanSatira(dosya2);
		Patch patch = DiffUtils.diff(ilkDosya, ikinciDosya);
		return patch;
	}


	private static void pacthEkranaYazdir(Patch patch2) {
		System.out.println("dosya okundu " + patch2.getDeltas().size() + " fark,");
		for(int i=0;i<patch2.getDeltas().size();i++){
			System.out.println("okunan dosya:");
			// ChangeDelta sýnýfýnýn, toString() metodu eklenmemiþ, bu yuzden boyle bir etrafýndan dolaþma kullanýyoruz.
			if (patch2.getDelta(i).getClass().getName() == "difflib.ChangeDelta")
				System.out.println("[ChangeDelta, position: " + patch2.getDelta(i).getOriginal().getPosition() + ", lines: "
	                + patch2.getDelta(i).getOriginal().getLines() + " to " + patch2.getDelta(i).getRevised().getLines() + "]");
			else
				System.out.println(patch2.getDelta(i));
		}
	}
	
	public static void main(String[] args) {
		Sunucu sunucu = new Sunucu();
		Patch patch = new Patch();
		patch = sunucu.farkAl("Temp/yenidosya.txt", "Head/eskidosya.txt");
		for(int i=0;i<patch.getDeltas().size();i++)
			System.out.println(patch.getDelta(i));
		List<String> unifiedPatch = DiffUtils.generateUnifiedDiff("Temp/yenidosya.txt", "Head/eskidosya.txt", sunucu.dosyadanSatira("Temp/yenidosya.txt"), patch, 2);
		YazilacakMetinDosya deltaDosya = new YazilacakMetinDosya("Deltas/delta.txt");
		deltaDosya.satirYaz(unifiedPatch);
		
		Patch patch2 = DiffUtils.parseUnifiedDiff(sunucu.dosyadanSatira("delta.txt"));
		
		// fark dosyasýnýn okunmasýndan sonra, okunan patch2 nesnesini incelemek icin ekrana yazdýran dongu.
		pacthEkranaYazdir(patch2);
		
	}

}
