package no.java.beerduke.rfid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class PersonImporter {

	public static Map loadPersons(File source) {
		Map map = new HashMap();
		BufferedReader input = null;
		try {
			if (source.exists()) {

				input = new BufferedReader(new InputStreamReader(new FileInputStream(source), "utf-8"));
				String line = input.readLine(); // heading, don't care
				line = input.readLine();
				while(line != null){
					String[] items = line.split(";");
					if(items.length >= 5){
//						company;name;status;rfidDec;rfid;hero
						Person p = new Person(items[4].trim(), items[1].trim(), items[0].trim(), items.length >= 6 ? items[5].trim() : "", items[3].trim());
						if(items.length >= 7){
							p.setBeerCount(Integer.parseInt(items[6].trim()));
						}
						if(items.length >= 8){
							p.setRejectCount(Integer.parseInt(items[7].trim()));
						}
						System.out.println("Loaded person: " + p.toString());
						map.put(p.getRfid(), p);
					}
					line = input.readLine();
				}
			}else{
				System.out.println("File " + source.getPath() + " not found.");
			}
		} catch (FileNotFoundException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			if (input != null)
				try {
					input.close();
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
		}

		return map;
	}

}
