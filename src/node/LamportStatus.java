package node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LamportStatus implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private HashMap<String,Integer> ticket;
	private HashMap<String,Boolean> entering;
	
	public LamportStatus(){
		ticket = new HashMap<String,Integer>();
		entering = new HashMap<String, Boolean>();
	}
	
	public void addMyStatus(Lamport l){
		this.ticket.put(l.getId(), l.getTicketValue());
		this.entering.put(l.getId(), l.getEnteringValue());
	}
	
	public int getSize(){
		return this.entering.size();
	}
	
	public List<String> getKeys(){
		List<String> keys = new ArrayList<String>();
		for(String k : this.entering.keySet()){
			keys.add(k);
		}
		return keys;
	}
	
	public int getTicket(String key){
		return this.ticket.get(key);
	}
	
	public boolean getEntering(String key){
		return this.entering.get(key);
	}

}
