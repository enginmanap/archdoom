import java.io.*;
import java.util.zip.*;


public class Unzip implements Unzipleme{
	private String zipYolu = null;
	private String dizinYolu = null;
	final int BUFFER = 2048;
	public Unzip(String dosyaYolu, String zipYolu){
		this.zipYolu = zipYolu;
//		this.zipYolu = System.getProperty("user.dir")+File.separatorChar+".tist"+File.separatorChar+zipAdi;
		this.dizinYolu = dosyaYolu + File.separatorChar;
		
	}
	
	public String unziple(){
		try {
			BufferedOutputStream dest = null;
		    FileInputStream fis = new FileInputStream(zipYolu);
		    ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
		    ZipEntry entry;
		    while((entry = zis.getNextEntry()) != null) {
		    	if (Sunucu.DEBUG)
		    		System.out.println("Extracting: " +entry);
		        int count;
		        byte data[] = new byte[BUFFER];
		        // write the files to the disk
		        int index = entry.getName().lastIndexOf("/");
		        File dosya = new File(dizinYolu + entry.getName().substring(0, index));
		        if (!dosya.exists())
		        	dosya.mkdir();
		        FileOutputStream fos = new FileOutputStream(dizinYolu + entry.getName());
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

