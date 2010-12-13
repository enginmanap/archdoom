import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;


public class MockCikisAkisi extends OutputStream {
	
	List<byte[]> expectedList = new ArrayList<byte[]>();
	List<byte[]> actualList = new ArrayList<byte[]>();

	@Override
	public void write(int arg0) throws IOException {
				
	}

	public void addExpectedList(byte[] beklenenByteDizisi) {
		expectedList.add(beklenenByteDizisi);
	}
	
	public void write(byte[] writeFrom,int startPoint,int lenght) throws IOException {
		if(expectedList.size() <= actualList.size())
			Assert.fail("beklenen deger sayisindan fazla");
		int index = actualList.size();
		byte[] beklenenSatir = expectedList.get(index);
		Assert.assertEquals(beklenenSatir, writeFrom);
		actualList.add(writeFrom);
	}
	public void flush(){
		
	}
	
	public void verify(){
		if (expectedList.size() != actualList.size())
			Assert.fail("Beklenen deger sayisi:"+expectedList.size()+"Olusan deger sayisi:"+actualList.size());
		for (int i=0;i<actualList.size();i++){
			System.out.println("yazýlan eleman : " + actualList.get(i));
		}
	}
}
