package node;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Lamport {
	
	private HashMap<String,Integer> ticket;
	private HashMap<String,Boolean> entering;
	private Node node;
	private String id;
	
	public Lamport(Node n){
		this.node = n;
		this.id = n.getMyIp() + ":" + n.getMyPort();
		ticket = new HashMap<String,Integer>();
		entering = new HashMap<String, Boolean>();
	}
	
	public void updateLamportStatus(LamportUpdate lu){
		this.ticket.put(lu.getKey(), lu.getTicketValue());
		this.entering.put(lu.getKey(), lu.getEnteringValue());
	}
	
	public void lock(){
//		entering.set(id, true);
    	LamportUpdate lu = new LamportUpdate(this.id);
    	lu.setEnteringValue(true);
    	entering.put(this.id, true);
    	this.node.sendLamportUpdate(lu);
    	
    	int n = ticket.size();
    	List<String> keys = this.getKeys();
    	
		int max = 0;
    	for (int index = 0; index < n; index++){
    		String i = keys.get(index);
    		int current = ticket.get(i);
			if (current > max){
				max = current;
			}
    	}
    	
//    	ticket.set(id, 1 + max); 
//    	entering.set(id, false);
    	lu.setTicketValue(1+max);
    	lu.setEnteringValue(false);
    	this.ticket.put(this.id, 1 + max);
    	this.entering.put(this.id, false);
    	this.node.sendLamportUpdate(lu);
    	
    	for (int index = 0; index < n; ++index) {
    		String i = keys.get(index);
    		if (i != id) {
    			while (entering.get(i) == true) { 
    				Thread.yield(); 
    			}
    			while (ticket.get(i) != 0 && ( ticket.get(id) > ticket.get(i)  || 
    					( ticket.get(id) == ticket.get(i) && id.compareTo(i) > 0))){ 
    				Thread.yield(); 
    			}
    		}
    	}
    	System.out.println(this.id + " consegui el lock");
	}
	
	public void unlock(){
		System.out.println(this.id + " desbloqueo");
		this.ticket.put(this.id, 0);
		LamportUpdate lu = new LamportUpdate(this.id);
		lu.setTicketValue(0);
		this.node.sendLamportUpdate(lu);
	}
	
	private List<String> getKeys(){
		List<String> keys = new ArrayList<String>();
		for(String k : this.entering.keySet()){
			keys.add(k);
		}
		return keys;
	}

}
