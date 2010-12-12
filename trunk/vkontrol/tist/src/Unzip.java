import java.io.*;
import java.util.zip.*;


public class Unzip implements Unzipleme{
	private String zipYolu = null;
	final int BUFFER = 2048;
	public Unzip(String dosyaYolu, String zipAdi){
		this.zipYolu = dosyaYolu+"/.tist/"+zipAdi;
		
	}
	
	public String unziple(){
		try {
			BufferedOutputStream dest = null;
		    FileInputStream fis = new 
			FileInputStream(zipYolu);
		    ZipInputStream zis = new 
			ZipInputStream(new BufferedInputStream(fis));
		    ZipEntry entry;
		    while((entry = zis.getNextEntry()) != null) {
		    	System.out.println("Extracting: " +entry);
		        int count;
		        byte data[] = new byte[BUFFER];
		        // write the files to the disk
		        FileOutputStream fos = new 
			    FileOutputStream(entry.getName());
		        dest = new 
		              BufferedOutputStream(fos, BUFFER);
		        while ((count = zis.read(data, 0, BUFFER)) 
		        != -1) {
		        dest.write(data, 0, count);
		        }
		        dest.flush();
		        dest.close();
		       }
		       zis.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return zipYolu;
		
	}
	


}

