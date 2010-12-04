import junit.framework.TestCase;
import org.junit.Before;
import difflib.Delta;
import difflib.Patch;


public class SunucuTest extends TestCase{

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}
	
	public void testFarkAlma(){
		Sunucu sunucu = new Sunucu();
		
		YazilacakDosya ilkDosya = new YazilacakDosya("ilkdosya.txt");
		ilkDosya.satirYaz("engin");
		ilkDosya.satirYaz("mustafa");
		ilkDosya.satirYaz("mesutcan");
		ilkDosya.satirYaz("semih");
		
		YazilacakDosya ikinciDosya = new YazilacakDosya("ikincidosya.txt");
		ikinciDosya.satirYaz("engin");
		ikinciDosya.satirYaz("mustafa");
		ikinciDosya.satirYaz("mesutcan");
		ikinciDosya.satirYaz("semih");
		
		
		Patch fark = sunucu.farkAl("ilkdosya.txt", "ikincidosya.txt");
		
		for(int i=0;i<fark.getDeltas().size();i++){
			
			System.out.println(fark.getDelta(i));
		}
		assertNotNull(fark);
		
	}
}
