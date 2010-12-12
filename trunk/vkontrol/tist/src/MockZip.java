import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;


public class MockZip {
	private List<String> expectedList = new ArrayList<String>();
	private List<String> actualList = new ArrayList<String>();
	private List<String> sanalDosya = null;
	
	public MockZip(List<String> dosyaIcerigi){
		this.sanalDosya = dosyaIcerigi;
		
	}
	
	public void addExpectedLine(String beklenen) {
		expectedList.add(beklenen);
	}
	
	public String ziple(){
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
