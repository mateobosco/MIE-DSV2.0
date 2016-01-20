package node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NetworkStatus implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String, Connection> table;
	private int counter = 0;
	
	public NetworkStatus(){
		this.table = new HashMap<String, Connection>();
	}
	
	public void set(Connection sender, Connection receiver){		
		if (table.containsKey(sender.getId())) counter ++;
		else counter = 0;
		table.put(sender.getId(), receiver);
	}
	
	public void remove(Connection disconnected){
		table.remove(disconnected.getId());
	}
	
	public void setTable(HashMap<String, Connection> table){
		this.table = table;
	}
	
	public HashMap<String, Connection> getTable(){
		return table;
	}

	public boolean isNew() {
		return counter == 0;
	}
	
	public boolean isTriple(){
		return counter >=10;
	}
	
	public void restartCounter(){
		this.counter = 0;
	}
	
	public void print(){
		System.out.println("---------------TABLA---------------");
		for (String key : this.getKeys()){
			System.out.println("Clave " + key + " => " + table.get(key).getId());
		}
	}
	
	public List<String> getKeys(){
		List<String> keys = new ArrayList<String>();
		for(String k : this.table.keySet()){
			keys.add(k);
		}
		return keys;
	}
}
