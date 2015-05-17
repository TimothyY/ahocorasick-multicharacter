package timothyyudi.ahocorasickmulticharacter.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class Utility {

	/**read keyword file from snort rules*/
	public ArrayList<String> readKeyword(String keywordFilePath){
		Scanner scanner;
		ArrayList<String> list = new ArrayList<String>();
		String temp;
		int firstQuotes=0, secondQuotes=0, thirdQuotes=0, fourthQuotes=0;
		try {
			scanner = new Scanner(new File(keywordFilePath));
			
			while (scanner.hasNext()){
				list.add(scanner.next().trim()); //ambil per spasi.*/
			/*while (scanner.hasNextLine()){
			    temp = scanner.nextLine();
			    firstQuotes=temp.indexOf("\"")+1;
			    secondQuotes=temp.indexOf("\"", firstQuotes); //to be used on snort rules message [TEMP]
			    //secondQuotes=temp.indexOf("\"", firstQuotes)+1; //to be used in snort rules content
			    thirdQuotes=temp.indexOf("\"", secondQuotes)+1;
			    fourthQuotes=temp.indexOf("\"", thirdQuotes);
			    if(firstQuotes!=0){
			    	list.add(temp.substring(firstQuotes, secondQuotes).trim()); //to be used on snort rules message [TEMP]
			    	//list.add(temp.substring(thirdQuotes, fourthQuotes).trim()); //to be used in snort rules content
			    }*/
			}
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	
	/**Read input string file as inputString*/
	public String readInputString(String path, Charset encoding) throws IOException {
		byte[] encoded = Files.readAllBytes(Paths.get(path));
		return new String(encoded, encoding).toLowerCase();
	}
	
}
