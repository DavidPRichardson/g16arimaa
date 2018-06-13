package g16arimaa;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

import javafx.scene.layout.Border;

public class Arimaa implements ActionListener, MouseListener {
	//set the size of the frame
	int width = 932;
	int height = 800;
	//initialize the components of the frame
	JFrame frame = new JFrame("Arimaa");
	ArimaaPanel panel = new ArimaaPanel();
	Container east = new Container();
	JLabel turnlabel = new JLabel("Turn:");
	JLabel silverturnlabel = new JLabel("Silver", SwingConstants.CENTER);
	JLabel goldturnlabel = new JLabel("Gold", SwingConstants.CENTER);
	JLabel movesleftlabel = new JLabel("Moves left: ");
	JLabel messagelabel = new JLabel("Message: Place gold pieces!");
	JButton pushbutton = new JButton("Push");
	JButton pullbutton = new JButton("Pull");
	JButton restartbutton = new JButton("Restart");
	JButton skipbutton = new JButton("Skip");
	Container south = new Container();
	Container placebuttons = new Container();
	JButton placerabbit = new JButton("rabbit");
	JButton placecat = new JButton("cat");
	JButton placedog = new JButton("dog");
	JButton placehorse = new JButton("horse");
	JButton placecamel = new JButton("camel");
	JButton placeelephant = new JButton("elephant");

	//# of each animal that has to be added to the board
	int rabbitsleft = 8;
	int catsleft = 2;
	int dogsleft = 2;
	int horsesleft = 2;
	int camelsleft = 1;
	int elephantsleft = 1;

	//constant for each animal
	final int RABBIT = 0;
	final int CAT = 1;
	final int DOG = 2;
	final int HORSE = 3;
	final int CAMEL = 4;
	final int ELEPHANT = 5;
	int piecetobeplaced;

	//these variables show the phase of the game
	boolean placementphase = true;
	boolean boardInteraction = true;
	
	Piece selectedpiece = null;

	//constant for each color
	final int GOLD = 1;
	final int SILVER = 2;
	int turn = GOLD;

	int movesleft = 4;

	//variables for push method
	boolean push = false;
	Piece push_mypiece = null;
	Piece push_otherpiece = null;
	int push_xgrid = -1;
	int push_ygrid = -1;

	//variables for pull method
	boolean pull = false;
	Piece pull_mypiece = null;
	Piece pull_otherpiece = null;
	int pull_xgrid = -1;
	int pull_ygrid = -1;
	
	String message;

	//main method
	public static void main(String args[]) {
		new Arimaa();
	}

	//constructor
	public Arimaa() {
		frame.setSize(width, height);
		frame.setLayout(new BorderLayout());
		frame.add(panel, BorderLayout.CENTER);

		//add stuff to the frame
		east.setLayout(new GridLayout(6, 2));
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

		//set background color
		goldturnlabel.setBackground(Color.GREEN);
		silverturnlabel.setBackground(Color.RED);

		frame.add(east, BorderLayout.EAST);

		//add buttons and message on the bottom
		south.setLayout(new GridLayout(2,1));
		placebuttons.setLayout(new GridLayout(1, 6));
		placebuttons.add(placerabbit);
		placerabbit.addActionListener(this);
		placebuttons.add(placecat);
		placecat.addActionListener(this);
		placebuttons.add(placedog);
		placedog.addActionListener(this);
		placebuttons.add(placehorse);
		placehorse.addActionListener(this);
		placebuttons.add(placecamel);
		placecamel.addActionListener(this);
		placebuttons.add(placeelephant);
		placeelephant.addActionListener(this);
		south.add(placebuttons);
		south.add(messagelabel);
		messagelabel.setBorder(BorderFactory.createLineBorder(Color.black));
		messagelabel.setFont(new Font(messagelabel.getName(), Font.PLAIN, 20));

		//set back ground color
		placerabbit.setBackground(Color.GREEN);
		placecat.setBackground(Color.GREEN);
		placedog.setBackground(Color.GREEN);
		placehorse.setBackground(Color.GREEN);
		placecamel.setBackground(Color.GREEN);
		placeelephant.setBackground(Color.GREEN);

		frame.add(south, BorderLayout.SOUTH);

		panel.addMouseListener(this);
		//set the original state to be just moving
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
		if(boardInteraction) {//if board is not freezed
			//set xgrid and ygrid to the mouse position based on the grid
			int ygrid = e.getY() / panel.getGridsize();
			int xgrid = e.getX() / panel.getGridsize();
			if (xgrid >= 0 && xgrid < 8 && ygrid >= 0 && ygrid < 8) {//if mouse is on the board
				if (placementphase && panel.getPiece(xgrid, ygrid) == null && xgrid < 8) {// add pieces to the grid
					if (turn == GOLD && (ygrid == 6 || ygrid == 7)) {// based on the piece the user wants to place,
																		// determine
																		// whether they can, in fact, place it at the given
																		// square
						switch (piecetobeplaced) {//place animals based on this variable
						case RABBIT:
							if (rabbitsleft > 0) {
								panel.addPiece(new Rabbit(xgrid, ygrid, GOLD));
								rabbitsleft--;
								if (rabbitsleft == 0) {
									placerabbit.setBackground(Color.RED);//change color
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
						if (endplacementturn()) {//if all the pieces are placed
							turn = SILVER;//change turn
							//reset color
							placerabbit.setBackground(Color.GREEN);
							placecat.setBackground(Color.GREEN);
							placedog.setBackground(Color.GREEN);
							placehorse.setBackground(Color.GREEN);
							placecamel.setBackground(Color.GREEN);
							placeelephant.setBackground(Color.GREEN);
							goldturnlabel.setBackground(Color.RED);
							silverturnlabel.setBackground(Color.GREEN);
							messagelabel.setText("Message: Place silver pieces!");
						}
					}
					else if (turn == SILVER && (ygrid == 0 || ygrid == 1)) {//if turn is silver and ygrid is appropriate
						switch (piecetobeplaced) {//place animals based on this variable
						case RABBIT:
							if (rabbitsleft > 0) {
								panel.addPiece(new Rabbit(xgrid, ygrid, SILVER));
								rabbitsleft--;
								if (rabbitsleft == 0) {
									placerabbit.setBackground(Color.RED);//change color
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
						if (endplacementturn()) {//if all the pieces are placed
							turn = GOLD;//change turn
							goldturnlabel.setBackground(Color.GREEN);
							silverturnlabel.setBackground(Color.RED);
							placementphase = false;//change game phase to actual game
							movesleftlabel.setText("Moves left: 4");
							//remove buttons
							south.remove(placebuttons);
							south.setLayout(new GridLayout(1,1));
							frame.revalidate();
							messagelabel.setText("Message: Game starts! Select your piece to move");
						}
					}
					else {//if ygrid is not proper
						messagelabel.setText("Message: You can only place pieces on the first two rows.");
					}
					
					
					// this is the actual game portion
				} else if (!placementphase) {//if the phase is actual game
					if (!push && !pull) {//if it is normal moving condition
						if (selectedpiece == null && panel.getPiece(xgrid, ygrid) != null&&panel.getPiece(xgrid, ygrid).getColor() == turn) {// if no piece is
																										// selected,
																										// select a new
																										// piece
							selectedpiece = panel.getPiece(xgrid, ygrid);
							panel.clearSelectedSquares();
							panel.addSelectedSquare(selectedpiece.getX(), selectedpiece.getY());
							panel.setMessage("Select the grid to move.");
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
							panel.setMessage("Select the grid to move.");
						} else if (panel.getPiece(xgrid, ygrid) == null && selectedpiece!=null) {// move the piece
							if (panel.move(selectedpiece, xgrid, ygrid, true)) {
								if(panel.checkWin(selectedpiece)!=0) {//game ends
									gameEnd(panel.checkWin(selectedpiece));
								}
								//decrease moveleft
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
							panel.setMessage("It's not your turn!");
						}
					} else if (push) {// conditions for pushing a piece
						// first, find mypiece
						if (push_mypiece == null && push_otherpiece == null && panel.getPiece(xgrid, ygrid) != null&& panel.getPiece(xgrid, ygrid).getColor() == turn) {//if nothing is selected, 
																																									//set the piece as mypiece
							push_mypiece = panel.getPiece(xgrid, ygrid);
							panel.addSelectedSquare(push_mypiece.getX(), push_mypiece.getY());
							panel.setMessage("Select the enemy piece to push.");
						} else if (push_otherpiece == null && push_mypiece!=null && panel.getPiece(xgrid, ygrid) != null && panel.getPiece(xgrid, ygrid).getColor() != turn) {//if mypiece is selected,
																																										//set the next piece as otherpiece
							push_otherpiece = panel.getPiece(xgrid, ygrid);
							panel.addSelectedSquare(push_otherpiece.getX(), push_otherpiece.getY());
							panel.setMessage("Select the grid to push.");
						} else if (push_otherpiece != null && push_mypiece!=null && push_xgrid == -1) {//when the grid to push is not selected yet
							push_xgrid = xgrid;
							push_ygrid = ygrid;
						}
						if (push_mypiece != null && push_otherpiece != null && push_xgrid!=-1 && push_ygrid!=-1) {//if everything is selected, push
							if(movesleft > 1) {
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
									setpush(false);
									if (movesleft == 0) {
										changeturn();
									}
									movesleftlabel.setText("Moves left: " + movesleft);
								}
								else {
									panel.setMessage("Failed to push.");
									setpush(false);
								}
							}
							else {
								panel.setMessage("Push requires 2 moves.");
							}
						}
					} else if (pull) {// conditions for pushing a piece
						// first, find mypiece
						if (pull_mypiece == null && pull_otherpiece == null && panel.getPiece(xgrid, ygrid) != null && panel.getPiece(xgrid, ygrid).getColor() == turn) {//if nothing is selected, 
																																								//set the piece as mypiece
							pull_mypiece = panel.getPiece(xgrid, ygrid);
							panel.addSelectedSquare(pull_mypiece.getX(), pull_mypiece.getY());
							panel.setMessage("Select the enemy piece to pull.");
						} else if (pull_otherpiece == null && pull_mypiece!=null && panel.getPiece(xgrid, ygrid) != null && panel.getPiece(xgrid, ygrid).getColor() != turn) {//if mypiece is selected,
																																								//set the next piece as otherpiece
							pull_otherpiece = panel.getPiece(xgrid, ygrid);
							panel.addSelectedSquare(pull_otherpiece.getX(), pull_otherpiece.getY());
							panel.setMessage("Select the grid to pull.");
						} else if (pull_xgrid == -1 && pull_otherpiece != null && pull_mypiece!=null ) {//when the grid to pull is not selected yet
							pull_xgrid = xgrid;
							pull_ygrid = ygrid;
						}
						if (pull_mypiece != null && pull_otherpiece != null && pull_xgrid!=-1 && pull_ygrid!=-1) {//when everything is selected, pull
							if(movesleft > 1) {
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
									setpull(false);
									if (movesleft == 0) {
										changeturn();
									}
									movesleftlabel.setText("Moves left: " + movesleft);
								}
								else {
									panel.setMessage("Failed to Pull");
									setpull(false);
								}
							}
							else {
								panel.setMessage("Pull requires 2 moves.");
							}
						}
					}
					if(boardInteraction) {
						messagelabel.setText("Message: " +panel.getMessage());
					}
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//set the piecetobeplaced method to each animal's constant when the button is clicked
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
		if (!placementphase) {//if in actual game portion
			if (e.getSource().equals(pushbutton)) {//push button
				if (!push) {//set the condition to push
					setpush(true);
					push_mypiece = null;
					push_otherpiece = null;
					push_xgrid = -1;
					push_ygrid = -1;
					panel.clearSelectedSquares();
					messagelabel.setText("Message: Select piece to Push others.");
				} else {//set the condition to move
					setpush(false);
					messagelabel.setText("Message: Select piece to Move.");
				}
			}
			if (e.getSource().equals(pullbutton)) {//pull button
				if (!pull) {//set the condition to pull
					setpull(true);
					pull_mypiece = null;
					pull_otherpiece = null;
					pull_xgrid = -1;
					pull_ygrid = -1;
					panel.clearSelectedSquares();
					messagelabel.setText("Message: Select piece to Pull others.");
				} else {//set the condition to move
					setpull(false);
					messagelabel.setText("Message: Select piece to Move.");
				}
			}
			if(e.getSource().equals(skipbutton)) {//skip button
				//if moves left is 4, asks if they cannot move anymore
				if(movesleft==4) {
					int answer = JOptionPane.showConfirmDialog(frame, "Do you mean you have no possible moves? (If yes, you will lose)");
					if(answer == JOptionPane.YES_OPTION) {
						gameEnd(3-turn);
					}
					else {
						messagelabel.setText("Message: Cancel to skip.");
						//do nothing
					}
				}
				else {//skip 
					changeturn();
					movesleft=4;
					movesleftlabel.setText("Moves left: " + movesleft);
					messagelabel.setText("Message: Skip the moves.");
				}
			}
		}
		if(e.getSource().equals(restartbutton)) {//restart button
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

	//method that changes the turn
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

	//set pull condition
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

	//set push condition
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
		boardInteraction=false;//freeze the board
		messagelabel.setText("Click restart!");
	}
	/**
	 * restarts the game
	 */
	public void restart() {
		//see if we need to add the south buttons back.
		if(!placementphase) {
			south.setLayout(new GridLayout(2,1));
			south.add(placebuttons, BorderLayout.SOUTH);
			frame.revalidate();
			placerabbit.setBackground(Color.GREEN);
			placecat.setBackground(Color.GREEN);
			placedog.setBackground(Color.GREEN);
			placehorse.setBackground(Color.GREEN);
			placecamel.setBackground(Color.GREEN);
			placeelephant.setBackground(Color.GREEN);
		}
		frame.revalidate();
		
		messagelabel.setText("Message: Place gold pieces!");
		//revert to intial declarations
		rabbitsleft = 8;
		catsleft = 2;
		dogsleft = 2;
		horsesleft = 2;
		camelsleft = 1;
		elephantsleft = 1;

		placementphase = true;
		boardInteraction = true;

		selectedpiece = null;

		turn = GOLD;

		movesleft = 4;

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
		panel.clearSelectedSquares();
	}
}
