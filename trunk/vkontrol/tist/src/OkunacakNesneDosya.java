import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;


public class OkunacakNesneDosya {

	private ObjectInputStream objeGirisAkisi;
	private FileInputStream dosyaGirisAkisi = null;
	
	public OkunacakNesneDosya(String dosyaYolu) {
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
		
	public Object satirOku(){
		if (objeGirisAkisi.getClass().getName() == "MockObjectReader"){
			try {
				return objeGirisAkisi.readUnshared();
			} catch (EOFException e) {
				System.out.println("Mock DosyaSonu");
				return null;
			} catch (IOException e) {
				System.out.println("Mock Obje akisi okunamadi");
				return null;
			} catch (ClassNotFoundException e) {
				System.out.println("Mock boyle bir sinif yok");
				return null;
			}
		}
		else{
			try {
				return objeGirisAkisi.readObject();
			} catch (EOFException e) {
				System.out.println("DosyaSonu");
				return null;
			} catch (IOException e) {
				System.out.println("Obje akisi okunamadi");
				return null;
			} catch (ClassNotFoundException e) {
				System.out.println("Boyle bir sinif yok");
				return null;
			}	
		}
		
	}

}
