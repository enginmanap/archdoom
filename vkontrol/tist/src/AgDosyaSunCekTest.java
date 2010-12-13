import java.io.IOException;

import junit.framework.TestCase;



public class AgDosyaSunCekTest extends TestCase {
	
	public void testDosyaSun() {
		MockCikisAkisi sahtecikis = new MockCikisAkisi();
		AgDosyaSun agSunucu = new AgDosyaSun("ayarlar.cfg");
		byte[] testByteArray = new byte[10];
		byte i = 1;
		for (@SuppressWarnings("unused") byte newByte:testByteArray){
			newByte = (byte) (i+1);
		}
		if(Sunucu.DEBUG)
			System.out.println("testByteArray = "+testByteArray);
		sahtecikis.addExpectedList(testByteArray);
			
			
		agSunucu.setMyByteArray(testByteArray);
		
		agSunucu.setCikisAkisi(sahtecikis);
		try {
			agSunucu.dosyaSun();
		} catch (IOException e) {
			System.out.println("dosya sunulamadi");
			e.printStackTrace();
		}
		
		sahtecikis.verify();
	}
	public void testDosyaCek(){
		byte[] testByteArray = new byte[10];
		byte i = 1;
		for (@SuppressWarnings("unused") byte newByte:testByteArray){
			newByte = (byte) (i+1);
		}
		
		AgDosyaCek agIstemci = new AgDosyaCek("agdeneme.txt");
		MockGirisAkisi sahteGirisAkisi = new MockGirisAkisi();
		agIstemci.setGirisAkisi(sahteGirisAkisi);
		
		sahteGirisAkisi.addExpectedList(testByteArray);
		sahteGirisAkisi.addSanalDosya(testByteArray);
		try {
			agIstemci.dosyaCek("127.0.0.1");
		} catch (IOException e) {
			System.out.println("dosya cekme basarisiz");
			e.printStackTrace();
		}
		
		sahteGirisAkisi.verify();
	}
	
}
