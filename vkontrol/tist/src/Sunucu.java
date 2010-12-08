import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

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
	
	public void baslangicIslemleri() {
		System.out.println("calisma klasoru icin yol giriniz :");
		Scanner in = new Scanner(System.in);
		String dosyaYolu = in.nextLine();
		DizinOlustur workdir = new DizinOlustur(dosyaYolu);
		workdir.olustur();
		DizinOlustur temp = new DizinOlustur(dosyaYolu+"\\Temp");
		temp.olustur();
		DizinOlustur head = new DizinOlustur(dosyaYolu+"\\Head");
		head.olustur();
		DizinOlustur deltas = new DizinOlustur(dosyaYolu+"\\Deltas");
		deltas.olustur();
		YazilacakMetinDosya sunucuAyarDosya= new YazilacakMetinDosya("ayarlar.cfg");
		sunucuAyarDosya.satirYaz(dosyaYolu);
		}
	
	public static void main(String[] args) {
		Sunucu sunucu = new Sunucu();
		
		OkunacakMetinDosya ayarlar = new OkunacakMetinDosya("ayarlar.cfg");
		String calismaKlasoru = ayarlar.satirOku();
		if (args.length != 0){
			if (args[0] == "baslat") sunucu.baslangicIslemleri();
		}
		
		if (calismaKlasoru == null){
			sunucu.baslangicIslemleri();
		}
		
		Patch patch = new Patch();
		patch = sunucu.farkAl(calismaKlasoru+"\\Temp\\dosya.txt", calismaKlasoru+"\\Head\\dosya.txt");
		List<String> unifiedPatch = DiffUtils.generateUnifiedDiff(calismaKlasoru+"\\Temp\\yenidosya.txt", calismaKlasoru+"\\Head\\dosya.txt", sunucu.dosyadanSatira(calismaKlasoru+"\\Temp\\dosya.txt"), patch, 2);
		YazilacakMetinDosya deltaDosya = new YazilacakMetinDosya(calismaKlasoru+"\\Deltas\\dosya.delta.r01.txt");
		deltaDosya.satirYaz(unifiedPatch);
		
		unifiedPatch = DiffUtils.generateUnifiedDiff(calismaKlasoru+"\\Temp\\yenidosya.txt", calismaKlasoru+"\\Head\\dosya.txt", sunucu.dosyadanSatira(calismaKlasoru+"\\Temp\\dosya.txt"), patch, 2);
		deltaDosya = new YazilacakMetinDosya(calismaKlasoru+"\\Deltas\\dosya.delta.r02.txt");
		deltaDosya.satirYaz(unifiedPatch);
		
		Patch patch2 = DiffUtils.parseUnifiedDiff(sunucu.dosyadanSatira(calismaKlasoru+"\\Deltas\\dosya.delta.r01.txt"));
		
		pacthEkranaYazdir(patch2);
		
	}

}
