import java.io.IOException;
import java.io.PrintWriter;


public class YazilacakMetinDosya {
	private PrintWriter dosyaYazici = null;
	
	public YazilacakMetinDosya(String dosyaYolu) {
		try {
			dosyaYazici = new PrintWriter(dosyaYolu);
		} catch (IOException e) {
			System.out.print("Metin cikis akisi baslatilamadi. ");
		}
	}
	
	public void setWriter(PrintWriter sahteYazici) {
		dosyaYazici = sahteYazici;
	}
	
	public boolean satirYaz(String yazilacakSatir){
		dosyaYazici.println(yazilacakSatir);
		dosyaYazici.flush();
		return true;
		}
	
	
	
}
