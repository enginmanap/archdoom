import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;
import difflib.*;

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
		FileInputStream fstream = null;
		try {
			fstream = new FileInputStream(dosyaAdi);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		DataInputStream in = new DataInputStream(fstream);
		BufferedReader br = new BufferedReader(new InputStreamReader(in));
		String strLine=null;
		try {
			strLine=br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return strLine;
		
	}
	
	private List<String> dosyadanSatira(String filename) {
        List<String> lines = new LinkedList<String>();
        String line = "";
        try {
                BufferedReader in = new BufferedReader(new FileReader(filename));
                while ((line = in.readLine()) != null) {
                        lines.add(line);
                }
        } catch (IOException e) {
                e.printStackTrace();
        }
        return lines;
	}

	public List<Delta> farkAl(String dosya1, String dosya2) {
		List<String> ilkDosya = dosyadanSatira(dosya1);
		List<String> ikinciDosya = dosyadanSatira(dosya2);
		Patch patch = DiffUtils.diff(ilkDosya, ikinciDosya);
		return patch.getDeltas();
	}

}
