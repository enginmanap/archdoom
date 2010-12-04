import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import org.junit.Before;

public class DosyaTest extends TestCase {

	@Before
	public void setUp() throws Exception {
	}
	
	public void testDosyaYazma(){
		MockWriter sahteYazici = null;
		try {
			sahteYazici = new MockWriter();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		sahteYazici.addExpectedLine("engin");
		sahteYazici.addExpectedLine("mustafa");
		sahteYazici.addExpectedLine("mesutcan");
		sahteYazici.addExpectedLine("semih");
		
		Dosya deneme = new Dosya("deneme.txt");
		deneme.setWriter(sahteYazici);
		
		deneme.satirYaz("engin");
		deneme.satirYaz("mustafa");
		deneme.satirYaz("mesutcan");
		deneme.satirYaz("semih");
		
		sahteYazici.verify();
	}
	public void testDosyaOkuma(){
		List<String> dummyFile = new ArrayList<String>();
		dummyFile.add("engin");
		dummyFile.add("mustafa");
		dummyFile.add("mesutcan");
		dummyFile.add("semih");
		MockReader sahteOkuyucu = null;
		try {
			sahteOkuyucu = new MockReader(dummyFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		sahteOkuyucu.addExpectedLine("engin");
		sahteOkuyucu.addExpectedLine("mustafa");
		sahteOkuyucu.addExpectedLine("mesutcan");
		sahteOkuyucu.addExpectedLine("semih");
		
		Dosya deneme = new Dosya("deneme.txt");
		deneme.setReader(sahteOkuyucu);
		
		assertEquals("engin", deneme.satirOku());
		assertEquals("mustafa", deneme.satirOku());
		assertEquals("mesutcan", deneme.satirOku());
		assertEquals("semih", deneme.satirOku());
	}
}
