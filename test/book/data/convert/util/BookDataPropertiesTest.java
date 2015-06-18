package book.data.convert.util;

import static org.junit.Assert.*;

import java.util.Properties;

import org.junit.Test;

public class BookDataPropertiesTest {

	@Test
	public void testGetConvertFormatDetails() {
		Properties prop = BookDataProperties.getConvertFormatDetails();
		String targetFormat = prop.getProperty("targetFormat");
		String storageEnable = prop.getProperty("storageEnabled");
		String storageFile = prop.getProperty("storageFile");
		
		assertEquals("json", targetFormat);
		assertEquals("true", storageEnable);
		assertEquals("resource/output.txt", storageFile);
	}
	
	@Test
	public void testGetConvertFormatDetailsError(){
		Properties prop = BookDataProperties.getConvertFormatDetails();
		String targetFormat = prop.getProperty("targetFormat");
		String storageEnable = prop.getProperty("storageEnabled");
		String storageFile = prop.getProperty("storageFile");
	
		assertEquals("json", targetFormat);
		assertEquals("false", storageEnable);
		assertEquals("output.txt", storageFile);
	}
}
