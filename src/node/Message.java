package node;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String from;
	private String body;
	private String username;
	private Date date;
	private int id;
	private int logicalTime;
	private boolean connection;
	
	public Message(String from, String username, String body){
		this.from = from;
		this.username = username;
		this.body = body;
		this.date = new Date();
//		this.logicalTime = LogicalClock.getInstance().getTime();
		this.connection = false;
	}
	
	public Message(Node node, User user, String body){
		this.from = node.getId();
		this.username = user.getUsername();
		this.body = body;
		this.date = new Date();
//		this.logicalTime = LogicalClock.getInstance().getTime();
		this.connection = false;
	}
	
	public String getFrom(){
		return this.from;
	}
	
	public String getUsername(){
		return this.username;
	}
	
	public String getBody(){
		return this.body;
	}
	
	public Date getDate(){
		return this.date;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public int getId(){
		return this.id;
	}
	
	public int getLogicalTime(){
		return this.logicalTime;
	}
	
	public boolean isConnection(){
		return this.connection;
	}
	
	public void setConnection(boolean c){
		this.connection = c;
	}
}
