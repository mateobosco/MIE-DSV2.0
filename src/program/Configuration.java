package program;

public class Configuration {
/*	public String localIp = "127.0.0.1";
	public int localPort = -1;
	public String destinationIp = "127.0.0.1";
	public int destinationPort = -1;
	public String username = "";*/
	
	
	public String localIp = "127.0.0.1";
	public int localPort = 2010;
	public String destinationIp = "127.0.0.1";
	public int destinationPort = 2010;
	public String username = "mateo";
	
	public boolean isValid(){
		
		if (username.length() < 1 ) return false;
		if (localPort == -1 ) return false;
		if (destinationPort == -1) return false;
		return true;
	}
}
