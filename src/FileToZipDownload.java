import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

;

class FileToZipDownload {

	public static void main(String args[]) {
		if (args.length != 2) {
			System.out
					.println("Proper Usage: java FileDownload URL OutputFileName");
			System.exit(0);
		}

		InputStream in = null;

		System.out.println("Downloading " + args[0]);

		try (FileOutputStream fOut = new FileOutputStream(args[1]);
			ZipOutputStream zOut = new ZipOutputStream(fOut)) {
			
			URL remoteFile = new URL(args[0]);
			URLConnection fileStream = remoteFile.openConnection();

			zOut.setLevel(9);
			zOut.setMethod(ZipOutputStream.DEFLATED);
			ZipEntry entry = new ZipEntry(remoteFile.getFile());
			zOut.putNextEntry(entry);

			// open input stream
			in = fileStream.getInputStream();
			
			// save the file
			byte[] buff = new byte[1024];
			int length;
			while ((length = in.read(buff)) > 0) {
				zOut.write(buff, 0, length);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println("The file " + args[0]
					+ " has been downloaded successfully as " + args[1]);
			try {
				in.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
