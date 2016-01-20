package node;

import java.util.List;

public class Lamport {
	
	private int myTicket;
	private boolean myEntering;
	private Node node;
	private String id;
	private boolean ready;
	private LamportStatus currentStatus;
	
	public Lamport(Node n){
		this.node = n;
		this.id = n.getId();
	}
	
	public void getCurrentStatus(){
		this.node.getCurrentLamportStatus();
		this.ready = false;
		while (this.ready){
			Thread.yield();
		}
		
	}
	
	public void updateCurrentStatus(LamportStatus ls){
		this.currentStatus = ls;
		this.ready = true;
	}
	
	
	public void lock(){
		myEntering = true;

    	getCurrentStatus();
    	int n = this.currentStatus.getSize();
    	List<String> keys = this.currentStatus.getKeys();
    	
		int max = 0;
    	for (int index = 0; index < n; index++){
    		String i = keys.get(index);
    		int current = this.currentStatus.getTicket(i);
			if (current > max){
				max = current;
			}
    	}
    	this.myTicket = 1 + max;
    	this.myEntering = false;
    	
    	getCurrentStatus();
    	n = this.currentStatus.getSize();
    	keys = this.currentStatus.getKeys();
    	
    	for (int index = 0; index < n; ++index) {
    		String i = keys.get(index);
    		if (i != id) {
    			while (this.currentStatus.getEntering(i) == true) { 
    				Thread.yield();
    				getCurrentStatus();
    			}
    			while (this.currentStatus.getTicket(i) != 0 && 
    					( this.currentStatus.getTicket(id) > this.currentStatus.getTicket(i)  || 
    					( this.currentStatus.getTicket(id) == this.currentStatus.getTicket(i) && id.compareTo(i) > 0))){ 
    				Thread.yield();
    				getCurrentStatus();
    			}
    		}
    	}
	}
	
	public void unlock(){		
		this.myTicket = 0;
	}
	
	public String getId(){
		return this.id;
	}
	
	public int getTicketValue(){
		return this.myTicket;
	}
	
	public boolean getEnteringValue(){
		return this.myEntering;
	}

}
