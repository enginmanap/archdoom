import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;


public class MockTextReader extends BufferedReader {
	private List<String> expectedList = new ArrayList<String>();
	private List<String> actualList = new ArrayList<String>();
	private List<String> sanalDosya;
	
	public MockTextReader(List<String> dosyaIcerigi) throws FileNotFoundException {
		super(new StringReader(""));
		sanalDosya = dosyaIcerigi;
	}

	public void addExpectedLine(String beklenen) {
		expectedList.add(beklenen);
	}
	

	public String readLine(){
		if(expectedList.size() <= actualList.size())
			Assert.fail("beklenen deger sayisindan fazla");
		int index = actualList.size();
		String beklenenSatir = expectedList.get(index);
		String okunanSatir = sanalDosya.get(index);
		Assert.assertEquals(beklenenSatir, okunanSatir);
		actualList.add(okunanSatir);
		return okunanSatir;
		}
	
	public void verify(){
		if (expectedList.size() != actualList.size())
			Assert.fail("Beklenen deger sayisi:"+expectedList.size()+"Olusan deger sayisi:"+actualList.size());
	}
}
