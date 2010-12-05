import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;


public class OkunacakDosya {

	private ObjectInputStream objeGirisAkisi;
	private FileInputStream dosyaGirisAkisi = null;
	
	public OkunacakDosya(String dosyaYolu) {
		try {
			dosyaGirisAkisi = new FileInputStream(dosyaYolu);
			objeGirisAkisi = new ObjectInputStream(dosyaGirisAkisi);
		} catch (FileNotFoundException e) {
			System.out.print("dosya okuyucu baslatilamadi. ");
		} catch (IOException e) {
			System.out.print("Obje giris akisi baslatilamadi. ");
		}
		

		
	}

	public void setReader(ObjectInputStream sahteOkuyucu) {
		objeGirisAkisi = sahteOkuyucu;
	}
		
	public String satirOku(){
		try {
			return objeGirisAkisi.readUTF();
		} catch (EOFException e) {
			return null;
		} catch (IOException e) {
			System.out.println("Utf akisi okunamadi");
			return null;
		}
	}

}
