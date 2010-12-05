import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;



public class MockObjectReader extends ObjectInputStream {
	private List<Object> expectedList = new ArrayList<Object>();
	private List<Object> actualList = new ArrayList<Object>();
	private List<Object> sanalDosya;
	
	public MockObjectReader(List<Object> dosyaIcerigi) throws IOException {
		super();
		sanalDosya = dosyaIcerigi;
	}

	public void addExpectedLine(Object beklenen) {
		expectedList.add(beklenen);
	}
	
	@Override
	public Object readUnshared(){
		if(expectedList.size() <= actualList.size())
			Assert.fail("beklenen deger sayisindan fazla");
		int index = actualList.size();
		Object beklenenSatir = expectedList.get(index);
		Object okunanSatir = sanalDosya.get(index);
		Assert.assertEquals(beklenenSatir, okunanSatir);
		actualList.add(okunanSatir);
		return okunanSatir;
		}
	
	public void verify(){
		if (expectedList.size() != actualList.size())
			Assert.fail("Beklenen deger sayisi:"+expectedList.size()+"Olusan deger sayisi:"+actualList.size());
	}
}
