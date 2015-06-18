package book.data.convert.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
	 * Check whether a file contains ISBN or not
	 * 
	 * @param fileName
	 */
	@Override
	public boolean checkISBN(String fileName){
		String[] array = fileName.split("\n");
		int i=0;
		while(i < array.length){
			String line = array[i];
			String[] lineSplit = line.split(":");
			if(lineSplit[0].toUpperCase().contains("ISBN")){
				return true;
			}
			i++;
		}
		return false;
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
	 * Convert a file from text to json & stored in a storage file
	 * 
	 * @param fileName
	 * @param storgeEnabled
	 * @param storageFile
	 */
	@Override
	public void txtToJsonConvert(String fileName, String storageEnable, String storageFile){
		try{
			String[] array = fileName.split("\n");
			JsonObject finalObj = new JsonObject();
			JsonObject obj = new JsonObject();
			int i=0;
			String res = "";
			while(i < array.length){
				String line = array[i];
				String lineSplit[] = line.split(":");
				String innerSplit[] = lineSplit[1].split(",");
				lineSplit[0] = lineSplit[0].replaceAll(" ", "-");
				
				JsonPrimitive primitive;
				JsonArray jsonArray = new JsonArray();
				if(innerSplit.length > 1){
					for(String element : innerSplit){
						primitive = new JsonPrimitive(element.trim());
						jsonArray.add(primitive);
					}
					obj.add(lineSplit[0].toLowerCase(), jsonArray);
				}
				else{
					obj.addProperty(lineSplit[0].toLowerCase(), innerSplit[0].trim());
				}
				if(lineSplit[0].toLowerCase().equals("name")){
					res += innerSplit[0].trim().toUpperCase();
				}
				i++;
			}
			finalObj.add("book", obj);
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			String json = gson.toJson(finalObj);
			System.out.println(res);
			if(storageEnable.equals("true")){
				writeFile(json, storageFile);
			}
			
		} catch (ArrayIndexOutOfBoundsException e){
			System.out.println("Error to convert data into json format.");
		}
	}
	
	/**
	 * Convert a file from json to text & stored in a storage file
	 * 
	 * @param fileName
	 * @param storgeEnabled
	 * @param storageFile
	 */
	@Override
	public void jsonToTxtConvert(String fileName, String storageEnable, String storageFile){
		JsonParser parser = new JsonParser();
		try {
			StringBuilder sb = new StringBuilder();
			JsonObject obj =  (JsonObject) parser.parse(new FileReader(fileName));
			JsonObject innerObj = (JsonObject) obj.get("book");
			int cnt=0;
			String res = "";
			for (Entry<String, JsonElement> entry : innerObj.entrySet()) {
				String key = entry.getKey();
				JsonElement value = entry.getValue();
				if(key.equals("name")){
					res += value.getAsString().toUpperCase();
				}
				sb.append(key);
				sb.append(": ");
				
				if(value.isJsonArray()){
					JsonArray array = value.getAsJsonArray();
					for(int i=0; i<array.size(); i++){
						String val = array.get(i).getAsString();
						sb.append(val);
						if(i != array.size()-1)
							sb.append(", ");
					}
				}
				else{
					sb.append(value.getAsString());
				}
				if(cnt != innerObj.entrySet().size()-1){
					sb.append("\n");
				}
				cnt++;
			}
			System.out.println(res);
			if(storageEnable.equals("true")){
				writeFile(sb.toString(), storageFile);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("Unable to open file '" + fileName + "'");
		} catch (JsonParseException je) {
			System.out.println("Error in gson parsing.");
		}
	}
	
	/**
	 * Stored data in a storage file
	 * 
	 * @param inputData
	 * @param storageFile
	 */
	private void writeFile(String inputData, String storageFile){
		try {
			FileWriter fileWriter = new FileWriter(storageFile);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			writer.write(inputData);
			writer.close();
		} catch (IOException e) {
			System.out.println(
	                "Error writing to file '"+ storageFile + "'");
		}
	}
}
