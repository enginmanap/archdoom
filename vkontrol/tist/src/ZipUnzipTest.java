import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;



public class ZipUnzipTest extends TestCase {
	public void testZip(){
		String zipName = "ornek.zip";
//		Zip zip = new Zip(System.getProperty("user.dir"), zipName);
		
		List<String> dummyFile = new ArrayList<String>();
		dummyFile.add("engin.txt");
		dummyFile.add("mustafa.txt");
		dummyFile.add("mesutcan.txt");
		dummyFile.add("semih.txt");
		MockZip sahteZip = new MockZip(dummyFile, zipName);
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
	
	public void testUnzip(){
//		Unzip unzip = new Unzip(System.getProperty("user.dir"), "ornek.zip");
		List<String> dummyFile = new ArrayList<String>();
		dummyFile.add("engin.txt");
		dummyFile.add("mustafa.txt");
		dummyFile.add("mesutcan.txt");
		dummyFile.add("semih.txt");
		MockUnzip sahteUnzip = new MockUnzip(dummyFile);
		sahteUnzip.addExpectedLine("engin.txt");
		sahteUnzip.addExpectedLine("mustafa.txt");
		sahteUnzip.addExpectedLine("mesutcan.txt");
		sahteUnzip.addExpectedLine("semih.txt");
		assertEquals("engin.txt", sahteUnzip.unziple());
		assertEquals("mustafa.txt", sahteUnzip.unziple());
		assertEquals("mesutcan.txt", sahteUnzip.unziple());
		assertEquals("semih.txt", sahteUnzip.unziple());
		
		
		sahteUnzip.verify();
		
//		unzip.unziple();
	}

}
