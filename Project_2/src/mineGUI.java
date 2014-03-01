import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

@SuppressWarnings("serial")
public class mineGUI extends JFrame implements ActionListener {

	private button mineButtons[][] = new button[10][10];
	private GridLayout mineBoard, infoBoard;
	private Container boardContainer;
	private JOptionPane dialogs;
	private JMenuBar topMenu;
	private JMenu gameMenu, hMenu;
	private JMenuItem gReset, gTopTen, gClearHS, gExit, hHelp, hAbout;
	private mine mines = new mine();
	private int[] mineLocs = new int[10];
	private JButton resetButton;
	private JLabel timeElapsed, buttonsLeft, timeLabel, buttonLabel;
	private int currTime;
	private Timer time;
	private int numLClicks = 0;
	private scoreBoard scoreboard;

	public mineGUI() throws ClassNotFoundException, IOException {

		super("Minesweeper");// set up new JFrame for game
		setSize(480, 720);// set game size to 480x720

		initUI();
		initMines();
		initScoreBoard();

		// create timer to keep track of game time
		int delay = 1000;
		time = new Timer(delay, new timeInfo());
	}

	final void initUI() throws ClassNotFoundException, IOException {
		topMenu = new JMenuBar();// create menu bar
		setJMenuBar(topMenu);// set menu bar on frame

		gameMenu = new JMenu("Game");// create game menu
		topMenu.add(gameMenu);// add game menu

		// Initialize each member of menu item.
		// add them to the menu,set a mnemonic for keyboard interaction
		// depending on
		// title name.
		// add actionListener to register human interaction
		// repeat for next items
		gReset = new JMenuItem("Reset");
		gameMenu.add(gReset);
		gReset.setMnemonic(KeyEvent.VK_R);
		gReset.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,
				ActionEvent.ALT_MASK));
		gReset.addActionListener(this);
		gameMenu.addSeparator();

		gTopTen = new JMenuItem("Top Ten");
		gameMenu.add(gTopTen);
		gTopTen.setMnemonic(KeyEvent.VK_T);
		gTopTen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T,
				ActionEvent.ALT_MASK));
		gTopTen.addActionListener(this);

		gClearHS = new JMenuItem("Clear High Scores");
		gameMenu.add(gClearHS);
		gClearHS.setMnemonic(KeyEvent.VK_C);
		gClearHS.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,
				ActionEvent.ALT_MASK));
		gClearHS.addActionListener(this);
		gameMenu.addSeparator();

		gExit = new JMenuItem("Exit");
		gExit.setMnemonic(KeyEvent.VK_X);
		gExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,
				ActionEvent.ALT_MASK));
		gExit.addActionListener(this);
		gameMenu.add(gExit);

		// create menu 2 of 2
		hMenu = new JMenu("Help");
		topMenu.add(hMenu);

		// same as other menu, just different names
		hHelp = new JMenuItem("Help");
		hHelp.setMnemonic(KeyEvent.VK_H);
		hHelp.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,
				ActionEvent.ALT_MASK));
		hHelp.addActionListener(this);
		hMenu.add(hHelp);
		hMenu.addSeparator();

		hAbout = new JMenuItem("About");
		hAbout.setMnemonic(KeyEvent.VK_A);
		hAbout.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A,
				ActionEvent.ALT_MASK));
		hAbout.addActionListener(this);
		hMenu.add(hAbout);

		// the UI will have 2 JPanels-1 grid layout and 1 plain panel
		// that will display game information
		JPanel mboard = new JPanel(new GridLayout(10, 10));

		// create panel that will contain previous two panels
		// place that in a box layout so that they can be placed on top of each
		// other without having to do much realignment
		JPanel top = new JPanel();

		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		// these initialize the information that will be displayed in the top
		// panel.time, reset button and buttons left
		timeLabel = new JLabel("Time: ");
		top.add(timeLabel);

		timeElapsed = new JLabel("0:0");
		top.add(timeElapsed);

		resetButton = new JButton("Reset");
		resetButton.addActionListener(this);
		top.add(resetButton);

		buttonLabel = new JLabel("Mines Left: ");
		top.add(buttonLabel);

		buttonsLeft = new JLabel("10");
		top.add(buttonsLeft);

		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				mineButtons[i][j] = new button();
				mineButtons[i][j].setPreferredSize(new Dimension(20, 20));
				mboard.add(mineButtons[i][j]);
				mineButtons[i][j].setXcoord(i);
				mineButtons[i][j].setYcoord(j);
				int position = (i * 10) + j;
				mineButtons[i][j].setPos(position);
				mineButtons[i][j].addMouseListener(new buttonListener());
			}
		}
		container.add(top);
		container.add(mboard);
		add(container);
		setVisible(true);
	}

	public void initMines() {

		mines.setMines(10);
		mineLocs = mines.getMines();

		for (int a = 0; a < 10; a++) {
			for (int b = 0; b < 10; b++) {
				for (int c = 0; c < 10; c++) {
					if (mineLocs[a] == mineButtons[b][c].getPos()) {
						mineButtons[b][c].setBomb();
					}
				}
			}
		}

		countAdjacentMines();
	}

	public void initScoreBoard() throws ClassNotFoundException, IOException {
		scoreboard = new scoreBoard();

		// test score board
		scoreboard.addHighScore("Empty", 59940);
	}

	public void resetMineLocs() {
		mineLocs = new int[10];
	}

	public void resetUI() {
		currTime = 0;
		numLClicks = 0;
		timeElapsed.setText("0:0");
		buttonsLeft.setText("10");
	}

	public void resetGame() {
		// reset all buttons
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				mineButtons[i][j].setText("");
				mineButtons[i][j].setEnabled(true);
				mineButtons[i][j].setState(0);
				mineButtons[i][j].removeBomb();
				mineButtons[i][j].setBackground(null);
			} // end for loop rows
		} // end for loop columns

		resetUI();
		resetMineLocs();
		mines.resetMines();
		mines.resetCleared();
		initMines();

	}

	public void endGameCheck() {
		int realMinesMarked = 0;
		if (mines.getCleared() == 90) {
			// for (int i = 0; i < 10; i++) {
			// for (int j = 0; j < 10; j++) {
			// if (mineButtons[i][j].getIsBomb() == true
			// && (mineButtons[i][j].getText()).equals("M")) {
			// System.out.println("bomb at row:" + i + " col: " + j
			// + " is successfully marked");
			// realMinesMarked = realMinesMarked + 1;
			// }
			// } // end for loop rows
			// } // end for loop columns
			// } else {
			// System.out
			// .println("Error: fake clear encountered: 90 buttons cleared but the marked one aren't real bombs");
			// }
			// System.out.println("there are " + realMinesMarked
			// + " real bombs marked");
			// if (realMinesMarked == 10) {
			System.out.println("player won!");
			endGame(90, false);
		}
	}

	public void endGame(int buttonsCleared, boolean bombExploded) {
		// if player lost
		if (bombExploded == true) {
			// first stop the timer
			time.stop();
			// then reveal all bombs
			revealMines();
			// then display the message, and close all listeners
			JOptionPane
					.showMessageDialog(
							boardContainer,
							"Sorry, you lost this game. :("
									+ "\nTime elapsed: "
									+ getTimeElapsed()
									+ "\nThanks for playing \nIf you would like to play another game, please press reset.");
		} else if (bombExploded == false) {
			// player won this game
			// if (buttonsCleared == 90 && mineFound == 10) {
			if (buttonsCleared == 90) {
				// here is when the player clears the game
				// first stop the timer
				time.stop();

				// then mark all bombs
				markAllMines();

				// then display the message, and close all listeners

				// we check the user's clear time against the 10th highest score
				// if the player's clear time is faster
				// prompt user to enter the name to be recorded onto the Top Ten
				if (currTime < scoreboard.getHighScores() || scoreboard.getSize() < 10) {
					String name = new String();
					name = JOptionPane.showInputDialog(gameMenu,
							"Congratulations, you beat a high score! \nTime elapsed: "
									+ getTimeElapsed()
									+ "\nThank you for playing :)",
							"High Score!", NORMAL);
					if (name.isEmpty()) {
						name = new String("Guest");
					}
					// add to the top ten
					if (scoreboard.getHighestScore().equals("Empty")) {
						try {
							scoreboard.clearHighScores();
							scoreboard.load();
						} catch (Exception e) {
						}
					}
					try {
						scoreboard.load();
						scoreboard.addHighScore(name, currTime);
						scoreboard.load();
					} catch (ClassNotFoundException e) {
						System.out
								.println("ClassNotFoundException in endGame()");
					} catch (IOException e) {
						System.out.println("IOException in endGame()");
					}

				} 
				else {
					JOptionPane.showMessageDialog(boardContainer,
							"Congratulations, you won the game! \nTime elapsed: "
									+ getTimeElapsed()
									+ "\nThank you for playing :)");
				}

			}
		} else
			System.out.println("Error occured in end-game method");

	}

	private void doClear(button[][] mineButtons, int x, int y) throws Exception {
		System.out.println("x:" + x + " y:" + y);
		if (x < 0 || x >= 10 || y < 0 || y >= 10) {
			return;
		}
		button currButton = mineButtons[x][y];
		if (!currButton.isEnabled()) {
			return;
		}

		currButton.setBackground(Color.lightGray);
		currButton.setEnabled(false);

		if (mineButtons[x][y].getIsBomb() == true) {
			// end game in this if statement
			time.stop();
			currButton.setText("X");
			// currButton.setIconImage("mineRed.png");

			endGame(0, true);

			System.out.println("clicked on button with bomb, exiting...");
			// end game
		} else {
			// every time when a button is "cleared", increment variable
			mines.incCleared();
			// check if player has won the game if 90 buttons are cleared
			System.out.println("currently, number of buttosn cleared: "
					+ mines.getCleared());
			if (mines.getCleared() == 90) {
				System.out.println("commencing end game check");
				endGameCheck();
			}

			if (mineButtons[x][y].getAdjacentBombs() != 0) {
				// if (mineButtons[x][y].getAdjacentBombs() == 1) {
				// currButton.setForeground(Color.BLUE);
				// }
				// else if (mineButtons[x][y].getAdjacentBombs() == 2) {
				// currButton.setForeground(Color.GREEN);
				// }
				// else if (mineButtons[x][y].getAdjacentBombs() == 3) {
				// currButton.setForeground(Color.RED);
				// }
				mineButtons[x][y].setText(mineButtons[x][y]
						.getAdjacentBombsString());
			} else {
				mineButtons[x][y].setText("");
			}
			if (currButton.getAdjacentBombs() == 0) {
				doClear(mineButtons, x - 1, y - 1); // top left
				doClear(mineButtons, x, y - 1); // top middle
				doClear(mineButtons, x + 1, y - 1); // top right
				doClear(mineButtons, x - 1, y); // mid left
				doClear(mineButtons, x + 1, y); // mid right
				doClear(mineButtons, x - 1, y + 1); // bot left
				doClear(mineButtons, x, y + 1); // bot middle
				doClear(mineButtons, x + 1, y + 1); // bot right
			}
		}
	}

	private void countAdjacentMines() {
		// TO DO: STUDENT CODE HERE
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (mineButtons[i][j].getIsBomb() != true) {
					int count = 0;
					for (int p = i - 1; p <= i + 1; p++) {
						for (int q = j - 1; q <= j + 1; q++) {
							if (0 <= p && p < mineButtons.length && 0 <= q
									&& q < mineButtons.length) {
								if (mineButtons[p][q].getIsBomb() == true)
									count++;
							} // end if
						} // end for
					} // end for

					mineButtons[i][j].setAdjacentBombs(count);
				} // end if

			} // end for loop rows
		} // end for loop columns
	} // end setAdjacentBombs

	private void revealMines() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (mineButtons[i][j].getIsBomb() == true) {
					mineButtons[i][j].setText("x");
				}
				mineButtons[i][j].setState(9);
			} // end for loop rows
		} // end for loop columns
	} // end method

	private void markAllMines() {
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				if (mineButtons[i][j].getIsBomb() == true) {
					mineButtons[i][j].setText("M");
					mines.setCurrentMinesZero();
				}
				mineButtons[i][j].setState(9);
			} // end for loop rows
		} // end for loop columns
	} // end method
		// this class implements the time keeping requirement for the program
		// the game starts keeping time once a left button is clicked,
		// the time is set to display in the format min:second
		// time is incremented each second and displayed

	private class timeInfo implements ActionListener {

		public void actionPerformed(ActionEvent a) {
			int minute = currTime / 60;
			int seconds = currTime - (minute * 60);
			timeElapsed.setText(Integer.toString(minute) + ":"
					+ Integer.toString(seconds));
			currTime++;
		}

	}

	private String getTimeElapsed() {
		int minutes = currTime / 60;
		int seconds = currTime - (minutes * 60);
		String minString = String.valueOf(minutes);
		String secString = String.valueOf(seconds);
		String timeString = minString.concat(" minute(s) ");
		timeString = timeString.concat(secString);
		timeString = timeString.concat(" second(s) ");
		System.out.println("displaying time elapsed:" + minString + ":"
				+ secString);
		return timeString;
	}

	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == gReset) {
			resetGame();
			System.out.println("Reset Pressed");

		}

		else if (e.getSource() == gTopTen) {
			try {
				scoreboard.load();
			} catch (ClassNotFoundException e1) {
				System.out.println("ClassNotFoundException in e.getSource() == gTopTen");
			} catch (IOException e1) {
				System.out.println("IOException in e.getSource() == gTopTen");
			}
			JOptionPane.showMessageDialog(mineGUI.this,
					scoreboard.printHighScores(scoreboard),
					"Top 10 High Scores", JOptionPane.PLAIN_MESSAGE);
		} else if (e.getSource() == gClearHS) {
			try {
				scoreboard.clearHighScores();
				scoreboard.load();
				scoreboard.addHighScore("Empty", 59940);
				scoreboard.load();
			} catch (Exception e1) {
				System.out
						.println("Error: Couldn't clear high score from menu");
			}
			JOptionPane.showMessageDialog(mineGUI.this, "High scores cleared!",
					"Top 10 High Scores", JOptionPane.PLAIN_MESSAGE);
		}

		else if (e.getSource() == gExit) {
			System.out.println("Exit");
			System.exit(0);
		} else if (e.getSource() == hHelp) {
			JOptionPane
					.showMessageDialog(
							mineGUI.this,
							"The game will start when you left click on the game board \n"
									+ "Left clicking will reveal a mine or the number of adjacent mines \n"
									+ "Right clicking will first indicate that that location hides a mine \n"
									+ "Right clicking again will indicate that there might be a mine there\n"
									+ "To win the game,  mark the 10 mines and clear the other 90 squares \n",
							"Help", JOptionPane.PLAIN_MESSAGE);

		} else if (e.getSource() == hAbout) {
			JOptionPane.showMessageDialog(mineGUI.this,
					"CS 342 Project Two-Minesweeper \n" + " Authors: \n"
							+ "Tianniu Lei(tlei2) \n"
							+ "Ryan Szymkiewicz(szymkie1) \n", "About",
					JOptionPane.PLAIN_MESSAGE);
		} else if (e.getSource() == resetButton) {
			resetGame();
			System.out.println("Reset Button Pressed.");

		} else {
			System.out.println(e);
		}

	}

	class buttonListener implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {

			// right click options-Windows/Unix-use right click
			// macs-check if control button is pressed
			if (SwingUtilities.isRightMouseButton(e) || e.isControlDown()) {

				// cast event to a button so that it can be changed depending on
				// current
				// state-nothing,M or ?
				button currButton = (button) e.getSource();
				// if already at endgame, do nothing
				if (currButton.getState() == 9) {
					return;
				}
				// if 0 then set to M...decrement mines left to be found if spot
				// actually contains a bomb
				if (currButton.getState() == 0) {
					currButton.setState(1);

					// currButton.setIconImage("flag.png");
					// if (currButton.getIsBomb() == true) {
					currButton.setText("");
					if (mines.getCurrentMines() != 0) {
						currButton.setText("M");
						mines.decMines();
						endGameCheck();
					}

					buttonsLeft
							.setText(String.valueOf(mines.getCurrentMines()));
					// }

				}

				// change to ? state-increment mines left to be found if spot
				// actually contains one
				else if (currButton.getState() == 1) {
					currButton.setState(2);
					if (mines.getCurrentMines() != 10
							&& (currButton.getText()).equals("M")) {
						mines.incMines();
					} else {
						JOptionPane
								.showMessageDialog(
										mineGUI.this,
										"You may only mark 10 buttons as bombs at most. \nTo mark another button as a bomb, please cancel \na flag from another button first.",
										"Too many flags",
										JOptionPane.PLAIN_MESSAGE);
					}

					currButton.setText("?");
					// if (currButton.getIsBomb() == true) {

					buttonsLeft
							.setText(String.valueOf(mines.getCurrentMines()));
					// }

				}
				// return state back to nothing
				else if (currButton.getState() == 2) {
					currButton.setText("");
					currButton.setState(0);
				} else {
					System.out.println("Problem here-Right Mouseclick");
				}

			}

			// left click functions
			else if (SwingUtilities.isLeftMouseButton(e)) {

				// if no clicks before and we got here, start timer
				if (numLClicks == 0) {
					time.start();
				}
				numLClicks++;

				button currButton = (button) e.getSource();
				// if the button hasn't been grayed out, do these actions
				if (currButton.getBackground() != Color.lightGray) {
					// if already at end-game, do nothing
					if (currButton.getState() == 9) {
						return;
					}

					if (currButton.getState() == 1
							|| currButton.getState() == 2) {
						// already "M", do nothing
						System.out
								.println("pressed on button that's already marked as bomb, no action done.");
					} else if (currButton.getState() == 0) {
						try {
							doClear(mineButtons, currButton.getXcoord(),
									currButton.getYcoord());
						} catch (Exception e1) {
						}
					} else {
						System.out.println("Problem here");
					}
				}

				else {
					//System.out.println("Problem Here in Left Mouse click");
				}
			}
			// else, do nothing
			else {
				System.out
						.println("user clicked a grayed out button, does nothing");
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {

		}

		@Override
		public void mouseExited(MouseEvent e) {

		}

	}

}