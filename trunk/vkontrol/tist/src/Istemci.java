import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Istemci {
	
	private static String COMMITZIPISMI = "committed.zip";
	private int revizyon;
	private String dosyaYolu;
	private String sunucuIP;
	public Istemci(String IP) {
		this.dosyaYolu = System.getProperty("user.dir"); //O anki dizini geri dondurur
		this.sunucuIP = IP;
		this.setRevizyon(0);
	}

	public String getDosyaYolu() {
		return dosyaYolu;
	}
	
	public void setRevizyon(int revizyon) {
		this.revizyon = revizyon;
	}

	public int getRevizyon() {
		return revizyon;
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
	
	public static void getir(Istemci istemci, String[] args, byte[] byteDizi) {
		istemci = new Istemci(args[1]);
		istemci.baslangicIslemleri();
		int revizyon=0;
		byteDizi[0] = Sunucu.CHECKOUT;
		if (args.length == 2){
			byteDizi[1]=0;byteDizi[2]=0;byteDizi[3]=0;byteDizi[4]=0;
		}
		else if (args.length == 3){			
			byteDizi[0] = Sunucu.CHECKOUT;
			revizyon = Integer.parseInt(args[2]);
			byteDizi[4] = (byte) (revizyon%8);
			revizyon /= 8;
			byteDizi[3] = (byte) (revizyon%8);
			revizyon /= 8;
			byteDizi[2] = (byte) (revizyon%8);
			revizyon /= 8;
			byteDizi[1] = (byte) (revizyon%8);
		}
		
		istekYolla(args[1], byteDizi);	
		
		istekDinle(istemci);
		
		if (istemci.revizyon != 0)
			dosyaCek(istemci, args[1], revizyon);
	}
	
	public static void yolla(Istemci istemci, byte[] byteDizi) {
		OkunacakMetinDosya sunucutxt = new OkunacakMetinDosya(System.getProperty("user.dir")+File.separatorChar+".tist"+File.separatorChar+"sunucu.txt");
		String sunucuIP = sunucutxt.satirOku();
		istemci = new Istemci(sunucuIP);
		Zip yollanacakZip = new Zip(istemci.getDosyaYolu(), istemci.getDosyaYolu()+File.separatorChar+".tist"+File.separatorChar+COMMITZIPISMI);
		yollanacakZip.ziple();
		byteDizi[0] = Sunucu.COMMIT;

		
		istekYolla(sunucuIP, byteDizi);
		
		dosyaYolla();
		

		
	}

	public static void dosyaYolla() {
		AgDosyaSun agdanYollanacakDosya = new AgDosyaSun(System.getProperty("user.dir")+File.separatorChar+".tist"+File.separatorChar+COMMITZIPISMI);
		
		try {
			agdanYollanacakDosya.dosyaSun();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		File yollanan = new File(System.getProperty("user.dir")+File.separatorChar+".tist"+File.separatorChar+COMMITZIPISMI);
		yollanan.delete();
	}

	public static void istekYolla(String sunucuIP, byte[] byteDizi) {
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
				cikisAkisi.write(byteDizi,0,byteDizi.length);
				cikisAkisi.flush();
				cikisAkisi.close();
				socket.close();
		
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public static void istekDinle(Istemci istemci){
		ServerSocket servsock = null;
		try {
			servsock = new ServerSocket(Sunucu.DEFAULTPORT);
			byte gelen[] = new byte[4];
			if (Sunucu.DEBUG)
				System.out.println("Revision No Waiting...");
			Socket sock = servsock.accept();
			System.out.println("Accepted connection : " + sock);
			InputStream is = sock.getInputStream();
			is.read(gelen, 0, gelen.length);
			istemci.setRevizyon(gelen[3]*8*8*8+gelen[2]*8*8+gelen[1]*8+gelen[0]);
			servsock.close();
		} catch (IOException e) {
			System.out.println("soket acilamadi");
		}
		
	}

	public static void dosyaCek(Istemci istemci, String sunucuIP, int revizyon) {
		AgDosyaCek gelenDosya = new AgDosyaCek(System.getProperty("user.dir")+File.separatorChar+"gelendosya"+istemci.revizyon+".zip");
		try {
			gelenDosya.dosyaCek(sunucuIP);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Unzip gelen = new Unzip(System.getProperty("user.dir"), System.getProperty("user.dir")+File.separatorChar+"gelendosya"+istemci.revizyon+".zip");
		gelen.unziple();
		File gelenZip = new File(System.getProperty("user.dir")+File.separatorChar+"gelendosya"+istemci.revizyon+".zip");
		gelenZip.delete();
		
	}



public static void main(String[] args) {
	Istemci istemci = null;
	byte[] byteDizi = new byte[5];
	if (args.length != 0){
		if (args[0].equals("getir"))		
			getir(istemci, args, byteDizi);						
		else if (args[0].equals("yolla"))
			yolla(istemci, byteDizi);
	}		
}

}