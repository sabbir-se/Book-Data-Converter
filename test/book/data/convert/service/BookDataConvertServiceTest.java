package book.data.convert.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import book.data.convert.service.impl.BookDataConvertServiceImpl;

public class BookDataConvertServiceTest {

	private BookDataConvertService impl;
	private String fileName;
	private String storageEnable;
	private String storageFile;
	
	@Before
	public void setUp() throws Exception {
		impl = new BookDataConvertServiceImpl();
		fileName = "resource/textInput1.txt";
		storageEnable = "true";
		storageFile = "resource/output.txt";
	}
	
	//@Test
	public void testReadDataFromFile() {
		System.out.println("Reading input ...");
		System.out.println("++++");
		System.out.println(impl.readDataFromFile(fileName));
		System.out.println("----");
	}

	//@Test
	public void testReadDataFromFileError() {
		System.out.println("Reading input ...");
		System.out.println("++++");
		String readFile = impl.readDataFromFile("resource/input.txt");
		if(readFile != null){
			System.out.println(readFile);
		}
		System.out.println("----");
	}
	
	@Test
	public void testReadFileOutputFormat() {
		System.out.println("Convert input into output format ...");
		System.out.println("++++");
		String readData = impl.readDataFromFile(fileName);
		System.out.println(impl.readFileOutputFormat(readData));
		System.out.println("----");
	}

	@Test
	public void testDetectDataFormat() {
		System.out.println("Detect data format from input file ...");
		String format = impl.detectDataFormat(impl.readDataFromFile(fileName));
		System.out.println("Input data is in " + format + " format");
	}
	
	//@Test
	public void testDetectDataFormatError() {
		System.out.println("Detect data format from input file ...");
		String format = impl.detectDataFormat(impl.readDataFromFile("resource/textInput3.txt"));
		if(format.equals("ERROR")){
			System.out.println("Format detection failed.");
		}
		else{
			System.out.println("Input data is in " + format + " format");
		}
	}

	@Test
	public void testCheckISBN(){
		assertEquals(true, impl.checkISBN(impl.readDataFromFile(fileName)));
	}
	
	//@Test
	public void testCheckISBNError(){
		assertEquals(true, impl.checkISBN(impl.readDataFromFile("resource/textInput3.txt")));
	}
	
	@Test
	public void testTxtToJsonConvert() {
		System.out.println("Convert data into JSON format ...");
		System.out.println("++++");
		impl.txtToJsonConvert(impl.readDataFromFile(fileName), storageEnable, storageFile);
		System.out.println("----");
	}

	//@Test
	public void testTxtToJsonConvertError() {
		System.out.println("Convert data into JSON format ...");
		System.out.println("++++");
		impl.txtToJsonConvert(impl.readDataFromFile("resource/textInput3.txt"), storageEnable, storageFile);
		System.out.println("----");
	}
	
	//@Test
	public void testJsonToTxtConvert() {
		System.out.println("Convert data into TXT format ...");
		System.out.println("++++");
		impl.jsonToTxtConvert(fileName, storageEnable, storageFile);
		System.out.println("----");
	}
	
	//@Test
	public void testJsonToTxtConvertError(){
		System.out.println("Convert data into TXT format ...");
		System.out.println("++++");
		impl.jsonToTxtConvert("resource/jsonInput3.txt", storageEnable, storageFile);
		System.out.println("----");
	}
}
