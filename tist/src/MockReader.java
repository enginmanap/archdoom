import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;



public class MockReader extends ObjectInputStream{
	private List<String> expectedList = new ArrayList<String>();
	private List<String> actualList = new ArrayList<String>();
	private List<String> dosya;
	
	public MockReader(List<String> dosyaIcerigi) throws IOException {
		super();
		dosya = dosyaIcerigi;
	}

	public void addExpectedLine(String string) {
		expectedList.add(string);
	}

	@Override
	public String readUTF() throws IOException {
		if(expectedList.size() <= actualList.size())
			Assert.fail("beklenen deger sayisindan fazla");
		int index = actualList.size();
		String beklenenSatir = expectedList.get(index);
		String okunanSatir = dosya.get(index);
		Assert.assertEquals(beklenenSatir, okunanSatir);
		actualList.add(okunanSatir);
		return okunanSatir;
		}
	
	public void verify(){
		if (expectedList.size() != actualList.size())
			Assert.fail("Beklenen deger sayisi:"+expectedList.size()+"Olusan deger sayisi:"+actualList.size());
	}
}
