import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;


public class MockTextWriter extends PrintWriter{
	
	private List<String> expectedList = new ArrayList<String>();
	private List<String> actualList = new ArrayList<String>();
	
	public MockTextWriter() throws FileNotFoundException{
		super(new StringWriter());
		
	}

	public void addExpectedLine(String beklenenSatir) {
		expectedList.add(beklenenSatir);
	}

	public void println (String yazilacakSatir){
		if(expectedList.size() <= actualList.size())
			Assert.fail("beklenen deger sayisindan fazla");
		int index = actualList.size();
		String beklenenSatir = expectedList.get(index);
		Assert.assertEquals(beklenenSatir, yazilacakSatir);
		actualList.add(yazilacakSatir);
		}
	public void flush() {
		// flush islemi yapilmadigindan, bos birakilmistir.
	}
	public void verify(){
		if (expectedList.size() != actualList.size())
			Assert.fail("Beklenen deger sayisi:"+expectedList.size()+"Olusan deger sayisi:"+actualList.size());
		for (int i=0;i<actualList.size();i++){
			System.out.println("yazilan eleman : " + actualList.get(i));
		}
	}

}
