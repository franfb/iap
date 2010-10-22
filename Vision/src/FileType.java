import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileType {
	public static final int UNKNOWN = 0;
	public static final int TIFF = 1;
	public static final int JPEG = 2;
	public static final int GIF = 3;
	public static final int PNG = 4;
	public static final int BMP = 5;

	public static int getFileType(String path) {
		File file = new File(path);
		String name = file.getName();
		InputStream is;
		byte[] buf = new byte[132];
		try {
			is = new FileInputStream(file);
			is.read(buf, 0, 132);
			is.close();
		} catch (IOException e) {
			return UNKNOWN;
		}
		int b0=buf[0]&255, b1=buf[1]&255, b2=buf[2]&255, b3=buf[3]&255;
		 // Big-endian TIFF ("MM")
		if (b0==73 && b1==73 && b2==42 && b3==0)
			return TIFF;
		 // Little-endian TIFF ("II")
		if (b0==77 && b1==77 && b2==0 && b3==42)
			return TIFF;
		 // JPEG
		if (b0==255 && b1==216 && b2==255)
			return JPEG;
		 // GIF ("GIF8")
		if (b0==71 && b1==73 && b2==70 && b3==56)
			return GIF;
		// PNG
		if (b0==137 && b1==80 && b2==78 && b3==71)
			return PNG;
		// BMP ("BM")
		if ((b0==66 && b1==77)||name.endsWith(".dib"))
			return BMP;
		return UNKNOWN;
	}
}