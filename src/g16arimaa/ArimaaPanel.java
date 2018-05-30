package g16arimaa;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import javax.swing.JPanel;

public class ArimaaPanel extends JPanel {

	int gridsize;// this should mostly be kept constant

	boolean[][] trap = new boolean[8][8];// [rows][columns]
	final int STRONG_ENEMY = 1;
	final int FRIEND = 2;

	boolean strong_enemy;
	boolean friend;

	ArrayList<Piece> pieces = new ArrayList<Piece>();

	public ArimaaPanel() {
		super();
		trap[2][2] = true;
		trap[2][5] = true;
		trap[5][5] = true;
		trap[5][2] = true;
	}

	public void paintComponent(Graphics g) {
		resetGridsize();
		super.paintComponent(g);
		// paintBoards
		for (int j = 0; j < trap.length; j++) {// go through rows
			for (int i = 0; i < trap[0].length; i++) {// go though columns
				if (trap[j][i]) {
					g.setColor(Color.RED);
				} else if ((i + j) % 2 == 0) {
					g.setColor(Color.BLACK);
				} else {
					g.setColor(Color.WHITE);
				}
				g.fillRect(i * gridsize, j * gridsize, gridsize, gridsize);
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
	public boolean move(Piece piece, int moved_xgrid, int moved_ygrid, boolean checkFreeze) {// checkFreeze stores if it
																								// needs to check
																								// freezing
		if (moved_xgrid >= 0 && moved_xgrid <= 7 && moved_ygrid >= 0 && moved_ygrid <= 7) {
			if (Math.abs(moved_xgrid - piece.getX()) + Math.abs(moved_ygrid - piece.getY()) == 1) {
				if (checkMove(piece, checkFreeze) && getPiece(moved_xgrid, moved_ygrid) == null) {
					// move
					piece.setX(moved_xgrid);
					piece.setY(moved_ygrid);
					if (checktrap(piece.getX(), piece.getY(), piece.getColor())) {
						pieces.remove(piece);
					}
					repaint();
					return true;
				} else {
					if (!checkMove(piece, checkFreeze)) {
						System.out.println("it is freezed");
						return false;
					} else {
						System.out.println("There is piece on the place");
						return false;
					}
				}
			} else {
				System.out.println("it is not next to the piece");
				return false;
			}

		} else {
			System.out.println("the selected square is outside the grid");
			return false;
		}
	}

	public boolean checkMove(Piece piece, boolean check) {
		if (check) {
			int x = piece.getX();
			int y = piece.getY();
			if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
				strong_enemy = false;
				friend = false;
				check_enemy_and_friend(x - 1, y, piece.getStrength(), piece.getColor());
				check_enemy_and_friend(x + 1, y, piece.getStrength(), piece.getColor());
				check_enemy_and_friend(x, y - 1, piece.getStrength(), piece.getColor());
				check_enemy_and_friend(x, y + 1, piece.getStrength(), piece.getColor());

				if (strong_enemy == false) {
					return true;
				} else if (strong_enemy == true && friend == true) {
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

	public void check_enemy_and_friend(int x, int y, int strength, int color) {
		if (x >= 0 && x <= 7 && y >= 0 && y <= 7) {
			Piece piece = getPiece(x, y);
			if (piece != null) {
				if (piece.getStrength() > strength && piece.getColor() != color) {
					strong_enemy = true;
				} else if (piece.getColor() == color) {
					friend = true;
				}
			}
		}
	}

	public boolean checktrap(int x, int y, int color) {
		if (trap[x][y]) {
			friend = false;
			check_enemy_and_friend(x - 1, y, 0, color);
			check_enemy_and_friend(x + 1, y, 0, color);
			check_enemy_and_friend(x, y - 1, 0, color);
			check_enemy_and_friend(x, y + 1, 0, color);
			if (friend) {
				return false;
			} else {
				return true;
			}
		}
		return false;
	}

	public void push(Piece my_piece, Piece enemy_piece, int pushed_xgrid, int pushed_ygrid) {
		if (my_piece.getColor() != enemy_piece.getColor() && my_piece.getStrength() > enemy_piece.getStrength()) {// check
																													// color
																													// and
																													// strength
			int moved_xgrid = enemy_piece.getX();
			int moved_ygrid = enemy_piece.getY();// store the place that mypiece wants to move
			move(enemy_piece, pushed_xgrid, pushed_ygrid, false);
			if (getPiece(moved_xgrid, moved_ygrid) == null) {
				// move my piece
				// need to check if it is freezed??
			} else {
				System.out.println("enemy cannot move to the place");
			}
		} else {
			System.out.println("cannot push the piece");
		}
	}

}
