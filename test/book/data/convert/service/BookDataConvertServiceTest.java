package book.data.convert.service;

import org.junit.Before;
import org.junit.Test;

import book.data.convert.service.impl.BookDataConvertServiceImpl;

public class BookDataConvertServiceTest {

	private BookDataConvertService impl;
	private String fileName;
	
	@Before
	public void setUp() throws Exception {
		impl = new BookDataConvertServiceImpl();
		fileName = "resource/textInput1.txt";
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
		String readFile = impl.readDataFromFile(fileName);
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
	public void testTxtToJsonConvert() {
		System.out.println("Convert data into JSON format ...");
		System.out.println("++++");
		impl.txtToJsonConvert(impl.readDataFromFile(fileName));
		System.out.println("----");
	}

	//@Test
	public void testTxtToJsonConvertError() {
		System.out.println("Convert data into JSON format ...");
		System.out.println("++++");
		impl.txtToJsonConvert(impl.readDataFromFile("resource/textInput3.txt"));
		System.out.println("----");
	}
	
	//@Test
	public void testJsonToTxtConvert() {
		System.out.println("Convert data into TXT format ...");
		System.out.println("++++");
		impl.jsonToTxtConvert(fileName);
		System.out.println("----");
	}
	
	//@Test
	public void testJsonToTxtConvertError(){
		System.out.println("Convert data into TXT format ...");
		System.out.println("++++");
		impl.jsonToTxtConvert("resource/jsonInput3.txt");
		System.out.println("----");
	}
}
