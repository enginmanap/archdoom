import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;

public class NesneDosyaOkuYazTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}
	
	public void testDosyaYazma(){
		MockObjectWriter sahteYazici = null;
		try {
			sahteYazici = new MockObjectWriter();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sahteYazici.addExpectedLine("engin");
		sahteYazici.addExpectedLine("mustafa");
		sahteYazici.addExpectedLine("mesutcan");
		sahteYazici.addExpectedLine("semih");
		Integer sayi = new Integer(15);
		sahteYazici.addExpectedLine(sayi);
		
		YazilacakNesneDosya deneme = new YazilacakNesneDosya("deneme.obj");
		deneme.setWriter(sahteYazici);
		
		
		
		deneme.satirYaz("engin");
		deneme.satirYaz("mustafa");
		deneme.satirYaz("mesutcan");
		deneme.satirYaz("semih");
		deneme.satirYaz(sayi);
		
		sahteYazici.verify();
	}
	
	public void testDosyaOkuma(){
		List<Object> dummyFile = new ArrayList<Object>();
		Integer sayi = new Integer(15);
		dummyFile.add("engin");
		dummyFile.add("mustafa");
		dummyFile.add("mesutcan");
		dummyFile.add("semih");
		dummyFile.add(sayi);
		MockObjectReader sahteOkuyucu = null;
		try {
			sahteOkuyucu = new MockObjectReader(dummyFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sahteOkuyucu.addExpectedLine("engin");
		sahteOkuyucu.addExpectedLine("mustafa");
		sahteOkuyucu.addExpectedLine("mesutcan");
		sahteOkuyucu.addExpectedLine("semih");
		sahteOkuyucu.addExpectedLine(sayi);
		
		OkunacakNesneDosya deneme = new OkunacakNesneDosya("deneme.obj");
		deneme.setReader(sahteOkuyucu);
		
		assertEquals("engin", deneme.satirOku());
		assertEquals("mustafa", deneme.satirOku());
		assertEquals("mesutcan", deneme.satirOku());
		assertEquals("semih", deneme.satirOku());
		assertEquals(sayi, deneme.satirOku());
		sahteOkuyucu.verify();
	}
}
