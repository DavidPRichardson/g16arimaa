package g16arimaa;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ArimaaPanel extends JPanel {

	final int GRIDSIZE=30;
	
	int[][] board = new int[8][8];//[rows][columns]
	final int NONE=0;
	final int GOLD_PIECE=1;
	final int SILVER_PIECE=2;
	final int TRAP=3;
	
	public ArimaaPanel() {
		super();
		board[2][2]=TRAP;
		board[5][2]=TRAP;
		board[2][5]=TRAP;
		board[5][5]=TRAP;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		//paintBoards
		for (int j = 0; j < board.length; j++) {//go through rows
			for (int i = 0; i < board[0].length; i++) {//go though columns
				if(board[j][i]==TRAP) {
					g.setColor(Color.RED);
				}
				else if(i+j%2==0) {
					g.setColor(Color.BLACK);
				}
				else{
					g.setColor(Color.WHITE);
				}
				g.fillRect(i*GRIDSIZE, j*GRIDSIZE, (i+1)*GRIDSIZE, (j+1)*GRIDSIZE);
			}
		}
	}

}
