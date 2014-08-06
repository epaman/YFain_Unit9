import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

class FileToZipDownload {

	public static void main(String args[]) {
		if (args.length != 3) {
			System.out
					.println("Proper Usage: java FileDownload URL OutputFileName");
			System.exit(0);
		}

		InputStream in01 = null;
		InputStream in02 = null;

		System.out.println("Downloading " + args[0] + " " + args[1]);

		try (FileOutputStream fOut = new FileOutputStream(args[2]);
			ZipOutputStream zOut = new ZipOutputStream(fOut)) {
			
			URL remoteFile01 = new URL(args[0]);
			URL remoteFile02 = new URL(args[1]);
			URLConnection fileStream01 = remoteFile01.openConnection();
			URLConnection fileStream02 = remoteFile02.openConnection();

			zOut.setLevel(9);
			zOut.setMethod(ZipOutputStream.DEFLATED);
			ZipEntry entry01 = new ZipEntry(remoteFile01.getFile());
			zOut.putNextEntry(entry01);


			// open input stream
			in01 = fileStream01.getInputStream();
			
			// save the file
			byte[] buff01 = new byte[1024];
			int length01;
			while ((length01 = in01.read(buff01)) > 0) {
				zOut.write(buff01, 0, length01);
			}
			
			// save second file
			ZipEntry entry02 = new ZipEntry(remoteFile02.getFile());
			zOut.putNextEntry(entry02);
			
			in02 = fileStream02.getInputStream();
			
			byte[] buff02 = new byte[1024];
			int length02;
			while ((length02 = in02.read(buff02)) > 0) {
				zOut.write(buff02, 0, length02);
			}
			

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("The file " + args[0] + " and " + args[1]
					+ " has been downloaded successfully as " + args[2]);
			try {
				in01.close();
				in02.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
