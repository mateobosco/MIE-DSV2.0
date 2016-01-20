package node;

import java.io.Serializable;


public class LamportUpdate implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ticketValue;
	private boolean enteringValue;
	private String key;
	
	public LamportUpdate(String key){
		this.key = key;
	}
	
	public int getTicketValue() {
		return ticketValue;
	}
	
	public boolean getEnteringValue() {
		return enteringValue;
	}
	
	public String getKey() {
		return key;
	}
	
	public void setTicketValue(int ticketValue) {
		this.ticketValue = ticketValue;
	}
	
	public void setEnteringValue(boolean enteringValue) {
		this.enteringValue = enteringValue;
	}
	
	public void setKey(String key) {
		this.key = key;
	}

}
