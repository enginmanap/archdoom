import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

import difflib.DiffUtils;
import difflib.Patch;
import difflib.PatchFailedException;

public class Sunucu {
	public final static boolean DEBUG = true;
	private static final int OrtakSatir = 2;
	private static final byte COMMIT = 1;
	private static final byte CHECKOUT = 2;
	public static int DEFAULTPORT = 13267;
	private String calismaKlasoru = null;
	private int revizyonNumarasi = 0;

	public Sunucu() {
		OkunacakMetinDosya ayarlar = new OkunacakMetinDosya("ayarlar.cfg");
		calismaKlasoru = ayarlar.satirOku();
		if (calismaKlasoru == null) {
			this.baslangicIslemleri();
		}
		OkunacakMetinDosya ayarlar2 = new OkunacakMetinDosya("ayarlar.cfg");
		calismaKlasoru = ayarlar2.satirOku();
		revizyonNumarasi = Integer.parseInt(ayarlar2.satirOku());
		ayarlar2.dosyaKapat();
	}

	public String getCalismaKlasoru() {
		return calismaKlasoru;
	}

	public List<String> dosyadanSatira(String filename) {
		List<String> lines = new LinkedList<String>();

		OkunacakMetinDosya dosya = new OkunacakMetinDosya(filename);

		String newLine = null;
		while (true) {
			newLine = dosya.satirOku();
			if (newLine != null)
				lines.add(newLine);
			else
				break;
		}
		return lines;
	}

	List<String> dosyadanSatira(OkunacakMetinDosya okunacakDosya) {
		List<String> lines = new LinkedList<String>();

		while (true) {
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

	private void farkAl(String gelenKlasorAdi) {
		File gelenKlasor = new File(gelenKlasorAdi);
		String yalnizGelenKlasor = gelenKlasor.getName();
		if (Sunucu.DEBUG)
			System.out.println("yalniz gelen klasor :" + yalnizGelenKlasor);
		File[] files = gelenKlasor.listFiles();
		if (Sunucu.DEBUG)
			System.out.println("Adding directory " + gelenKlasor.getName());
		for (int i = 0; i < files.length; i++) {
			// if the file is directory, call the function recursively
			if (files[i].isDirectory()) {
				if (Sunucu.DEBUG)
					System.out.println("klasor yolu: "
							+ files[i].getAbsolutePath());
				String klasorYolu = files[i].getAbsolutePath().substring(
						this.calismaKlasoru.length() + 6);
				if (Sunucu.DEBUG)
					System.out.println("klasor yolu degiskeni: " + klasorYolu);
				DizinOlustur yeniDizin = new DizinOlustur(calismaKlasoru
						+ File.separatorChar + "Deltas" + File.separatorChar
						+ klasorYolu);
				yeniDizin.olustur();
				DizinOlustur yeniDizinHead = new DizinOlustur(calismaKlasoru
						+ File.separatorChar + "Head" + File.separatorChar
						+ klasorYolu);
				yeniDizinHead.olustur();
				farkAl(files[i].getAbsolutePath());
				continue;

			}
			
			 patchDosyayaYazdir(files[i].getAbsolutePath().substring(
						this.calismaKlasoru.length() + 6), this.revizyonNumarasi);
		}
	}

	public void patchDosyayaYazdir(String dosyaAdi, int dosyaRevizyonu) {
		Patch patch = this.farkAl(calismaKlasoru + File.separatorChar + "Head"
				+ File.separatorChar + dosyaAdi, calismaKlasoru
				+ File.separatorChar + "Temp" + File.separatorChar + dosyaAdi);
		List<String> unifiedPatch = DiffUtils.generateUnifiedDiff(
				calismaKlasoru + File.separatorChar + "Head"
						+ File.separatorChar + dosyaAdi,
				calismaKlasoru + File.separatorChar + "Temp"
						+ File.separatorChar + dosyaAdi,
				this.dosyadanSatira(calismaKlasoru + File.separatorChar
						+ "Head" + File.separatorChar + dosyaAdi), patch,
				OrtakSatir);
		YazilacakMetinDosya deltaDosya = new YazilacakMetinDosya(calismaKlasoru
				+ File.separatorChar + "Deltas" + File.separatorChar + dosyaAdi
				+ ".delta.r" + dosyaRevizyonu);
		deltaDosya.satirYaz(unifiedPatch);
		deltaDosya.dosyaKapat();
	}

	public void patchEkranaYazdir(Patch patch) {
		System.out.println("dosya okundu " + patch.getDeltas().size()
				+ " fark,");
		for (int i = 0; i < patch.getDeltas().size(); i++) {
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
		DizinOlustur temp = new DizinOlustur(dosyaYolu + File.separatorChar
				+ "Temp");
		temp.olustur();
		DizinOlustur head = new DizinOlustur(dosyaYolu + File.separatorChar
				+ "Head");
		head.olustur();
		DizinOlustur deltas = new DizinOlustur(dosyaYolu + File.separatorChar
				+ "Deltas");
		deltas.olustur();
		YazilacakMetinDosya sunucuAyarDosya = new YazilacakMetinDosya(
				"ayarlar.cfg");
		sunucuAyarDosya.satirYaz(dosyaYolu);
		sunucuAyarDosya.satirYaz("1");
		sunucuAyarDosya.dosyaKapat();
		calismaKlasoru = dosyaYolu;
		revizyonNumarasi = 1;
	}

	public Patch patchDosyadanOku(String DosyaAdi, int DosyaRevizyonu) {
		return DiffUtils.parseUnifiedDiff(this.dosyadanSatira(calismaKlasoru
				+ File.separatorChar + "Deltas" + File.separatorChar + DosyaAdi
				+ ".delta.r" + DosyaRevizyonu));
	}

	public boolean patchUygula(String farkUygulanacakDosya,
			Patch uygulanacakFark) {

		List<String> eskiDosya = dosyadanSatira(farkUygulanacakDosya);
		if (Sunucu.DEBUG) {
			System.out.println("okunan dosyanin satirlari:");
			for (String satir : eskiDosya)
				System.out.println(satir);
		}
		try {
			@SuppressWarnings("unchecked")
			List<String> yeniDosya = (List<String>) DiffUtils.patch(
					dosyadanSatira(farkUygulanacakDosya), uygulanacakFark);
			if(Sunucu.DEBUG)
				System.out.println("patch listeye uygulandi");
			YazilacakMetinDosya yazilacakDosya = new YazilacakMetinDosya(
					farkUygulanacakDosya);
			if(Sunucu.DEBUG) System.out.println("dosya fark yazmak icin acildi.");
			yazilacakDosya.satirYaz(yeniDosya);
			return true;
		} catch (PatchFailedException e) {
			System.out.println("yama uygulanamadi");
			e.printStackTrace();
			return false;
		}

	}
	
	public void patchUygula(String yazilacakKlasorAdi, int revizyon){
		String yazilacakKlasor = yazilacakKlasorAdi.substring(this.calismaKlasoru.length()+1);
		File gelenKlasor = new File(this.calismaKlasoru+File.separatorChar+"Deltas");
		String yalnizGelenKlasor = gelenKlasor.getName();
		if (Sunucu.DEBUG)
			System.out.println("yaln�z gelen klasor :" + yalnizGelenKlasor);
		File[] fileArray = gelenKlasor.listFiles();
		List<File> fileList = new ArrayList<File>();
		for(File i:fileArray)
			fileList.add(i);
		fileList = ozelBul(fileList);
		File[] files = new File[fileList.size()];
		for(int i=0;i<fileList.size();i++){
			files[i]=fileList.get(i);
		}
		if (Sunucu.DEBUG)
			System.out.println("Adding directory " + gelenKlasor.getName());
		for (int i = 0; i < files.length; i++) {
			// if the file is directory, call the function recursively
			if (files[i].isDirectory()) {
				farkAl(files[i].getAbsolutePath());
				continue;

			}
			if (Sunucu.DEBUG)
				System.out.println("patch icin revizyon :"+revizyon);
			for(int rev=1;rev <= revizyon; rev++){
				Patch uygulanacakFark = patchDosyadanOku(files[i].getAbsolutePath().substring( this.calismaKlasoru.length() + 8, files[i].getAbsolutePath().lastIndexOf(".delta")), rev);
				String uygulanacakDosya = this.calismaKlasoru+File.separatorChar+yazilacakKlasor+File.separatorChar + files[i].getAbsolutePath().substring( this.calismaKlasoru.length() + 8, files[i].getAbsolutePath().lastIndexOf(".delta"));
				if (Sunucu.DEBUG)
					System.out.println("patch uygulaniyor, dosya : "+uygulanacakDosya+" patch : "+ uygulanacakFark);
				patchUygula(uygulanacakDosya, uygulanacakFark);
				
			}
		}
	}

	public boolean commit(String committerIP){
		if (Sunucu.DEBUG)
			System.out.println("commit sinyali al�nd�, ip:"+committerIP);
		AgDosyaCek cekilenDosya = new AgDosyaCek(calismaKlasoru+File.separatorChar+"Temp"+File.separatorChar+"istemci-r"+revizyonNumarasi+".zip");
		try {
			cekilenDosya.dosyaCek(committerIP);
			Unzip alinanDosya = new Unzip(calismaKlasoru+File.separatorChar+"Temp", calismaKlasoru+File.separatorChar+"Temp"+File.separatorChar+"istemci-r"+revizyonNumarasi+".zip" );
			alinanDosya.unziple();
			File zipDosyasi = new File(calismaKlasoru+File.separatorChar+"Temp"+File.separatorChar+"istemci-r"+revizyonNumarasi+".zip");
			if (Sunucu.DEBUG)
				System.out.print("silinecek zip dosyasi :"+zipDosyasi.getAbsolutePath());
			zipDosyasi.delete();
			if (Sunucu.DEBUG)
				System.out.println(" silindi");
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		farkAl(this.calismaKlasoru+File.separatorChar+"Temp");
		if(Sunucu.DEBUG)
			System.out.println("Patchler Head'e aktariliyor");
		patchUygula(this.calismaKlasoru+File.separatorChar+"Head", this.revizyonNumarasi);
		this.revizyonNumarasi++;
		
		YazilacakMetinDosya sunucuAyarDosya= new YazilacakMetinDosya("ayarlar.cfg");
		sunucuAyarDosya.satirYaz(this.calismaKlasoru);
		sunucuAyarDosya.satirYaz(""+revizyonNumarasi);
		sunucuAyarDosya.dosyaKapat();
		return true;
	}
	
	public boolean checkOut(int revizyonNumarasi){
		if (Sunucu.DEBUG)
			System.out.println("revizyon Numarasi:"+revizyonNumarasi);
		
		
		AgDosyaSun sunulacakDosya = new AgDosyaSun(this.calismaKlasoru+File.separatorChar+"temp"+File.separatorChar+"checkOut.r"+this.revizyonNumarasi+".zip");
		try {
			sunulacakDosya.dosyaSun();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		
	}

	public static void main(String[] args) {
		Sunucu sunucu = new Sunucu();

		if (args.length != 0) {
			if (DEBUG)
				System.out.println("arguman alindi :" + args[0]
						+ ": uzunlugu :" + args.length);
			if (args[0].equals("baslat")) {
				sunucu.baslangicIslemleri();
			}
		}

		
		ServerSocket servsock = null;
		try {
			servsock = new ServerSocket(DEFAULTPORT);
			byte istek[] = new byte[5];
			while (true) {
				if (Sunucu.DEBUG)
					System.out.println("Waiting...");

				Socket sock = servsock.accept();
				System.out.println("Accepted connection : " + sock);
				InputStream is = sock.getInputStream();
				is.read(istek, 0, 1);
				if (istek[0] == Sunucu.COMMIT) {
					servsock.close();
					String commiterIP = sock.getInetAddress().getHostAddress()
							.toString();
					sock.close();
					if (Sunucu.DEBUG)
						System.out.println(commiterIP);
					sunucu.commit(commiterIP);
					servsock = new ServerSocket(DEFAULTPORT);
				}
				if (istek[0] == Sunucu.CHECKOUT) {
					servsock.close();
					sock.close();
					sunucu.checkOut(sunucu.revizyonNumarasi);
					servsock = new ServerSocket(DEFAULTPORT);
				}
			}
		} catch (IOException e) {
			System.out.println("soket acilamadi");
			
			System.exit(1);
		}

		/*
		 * Dosya �ekme Denemesi AgDosyaCek cekilenDosya = new
		 * AgDosyaCek(sunucu.calismaKlasoru
		 * +File.separatorChar+"istemci-r"+sunucu.revizyonNumarasi+".zip"); try
		 * { cekilenDosya.dosyaCek("192.168.1.41"); } catch (IOException e) {
		 * 
		 * e.printStackTrace(); }
		 */

		/*
		 * dosya sunma denemesi AgDosyaSun sunulanDosya = new
		 * AgDosyaSun("ayarlar.cfg"); try { sunulanDosya.dosyaSun(); } catch
		 * (IOException e) { e.printStackTrace(); }
		 */

		/*
		 * String dosyaAdi = new String("dosya.txt"); int DosyaRevizyonu = 1;
		 * sunucu.patchDosyayaYazdir(dosyaAdi,DosyaRevizyonu);
		 * 
		 * String patchDosyasiAdi = new String("dosya.txt"); int
		 * patchDosyasiRevizyonu = 1; Patch patch2 =
		 * sunucu.patchDosyadanOku(patchDosyasiAdi,patchDosyasiRevizyonu);
		 * sunucu.patchEkranaYazdir(patch2);
		 */
	}
	
public List<File> ozelBul(List<File> files){
	List<File> unik = new ArrayList<File>();
	for (File a: files){
		if (a.getName().lastIndexOf(".r") != -1){
			if (!unik.contains(new File(a.getAbsolutePath().substring(0, a.getAbsolutePath().lastIndexOf(".r")))))
				unik.add(new File(a.getAbsolutePath().substring(0, a.getAbsolutePath().lastIndexOf(".r"))));
			
		}
	}
	for (File a: unik){
		if (Sunucu.DEBUG)
			System.out.println(a.getName());
	}
	return unik;
}
}
