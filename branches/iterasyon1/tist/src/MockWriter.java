import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;


public class MockWriter extends ObjectOutputStream {
	private List<String> expectedList = new ArrayList<String>();
	private List<String> actualList = new ArrayList<String>();
	
	public MockWriter() throws SecurityException, IOException{
		super();
	}

	public void addExpectedLine(String beklenenSatir) {
		expectedList.add(beklenenSatir);
	}

	@Override
	public void writeUTF(String yazilacakSatir) throws IOException {
		if(expectedList.size() <= actualList.size())
			Assert.fail("beklenen deger sayisindan fazla");
		int index = actualList.size();
		String beklenenSatir = expectedList.get(index);
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
