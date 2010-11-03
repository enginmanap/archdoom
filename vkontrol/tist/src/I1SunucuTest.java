import static org.junit.Assert.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import junit.framework.TestCase;

import org.junit.Before;


public class I1SunucuTest extends TestCase{

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	public void testDosyaOkuma(){
		File dosya = new File("deneme.txt");
		File dosya2 = new File("olmayan.dosya");
		assertEquals(true, dosya.exists());
		assertEquals(false, dosya2.exists());
		
		File klasor = new File("src");
		assertEquals(true, dosya.isFile());
		assertEquals(false, dosya2.isFile());
		assertEquals(true, dosya.canRead());
		assertEquals(true, klasor.canRead());
		
		//Dosyanin icinde ne yaziyor bakilacak
		FileReader dosyaOkuyucu = null;
		try {
			dosyaOkuyucu = new FileReader(dosya);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BufferedReader girisAkisi = new BufferedReader(dosyaOkuyucu);
		
		//DataInputStream dosyaakisi = new DataInputStream(dosya);
		String satir = null;
		try {
			satir = girisAkisi.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// satir degiskeninde bir satir var artik.
	}
}
