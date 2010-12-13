import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;


public class MetinDosyaOkuYazTest extends TestCase {
	
	public void testMetinDosyaYazma(){
		MockTextWriter sahteYazici = null;
		try {
			sahteYazici = new MockTextWriter();
		} catch (FileNotFoundException e) {
			System.out.println("Mock dosya yazmak icin bulunamadi");
		}
		
		sahteYazici.addExpectedLine("engin");
		sahteYazici.addExpectedLine("mustafa");
		sahteYazici.addExpectedLine("mesutcan");
		sahteYazici.addExpectedLine("semih");
		
		YazilacakMetinDosya deneme = new YazilacakMetinDosya("deneme.txt");
		deneme.setWriter(sahteYazici);
		
		deneme.satirYaz("engin");
		deneme.satirYaz("mustafa");
		deneme.satirYaz("mesutcan");
		deneme.satirYaz("semih");
		
		sahteYazici.verify();
		
		
		
		
	}
	
	public void testMetinDosyaOkuma(){
		MockTextReader sahteOkuyucu = null;
		List<String> dummyFile = new ArrayList<String>();
		dummyFile.add("engin");
		dummyFile.add("mustafa");
		dummyFile.add("mesutcan");
		dummyFile.add("semih");
		try {
			sahteOkuyucu = new MockTextReader(dummyFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		sahteOkuyucu.addExpectedLine("engin");
		sahteOkuyucu.addExpectedLine("mustafa");
		sahteOkuyucu.addExpectedLine("mesutcan");
		sahteOkuyucu.addExpectedLine("semih");
		
		OkunacakMetinDosya deneme = new OkunacakMetinDosya("deneme.txt");
		deneme.setReader(sahteOkuyucu);
		assertEquals("engin", deneme.satirOku());
		assertEquals("mustafa", deneme.satirOku());
		assertEquals("mesutcan", deneme.satirOku());
		assertEquals("semih", deneme.satirOku());
		sahteOkuyucu.verify();
		
	}
	
}
