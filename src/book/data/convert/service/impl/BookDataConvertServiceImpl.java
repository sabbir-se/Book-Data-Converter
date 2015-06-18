package book.data.convert.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map.Entry;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import book.data.convert.service.BookDataConvertService;

public class BookDataConvertServiceImpl implements BookDataConvertService{
	
	/**
	 * Reading content of a file
	 * 
	 * @param fileName
	 */
	@Override
	public String readDataFromFile(String fileName) {
		try {
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			BufferedReader br = new BufferedReader(fileReader);
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();			
	        while (line != null) {
	        	if(line.length() > 0){
	        		sb.append(line.trim());
	        	}
	            
	        	line = br.readLine();
	            if(line != null && line.length() > 0 && sb.length() > 0){
	            	sb.append("\n");
	            }
	        }
	        fileReader.close();
	        return sb.toString();
	        
		} catch(FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "'");
            return null;
        } catch (IOException e) {
			System.out.println("Error reading file '" + fileName + "'");
			return null;
		}
	}
	
	/**
	 * Rearrange the content of a file into specific output format
	 * 
	 * @param fileName
	 */
	@Override
	public String readFileOutputFormat(String fileName){
		String[] array = fileName.split("\n");
		String line = "";
		for(int i=0; i<array.length; i++){
			line += capitalizeFirstCharacter(array[i]);
			if(i != array.length-1)
				line += "\n";
		}
		return line;
	}
	
	/**
	 * Capitalize first character of a line
	 * 
	 * @param fileName
	 */
	private String capitalizeFirstCharacter(String line){
		String str = line.trim();
		if(str.contains("-")){
			String[] split = str.split("-");
			split[0] = split[0].substring(0, 1).toUpperCase() + split[0].substring(1);
			split[1] = split[1].substring(0, 1).toUpperCase() + split[1].substring(1);
			str = split[0] + " " + split[1];
		}
		else{
			str = str.substring(0, 1).toUpperCase() + str.substring(1);
		}
		return str;
	}
	
	/**
	 * Detect data format of a file
	 * 
	 * @param fileName
	 */
	@Override
	public String detectDataFormat(String fileName) {
		String[] array = fileName.split("\n");
		String line = array[0];
		String[] lineRead = line.split(":");
		
		if(lineRead[0].contains("{") || lineRead[0].contains("\"")){
			return "JSON";
		}
		else if(Character.isLetter(lineRead[0].charAt(0))){
			return "TXT";
		}
		return "ERROR";
	}
	
	/**
	 * Convert a file from text to json
	 * 
	 * @param fileName
	 */
	@Override
	public void txtToJsonConvert(String fileName){
		try{
			String[] array = fileName.split("\n");
			JsonObject finalObj = new JsonObject();
			JsonObject obj = new JsonObject();
			int i=0;
			while(i < array.length){
				String line = array[i];
				String split[] = line.split(":");
				String split1[] = split[1].split(",");
				split[0] = split[0].replaceAll(" ", "-");
				
				JsonPrimitive primitive;
				JsonArray jsonArray = new JsonArray();
				if(split1.length > 1){
					for(String element : split1){
						primitive = new JsonPrimitive(element.trim());
						jsonArray.add(primitive);
					}
					obj.add(split[0].toLowerCase(), jsonArray);
				}
				else{
					obj.addProperty(split[0].toLowerCase(), split1[0].trim());
				}
				i++;
			}
			finalObj.add("book", obj);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(finalObj);
			System.out.println(json);
			
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Error to convert data into json format.");
		}
	}
	
	/**
	 * Convert a file from json to text
	 * 
	 * @param fileName
	 */
	@Override
	public void jsonToTxtConvert(String fileName){
		JsonParser parser = new JsonParser();
		try {
			JsonObject obj =  (JsonObject) parser.parse(new FileReader(fileName));
			JsonObject innerObj = (JsonObject) obj.get("book");
			for (Entry<String, JsonElement> entry : innerObj.entrySet()) {
				String res = "";
				String key = entry.getKey();
				JsonElement value = entry.getValue();
				key = capitalizeFirstCharacter(key);
				res += (key + ": ");
				
				if(value.isJsonArray()){
					JsonArray array = value.getAsJsonArray();
					for(int i=0; i<array.size(); i++){
						String val = array.get(i).getAsString();
						res += val;
						if(i != array.size()-1)
							res += ", ";
					}
				}
				else{
					res += value.getAsString();
				}
				System.out.println(res);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (JsonParseException je) {
			System.out.println("Error in gson parsing.");
		}
	}
}
