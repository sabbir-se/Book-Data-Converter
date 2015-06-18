package book.data.convert.service;

public interface BookDataConvertService {
	
	public String readDataFromFile(String fileName);
	
	public String readFileOutputFormat(String fileName);
	
	public String detectDataFormat(String file);
	
	public void txtToJsonConvert(String file);
	
	public void jsonToTxtConvert(String file);
}
