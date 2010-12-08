import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import difflib.DiffUtils;
import difflib.Patch;


public class Sunucu {

	private static String calismaKlasoru = null;
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

	private static Patch patchDosyayaYazdir(Sunucu sunucu, String dosyaAdi) {
		Patch patch = new Patch();
		patch = sunucu.farkAl(calismaKlasoru+"\\Temp\\"+dosyaAdi, calismaKlasoru+"\\Head\\"+dosyaAdi);
		List<String> unifiedPatch = DiffUtils.generateUnifiedDiff(calismaKlasoru+"\\Temp\\"+dosyaAdi, calismaKlasoru+"\\Head\\"+dosyaAdi, sunucu.dosyadanSatira(calismaKlasoru+"\\Temp\\"+dosyaAdi), patch, 2);
		YazilacakMetinDosya deltaDosya = new YazilacakMetinDosya(calismaKlasoru+"\\Deltas\\"+dosyaAdi+".delta.r01.txt");
		deltaDosya.satirYaz(unifiedPatch);
		return patch;
	}
	
	
	public static void patchEkranaYazdir(Patch patch) {
		System.out.println("dosya okundu " + patch.getDeltas().size() + " fark,");
		for(int i=0;i<patch.getDeltas().size();i++){
			System.out.println("okunan dosya:");
			// ChangeDelta sýnýfýnýn, toString() metodu eklenmemiþ, bu yuzden boyle bir etrafýndan dolaþma kullanýyoruz.
			if (patch.getDelta(i).getClass().getName() == "difflib.ChangeDelta")
				System.out.println("[ChangeDelta, position: " + patch.getDelta(i).getOriginal().getPosition() + ", lines: "
	                + patch.getDelta(i).getOriginal().getLines() + " to " + patch.getDelta(i).getRevised().getLines() + "]");
			else
				System.out.println(patch.getDelta(i));
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
		
		if (args.length != 0){
			System.out.println("arguman alindi :" + args[0] +": uzunlugu :");
			
			if ( args[0].equals("baslat")) {
				
				sunucu.baslangicIslemleri();
			}
		}
		
		OkunacakMetinDosya ayarlar = new OkunacakMetinDosya("ayarlar.cfg");
		calismaKlasoru = ayarlar.satirOku();
		
		
		if (calismaKlasoru == null){
			sunucu.baslangicIslemleri();
		}
		
		ayarlar = new OkunacakMetinDosya("ayarlar.cfg");
		calismaKlasoru = ayarlar.satirOku();
		String dosyaAdi = new String("dosya.txt");

		Sunucu.patchDosyayaYazdir(sunucu, dosyaAdi);
		
				
		Patch patch2 = DiffUtils.parseUnifiedDiff(sunucu.dosyadanSatira(calismaKlasoru+"\\Deltas\\dosya.delta.r01.txt"));
		
		patchEkranaYazdir(patch2);
		
	}



}
