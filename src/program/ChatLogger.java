package program;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import node.LogicalClock;

public class ChatLogger {
	
	private String id;
	private PrintWriter writer;
	
	public ChatLogger(String id){
		this.id = id;		
		try {
			writer = new PrintWriter(id + ".log", "UTF-8");
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public void log(String body){
		int logicalTime = LogicalClock.getInstance().getTime();
		String datetime = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(Calendar.getInstance().getTime());
		String logicalTimeString = "Logical Time: " + logicalTime;
		String datetimeString = " | " + datetime;
		String finalBody = id + " : " + body + "(" + logicalTimeString + datetimeString + ")";
		
		writer.println(finalBody);
		writer.flush();
	}
}
