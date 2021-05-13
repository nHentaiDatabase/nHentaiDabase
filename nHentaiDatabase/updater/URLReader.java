package updater;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class URLReader {

	/**
	 * Download a file from a URL and save it on the PC.
	 * 
	 * @param url The download URL
	 * @param file The destination of the saved file
	 */
	public static void copyURLToFile(URL url, File file) {
		
		try {
			InputStream input = url.openStream();
			if (file.exists()) {
				if (file.isDirectory())
					throw new IOException("File '" + file + "' is a directory");
				
				if (!file.canWrite())
					throw new IOException("File '" + file + "' cannot be written");
			} else {
				File parent = file.getParentFile();
				if ((parent != null) && (!parent.exists()) && (!parent.mkdirs())) {
					throw new IOException("File '" + file + "' could not be created");
				}
			}

			FileOutputStream output = new FileOutputStream(file);

			byte[] buffer = new byte[4096];
			int n = 0;
			while (-1 != (n = input.read(buffer))) {
				output.write(buffer, 0, n);
			}

			input.close();
			output.close();
			
			System.out.println("File '" + file + "' downloaded successfully!");
		}
		catch(IOException ioEx) {
			ioEx.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		
		//URL pointing to the file
		String sUrl = "https://github.com/PhilippBleimund/nHentaiDatabase/releases/latest/download/nHentaiDatabase.v1.4.9.jar";
		
		URL url = new URL(sUrl);
		
		//File where to be downloaded
		File file = new File("C:\\Users\\Philipp Bleimund\\AppData\\Roaming\\nHentaiDatabase\\test.jar");
		
		URLReader.copyURLToFile(url, file);
		
		
	}
	
	

}