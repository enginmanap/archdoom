import java.io.*;
import java.util.zip.*;

public class Zip implements Zipleme {
   static final int BUFFER = 2048;
   private File ziplenecekDizin = null;
   private ZipOutputStream zipYolu = null;
   private String dizinIsmi = null;
   private String zipIsmi = null;
   
   public Zip(String dosyaYolu, String zipYolu){
	   this.ziplenecekDizin = new File(dosyaYolu+File.separatorChar);
	   int index = dosyaYolu.lastIndexOf(File.separatorChar);
	   dizinIsmi = dosyaYolu.substring(index);
	   FileOutputStream fout = null;
	   index = zipYolu.lastIndexOf(File.separatorChar);
	   zipIsmi = zipYolu.substring(index+1);
	try {
		fout = new FileOutputStream(zipYolu);
	} catch (FileNotFoundException e) {
		e.printStackTrace();
	}
	   this.zipYolu = new ZipOutputStream(fout);
	   
   }
   
   public String ziple(){
	   addDirectory(zipYolu, ziplenecekDizin);
	   try {
		zipYolu.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   return null;
   }
   
   private void addDirectory(ZipOutputStream zout, File kaynakDizin) {
	   
	 //get sub-folder/files list
	 File[] files = kaynakDizin.listFiles();
	 if (Sunucu.DEBUG)
		 System.out.println("Adding directory " + kaynakDizin.getName());
	  
	 for(int i=0; i < files.length; i++)
	 {
	 //if the file is directory, call the function recursively

		 if (files[i].getName().equals(zipIsmi))
			 continue;
	
	 if(files[i].isDirectory())
	 {
		 addDirectory(zout, files[i]);
		 continue;
	 }
	  
	 /*
	 * we are here means, its file and not directory, so
	 * add it to the zip file
	 */
	  
	 try
	 {
		 if (Sunucu.DEBUG)
			 System.out.println("Adding file " + files[i].getName());
	  
	 //create byte buffer
	 byte[] buffer = new byte[1024];
	  
	 //create object of FileInputStream
	 FileInputStream fin = new FileInputStream(files[i]);
	 int index = kaynakDizin.getAbsolutePath().lastIndexOf(dizinIsmi)+1+dizinIsmi.length()-1;
	 String gercekYol = kaynakDizin.getAbsolutePath().substring(index);
	 zout.putNextEntry(new ZipEntry(gercekYol+File.separatorChar+files[i].getName()));
	 /*
	 * After creating entry in the zip file, actually
	 * write the file.
	 */
	 int length;
	  
	 while((length = fin.read(buffer)) > 0)
	 {
	 zout.write(buffer, 0, length);
	 }
	  
	 /*
	 * After writing the file to ZipOutputStream, use
	 *
	 * void closeEntry() method of ZipOutputStream class to
	 * close the current entry and position the stream to
	 * write the next entry.
	 */
	  
	 zout.closeEntry();
	  
	 //close the InputStream
	 fin.close();
	  
	 }
	 catch(IOException ioe)
	 {
	 System.out.println("IOException :" + ioe);
	 }
	 }
	  
	 }

	      
}
