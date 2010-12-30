import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class YazilacakNesneDosya {

	private ObjectOutputStream objeCikisAkisi;
	private FileOutputStream dosyaCikisAkisi = null;
	
	public YazilacakNesneDosya(String dosyaYolu) {
		try {
			dosyaCikisAkisi = new FileOutputStream(dosyaYolu);
			objeCikisAkisi = new ObjectOutputStream(dosyaCikisAkisi);
		} catch (FileNotFoundException e) {
			System.out.print("dosya yazici baslatilamadi. ");
		} catch (IOException e) {
			System.out.print("Obje cikis akisi baslatilamadi. ");
		}
	}
	
	public void setWriter(ObjectOutputStream sahteYazici) {
		objeCikisAkisi = sahteYazici;
	}
	
	public boolean satirYaz(Object yazilacakSatir){
		if (objeCikisAkisi.getClass().getName() == "MockObjectWriter"){
			try {
				objeCikisAkisi.writeUnshared(yazilacakSatir);
				objeCikisAkisi.flush();
				return true;
			} catch (IOException e) {
				System.out.println("Mock Obje akisina yazilamadi");
				return false;
			}
		}
		else{
		try {
			objeCikisAkisi.writeObject(yazilacakSatir);
			objeCikisAkisi.flush();
			return true;
		} catch (IOException e) {
			System.out.println("Obje akisina yazilamadi");
			e.printStackTrace();
			return false;
		}
		}
	}
}
