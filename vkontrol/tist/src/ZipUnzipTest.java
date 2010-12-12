import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;



public class ZipUnzipTest extends TestCase {
	public void testZip(){
//		Zip zip = new Zip(System.getProperty("user.dir"));
		List<String> dummyFile = new ArrayList<String>();
		dummyFile.add("engin.txt");
		dummyFile.add("mustafa.txt");
		dummyFile.add("mesutcan.txt");
		dummyFile.add("semih.txt");
		MockZip sahteZip = new MockZip(dummyFile);
		sahteZip.addExpectedLine("engin.txt");
		sahteZip.addExpectedLine("mustafa.txt");
		sahteZip.addExpectedLine("mesutcan.txt");
		sahteZip.addExpectedLine("semih.txt");
		assertEquals("engin.txt", sahteZip.ziple());
		assertEquals("mustafa.txt", sahteZip.ziple());
		assertEquals("mesutcan.txt", sahteZip.ziple());
		assertEquals("semih.txt", sahteZip.ziple());
		
		
		sahteZip.verify();
		
//		zip.ziple();
	}

}
