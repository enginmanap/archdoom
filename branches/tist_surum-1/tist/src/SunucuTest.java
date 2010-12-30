import java.io.File;
import java.util.List;

import junit.framework.TestCase;
import org.junit.Before;
import difflib.Patch;



public class SunucuTest extends TestCase{
	
	

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	public void testDosyadanSatira(){
		Sunucu sunucu = new Sunucu();
		YazilacakMetinDosya ilkYazilacakDosya = new YazilacakMetinDosya("denemedosya.txt");
		ilkYazilacakDosya.satirYaz("engin");
		ilkYazilacakDosya.satirYaz("mustafa");
		ilkYazilacakDosya.satirYaz("mesutcan");
		ilkYazilacakDosya.satirYaz("semih");
		ilkYazilacakDosya.dosyaKapat();
		
		//dosya ismi alan icin
		List<String> satirlar = sunucu.dosyadanSatira("denemedosya.txt");
		assertEquals("engin", satirlar.get(0));
		assertEquals("mustafa", satirlar.get(1));
		assertEquals("mesutcan", satirlar.get(2));
		assertEquals("semih", satirlar.get(3));
		
		
		//okunacakMetin Dosya alanicin
		OkunacakMetinDosya deneme = new OkunacakMetinDosya("denemedosya.txt");
		List<String> satirlar2 = sunucu.dosyadanSatira(deneme);
		assertEquals("engin", satirlar2.get(0));
		assertEquals("mustafa", satirlar2.get(1));
		assertEquals("mesutcan", satirlar2.get(2));
		assertEquals("semih", satirlar2.get(3));
	}
	
	public void testPatchUygula(){
		Sunucu sunucu = new Sunucu();

		/*
		YazilacakMetinDosya ilkYazilacakDosya = new YazilacakMetinDosya(sunucu.getCalismaKlasoru()+File.separatorChar+"Head"+File.separatorChar+"denemedosya.txt");
		ilkYazilacakDosya.satirYaz("engin");
		ilkYazilacakDosya.satirYaz("mustafa");
		ilkYazilacakDosya.satirYaz("mesutcan");
		ilkYazilacakDosya.satirYaz("semih");
		ilkYazilacakDosya.dosyaKapat();
		*/
		
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
		assertNull( head.satirOku());
		
	}
}
