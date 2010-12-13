import junit.framework.TestCase;


public class DizinOlusturTest extends TestCase{
		
	public void testDizinOlustuMu(){
		
		String dosyaYolu1 = System.getProperty("user.dir")+"\\.tist";
		
//		MockDizinOlustur sahteDizin = new MockDizinOlustur(dosyaYolu1);
//		sahteDizin.addExpectedLine(dosyaYolu1);
//		sahteDizin.olustur();
		
		DizinOlustur dizin = new DizinOlustur(dosyaYolu1);
		assertEquals(dosyaYolu1, dizin.olustur());
		
//		sahteDizin.verify();
		
	}
}
