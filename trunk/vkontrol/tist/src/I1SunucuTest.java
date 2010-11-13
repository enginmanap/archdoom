import java.util.List;
import junit.framework.TestCase;
import org.junit.Before;
import difflib.Delta;


public class I1SunucuTest extends TestCase{

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	public void testDosyaDizinOkuma(){
		Sunucu sunucu = new Sunucu();
		assertEquals(true, sunucu.varMi("deneme.txt"));
		assertEquals(false, sunucu.varMi("mesutcank.txt"));
		assertEquals(true, sunucu.varMi("src"));
		
		assertEquals(true, sunucu.dosyaMi("deneme.txt"));
		assertEquals(true, sunucu.dizinMi("src"));
		
		assertEquals(true, sunucu.okunabilirMi("deneme.txt"));
		assertEquals(true, sunucu.okunabilirMi("src"));
		
		assertNotNull(sunucu.satirOku("deneme.txt"));
		assertNull(sunucu.satirOku("mesutcan.txt"));
	}
	
	public void testFarkAlma(){
		Sunucu sunucu = new Sunucu();
		
		List<Delta> fark = sunucu.farkAl("deneme.txt", "mesutcan.txt");
		
		assertNotNull(fark);
		
	}
}
