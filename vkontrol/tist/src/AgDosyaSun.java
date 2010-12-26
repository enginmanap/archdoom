import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class AgDosyaSun {
	private byte[] myByteArray;
	private OutputStream cikisAkisi = null;
	
	public void setCikisAkisi(OutputStream cikisAkisi) {
		this.cikisAkisi = cikisAkisi;
	}

	private byte[] yerelDosyayiOku(String dosyaYolu) throws FileNotFoundException, IOException {
		File myFile = new File (dosyaYolu);
		byte [] mybytearray  = new byte [(int)myFile.length()];
		FileInputStream fis = new FileInputStream(myFile);
		BufferedInputStream bis = new BufferedInputStream(fis);
		bis.read(mybytearray,0,mybytearray.length);
		return mybytearray;
	}
	
	public void setMyByteArray(byte[] myByteArray) {
		this.myByteArray = myByteArray;
	}

	public AgDosyaSun(String dosyaYolu){
		try {
			setMyByteArray(yerelDosyayiOku(dosyaYolu));
		} catch (FileNotFoundException e) {
			System.out.println("yerel "+dosyaYolu+" dosyasi bulunamadi.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("yerel "+dosyaYolu+" dosyasi icin genel giris cikis hatasi.");
			e.printStackTrace();
		}
	}
	
	public void dosyaSun() throws IOException {

		ServerSocket serverSocket = new ServerSocket(Sunucu.DEFAULTPORT);
			if (Sunucu.DEBUG)
				System.out.println("Dosya sunmak icin bekleniyor");
			// eger baska bir atama yapimadi ise otomatik olarak agdan yollanacak
			Socket sock = null;
			if (cikisAkisi==null){
				sock = serverSocket.accept();
				if (Sunucu.DEBUG)
					System.out.println("Dosya sunmak icin baglantý kabul edildi : " + sock);
					cikisAkisi = sock.getOutputStream();
				}
				if (Sunucu.DEBUG)
					System.out.println("Dosya yollanýyor");
				cikisAkisi.write(myByteArray,0,myByteArray.length);
				cikisAkisi.flush();
				cikisAkisi.close();
				if (sock!=null)
					sock.close();
				serverSocket.close();
	}
}