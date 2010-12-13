import java.io.File;


public class DizinOlustur {
	
	File dizin = null; 
	
	public DizinOlustur(String dosyaYolu) {
		dizin = new File(dosyaYolu);
	}
	public String olustur(){
		dizin.mkdir();
		return dizin.getPath();
	}
	
}
