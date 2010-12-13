import java.io.File;




public class Istemci {
	private String dosyaYolu;
	private String sunucuIP;
	public Istemci(String IP) {
		this.dosyaYolu = System.getProperty("user.dir"); //O anki dizini geri dondurur
		this.sunucuIP = IP;
	}

	public String getDosyaYolu() {
		return dosyaYolu;
	}
	
	
	public String getIp() {
		return sunucuIP;
	}

	public void baslangicIslemleri() {
		DizinOlustur dizin = new DizinOlustur(dosyaYolu+File.separatorChar+".tist");
		dizin.olustur();
		YazilacakMetinDosya sunucuIPDosya= new YazilacakMetinDosya(dosyaYolu+File.separatorChar+".tist"+File.separatorChar+"sunucu.txt");
		sunucuIPDosya.satirYaz(sunucuIP);
	}
	
	public static void main(String[] args) {
		Istemci istemci = null;
		if (args.length != 0){
			if (args[0].equals("getir")){
				if (args.length < 2)
					System.exit(1);
				
				istemci = new Istemci(args[1]);
				istemci.baslangicIslemleri();
				
			}
		}
		
	}

}
