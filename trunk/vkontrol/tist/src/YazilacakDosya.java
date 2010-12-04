import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;


public class YazilacakDosya {

	
	private ObjectOutputStream objeCikisAkisi;
	
	public YazilacakDosya(String dosyaYolu) {
		FileOutputStream dosyaCikisAkisi = null;
	
		
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
	
	public boolean satirYaz(String yazilacakSatir){
		try {
			objeCikisAkisi.writeUTF(yazilacakSatir);
			return true;
		} catch (IOException e) {
			System.out.println("Utf akisina yazilamadi");
			return false;
		}
	}
}
