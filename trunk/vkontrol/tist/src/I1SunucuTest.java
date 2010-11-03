import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import junit.framework.TestCase;

import org.junit.Before;


public class I1SunucuTest extends TestCase{

	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	public void testDosyaOkuma(){
		File dosya = new File("deneme.txt");
		File dosya2 = new File("olmayan.dosya");
		assertEquals(true, dosya.exists());
		assertEquals(false, dosya2.exists());
		
		
		
	}
}
