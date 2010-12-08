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
        	System.out.println("okunan satir: "+newLine);
        	if (newLine != null)
        		lines.add(newLine);
        	else
        		break;
		}
        System.out.println("geridonus listesi"+ lines.size());
        return lines;
	}

	public Patch farkAl(String dosya1, String dosya2) {
		List<String> ilkDosya = dosyadanSatira(dosya1);
		List<String> ikinciDosya = dosyadanSatira(dosya2);
		Patch patch = DiffUtils.diff(ilkDosya, ikinciDosya);
		return patch;
	}

	
	public static void main(String[] args) {
		Sunucu sunucu = new Sunucu();
		Patch patch = new Patch();
		patch = sunucu.farkAl("Temp/yenidosya.txt", "Head/eskidosya.txt");
		List<String> unifiedPatch = DiffUtils.generateUnifiedDiff("Temp/yenidosya.txt", "Head/eskidosya.txt", sunucu.dosyadanSatira("Temp/yenidosya.txt"), patch, 1);
		YazilacakMetinDosya deltaDosya = new YazilacakMetinDosya("delta.txt");
		deltaDosya.satirYaz(unifiedPatch);
		
		Patch patch2 = DiffUtils.parseUnifiedDiff(sunucu.dosyadanSatira("delta.txt"));
		
		System.out.println("dosya okundu " + patch2.getDeltas().size() + " fark");
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
}
