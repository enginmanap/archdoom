
import junit.framework.TestCase;

import org.junit.Before;


public class IstemciTest extends TestCase{

	@Before
	public void setUp() throws Exception {
	}
	
	public void testBaslangicIslemleri(){
		Istemci istemci = new Istemci("127.0.0.1");
		assertEquals("127.0.0.1", istemci.getIp());
		istemci.baslangicIslemleri();
	}
	
	
	
}
