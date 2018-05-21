package g16arimaa;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Arimaa implements ActionListener, MouseListener {
Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
// alvinalexander.com/blog/post/jfc-swing/how-determine-get-screen-size-java-swing-app
int width = 932;
int height = 800;
JFrame frame = new JFrame("Arimaa");
ArimaaPanel panel = new ArimaaPanel();
Container east = new Container();
JLabel turnlabel = new JLabel("Turn");
JLabel silverturnlabel = new JLabel("Silver");
JLabel goldturnlabel = new JLabel("Gold");
JLabel movesleftlabel = new JLabel("Moves:");
JButton pushbutton = new JButton("Push");
JButton pullbutton = new JButton("Pull");
JButton restartbutton = new JButton("Restart");
JButton skipbutton = new JButton("Skip");

ArrayList<Piece> pieces = new ArrayList<Piece>();

	public static void main(String args[]) {
		Arimaa arimaa = new Arimaa(); 
	}
	
	public Arimaa() {
		frame.setSize(width, height);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);
		
		east.setLayout(new GridLayout(5, 2));
		east.add(turnlabel);
		east.add(new JLabel(""));//empty for formating
		east.add(silverturnlabel);
		east.add(goldturnlabel);
		east.add(movesleftlabel);
		east.add(new JLabel(""));//empty for formating
		east.add(pushbutton);
		pushbutton.addActionListener(this);
		east.add(pullbutton);
		pullbutton.addActionListener(this);
		east.add(restartbutton);
		restartbutton.addActionListener(this);
		east.add(skipbutton);
		skipbutton.addActionListener(this);
		frame.add(east, BorderLayout.EAST);
		
		panel.addMouseListener(this);

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getX());
		System.out.println(e.getY());
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
