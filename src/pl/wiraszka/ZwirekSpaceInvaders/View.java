/**
 * 
 */
package pl.wiraszka.ZwirekSpaceInvaders;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <h2>View in Zwirek Space Invaders</h2>
 * MVC View module for the game. GUI of the program.
 * @author Maciej Wiraszka
 *
 */
public class View implements Commons
{
	/**
	 * Game MVC component Controller.
	 */
	private Controller gameController;
	
	/**
	 * Main frame of GUI.
	 */
	private JFrame frame;
	
	/**
	 * Main panel of GUI, for start/end and game field.
	 */
	private JPanel mainPanel;
	
	/**
	 * Panel with information about actual player score and number of lives.
	 */
	private JPanel infoPanel;
	
	/**
	 * Invisible, focusable panel with KeyListener.
	 */
	private JPanel keyActions;
	
	/**
	 * Label to be viewed in mainPanel. Shows game start or end communicate.
	 */
	private JLabel startEndScreen;
	
	/**
	 * Label to be viewed in mainPanel. Shows actual battlefield view.
	 */
	private JLabel[][] boardField;
	
	/**
	 * Label to be viewed in infoPanel. Shows actual player score.
	 */
	private JLabel health;
	
	/**
	 * Label to be viewed in infoPanel. Shows actual player number of lives.
	 */
	private JLabel score;
	
	/**
	 * True before first printing of battlefield. 
	 * To avoid clearing boardField label before there is any element.
	 */
	private boolean firstPrint;
	
	/**
	 * View constructor.
	 * <ul>
	 *  <li>Initiate GUI fields and set their proper values </li>
	 * 	<li>Set frame visible</li> 
	 * </ul>
	 */
	public View()
	{
		firstPrint = true;
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(440,500);
		frame.setResizable(false);
		frame.getContentPane().setBackground(Color.BLACK);
		
		mainPanel = new JPanel();
	    mainPanel.setPreferredSize(new Dimension(440, 400));
	    
	    infoPanel = new JPanel();
	    infoPanel.setPreferredSize(new Dimension(440, 100));
	    infoPanel.setLayout(new BorderLayout());
	    infoPanel.setBackground(Color.BLACK);

		keyActions = new JPanel();
		keyActions.setFocusable(true);
		keyActions.requestFocusInWindow();
		keyActions.addKeyListener(new PlayerActions());
	    keyActions.setPreferredSize(new Dimension(440, 0));
		
		frame.add(BorderLayout.NORTH, mainPanel);
		frame.add(BorderLayout.CENTER, infoPanel);
		frame.add(BorderLayout.SOUTH, keyActions);
		
		frame.setVisible(true);
	}
	
	/**
	 * Set controller to send info about KeyEvents.
	 * @param controller MVC controller module
	 */
	public void setController(Controller controller) 
	{
		gameController = controller;	
	}
	
	/**
	 * View start screen of the game, 
	 * containing start communicate, basic score and basic number of lives.
	 */
	public void startScreen() 
	{
		ImageIcon startImage = new ImageIcon(START_IMAGE_NAME);
		startEndScreen = new JLabel(startImage);
		mainPanel.add(startEndScreen);
		mainPanel.setBackground(Color.BLACK);
		
		ImageIcon scoreImage = new ImageIcon(SCORE_IMAGE_PATH + "0.png");
		score = new JLabel(scoreImage);
		
		ImageIcon healthImage = new ImageIcon(HEALTH_IMAGE_PATH + PLAYER_NUM_OF_LIVES + ".png");
		health = new JLabel(healthImage);
		
		infoPanel.add(BorderLayout.WEST, score);
		infoPanel.add(BorderLayout.EAST, health);
		
		frame.revalidate();
		//frame.repaint();
		
	}
	/**
	 * Prepare GUI to start the game.
	 */
	public void startGame()
	{
		mainPanel.remove(startEndScreen);
		mainPanel.setBackground(Color.BLACK);
		mainPanel.setLayout(new GridLayout(BOARD_HEIGTH, BOARD_WIDTH));
		boardField = new JLabel[BOARD_HEIGTH][BOARD_WIDTH];
		
		frame.revalidate();
		//frame.repaint();
	}
	
	/**
	 * Actualize score and number of lives displayed in infoPanel
	 * @param actualScore new score
	 * @param numOfLives new number of lives
	 */
	public void setInfoPanel(int actualScore, int numOfLives) 
	{
		
		infoPanel.remove(score);
		infoPanel.remove(health);
		
		ImageIcon scoreImage = new ImageIcon(SCORE_IMAGE_PATH + actualScore + ".png");
		score = new JLabel(scoreImage);
		
		ImageIcon healthImage = new ImageIcon(HEALTH_IMAGE_PATH + numOfLives + ".png");
		health = new JLabel(healthImage);
		
		infoPanel.add(BorderLayout.WEST, score);
		infoPanel.add(BorderLayout.EAST, health);	
	}
	
	/**
	 * Remove all GridLayout content from mainPanel.
	 */
	private void cleanGameView()
	{
		for (int i = BOARD_HEIGTH - 1; i >= 0; --i)
		{
			for (int j = BOARD_WIDTH - 1; j >= 0; --j)
			{
				mainPanel.remove(boardField[i][j]);
			}
		}
	}
	
	/**
	 * Actualize battlefield view.
	 * <ul>
	 *  <li>Remove previous battlefield view. </li>
	 * 	<li>Set new battlefield view</li> 
	 * </ul>
	 * @param battlefield new battlefield view
	 */
	public void printGameView(VisualBattlefield battlefield)
	{
		ImageIcon icon;
		
		if(firstPrint == false)
		{
			cleanGameView();
				
		}
		else firstPrint = false;
		
		for (int i = 0; i < BOARD_HEIGTH; ++i)
		{
			for (int j = 0; j < BOARD_WIDTH; ++j)
			{
				icon = new ImageIcon(battlefield.imageName[i][j]);
				boardField[i][j] = new JLabel(icon);
				mainPanel.add(boardField[i][j]);
			}
		}
		
		frame.revalidate();
		//frame.repaint();
	}
	
	/**
	 * View end screen of the game, 
	 * containing player or aliens win communicate, end score and end number of lives.
	 * @param win true if player won, false if aliens won
	 */
	public void endWinScreen(boolean win)
	{
		String imageName;
		
		if(win) imageName = END_WIN_IMAGE_NAME;
		else imageName = END_NEG_IMAGE_NAME;
		
		mainPanel = new JPanel();
	    mainPanel.setPreferredSize(new Dimension(440, 400));
		frame.add(BorderLayout.NORTH, mainPanel);
		
		ImageIcon endImage = new ImageIcon(imageName);
		startEndScreen = new JLabel(endImage);
		mainPanel.add(startEndScreen);
		mainPanel.setBackground(Color.BLACK);
		
		frame.revalidate();
		//frame.repaint();
	}
	
	/**
	 * Inner KeyListener class for user interaction handling.
	 * @author Maciej Wiraszka
	 *
	 */
    private class PlayerActions extends KeyAdapter 
    {
    	/*
    	 * (non-Javadoc)
    	 * @see java.awt.event.KeyAdapter#keyPressed(java.awt.event.KeyEvent)
    	 */
    	@Override
 		public void keyPressed(KeyEvent e) 
    	{
 			gameController.keyPressed(e);
 		}
 		
    	/*
    	 * (non-Javadoc)
    	 * @see java.awt.event.KeyAdapter#keyReleased(java.awt.event.KeyEvent)
    	 */
 		@Override
 		public void keyReleased(KeyEvent e) 
 		{
 			gameController.keyReleased(e);
 		}
    }	
}
