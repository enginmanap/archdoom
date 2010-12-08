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
		
		YazilacakMetinDosya ilkDosya = new YazilacakMetinDosya("ilkdosya.txt");
		ilkDosya.satirYaz("engin");
		ilkDosya.satirYaz("mustafa");
		//ilkDosya.satirYaz("mesutcan");
		ilkDosya.satirYaz("semih");
		
		YazilacakMetinDosya ikinciDosya = new YazilacakMetinDosya("ikincidosya.txt");
		ikinciDosya.satirYaz("engin");
		//ikinciDosya.satirYaz("mustafa");
		ikinciDosya.satirYaz("mesutcan");
		ikinciDosya.satirYaz("semih");
		
		
		Patch fark = sunucu.farkAl("ilkdosya.txt", "ikincidosya.txt");
		
		for(int i=0;i<fark.getDeltas().size();i++){
			System.out.println("okunan dosya:");
			// ChangeDelta sýnýfýnýn, toString() metodu eklenmemiþ, bu yuzden boyle bir etrafýndan dolaþma kullanýyoruz.
			if (fark.getDelta(i).getClass().getName() == "difflib.ChangeDelta")
				System.out.println("[ChangeDelta, position: " + fark.getDelta(i).getOriginal().getPosition() + ", lines: "
	                + fark.getDelta(i).getOriginal().getLines() + " to " + fark.getDelta(i).getRevised().getLines() + "]");
			else
				System.out.println(fark.getDelta(i));
		}
		

	}
}
