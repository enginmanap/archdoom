import java.io.BufferedReader;
import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class OkunacakMetinDosya {
	BufferedReader dosya = null;
	public OkunacakMetinDosya(String dosyaYolu) {
		
		try {
			dosya = new BufferedReader(new FileReader(dosyaYolu));
		} catch (FileNotFoundException e) {
			System.out.println("okunacak Dosya "+dosyaYolu+" Bulunamadi");
		}
	}

	public String satirOku(){
	try {
		String satir = dosya.readLine();
		if (Sunucu.debug)
			System.out.println(satir);
		return satir;
	}
	catch (EOFException e){
		if (Sunucu.debug)
			System.out.println("Dosya sonu");
		return null;
	}
	catch (Exception e){
		System.out.println("dosyadan satir okunamadi");
		return null;
	}
	}

	public void setReader(BufferedReader sahteOkuyucu) {
		dosya = sahteOkuyucu;
		
	}
	
	public boolean dosyaKapat(){
		try {
			dosya.close();
			return true;
		} catch (IOException e) {
			System.out.println("dosya kapatilamadi");
			return false;
		}
		
	}
}
