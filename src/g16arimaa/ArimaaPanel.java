package g16arimaa;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ArimaaPanel extends JPanel {

	int gridsize;// this should mostly be kept constant

	boolean[][] trap = new boolean[8][8];// [rows][columns]

	boolean strong_enemy;
	boolean friend;
	
	int[] rabbitcount = new int[3];//gold is 1, and silver is 2

	ArrayList<Piece> pieces = new ArrayList<Piece>();// store all pieces on the board
	ArrayList<Square> selectedsquares = new ArrayList<Square>(); // all squares currently selected, to be highlighted

	public ArimaaPanel() {
		super();
		// set traps
		trap[2][2] = true;
		trap[2][5] = true;
		trap[5][5] = true;
		trap[5][2] = true;
		//initialize rabbitcount
		rabbitcount[1]=8;
		rabbitcount[2]=8;
	}

	public void paintComponent(Graphics g) {
		resetGridsize();
		super.paintComponent(g);
		// paintBoards
		for (int j = 0; j < trap.length; j++) {// go through rows
			for (int i = 0; i < trap[0].length; i++) {// go though columns
				if (isSelectedSquare(i, j)) {
					g.setColor(Color.YELLOW);
				} else if (trap[j][i]) {
					g.setColor(Color.RED);
				} else if ((i + j) % 2 == 0) {
					g.setColor(Color.BLACK);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect(i * gridsize, j * gridsize, gridsize, gridsize);
			}
		}
		for (int i = 0; i < pieces.size(); i++) {// print pieces
			pieces.get(i).paintPiece(g, gridsize);
		}
	}

	public void resetGridsize() {
		gridsize = Math.min(getHeight(), getWidth()) / 8;
	}

	public int getGridsize() {
		return gridsize;
	}

	// add piece to the board
	public void addPiece(Piece p) {
		pieces.add(p);
	}

	/**
	 * Returns the piece at the given square
	 * 
	 * @param xgrid
	 * @param ygrid
	 * @return
	 */
	public Piece getPiece(int xgrid, int ygrid) {
		for (int i = 0; i < pieces.size(); i++) {
			if (pieces.get(i).getX() == xgrid && pieces.get(i).getY() == ygrid) {
				return pieces.get(i);
			}
		}
		return null;
	}

	/**
	 * try to move with the given parameters. Return true if a piece was moved,
	 * false otherwise.
	 * 
	 * @param piece
	 * @param moved_xgrid
	 * @param moved_ygrid
	 * @return
	 */
	public boolean move(Piece piece, int moved_xgrid, int moved_ygrid, boolean check) {// check stores if it
																								// needs to check
																								// freezing and rabbits' moving backward
		if (moved_xgrid >= 0 && moved_xgrid <= 7 && moved_ygrid >= 0 && moved_ygrid <= 7) {
			if (piece.possibleMoves(moved_xgrid, moved_ygrid,check)) {
				if (getPiece(moved_xgrid, moved_ygrid) == null) {
					if(checkMove(piece, check)) {
					// move
					piece.setX(moved_xgrid);
					piece.setY(moved_ygrid);
					checktrap();
					repaint();					
					return true;
					} else {
						System.out.println("It is freezed");
						return false;
					}
				} else {
					System.out.println("There is piece on the place");
					return false;
				}
			} else {
				System.out.println("it is not next to the piece, or rabbit cannot move backward");
				return false;
			}

		} else {
			System.out.println("the selected square is outside the grid");
			return false;
		}
	}

	// return if it is freezing based on the variable strong_enemy and friend
	public boolean checkMove(Piece piece, boolean check) {
		if (check) {
			int x = piece.getX();
			int y = piece.getY();
			if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
				strong_enemy = false;
				friend = false;
				// check surrounding
				check_enemy_and_friend(x - 1, y, piece.getStrength(), piece.getColor());
				check_enemy_and_friend(x + 1, y, piece.getStrength(), piece.getColor());
				check_enemy_and_friend(x, y - 1, piece.getStrength(), piece.getColor());
				check_enemy_and_friend(x, y + 1, piece.getStrength(), piece.getColor());

				if (strong_enemy == false) {
					return true;
				} else if (strong_enemy == true && friend == true) {// freezing is solved
					return true;
				}
				return false;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	// return if there is enemy or friend in the given grid
	public void check_enemy_and_friend(int x, int y, int strength, int color) {
		if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {// check if it is in the grid
			Piece piece = getPiece(x, y);
			if (piece != null) {
				if (piece.getStrength() > strength && piece.getColor() != color) {// check strength and color
					strong_enemy = true;
				} else if (piece.getColor() == color) {
					friend = true;
				}
			}
		}
	}

	// use trap method to all the traps on the board
	public void checktrap() {
		for (int j = 0; j < trap.length; j++) {// go through rows
			for (int i = 0; i < trap[0].length; i++) {// go though columns
				if (trap[j][i]) {
					trap(j, i);
				}
			}
		}
	}

	// check a place if there is a piece in the grid, and if it will be removed or
	// not (check friends)
	public void trap(int x, int y) {
		Piece piece = getPiece(x, y);
		if (piece != null) {
			int color = piece.getColor();
			friend = false;
			check_enemy_and_friend(x - 1, y, 0, color);
			check_enemy_and_friend(x + 1, y, 0, color);
			check_enemy_and_friend(x, y - 1, 0, color);
			check_enemy_and_friend(x, y + 1, 0, color);
			if (!friend) {
				pieces.remove(piece);
				if (piece.isRabbit()) {
					rabbitcount[piece.getColor()]--;
				}
			}

		}
	}

	/**
	 * try to push with the given parameters. Return true if a piece was moved,
	 * false otherwise.
	 * 
	 * @param my_piece
	 * @param enemy_piece
	 * @param pushed_xgrid
	 * @param pushed_ygrid
	 * @return
	 */
	public boolean push(Piece my_piece, Piece enemy_piece, int pushed_xgrid, int pushed_ygrid) {
		if (my_piece.getColor() != enemy_piece.getColor() && my_piece.getStrength() > enemy_piece.getStrength()) {// check
																													// color
																													// and
																													// strength
			if (checkMove(my_piece, true)) {
				int moved_xgrid = enemy_piece.getX();
				int moved_ygrid = enemy_piece.getY();// store the place that mypiece wants to move
				if (move(enemy_piece, pushed_xgrid, pushed_ygrid, false)) {
					move(my_piece, moved_xgrid, moved_ygrid, false);
					return true;
				} else {
					System.out.println("enemy couldn't move to the place");
					return false;
				}
			} else {
				System.out.println("my piece is freezed");
				return false;
			}
		} else {
			System.out.println("my piece cannot push that piece");
			return false;
		}
	}

	/**
	 * try to pull with the given parameters. Return true if a piece was moved,
	 * false otherwise.
	 * 
	 * @param my_piece
	 * @param enemy_piece
	 * @param moved_xgrid
	 * @param moved_ygrid
	 * @return
	 */
	public boolean pull(Piece my_piece, Piece enemy_piece, int moved_xgrid, int moved_ygrid) {
		if (my_piece.getColor() != enemy_piece.getColor() && my_piece.getStrength() > enemy_piece.getStrength()) {// check
																													// color
																													// and
																													// strength
			if (checkMove(my_piece, true)) {
				int pulled_xgrid = my_piece.getX();
				int pulled_ygrid = my_piece.getY();// store the place that enemy piece should move
				if (move(my_piece, moved_xgrid, moved_ygrid, false)) {
					move(enemy_piece, pulled_xgrid, pulled_ygrid, false);
					return true;
				} else {
					System.out.println("my piece couldn't move to the place");
					return false;
				}
			} else {
				System.out.println("my piece is freezed");
				return false;
			}
		} else {
			System.out.println("cannot push the piece");
			return false;
		}
	}
	
	//check win with input (moved piece) and returns the winner color
	public int checkWin(Piece piece) {
		//check winning and losing
		int color=piece.getColor();
		if(piece.isRabbit()) {
			if(((Rabbit)piece).isOtherSide()) {
				return color;
			}
		}
		if(rabbitcount[color]<=0) {
			return 3-color;//the opponent wins
		}
		return 0;
	}

	/**
	 * adds a square to be highlighted
	 */
	public void addSelectedSquare(int sx, int sy) {
		selectedsquares.add(new Square(sx, sy));
		repaint();
	}

	/**
	 * clears all highlighted squares
	 */
	public void clearSelectedSquares() {
		selectedsquares.clear();
		repaint();
	}

	/**
	 * returns whether the square at the given coords is selected
	 * 
	 * @param sx
	 * @param sy
	 * @return
	 */
	public boolean isSelectedSquare(int sx, int sy) {
		for (int i = 0; i < selectedsquares.size(); i++) {
			if (selectedsquares.get(i).getX() == sx && selectedsquares.get(i).getY() == sy) {
				return true;
			}
		}
		return false;
	}
}
