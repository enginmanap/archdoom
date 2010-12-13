import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;


public class MockObjectWriter extends ObjectOutputStream {
	private List<Object> expectedList = new ArrayList<Object>();
	private List<Object> actualList = new ArrayList<Object>();
	
	public MockObjectWriter() throws SecurityException, IOException{
		super();
	}

	public void addExpectedLine(Object beklenenSatir) {
		expectedList.add(beklenenSatir);
	}

	public void writeUnshared (Object yazilacakSatir) throws IOException {
		if(expectedList.size() <= actualList.size())
			Assert.fail("beklenen deger sayisindan fazla");
		int index = actualList.size();
		Object beklenenSatir = expectedList.get(index);
		Assert.assertEquals(beklenenSatir, yazilacakSatir);
		actualList.add(yazilacakSatir);
		}
	@Override
	public void flush() throws IOException {
		// flush islemi yapýlmadýgýndan, bos birakilmistir.
	}
	
	public void verify(){
		if (expectedList.size() != actualList.size())
			Assert.fail("Beklenen deger sayisi:"+expectedList.size()+"Olusan deger sayisi:"+actualList.size());
		for (int i=0;i<actualList.size();i++){
			System.out.println("yazýlan eleman : " + actualList.get(i));
		}
	}

}
