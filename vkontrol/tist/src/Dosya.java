import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;


public class Dosya {

	private ObjectInputStream objeGirisAkisi;
	private ObjectOutputStream objeCikisAkisi;
	
	public Dosya(String dosyaYolu) {
		FileInputStream dosyaGirisAkisi = null;
		FileOutputStream dosyaCikisAkisi = null;

		try {
			dosyaGirisAkisi = new FileInputStream(dosyaYolu);
			objeGirisAkisi = new ObjectInputStream(dosyaGirisAkisi);
		} catch (FileNotFoundException e) {
			System.out.print("dosya okuyucu baslatilamadi. ");
		} catch (IOException e) {

			System.out.print("Obje giris akisi baslatilamadi. ");
			e.printStackTrace();
		}
		
		try {
			dosyaCikisAkisi = new FileOutputStream(dosyaYolu);
			objeCikisAkisi = new ObjectOutputStream(dosyaCikisAkisi);
		} catch (FileNotFoundException e) {
			System.out.print("dosya yazici baslatilamadi. ");
		} catch (IOException e) {
			System.out.print("Obje cikis akisi baslatilamadi. ");
		}
		
	}

	public void setReader(ObjectInputStream sahteOkuyucu) {
		objeGirisAkisi = sahteOkuyucu;
	}
	
	public void setWriter(ObjectOutputStream sahteYazici) {
		objeCikisAkisi = sahteYazici;
	}
	
	public String satirOku(){
		try {
			return objeGirisAkisi.readUTF();
		} catch (IOException e) {
			System.out.println("Utf akisi okunamadi");
			return null;
		}
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
