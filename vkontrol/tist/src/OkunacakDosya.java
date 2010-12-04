import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;


public class OkunacakDosya {

	private ObjectInputStream objeGirisAkisi;
	
	public OkunacakDosya(String dosyaYolu) {
		FileInputStream dosyaGirisAkisi = null;
		
		try {
			dosyaGirisAkisi = new FileInputStream(dosyaYolu);
			objeGirisAkisi = new ObjectInputStream(dosyaGirisAkisi);
		} catch (FileNotFoundException e) {
			System.out.print("dosya okuyucu baslatilamadi. ");
		} catch (IOException e) {

			System.out.print("Obje giris akisi baslatilamadi. ");
			e.printStackTrace();
		}
		

		
	}

	public void setReader(ObjectInputStream sahteOkuyucu) {
		objeGirisAkisi = sahteOkuyucu;
	}
		
	public String satirOku(){
		try {
			return objeGirisAkisi.readUTF();
		} catch (IOException e) {
			System.out.println("Utf akisi okunamadi");
			return null;
		}
	}

}
