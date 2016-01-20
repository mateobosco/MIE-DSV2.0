package node;

import java.io.Serializable;

public class Connection implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ip;
	private int port;
	
	public Connection(String ip, int port){
		this.ip = ip;
		this.port = port;
	}
	
	public String getIp(){
		return this.ip;
	}
	
	public int getPort(){
		return this.port;
	}
	
	public String getId(){
		return this.ip + ":" + this.port;
	}
	
	public boolean equals(Connection c){
		return (this.ip.equals(c.ip) && this.port == c.port);
	}

}
