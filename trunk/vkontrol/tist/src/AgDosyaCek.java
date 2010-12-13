import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class AgDosyaCek {
		private String dosyaAdi;
		private InputStream girisAkisi = null;
		
		public void setGirisAkisi(InputStream girisAkisi) {
			this.girisAkisi = girisAkisi;
		}
		
		
		public AgDosyaCek(String dosyaAdi){
			this.dosyaAdi = dosyaAdi;
		}
		
	public boolean dosyaCek(String sunucuIP) throws IOException{
	    int filesize=6022386; // filesize temporary hardcoded

	    long start = System.currentTimeMillis();
	    int bytesRead;
	    int current = 0;
	    // localhost for testing
	    Socket sock;
		try {
			sock = new Socket(sunucuIP,Sunucu.DEFAULTPORT);
		} catch (UnknownHostException e) {
			System.out.println("Host bulunamadi");
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			System.out.println("Genel giris cikis hatasi");
			e.printStackTrace();
			return false;
		}
		if(Sunucu.DEBUG)
			System.out.println("Connecting...");

	    // receive file
	    byte [] mybytearray  = new byte [filesize];
	    if(girisAkisi==null)
	    	girisAkisi = sock.getInputStream();
	    FileOutputStream fos = new FileOutputStream(dosyaAdi);
	    BufferedOutputStream bos = new BufferedOutputStream(fos);
	    bytesRead = girisAkisi.read(mybytearray,0,mybytearray.length);
	    current = bytesRead;

	    // thanks to A. Cádiz for the bug fix
	    do {
	       bytesRead =
	          girisAkisi.read(mybytearray, current, (mybytearray.length-current));
	       if(bytesRead >= 0) current += bytesRead;
	    } while(bytesRead > -1);
	    
	    bos.write(mybytearray, 0 , current);
	    bos.flush();
	    long end = System.currentTimeMillis();
	    if(Sunucu.DEBUG)
	    	System.out.println(end-start);
	    bos.close();
	    sock.close();
	    return true;
	}
	
}
