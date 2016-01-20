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
	private boolean isNew = true;
	private boolean isTriple = false;
	
	public NetworkStatus(){
		this.table = new HashMap<String, Connection>();
	}
	
	public void set(Connection sender, Connection receiver){
		if (table.containsKey(sender.getId()) && !isNew) isTriple = true;
		if (table.containsKey(sender.getId())) isNew = false;
		table.put(sender.getId(), receiver);
		System.out.println("agrego una linea");
	}
	
	public void remove(Connection disconnected){
		table.remove(disconnected.getId());
	}

	public boolean isNew() {
		return isNew;
	}
	
	public boolean isTriple(){
		return this.isTriple;
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
