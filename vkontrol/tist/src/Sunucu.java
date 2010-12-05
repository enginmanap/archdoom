import java.util.LinkedList;
import java.util.List;

import difflib.DiffUtils;
import difflib.Patch;

public class Sunucu {

	
	public List<Object> dosyadanSatira(String filename) {
        List<Object> lines = new LinkedList<Object>();
        
        OkunacakNesneDosya dosya = new OkunacakNesneDosya(filename);
        while(true){
        	Object newLine = dosya.satirOku();
        	if (newLine != null)
        		lines.add(newLine);
        	else
        		break;
		}
        return lines;
	}

	public Patch farkAl(String dosya1, String dosya2) {
		List<Object> ilkDosya = dosyadanSatira(dosya1);
		List<Object> ikinciDosya = dosyadanSatira(dosya2);
		Patch patch = DiffUtils.diff(ilkDosya, ikinciDosya);
		return patch;
	}

}
