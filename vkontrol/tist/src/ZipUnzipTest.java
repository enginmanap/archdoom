
import java.io.File;

import junit.framework.TestCase;



public class ZipUnzipTest extends TestCase {
	public void testZipUnzip(){
		
		File sil = new File(System.getProperty("user.dir"), "ziple1.zip");
		sil.delete();
		sil = new File(System.getProperty("user.dir"), "ziple2.zip");
		sil.delete();
		
		File[] orig1 = new File(System.getProperty("user.dir")).listFiles();
		File[] orig2 = new File(System.getProperty("user.dir")+File.separatorChar+"src").listFiles();
		
		Zip zip1 = new Zip(System.getProperty("user.dir"), "ziple1.zip");
		zip1.ziple();
		

		
		Zip zip2 = new Zip(System.getProperty("user.dir")+File.separatorChar+"src", "ziple2.zip");
		zip2.ziple();
	
		String dizin1 = System.getProperty("user.dir")+File.separatorChar+"unzip1";
		String dizin2 = System.getProperty("user.dir")+File.separatorChar+"unzip2";
		
		

		
		DizinOlustur dizin = new DizinOlustur(dizin1);
		dizin.olustur();
		dizin = new DizinOlustur(dizin2);
		dizin.olustur();
		Unzip unzip = new Unzip(dizin1, "ziple1.zip");
		unzip.unziple();
		
		File[] zip1List = new File(dizin1).listFiles();

		
		
		Unzip unzip2 = new Unzip(dizin2, "ziple2.zip");
		unzip2.unziple();
		
		File[] zip2List = new File(dizin2).listFiles();
		
		assertEquals(zip1List.length, orig1.length);
		for (int i=0;i<orig1.length;i++){
			assertEquals(zip1List[i].getName(), orig1[i].getName());

		}
		
		assertEquals(zip2List.length, orig2.length);
		for (int i=0;i<zip2List.length;i++){
			assertEquals(zip2List[i].getName(), orig2[i].getName());
		}
	}


}
