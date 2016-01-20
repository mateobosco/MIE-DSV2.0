package program;

import java.awt.*;
import java.awt.event.*;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import node.Connection;
import node.Message;
import node.Node;
import node.User;

public class ChatGUI extends JFrame implements WindowListener, MouseListener, KeyListener, MessageReceiver{

	private static final long serialVersionUID = 1L;
	private TextArea messageArea = null;
	private TextField sendArea = null;
	private Node node = null;
	private User user = null;
	
	
	public ChatGUI(Configuration conf){
		super("Chat");
		this.addWindowListener(this);
		this.setSize(400,500);
		this.setResizable(true);
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		        node.logout();
		    }
		});
		
		this.messageArea = new TextArea();
		this.messageArea.setEditable(false);
		this.add(this.messageArea, "Center");
		this.messageArea.setFont(new Font("Arial", Font.PLAIN, 16));
		
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		
		this.sendArea = new TextField(30);
		this.sendArea.addKeyListener(this);
		this.sendArea.setFont(new Font("Arial", Font.PLAIN, 16));
		p.add(this.sendArea);
		p.setBackground(new Color(221,221,211));
		
		Button send = new Button("Send");
		send.addMouseListener(this);
		p.add(send);
		
		this.add(p, "South");
		this.setVisible(true);
		this.sendArea.requestFocus();
		

		Connection connTo = new Connection( conf.destinationIp, conf.destinationPort);
		Connection connLocal = new Connection(conf.localIp, conf.localPort);
		this.node = new Node(connTo, connLocal);
		this.user = new User(conf.username);
		this.node.setMessageReceiver(this);
	}
	
	public void enterText(){
		String text = this.sendArea.getText();
		if (text.length() < 1) return;
		this.sendArea.setText("");
		Message m = new Message(this.node, this.user, text);
		this.node.sendMessage(m);
	}
	
	public void keyPressed(KeyEvent arg0) {	
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
			enterText();
		}
	}
	
	@SuppressWarnings("deprecation")
	public void receiveMessage(Message m) {
		String time = m.getDate().getHours() + ":" + m.getDate().getMinutes();
		this.messageArea.append(m.getUsername() + " (" + time + ")> " + m.getBody() + "\n" );
	}

	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}

	public void mouseClicked(MouseEvent arg0) {
		if (SwingUtilities.isLeftMouseButton(arg0)){
			enterText();
		}
	}

	public void mouseEntered(MouseEvent arg0) {	}

	public void mouseExited(MouseEvent arg0) {	}

	public void mousePressed(MouseEvent arg0) {	}

	public void mouseReleased(MouseEvent arg0) {}

	public void windowActivated(WindowEvent e) {}

	public void windowClosed(WindowEvent e) {}

	public void windowClosing(WindowEvent e) {}

	public void windowDeactivated(WindowEvent e) {}

	public void windowDeiconified(WindowEvent e) {}

	public void windowIconified(WindowEvent e) {}

	public void windowOpened(WindowEvent e) {}


}
