import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;




public class Istemci {
	
	private static String COMMITZIPISMI = "committed.zip";
	
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
			if (args[0].equals("yolla")){
				OkunacakMetinDosya sunucutxt = new OkunacakMetinDosya(System.getProperty("user.dir")+File.separatorChar+".tist"+File.separatorChar+"sunucu.txt");
				String sunucuIP = sunucutxt.satirOku();
				istemci = new Istemci(sunucuIP);
				Zip yollanacakZip = new Zip(istemci.getDosyaYolu(), COMMITZIPISMI);
				yollanacakZip.ziple();
				byte[] bytedizi = new byte[5];
				bytedizi[0] = 1;
				
				
				try {
					Socket socket = new Socket(sunucuIP, Sunucu.DEFAULTPORT);
					if (Sunucu.DEBUG)
						System.out.println("Bekleniyor");
					// eger baska bir atama yapimadi ise otomatik olarak agdan yollanacak
					OutputStream cikisAkisi = null;
					if (cikisAkisi==null){
						
						if (Sunucu.DEBUG)
							System.out.println("Baglant� kabul edildi : " + socket);
							cikisAkisi = socket.getOutputStream();
						}
						if (Sunucu.DEBUG)
							System.out.println("Dosya yoll");
						cikisAkisi.write(bytedizi,0,bytedizi.length);
						cikisAkisi.flush();
						cikisAkisi.close();
						socket.close();
				
						AgDosyaSun agdanYollanacakDosya = new AgDosyaSun(System.getProperty("user.dir")+File.separatorChar+".tist"+File.separatorChar+COMMITZIPISMI);
						agdanYollanacakDosya.dosyaSun();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
			}
		}
		
	}

}
