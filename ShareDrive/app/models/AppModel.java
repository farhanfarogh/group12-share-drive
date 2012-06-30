package models;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AppModel {
	public HashMap<Integer, String> destinationCampusMap;
	public List<String> regularityList;
	
	public AppModel()
	{
		destinationCampusMap = new HashMap<Integer, String>();
		destinationCampusMap.put(1, "TUM, Garching");
		destinationCampusMap.put(2, "TUM, Stammgelende");
		destinationCampusMap.put(3, "LMU, City center");
		
		regularityList = new LinkedList<String>();
		regularityList.add("once");
		regularityList.add("multiple times");
		regularityList.add("weekly");
	}
	
	public static boolean ValidateEmail(String email){
		
		if(email.toLowerCase().endsWith("@tum.de") || 
			email.toLowerCase().endsWith("@in.tum.de") ||
			email.toLowerCase().endsWith("@mytum.de"))
			return true;
		else
		return false;
	}
}
