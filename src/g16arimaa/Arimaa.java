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
Container south = new Container();
JButton placerabbit = new JButton("rabbit");
JButton placecat = new JButton("cat");
JButton placedog = new JButton("dog");
JButton placehorse = new JButton();
JButton placecamel = new JButton();
JButton placeelephant = new JButton();

ArrayList<Piece> pieces = new ArrayList<Piece>();
int rabbitsleft = 8;
int catsleft = 2;
int dogsleft = 2;
int horsesleft = 2;
int camelsleft = 1;
int elephantsleft = 1;

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
		
		south.setLayout(new GridLayout(1, 6));
		south.add(placerabbit);
		south.add(placecat);
		south.add(placedog);
		south.add(placehorse);
		south.add(placecamel);
		south.add(placeelephant);
		frame.add(south, BorderLayout.SOUTH);
		
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
		int ygrid = e.getY()/panel.getGridsize();
		int xgrid = e.getX()/panel.getGridsize();
		System.out.println(ygrid);
		System.out.println(xgrid);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
