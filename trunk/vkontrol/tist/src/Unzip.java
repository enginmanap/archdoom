import java.io.*;
import java.util.zip.*;


public class Unzip{
	private String zipYolu = null;
	private String dizinYolu = null;
	final int BUFFER = 2048;
	public Unzip(String dosyaYolu, String zipYolu){
		this.zipYolu = zipYolu;
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
		        int index = entry.getName().lastIndexOf(File.separatorChar)+1;
		        File dosya = new File(dizinYolu + File.separatorChar+ entry.getName().substring(0, index));

		        if (!dosya.exists())
		        	dosya.mkdir();
		        String fixedEntryName = entry.getName().replace('/', File.separatorChar);
		        fixedEntryName = fixedEntryName.replace('\\', File.separatorChar);
		        String dizinYapisi = fixedEntryName;
		        if( fixedEntryName.lastIndexOf(File.separatorChar) != -1 ){
		        	String yedekDizinYolu = dizinYolu;
		        	do{
		        		if (dizinYapisi.indexOf(File.separatorChar) != -1) {
			        	DizinOlustur yeniDizin = new DizinOlustur(yedekDizinYolu+File.separatorChar+dizinYapisi.substring(0,dizinYapisi.indexOf(File.separatorChar)));
			        	yeniDizin.olustur();
		        		}
			        	if (dizinYapisi.indexOf(File.separatorChar) == -1)
			        		break;
			        	yedekDizinYolu = yedekDizinYolu + File.separatorChar + dizinYapisi.substring(0, dizinYapisi.indexOf(File.separatorChar));
			        	dizinYapisi = dizinYapisi.substring(dizinYapisi.indexOf(File.separatorChar)+1);
			        }while(true);	

		        }
		        Thread.currentThread().sleep(100);
		        FileOutputStream fos = new FileOutputStream(dizinYolu + fixedEntryName);
		        Thread.currentThread().sleep(100);
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