import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import difflib.DiffUtils;
import difflib.Patch;
import difflib.PatchFailedException;


public class Sunucu {
	public final static boolean DEBUG = true;
	private static final int OrtakSatir = 2;
	public static int DEFAULTPORT = 13267;
	private String calismaKlasoru = null;
	@SuppressWarnings("unused")
	private int revizyonNumarasi = 0; 

	public Sunucu() {
		OkunacakMetinDosya ayarlar = new OkunacakMetinDosya("ayarlar.cfg");
		calismaKlasoru = ayarlar.satirOku();
		if (calismaKlasoru == null){
			this.baslangicIslemleri();
		}
		revizyonNumarasi = Integer.parseInt(ayarlar.satirOku());
		ayarlar.dosyaKapat();
	}
	
	public String getCalismaKlasoru() {
		return calismaKlasoru;
	}

	public List<String> dosyadanSatira(String filename) {
        List<String> lines = new LinkedList<String>();
        
        OkunacakMetinDosya dosya = new OkunacakMetinDosya(filename);
        
        String newLine = null;
        while(true){
        	newLine = dosya.satirOku();
        	if (newLine != null)
        		lines.add(newLine);
        	else
        		break;
		}
        return lines;
	}
	
	private List<String> dosyadanSatira(OkunacakMetinDosya okunacakDosya) {
    List<String> lines = new LinkedList<String>();
        
        while(true){
        	String newLine = okunacakDosya.satirOku();
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

	public Patch farkAl(OkunacakMetinDosya ilkOkunacakDosya,
			OkunacakMetinDosya ikinciOkunacakDosya) {
		List<String> ilkDosya = dosyadanSatira(ilkOkunacakDosya);
		List<String> ikinciDosya = dosyadanSatira(ikinciOkunacakDosya);
		Patch patch = DiffUtils.diff(ilkDosya, ikinciDosya);
		return patch;
	}

	public void patchDosyayaYazdir(String dosyaAdi, int dosyaRevizyonu) {
		Patch patch = this.farkAl(calismaKlasoru+File.separatorChar+"Head"+File.separatorChar+dosyaAdi, calismaKlasoru+File.separatorChar+"Temp"+File.separatorChar+dosyaAdi);
		List<String> unifiedPatch = DiffUtils.generateUnifiedDiff(calismaKlasoru+File.separatorChar+"Head"+File.separatorChar+dosyaAdi, calismaKlasoru+File.separatorChar+"Temp"+File.separatorChar+dosyaAdi, this.dosyadanSatira(calismaKlasoru+File.separatorChar+"Head"+File.separatorChar+dosyaAdi), patch, OrtakSatir);
		YazilacakMetinDosya deltaDosya = new YazilacakMetinDosya(calismaKlasoru+File.separatorChar+"Deltas"+File.separatorChar+dosyaAdi+".delta.r"+dosyaRevizyonu);
		deltaDosya.satirYaz(unifiedPatch);
		deltaDosya.dosyaKapat();
	}
	
	public void patchEkranaYazdir(Patch patch) {
		System.out.println("dosya okundu " + patch.getDeltas().size() + " fark,");
		for(int i=0;i<patch.getDeltas().size();i++){
			System.out.println("okunan dosya:");
				System.out.println(patch.getDelta(i));
		}
	}
	
	public void baslangicIslemleri() {
		System.out.println("calisma klasoru icin yol giriniz :");
		Scanner in = new Scanner(System.in);
		String dosyaYolu = in.nextLine();
		DizinOlustur workdir = new DizinOlustur(dosyaYolu);
		workdir.olustur();
		DizinOlustur temp = new DizinOlustur(dosyaYolu+File.separatorChar+"Temp");
		temp.olustur();
		DizinOlustur head = new DizinOlustur(dosyaYolu+File.separatorChar+"Head");
		head.olustur();
		DizinOlustur deltas = new DizinOlustur(dosyaYolu+File.separatorChar+"Deltas");
		deltas.olustur();
		YazilacakMetinDosya sunucuAyarDosya= new YazilacakMetinDosya("ayarlar.cfg");
		sunucuAyarDosya.satirYaz(dosyaYolu);
		sunucuAyarDosya.satirYaz("1");
		sunucuAyarDosya.dosyaKapat();
		calismaKlasoru = dosyaYolu;
		revizyonNumarasi = 1;
		}
	
	public Patch patchDosyadanOku(String DosyaAdi, int DosyaRevizyonu){
		return DiffUtils.parseUnifiedDiff(this.dosyadanSatira(calismaKlasoru+File.separatorChar+"Deltas"+File.separatorChar+DosyaAdi+".delta.r"+DosyaRevizyonu));
	}
	
	public boolean patchUygula(String farkUygulanacakDosya, Patch uygulanacakFark) {
		
		List <String> eskiDosya = dosyadanSatira(farkUygulanacakDosya);
		if (Sunucu.DEBUG){
			System.out.println("okunan dosyanin satirlari:");
			for (String satir:eskiDosya) System.out.println(satir);
		}
		try {
@SuppressWarnings("unchecked")
			List<String> yeniDosya = (List<String>) DiffUtils.patch(dosyadanSatira(farkUygulanacakDosya), uygulanacakFark);
			YazilacakMetinDosya yazilacakDosya = new YazilacakMetinDosya(farkUygulanacakDosya);
			yazilacakDosya.satirYaz(yeniDosya);
			return true;
		} catch (PatchFailedException e) {
			System.out.println("yama uygulanamadi");
			e.printStackTrace();
			return false;
		}

	}
	
	public static void main(String[] args) {
		Sunucu sunucu = new Sunucu();
		
		if (args.length != 0){
			if (DEBUG) 
				System.out.println("arguman alindi :" + args[0] +": uzunlugu :"+args.length);
			if ( args[0].equals("baslat")) {
				sunucu.baslangicIslemleri();
			}
		}
		/*Dosya Çekme Denemesi
		AgDosyaCek cekilenDosya = new AgDosyaCek(sunucu.calismaKlasoru+File.separatorChar+"istemci-r"+sunucu.revizyonNumarasi+".zip");
		try {
			cekilenDosya.dosyaCek("192.168.1.41");
		} catch (IOException e) {

			e.printStackTrace();
		}
		*/
		
		
		/* dosya sunma denemesi
		AgDosyaSun sunulanDosya = new AgDosyaSun("ayarlar.cfg");
		try {
			sunulanDosya.dosyaSun();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
		
		/*
		String dosyaAdi = new String("dosya.txt");
		int DosyaRevizyonu = 1;
		sunucu.patchDosyayaYazdir(dosyaAdi,DosyaRevizyonu);
		
		String patchDosyasiAdi = new String("dosya.txt");
		int patchDosyasiRevizyonu = 1;
		Patch patch2 = sunucu.patchDosyadanOku(patchDosyasiAdi,patchDosyasiRevizyonu);
		sunucu.patchEkranaYazdir(patch2);
		*/
	}
}
