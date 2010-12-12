


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
		//FIXME:  Eger win ise \ ile linux ise / ile dosyaYolu bulunmali. separatorChar
		DizinOlustur dizin = new DizinOlustur(dosyaYolu+"/.tist");
		dizin.olustur();
		YazilacakMetinDosya sunucuIPDosya= new YazilacakMetinDosya(dosyaYolu+"/.tist/sunucu.txt");
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
