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
		this.id = n.getMyIp() + ":" + n.getMyPort();
	}
	
	public void getCurrentStatus(){
		this.node.getCurrentLamportStatus();
		this.ready = false;
//		try {
//			this.wait();
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		while (this.ready){
			Thread.yield();
		}
		
	}
	
	public void updateCurrentStatus(LamportStatus ls){
		this.currentStatus = ls;
		this.ready = true;
//		this.notify();
	}
	
	
	public void lock(){
//		entering.set(id, true);
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
    	
//    	ticket.set(id, 1 + max); 
//    	entering.set(id, false);
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
    	System.out.println(this.id + " consegui el lock");
	}
	
	public void unlock(){
		System.out.println(this.id + " desbloqueo");
		
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
