import java.util.LinkedList;
import java.util.List;
import difflib.*;

public class Sunucu {

	
	private List<String> dosyadanSatira(String filename) {
        List<String> lines = new LinkedList<String>();
        
        OkunacakDosya dosya = new OkunacakDosya(filename);
        while(true){
        	String newLine = dosya.satirOku();
        	if (newLine != null)
        		lines.add(newLine);
        	else
        		break;
		}
        return lines;
	}

	public Patch farkAl(String dosya1, String dosya2) {
		List<String> ilkDosya = dosyadanSatira(dosya1);
		List<String> ikinciDosya = dosyadanSatira(dosya2);
		Patch patch = DiffUtils.diff(ilkDosya, ikinciDosya);
		return patch;
	}

}
