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
	final int STRONG_ENEMY=1;
	final int FRIEND=2;
	
	boolean strong_enemy;
	boolean friend;
	
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
	/**
	 * Returns the piece at the given square
	 * @param xgrid
	 * @param ygrid
	 * @return
	 */
	public Piece getPiece(int xgrid, int ygrid) {
		for (int i = 0; i < pieces.size(); i++) {
			if(pieces.get(i).getX() == xgrid && pieces.get(i).getY() == ygrid) {
				return pieces.get(i);
			}
		}
		return null;
	}
	
	public void move(Piece piece, int moved_xgrid, int moved_ygrid) {
		if(checkMove(piece) && getPiece(moved_xgrid,moved_ygrid) == null){
			piece.setX(moved_xgrid);
			piece.setY(moved_ygrid);
		}
		else {
			System.out.println("It cannot move to the place.");
		}
	}
	
	public boolean checkMove(Piece piece) {
		int x=piece.getX();
		int y=piece.getY();
		strong_enemy=false;
		friend=false;
		check_enemy_and_friend(x-1,y,piece.getStrength(),piece.getColor());
		check_enemy_and_friend(x+1,y,piece.getStrength(),piece.getColor());
		check_enemy_and_friend(x,y-1,piece.getStrength(),piece.getColor());
		check_enemy_and_friend(x,y+1,piece.getStrength(),piece.getColor());
		
		if(strong_enemy==false) {
			return true;
		}
		else if(strong_enemy==true && friend==true) {
			return true;
		}
		
		return false;
	}
	
	public void check_enemy_and_friend(int x, int y, int strength,int color) {
		if(x>=0&&x<=7&&y>=0&&y<=7) {
			Piece piece = getPiece(x,y);
			if(piece.getStrength()>strength&&piece.getColor()!=color) {
				strong_enemy=true;
			}
			else if(piece.getColor()==color) {
				friend=true;
			}
		}
		
	}
}

