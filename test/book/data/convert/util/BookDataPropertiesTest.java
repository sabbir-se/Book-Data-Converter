package book.data.convert.util;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

public class BookDataPropertiesTest {

	@Test
	public void testGetConvertFormatDetails() {
		Properties prop = BookDataProperties.getConvertFormatDetails();
		String targetFormat = prop.getProperty("targetFormat");
		assertEquals("json", targetFormat);
	}
	
	@Test
	public void testGetConvertFormatDetailsError(){
		Properties prop = BookDataProperties.getConvertFormatDetails();
		String targetFormat = prop.getProperty("targetFormat");
		assertEquals("txt", targetFormat);
	}
}
