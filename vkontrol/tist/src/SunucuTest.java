import java.io.File;

import junit.framework.TestCase;
import org.junit.Before;
import difflib.Patch;



public class SunucuTest extends TestCase{
	
	

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	public void testPatchUygula(){
		Sunucu sunucu = new Sunucu();
		
		
		YazilacakMetinDosya ilkYazilacakDosya = new YazilacakMetinDosya(sunucu.getCalismaKlasoru()+File.separatorChar+"Head"+File.separatorChar+"denemedosya.txt");
		ilkYazilacakDosya.satirYaz("engin");
		ilkYazilacakDosya.satirYaz("mustafa");
		ilkYazilacakDosya.satirYaz("mesutcan");
		ilkYazilacakDosya.satirYaz("semih");
		ilkYazilacakDosya.dosyaKapat();

		YazilacakMetinDosya ikinciYazilacakDosya = new YazilacakMetinDosya(sunucu.getCalismaKlasoru()+File.separatorChar+"Temp"+File.separatorChar+"denemedosya.txt");
		ikinciYazilacakDosya.satirYaz("engin");
		//ikinciYazilacakDosya.satirYaz("mustafa");
		ikinciYazilacakDosya.satirYaz("mesutcan");
		ikinciYazilacakDosya.satirYaz("semih");
		ikinciYazilacakDosya.dosyaKapat();

		
		sunucu.patchDosyayaYazdir("denemedosya.txt",1);
				
		Patch okunanPatch = sunucu.patchDosyadanOku("denemedosya.txt",1);
		if (Sunucu.DEBUG)
			sunucu.patchEkranaYazdir(okunanPatch);
		
		// patch islemi basarili oldumu testi
		assertTrue(sunucu.patchUygula(sunucu.getCalismaKlasoru()+File.separatorChar+"Head"+File.separatorChar+"denemedosya.txt", okunanPatch));
		
		// patch islemi sonrasi dosya icerigi testi
		OkunacakMetinDosya head = new OkunacakMetinDosya(sunucu.getCalismaKlasoru()+File.separatorChar+"Head"+File.separatorChar+"denemedosya.txt");
		assertEquals("engin", head.satirOku());
//		assertEquals("mustafa", head.satirOku());
		assertEquals("mesutcan", head.satirOku());
		assertEquals("semih", head.satirOku());
		
	}
}
