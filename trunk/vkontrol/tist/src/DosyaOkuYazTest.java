import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;

public class DosyaOkuYazTest extends TestCase {

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
		
		YazilacakNesneDosya deneme = new YazilacakNesneDosya("deneme.txt");
		//deneme.setWriter(sahteYazici);
		
		deneme.satirYaz("engin");
		deneme.satirYaz("mustafa");
		deneme.satirYaz("mesutcan");
		deneme.satirYaz("semih");
		
		//sahteYazici.verify();
	}
	
	public void testDosyaOkuma(){
		List<Object> dummyFile = new ArrayList<Object>();
		dummyFile.add("engin");
		dummyFile.add("mustafa");
		dummyFile.add("mesutcan");
		dummyFile.add("semih");
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
		
		OkunacakNesneDosya deneme = new OkunacakNesneDosya("deneme.txt");
		//deneme.setReader(sahteOkuyucu);
		assertEquals("engin", deneme.satirOku());
		assertEquals("mustafa", deneme.satirOku());
		assertEquals("mesutcan", deneme.satirOku());
		assertEquals("semih", deneme.satirOku());
		//sahteOkuyucu.verify();
	}
}
