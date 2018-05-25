package g16arimaa;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ArimaaPanel extends JPanel {

	int gridsize;//this should mostly be kept constant
	
	int[][] board = new int[8][8];//[rows][columns]
	final int NONE=0;
	final int GOLD_PIECE=1;
	final int SILVER_PIECE=2;
	final int TRAP=3;
	final int TRAP_WITH_GOLD = 4;
	final int TRAP_WITH_SILVER = 5;
	
	ArrayList<Piece> pieces = new ArrayList<Piece>();
	
	public ArimaaPanel() {
		super();
		board[2][2]=TRAP;
		board[5][2]=TRAP;
		board[2][5]=TRAP;
		board[5][5]=TRAP;
	}
	
	public void paintComponent(Graphics g) {
		resetGridsize();
		super.paintComponent(g);
		//paintBoards
		for (int j = 0; j < board.length; j++) {//go through rows
			for (int i = 0; i < board[0].length; i++) {//go though columns
				if(board[j][i]>=TRAP) {
					g.setColor(Color.RED);
				}
				else if((i+j)%2==0) {
					g.setColor(Color.BLACK);
				}
				else{
					g.setColor(Color.WHITE);
				}
				g.fillRect(i*gridsize, j*gridsize, gridsize, gridsize);
			}
		}
		for (int i = 0; i < pieces.size(); i++) {
			pieces.get(i).paintPiece(g, gridsize);
		}
	}
	
	public Piece getPiece(int x, int y) {
		for (int i = 0; i < pieces.size(); i++) {
			if(x>pieces.get(i).getX()-gridsize && x<pieces.get(i).getX()+gridsize
					&& y>pieces.get(i).getY()-gridsize && y<pieces.get(i).getY()+gridsize) {
				return pieces.get(i);
			}
		}
		return null;
	}
	
	public void resetGridsize() {
		gridsize = Math.min(getHeight(), getWidth()) / 8;
	}
	
	public int getGridsize() {
		return gridsize;
	}
	
	public int[][] getBoard() {
		return board;
	}
	/**
	 * return the value of the square at the given position
	 * @param y
	 * @param x
	 * @return
	 */
	public int getBoardSquare(int y, int x) {
		return board[y][x];
	}
	/**
	 * set the given square to the given value
	 * @param y
	 * @param x
	 * @param value
	 */
	public void setBoardSquare(int y, int x, int value) {
		board[y][x] = value;
	}
	public void addPiece(Piece p) {
		pieces.add(p);
	}
	public ArrayList<Piece> getPieces() {
		return pieces;
	}
	/**
	 * Returns the piece at the given square
	 * @param xgrid
	 * @param ygrid
	 * @return
	 */
	public Piece pieceAtSquare(int xgrid, int ygrid) {
		for (int i = 0; i < getPieces().size(); i++) {
			if(getPieces().get(i).getX() == xgrid && getPieces().get(i).getY() == ygrid) {
				return getPieces().get(i);
			}
		}
		return null;
	}
}

