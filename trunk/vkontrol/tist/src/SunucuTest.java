import java.util.List;

import junit.framework.TestCase;

import org.junit.Before;

import difflib.Patch;


/* change to için tostring
"[ChangeDelta, position: " + fark.getDelta(i).getOriginal().getPosition() + ", lines: "
+ fark.getDelta(i).getOriginal().getLines() + " to " + fark.getDelta(i).getRevised().getLines() + "]"
*/

public class SunucuTest extends TestCase{

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	public void testFarkAlma(){
		Sunucu sunucu = new Sunucu();
		
		YazilacakMetinDosya ilkYazilacakDosya = new YazilacakMetinDosya(Sunucu.getCalismaKlasoru()+"\\Temp\\denemedosya.txt");
		ilkYazilacakDosya.satirYaz("engin");
		ilkYazilacakDosya.satirYaz("mustafa");
		ilkYazilacakDosya.satirYaz("mesutcan");
		ilkYazilacakDosya.satirYaz("semih");
		
		YazilacakMetinDosya ikinciYazilacakDosya = new YazilacakMetinDosya(Sunucu.getCalismaKlasoru()+"\\Head\\denemedosya.txt");
		ikinciYazilacakDosya.satirYaz("engin");
		//ikinciYazilacakDosya.satirYaz("mustafa");
		ikinciYazilacakDosya.satirYaz("mesutcan");
		ikinciYazilacakDosya.satirYaz("semih");
		sunucu.patchDosyayaYazdir("denemedosya.txt",1);
		
		OkunacakMetinDosya ilkOkunacakDosya = new OkunacakMetinDosya(Sunucu.getCalismaKlasoru()+"\\Temp\\denemedosya.txt");
		OkunacakMetinDosya ikinciOkunacakDosya = new OkunacakMetinDosya(Sunucu.getCalismaKlasoru()+"\\head\\denemedosya.txt");
		
		Patch fark = sunucu.farkAl(ilkOkunacakDosya, ikinciOkunacakDosya);
		Patch okunanFark = sunucu.patchDosyadanOku("denemedosya.txt", 1);
		sunucu.patchEkranaYazdir(fark);
		sunucu.patchEkranaYazdir(okunanFark);
		assertTrue(fark.equals(okunanFark));
		//Sunucu.patchEkranaYazdir();
		
		

	}
}
