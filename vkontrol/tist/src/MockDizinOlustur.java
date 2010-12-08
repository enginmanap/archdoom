import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;



public class MockDizinOlustur extends DizinOlustur{
	public MockDizinOlustur(String dosyaYolu) {
		super(dosyaYolu);
		sanalDizin = dosyaYolu;
	}
	private List<String> expectedList = new ArrayList<String>();
	private List<String> actualList = new ArrayList<String>();
	private String sanalDizin;
	
	public void addExpectedLine(String dizinAdi){
		expectedList.add(dizinAdi);
	}
	public String olustur(){
		if(expectedList.size() <= actualList.size())
			Assert.fail("beklenen deger sayisindan fazla");
		int index = actualList.size();
		String beklenenSatir = expectedList.get(index);
		Assert.assertEquals(beklenenSatir,sanalDizin);
		actualList.add(sanalDizin);
		return actualList.get(index);
	}
	public void verify(){
		if (expectedList.size() != actualList.size())
			Assert.fail("Beklenen deger sayisi:"+expectedList.size()+"Olusan deger sayisi:"+actualList.size());
	}
		
}
