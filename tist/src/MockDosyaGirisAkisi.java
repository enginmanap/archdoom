import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import junit.framework.Assert;

public class MockDosyaGirisAkisi extends FileInputStream{
	List<byte[]> expectedList = new ArrayList<byte[]>();
	List<byte[]> actualList = new ArrayList<byte[]>();
	List<byte[]> sanalDosya = new ArrayList<byte[]>();
	
	public MockDosyaGirisAkisi(String name) throws FileNotFoundException {
		super(name);
	}

	public void addSanalDosya(byte[] sanalDosyaIcerigi) {
		sanalDosya.add(sanalDosyaIcerigi);
	}

	public void addExpectedList(byte[] beklenenByteDizisi) {
		expectedList.add(beklenenByteDizisi);
	}

	@Override
	public int read() throws IOException {
		return 0;
	}
	
	public int read(byte[] yazilacakDizi,int baslangic,int uzunluk){
		if(expectedList.size() <= actualList.size())
			return -1;
		int index = actualList.size();
		byte[] beklenenByteDizi = expectedList.get(index);
		byte[] okunanByteDizi = sanalDosya.get(index);
		Assert.assertEquals(beklenenByteDizi, okunanByteDizi);
		for (int i=0; i<uzunluk;i++){
			try{
				yazilacakDizi[baslangic+i] = okunanByteDizi[i];
			}catch (ArrayIndexOutOfBoundsException e) {
				break;
			}			
		}
		actualList.add(okunanByteDizi);
		return okunanByteDizi.length;
	}

	public int read(byte[] yazilacakDizi){
		if(expectedList.size() <= actualList.size())
			return -1;
		int index = actualList.size();
		byte[] beklenenByteDizi = expectedList.get(index);
		byte[] okunanByteDizi = sanalDosya.get(index);
		Assert.assertEquals(beklenenByteDizi, okunanByteDizi);
		for (int i=0; i<yazilacakDizi.length;i++){
			try{
				yazilacakDizi[i] = okunanByteDizi[i];
			}catch (ArrayIndexOutOfBoundsException e) {
				break;
			}			
		}
		actualList.add(okunanByteDizi);
		return okunanByteDizi.length;
	}

	public void verify(){
		if (expectedList.size() != actualList.size())
			Assert.fail("Beklenen deger sayisi:"+expectedList.size()+"Olusan deger sayisi:"+actualList.size());
		for (int i=0;i<actualList.size();i++){
			System.out.println("yaz�lan eleman : " + actualList.get(i));
		}
	}
}
