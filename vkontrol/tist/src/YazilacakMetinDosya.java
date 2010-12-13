import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


public class YazilacakMetinDosya {
	private PrintWriter dosyaYazici = null;
	
	public YazilacakMetinDosya(String dosyaYolu) {
		try {
			dosyaYazici = new PrintWriter(dosyaYolu);
		} catch (IOException e) {
			System.out.println(dosyaYolu+" icin, ");
			System.out.print("Metin cikis akisi baslatilamadi. ");
		}
	}
	
	public void setWriter(PrintWriter sahteYazici) {
		dosyaYazici = sahteYazici;
	}
	
	public boolean satirYaz(String yazilacakSatir){
		dosyaYazici.println(yazilacakSatir);
		if (Sunucu.debug) 
			System.out.println("dosyaya yazilan satir: "+ yazilacakSatir);
		dosyaYazici.flush();
		return true;
		}

	public void satirYaz(List<String> yazilacakSatirlar) {
		for(String satir: yazilacakSatirlar)
			dosyaYazici.println(satir);
		dosyaYazici.flush();
	}
	
	public void dosyaKapat(){
		dosyaYazici.close();
	}
	
}
