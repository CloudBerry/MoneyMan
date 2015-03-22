package moneyman;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;

public class CountryListReader {
	
	/**
	 * Gets information about countries
	 * @return Hashset where currency code is key
	 */
	public HashMap<String, String[]> getCountries(){
		HashMap<String, String[]> countries = new HashMap<>();
		try {
			
			File f = new File("res/countries.csv");
			BufferedReader br = new BufferedReader(new FileReader(f));
			
			String line;
			while ((line = br.readLine()) != null) {
				line = line.replace("\"", "");
				String[] data = line.split(",");
				String[] values = new String[2];
				values[0] = data[0];
				values[1] = data[1];
				countries.put(data[2], values);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return countries;
	}
	
}
