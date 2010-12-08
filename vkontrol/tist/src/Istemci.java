import java.io.File;


public class Istemci {
	private String dosyaYolu;
	private String sunucuIP;
	public Istemci(String IP) {
		this.dosyaYolu = System.getProperty("user.dir"); //O anki dizini geri döndürür
		System.out.println("dosyaYaolu="+dosyaYolu);
		this.sunucuIP = IP;
	}

	public String getDosyaYolu() {
		return dosyaYolu;
	}
	
	
	public String getIp() {
		return sunucuIP;
	}

	public void baslangicIslemleri() {
		File dosya = new File(dosyaYolu+"\\.tist");
		dosya.mkdir();
		YazilacakMetinDosya sunucuIPDosya= new YazilacakMetinDosya(dosyaYolu+"\\.tist\\sunucu.txt");
		sunucuIPDosya.satirYaz(sunucuIP);
	}
	
	public static void main(String[] args) {
				
	}

}
