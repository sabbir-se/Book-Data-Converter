package book.data.convert.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BookDataProperties {
	
	public static Properties getConvertFormatDetails() {
		Properties prop = new Properties();
		InputStream in = null;
		try {
			in = new FileInputStream("resource/book-info-converter.properties");
			prop.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}
}
