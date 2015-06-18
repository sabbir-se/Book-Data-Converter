package book.data.convert.service;

public interface BookDataConvertService {
	
	public String readDataFromFile(String fileName);
	
	public String readFileOutputFormat(String fileName);
	
	public boolean checkISBN(String fileName);
	
	public String detectDataFormat(String fileName);
	
	public void txtToJsonConvert(String fileName, String storageEnable, String storageFile);
	
	public void jsonToTxtConvert(String fileName, String storageEnable, String storageFile);
}
