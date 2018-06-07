package g16arimaa;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class Arimaa implements ActionListener, MouseListener {
	Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
	// alvinalexander.com/blog/post/jfc-swing/how-determine-get-screen-size-java-swing-app
	int width = 932;
	int height = 800;
	JFrame frame = new JFrame("Arimaa");
	ArimaaPanel panel = new ArimaaPanel();
	Container east = new Container();
	JLabel turnlabel = new JLabel("Turn:");
	JLabel silverturnlabel = new JLabel("Silver", SwingConstants.CENTER);
	JLabel goldturnlabel = new JLabel("Gold", SwingConstants.CENTER);
	JLabel movesleftlabel = new JLabel("Place Pieces!");
	JButton pushbutton = new JButton("Push");
	JButton pullbutton = new JButton("Pull");
	JButton restartbutton = new JButton("Restart");
	JButton skipbutton = new JButton("Skip");
	Container south = new Container();
	JButton placerabbit = new JButton("rabbit");
	JButton placecat = new JButton("cat");
	JButton placedog = new JButton("dog");
	JButton placehorse = new JButton("horse");
	JButton placecamel = new JButton("camel");
	JButton placeelephant = new JButton("elephant");

	// ArrayList<Piece> pieces = new ArrayList<Piece>();
	int rabbitsleft = 8;
	int catsleft = 2;
	int dogsleft = 2;
	int horsesleft = 2;
	int camelsleft = 1;
	int elephantsleft = 1;

	final int RABBIT = 0;
	final int CAT = 1;
	final int DOG = 2;
	final int HORSE = 3;
	final int CAMEL = 4;
	final int ELEPHANT = 5;
	int piecetobeplaced;

	boolean placementphase = true;

	Piece selectedpiece = null;

	final int GOLD = 1;
	final int SILVER = 2;
	int turn = GOLD;

	int movesleft = 4;

	boolean push = false;
	Piece push_mypiece = null;
	Piece push_otherpiece = null;
	int push_xgrid = -1;
	int push_ygrid = -1;

	boolean pull = false;
	Piece pull_mypiece = null;
	Piece pull_otherpiece = null;
	int pull_xgrid = -1;
	int pull_ygrid = -1;

	public static void main(String args[]) {
		Arimaa arimaa = new Arimaa();
	}

	public Arimaa() {
		frame.setSize(width, height);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);

		east.setLayout(new GridLayout(5, 2));
		east.add(turnlabel);
		east.add(new JLabel(""));// empty for formating
		east.add(goldturnlabel);
		goldturnlabel.setOpaque(true);
		east.add(silverturnlabel);
		silverturnlabel.setOpaque(true);
		east.add(movesleftlabel);
		east.add(new JLabel(""));// empty for formating
		east.add(pushbutton);
		pushbutton.addActionListener(this);
		east.add(pullbutton);
		pullbutton.addActionListener(this);
		east.add(restartbutton);
		restartbutton.addActionListener(this);
		east.add(skipbutton);
		skipbutton.addActionListener(this);

		goldturnlabel.setBackground(Color.GREEN);
		silverturnlabel.setBackground(Color.RED);

		frame.add(east, BorderLayout.EAST);

		south.setLayout(new GridLayout(1, 6));
		south.add(placerabbit);
		placerabbit.addActionListener(this);
		south.add(placecat);
		placecat.addActionListener(this);
		south.add(placedog);
		placedog.addActionListener(this);
		south.add(placehorse);
		placehorse.addActionListener(this);
		south.add(placecamel);
		placecamel.addActionListener(this);
		south.add(placeelephant);
		placeelephant.addActionListener(this);

		placerabbit.setBackground(Color.GREEN);
		placecat.setBackground(Color.GREEN);
		placedog.setBackground(Color.GREEN);
		placehorse.setBackground(Color.GREEN);
		placecamel.setBackground(Color.GREEN);
		placeelephant.setBackground(Color.GREEN);

		frame.add(south, BorderLayout.SOUTH);

		panel.addMouseListener(this);
		setpush(false);
		setpull(false);

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
		int ygrid = e.getY() / panel.getGridsize();
		int xgrid = e.getX() / panel.getGridsize();
		if (xgrid >= 0 && xgrid < 8 && ygrid >= 0 && ygrid < 8) {
			if (placementphase && panel.getPiece(xgrid, ygrid) == null && xgrid < 8) {// add pieces to the grid
				if (turn == GOLD && (ygrid == 6 || ygrid == 7)) {// based on the piece the user wants to place,
																	// determine
																	// whether they can, in fact, place it at the given
																	// square
					switch (piecetobeplaced) {
					case RABBIT:
						if (rabbitsleft > 0) {
							panel.addPiece(new Rabbit(xgrid, ygrid, GOLD));
							rabbitsleft--;
							if (rabbitsleft == 0) {
								placerabbit.setBackground(Color.RED);
							}
						}
						break;
					case CAT:
						if (catsleft > 0) {
							panel.addPiece(new Cat(xgrid, ygrid, GOLD));
							catsleft--;
							if (catsleft == 0) {
								placecat.setBackground(Color.RED);
							}
						}
						break;
					case DOG:
						if (dogsleft > 0) {
							panel.addPiece(new Dog(xgrid, ygrid, GOLD));
							dogsleft--;
							if (dogsleft == 0) {
								placedog.setBackground(Color.RED);
							}
						}
						break;
					case HORSE:
						if (horsesleft > 0) {
							panel.addPiece(new Horse(xgrid, ygrid, GOLD));
							horsesleft--;
							if (horsesleft == 0) {
								placehorse.setBackground(Color.RED);
							}
						}
						break;
					case CAMEL:
						if (camelsleft > 0) {
							panel.addPiece(new Camel(xgrid, ygrid, GOLD));
							camelsleft--;
							placecamel.setBackground(Color.RED);
						}
						break;
					case ELEPHANT:
						if (elephantsleft > 0) {
							panel.addPiece(new Elephant(xgrid, ygrid, GOLD));
							elephantsleft--;
							placeelephant.setBackground(Color.RED);
						}
						break;

					default:
						break;
					}
					panel.repaint();
					if (endplacementturn()) {
						turn = SILVER;
						placerabbit.setBackground(Color.GREEN);
						placecat.setBackground(Color.GREEN);
						placedog.setBackground(Color.GREEN);
						placehorse.setBackground(Color.GREEN);
						placecamel.setBackground(Color.GREEN);
						placeelephant.setBackground(Color.GREEN);
						goldturnlabel.setBackground(Color.RED);
						silverturnlabel.setBackground(Color.GREEN);
					}
				}
				if (turn == SILVER && (ygrid == 0 || ygrid == 1)) {
					switch (piecetobeplaced) {
					case RABBIT:
						if (rabbitsleft > 0) {
							panel.addPiece(new Rabbit(xgrid, ygrid, SILVER));
							rabbitsleft--;
							if (rabbitsleft == 0) {
								placerabbit.setBackground(Color.RED);
							}
						}
						break;
					case CAT:
						if (catsleft > 0) {
							panel.addPiece(new Cat(xgrid, ygrid, SILVER));
							catsleft--;
							if (catsleft == 0) {
								placecat.setBackground(Color.RED);
							}
						}
						break;
					case DOG:
						if (dogsleft > 0) {
							panel.addPiece(new Dog(xgrid, ygrid, SILVER));
							dogsleft--;
							if (dogsleft == 0) {
								placedog.setBackground(Color.RED);
							}
						}
						break;
					case HORSE:
						if (horsesleft > 0) {
							panel.addPiece(new Horse(xgrid, ygrid, SILVER));
							horsesleft--;
							if (horsesleft == 0) {
								placehorse.setBackground(Color.RED);
							}
						}
						break;
					case CAMEL:
						if (camelsleft > 0) {
							panel.addPiece(new Camel(xgrid, ygrid, SILVER));
							camelsleft--;
							placecamel.setBackground(Color.RED);
						}
						break;
					case ELEPHANT:
						if (elephantsleft > 0) {
							panel.addPiece(new Elephant(xgrid, ygrid, SILVER));
							elephantsleft--;
							placeelephant.setBackground(Color.RED);
						}
						break;

					default:
						break;
					}
					panel.repaint();
					if (endplacementturn()) {
						turn = GOLD;
						goldturnlabel.setBackground(Color.GREEN);
						silverturnlabel.setBackground(Color.RED);
						placementphase = false;
						movesleftlabel.setText("Moves left: 4");
						frame.remove(south);
						frame.revalidate();
					}
				}
				// this is the actual game portion
			} else if (!placementphase) {
				if (!push && !pull) {
					if (selectedpiece == null && panel.getPiece(xgrid, ygrid) != null&&panel.getPiece(xgrid, ygrid).getColor() == turn) {// if no piece is
																									// selected,
																									// select a new
																									// piece
						selectedpiece = panel.getPiece(xgrid, ygrid);
						panel.clearSelectedSquares();
						panel.addSelectedSquare(selectedpiece.getX(), selectedpiece.getY());
					} else if (panel.getPiece(xgrid, ygrid) != null && selectedpiece != null
							&& panel.getPiece(xgrid, ygrid).getColor() == turn) {// if there is a selected piece, but
																					// the
																					// user
																					// clicks a different piece, select
																					// that
																					// piece
						selectedpiece = panel.getPiece(xgrid, ygrid);
						panel.clearSelectedSquares();
						panel.addSelectedSquare(selectedpiece.getX(), selectedpiece.getY());
					} else if (panel.getPiece(xgrid, ygrid) == null && selectedpiece!=null) {// move the piece
						if (panel.move(selectedpiece, xgrid, ygrid, true)) {
							if(panel.checkWin(selectedpiece)!=0) {//game ends
								gameEnd(panel.checkWin(selectedpiece));
							}
							movesleft--;
							panel.clearSelectedSquares();
							if (movesleft == 0) {
								changeturn();
							} else {
								panel.addSelectedSquare(selectedpiece.getX(), selectedpiece.getY());
							}
							movesleftlabel.setText("Moves left: " + movesleft);
						}
					}
					else {
						System.out.println("It is not your turn");
					}
				} else if (push) {// conditions for pushing a piece
					// first, find mypiece
					if (push_mypiece == null && panel.getPiece(xgrid, ygrid) != null&& panel.getPiece(xgrid, ygrid).getColor() == turn) {
						push_mypiece = panel.getPiece(xgrid, ygrid);
						panel.addSelectedSquare(push_mypiece.getX(), push_mypiece.getY());
					} else if (push_otherpiece == null && panel.getPiece(xgrid, ygrid) != null && panel.getPiece(xgrid, ygrid).getColor() != turn) {
						push_otherpiece = panel.getPiece(xgrid, ygrid);
						panel.addSelectedSquare(push_otherpiece.getX(), push_otherpiece.getY());
					} else if (push_xgrid == -1) {
						push_xgrid = xgrid;
						push_ygrid = ygrid;
					}
					if (push_mypiece != null && push_otherpiece != null && push_xgrid!=-1 && push_ygrid!=-1 && movesleft > 1) {
						// do the push
						if (panel.push(push_mypiece, push_otherpiece, push_xgrid, push_ygrid)) {
							// the move was successfully performed
							if(panel.checkWin(push_mypiece)!=0) {//game ends
								gameEnd(panel.checkWin(push_mypiece));
							}
							if(panel.checkWin(push_otherpiece)!=0) {//game ends
								gameEnd(panel.checkWin(push_otherpiece));
							}
							movesleft -= 2;
							movesleftlabel.setText("Moves left: " + movesleft);
							setpush(false);
							if (movesleft == 0) {
								changeturn();
							}
						}
					}
				} else if (pull) {// conditions for pushing a piece
					// first, find mypiece
					if (pull_mypiece == null && panel.getPiece(xgrid, ygrid) != null && panel.getPiece(xgrid, ygrid).getColor() == turn) {
						pull_mypiece = panel.getPiece(xgrid, ygrid);
						panel.addSelectedSquare(pull_mypiece.getX(), pull_mypiece.getY());
					} else if (pull_otherpiece == null && panel.getPiece(xgrid, ygrid) != null && panel.getPiece(xgrid, ygrid).getColor() != turn) {
						pull_otherpiece = panel.getPiece(xgrid, ygrid);
						panel.addSelectedSquare(pull_otherpiece.getX(), pull_otherpiece.getY());
					} else if (pull_xgrid == -1) {
						pull_xgrid = xgrid;
						pull_ygrid = ygrid;
					}
					if (pull_mypiece != null && pull_otherpiece != null && pull_xgrid!=-1 && pull_ygrid!=-1 && movesleft > 1) {
						// do the push
						if (panel.pull(pull_mypiece, pull_otherpiece, pull_xgrid, pull_ygrid)) {
							// the move was successfully performed
							if(panel.checkWin(pull_mypiece)!=0) {//game ends
								gameEnd(panel.checkWin(pull_mypiece));
							}
							if(panel.checkWin(pull_otherpiece)!=0) {//game ends
								gameEnd(panel.checkWin(pull_otherpiece));
							}
							movesleft -= 2;
							movesleftlabel.setText("Moves left: " + movesleft);
							setpull(false);
							if (movesleft == 0) {
								changeturn();
							}
						}
					}
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(placerabbit)) {
			piecetobeplaced = RABBIT;
		}
		if (e.getSource().equals(placecat)) {
			piecetobeplaced = CAT;
		}
		if (e.getSource().equals(placedog)) {
			piecetobeplaced = DOG;
		}
		if (e.getSource().equals(placehorse)) {
			piecetobeplaced = HORSE;
		}
		if (e.getSource().equals(placecamel)) {
			piecetobeplaced = CAMEL;
		}
		if (e.getSource().equals(placeelephant)) {
			piecetobeplaced = ELEPHANT;
		}
		if (!placementphase) {
			if (e.getSource().equals(pushbutton)) {
				if (!push) {
					setpush(true);
					push_mypiece = null;
					push_otherpiece = null;
					push_xgrid = -1;
					push_ygrid = -1;
					panel.clearSelectedSquares();
				} else {
					setpush(false);
				}
			}
			if (e.getSource().equals(pullbutton)) {
				if (!pull) {
					setpull(true);
					pull_mypiece = null;
					pull_otherpiece = null;
					pull_xgrid = -1;
					pull_ygrid = -1;
					panel.clearSelectedSquares();
				} else {
					setpull(false);
				}
			}
		}
		if(e.getSource().equals(skipbutton)) {
			//if moves left is 4, asks if they cannot move anymore
			if(movesleft==4) {
				int answer = JOptionPane.showConfirmDialog(frame, "Do you mean you have no possible moves? (If yes, you will lose)");
				if(answer == JOptionPane.YES_OPTION) {
					gameEnd(3-turn);
				}
				else {
					System.out.println("Cancel to skip");
					//do nothing
				}
			}
			else {
				changeturn();
				movesleft=4;
				movesleftlabel.setText("Moves left: " + movesleft);
			}
		}
		if(e.getSource().equals(restartbutton)) {
			restart();
		}
	}

	/**
	 * check if every piece has been placed
	 * 
	 * @return
	 */
	public boolean endplacementturn() {
		if (rabbitsleft == 0 && catsleft == 0 && dogsleft == 0 && horsesleft == 0 && camelsleft == 0
				&& elephantsleft == 0) {
			rabbitsleft = 8;
			catsleft = 2;
			dogsleft = 2;
			horsesleft = 2;
			camelsleft = 1;
			elephantsleft = 1;
			return true;
		} else {
			return false;
		}
	}

	public void changeturn() {
		if (turn == GOLD) {
			turn = SILVER;
			goldturnlabel.setBackground(Color.RED);
			silverturnlabel.setBackground(Color.GREEN);
		} else {
			turn = GOLD;
			goldturnlabel.setBackground(Color.GREEN);
			silverturnlabel.setBackground(Color.RED);
		}
		selectedpiece = null;
		movesleft = 4;
		
	}

	public void setpull(boolean b) {
		if (b) {
			pull = true;
			pullbutton.setBackground(Color.GREEN);
			setpush(false);
		} else {
			pull = false;
			pullbutton.setBackground(Color.RED);
			pull_mypiece = null;
			pull_otherpiece = null;
			pull_xgrid = -1;
			pull_ygrid = -1;
			panel.clearSelectedSquares();
		}
	}

	public void setpush(boolean b) {
		if (b) {
			push = true;
			pushbutton.setBackground(Color.GREEN);
			setpull(false);
		} else {
			push = false;
			pushbutton.setBackground(Color.RED);
			push_mypiece = null;
			push_otherpiece = null;
			push_xgrid = -1;
			push_ygrid = -1;
			panel.clearSelectedSquares();
		}
	}
	
	//game ends with winner
	public void gameEnd(int winner) {
		if(winner==GOLD) {
			JOptionPane.showMessageDialog(frame, "Gold won!");
		}
		else if(winner == SILVER) {
			JOptionPane.showMessageDialog(frame, "Silver won!");
		}
	}
	/**
	 * restarts the game
	 */
	public void restart() {
		//see if we need to add the south buttons back.
		if(!placementphase) {
			frame.add(south, BorderLayout.SOUTH);
			frame.revalidate();
			placerabbit.setBackground(Color.GREEN);
			placecat.setBackground(Color.GREEN);
			placedog.setBackground(Color.GREEN);
			placehorse.setBackground(Color.GREEN);
			placecamel.setBackground(Color.GREEN);
			placeelephant.setBackground(Color.GREEN);
		}
		frame.revalidate();
		
		movesleftlabel.setText("Place Pieces!");
		//revert to intial declarations
		rabbitsleft = 8;
		catsleft = 2;
		dogsleft = 2;
		horsesleft = 2;
		camelsleft = 1;
		elephantsleft = 1;

		placementphase = true;

		selectedpiece = null;

		turn = GOLD;

		int movesleft = 4;

		push = false;
		push_mypiece = null;
		push_otherpiece = null;
		push_xgrid = -1;
		push_ygrid = -1;

		pull = false;
		pull_mypiece = null;
		pull_otherpiece = null;
		pull_xgrid = -1;
		pull_ygrid = -1;
		
		panel.reset();
	}
}
