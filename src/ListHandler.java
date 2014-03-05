import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import se.chalmers.ait.dat215.project.ShoppingItem;

public class ListHandler {
	private IMatModel model;
	private Map<String, Map<String, List<ShoppingItem>>> listMap;
	/**
	 * Creates an instance of the ListHandler working with the supplied model.
	 * @param model
	 */
	public ListHandler(IMatModel model) {
		this.model = model;
		this.listMap = null;
		try {
			this.listMap = this.getListMap();
		} catch (IOException e) {
			this.listMap = null;
		}
	}
	
	/**
	 * Returns the given user's lists.
	 * @param userName
	 * @return
	 * 			a Map<String, List<ShoppingItem> with the user's list names and corresponding products.
	 * 			Returns null if user has no lists.
	 * 
	 */
	public Map<String, List<ShoppingItem>> getLists(String userName) {		
		if (listMap.containsKey(userName)){
			return listMap.get(userName);
		} else {
			return null;
		}
	}
	
	/**
	 * Save the favorite list with the given user name and list name.
	 * If a list with matching user name and list name exists, it will be overwritten.
	 * @param list - List of ShoppingItem to save.
	 * @param listName - Name of the list.
	 * @param userName - The user name that the list belongs to.
	 */
	public void saveFavoriteList(List<ShoppingItem> list, String listName, String userName) {
		if (this.hasList(listName, userName)){
			listMap.get(userName).remove(listName);
			listMap.get(userName).put(listName, list);
		} else if (this.hasUser(userName)){
			listMap.get(userName).put(listName, list);
		} else {
			Map<String, List<ShoppingItem>> userMap = new HashMap<String, List<ShoppingItem>>();
			userMap.put(listName, list);
			listMap.put(userName, userMap);
		}
	}
	
	/**
	 * Saves all the favorite lists to file.
	 */
	public void saveState(){
		Writer writer = null;
		try {
			writer = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(model.getDataDirectory() + "/lists.txt"), "utf-8"));
			
			for (String userName : listMap.keySet()) {
				writer.write("uid" + ";" + userName + ";");
				Map<String, List<ShoppingItem>> userMap = listMap.get(userName);
				for (String listName : userMap.keySet()) {
					writer.write("listname;" + listName + ";");
					List<ShoppingItem> list = userMap.get(listName);
					for (ShoppingItem item : list){
						int productID = item.getProduct().getProductId();
						double amount = item.getAmount();
						writer.write("pid;" + productID + ";" + amount + ";");
					}
					writer.write("endlist;");
				}
				writer.write("enduser;");
			}
			
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException("I/O error. Changes could not be saved to file.");
		}
	}
	
	private boolean hasList(String listName, String userName) {
		if (listMap.containsKey(userName) && listMap.get(userName).containsKey(listName)) {
			return true;
		}
		return false;
	}
	
	private boolean hasUser(String userName) {
		if(listMap.containsKey(userName)){
			return true;
		} else {
			return false;
		}
	}
	
	private Map<String, Map<String, List<ShoppingItem>>> getListMap() throws IOException {
		Scanner sc = null;
		Map<String, Map<String, List<ShoppingItem>>> dbmap = new HashMap<String, Map<String, List<ShoppingItem>>>();
		try {
			sc = new Scanner(new FileReader(model.getDataDirectory() + "/lists.txt"));
			sc.useDelimiter(";");
			
			String userName = "";
			String listName = "";
			int productId;
			double amount;
			
			List<ShoppingItem> shoppingItems = new ArrayList<ShoppingItem>();
			Map<String, List<ShoppingItem>> userMap = new HashMap<String, List<ShoppingItem>>();
			
			if(sc.hasNext()) {
				while(sc.hasNext()){
					if(sc.hasNext("uid")){
						sc.next();
						userName = sc.next();
						userMap = new HashMap<String, List<ShoppingItem>>();
						shoppingItems = new ArrayList<ShoppingItem>();
					}
					if(sc.hasNext("listname")){
						sc.next();
						listName = sc.next();
					}
					if(sc.hasNext("pid")){
						sc.next();
						productId = Integer.parseInt(sc.next());
						amount = Double.parseDouble(sc.next());
						shoppingItems.add(new ShoppingItem(model.getProduct(productId), amount));
					}
					if(sc.hasNext("endlist")) {
						sc.next();
						userMap.put(listName, shoppingItems);
					}
					if(sc.hasNext("enduser")){
						sc.next();
						dbmap.put(userName, userMap);
					}
				}
				sc.close();
				return dbmap;
			} else { //empty file
				sc.close();
				return dbmap;
			}
		} catch (FileNotFoundException e) {
			return dbmap;
		}
	}
}
