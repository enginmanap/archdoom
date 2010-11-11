import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;


public class Sunucu {

	public boolean varMi(String dosyaAdi) {
		File dosya = new File(dosyaAdi);
		if (dosya.exists()) return true;
		else return false;
	}

	public boolean dizinMi(String dizinAdi) {
		File dizin = new File(dizinAdi);
		if (dizin.isDirectory()) return true;
		else return false;
	}

	public boolean dosyaMi(String dosyaAdi) {
		File dosya = new File(dosyaAdi);
		if (dosya.isFile()) return true;
		else return false;
	}

	public boolean okunabilirMi(String gelenAd) {
		File gelen = new File(gelenAd);
		if (gelen.canRead()) return true;
		else return false;
	}

	public String satirOku(String dosyaAdi) {
//		File dosya = new File(dosyaAdi);
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(dosyaAdi);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine=null;
		try {
			strLine=br.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strLine;
		
	}

}
