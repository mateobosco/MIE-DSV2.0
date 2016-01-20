package program;

import java.awt.Button;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class LoginGUI extends JFrame implements WindowListener, KeyListener, MouseListener{
	

	private static final long serialVersionUID = 1L;
	private TextField usernameArea = null;
	private TextField localIpArea = null;
	private TextField localPortArea = null;
	private TextField destinationIpArea = null;
	private TextField destinationPortArea = null;

	public static void main(String[] args){
		new LoginGUI();
	}
	
	public LoginGUI(){
		super("Login");
		this.addWindowListener(this);
		this.setSize(350,320);
		this.setResizable(true);
		this.setLayout(new GridLayout());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Panel p = new Panel();
		p.setLayout(new FlowLayout());
		
		JLabel usernameLabel = new JLabel("Username", JLabel.TRAILING);
        p.add(usernameLabel);
		this.usernameArea = new TextField(30);
		this.usernameArea.addKeyListener(this);
		this.usernameArea.setFont(new Font("Arial", Font.PLAIN, 16));
		p.add(this.usernameArea);
		
		JLabel localIpLabel = new JLabel("Local IP", JLabel.TRAILING);
        p.add(localIpLabel);
		this.localIpArea = new TextField(30);
		this.localIpArea.addKeyListener(this);
		this.localIpArea.setFont(new Font("Arial", Font.PLAIN, 16));
		p.add(this.localIpArea);
		
		JLabel localPortLabel = new JLabel("Local Port", JLabel.TRAILING);
        p.add(localPortLabel);
		this.localPortArea = new TextField(30);
		this.localPortArea.addKeyListener(this);
		this.localPortArea.setFont(new Font("Arial", Font.PLAIN, 16));
		p.add(this.localPortArea);
		
		JLabel destinationIpLabel = new JLabel("Destination IP", JLabel.TRAILING);
        p.add(destinationIpLabel);
		this.destinationIpArea = new TextField(30);
		this.destinationIpArea.addKeyListener(this);
		this.destinationIpArea.setFont(new Font("Arial", Font.PLAIN, 16));
		p.add(this.destinationIpArea);
		
		JLabel destinationPortLabel = new JLabel("Destination Port", JLabel.TRAILING);
        p.add(destinationPortLabel);
		this.destinationPortArea = new TextField(30);
		this.destinationPortArea.addKeyListener(this);
		this.destinationPortArea.setFont(new Font("Arial", Font.PLAIN, 16));
		p.add(this.destinationPortArea);
		
		p.setBackground(new Color(221,221,211));
		
		Button send = new Button("Enter");
		send.addMouseListener(this);
		p.add(send);
		
		this.add(p, "South");
		this.setVisible(true);
		this.usernameArea.requestFocus();
	}
	
	private void enterText() {
		
		Configuration c = new Configuration();
		c.username = this.usernameArea.getText();
		c.localIp = this.localIpArea.getText();
		c.localPort = Integer.parseInt(this.localPortArea.getText());
		c.destinationIp = this.destinationIpArea.getText();
		c.destinationPort = Integer.parseInt(this.destinationPortArea.getText());
		
		if (c.isValid()){
			this.setVisible(false);
			new ChatGUI(c);
			
		}
	}
	
	public void keyPressed(KeyEvent arg0) {	
		if (arg0.getKeyCode() == KeyEvent.VK_ENTER){
			enterText();
		}
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
