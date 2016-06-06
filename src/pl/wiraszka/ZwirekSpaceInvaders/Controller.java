/**
 * 
 */
package pl.wiraszka.ZwirekSpaceInvaders;

import java.awt.event.KeyEvent;


/**
 * <h2>Controller in Zwirek Space Invaders</h2>
 * MVC Controller module for the game.
 * <ul>
 * 	<li>Start running View and Model.</li> 
 * 	<li>Start and close game.</li>
 *  <li>User interaction event handler.</li>
 * </ul>
 * @author Maciej Wiraszka
 *
 */
public class Controller 
{
	/**
	 * Game MVC component View.
	 */
	private View gameView;
	
	/**
	 * Game MVC component Model.
	 */
	private Model gameModel;
	
	/**
	 * Defines if game is running.
	 */
	private boolean inGame;
	
	/**
	 * True before player starts the game.
	 */
	private boolean begin;
	
	/**
	 * To avoid multiple space-actions during one space-key press.
	 */
	private boolean pressedSpace;
	
	/**
	 * Controller constructor.
	 * <ul>
	 *  <li>Initiate class fields</li>
	 * 	<li>Set this controller in other MVC components.</li> 
	 *  <li>Set start screen in View</li>
	 * </ul> 
	 * @param gameView MVC View of the program
	 * @param gameModel MVC Model of the program
	 * 
	 */
	public Controller(View gameView, Model gameModel) 
	{
		this.gameView = gameView;
		this.gameModel = gameModel;
		
		inGame = false;
		begin = true;
		pressedSpace = false;
		
		this.gameModel.setController(this);
		this.gameView.setController(this);
		
		this.gameView.startScreen();
	}
	
	/**
	 * KeyEvent handler.
	 * Handles program steering gained from user when he or she presses a key.
	 * @param e event type sent from action listener in JPanel.
	 */
    public void keyPressed(KeyEvent e) 
    {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_LEFT)
        {
            gameModel.playerMoveLeft(true);
        }

        if (key == KeyEvent.VK_RIGHT)
        {
        	gameModel.playerMoveRight(true);
        }
        
        if (key == KeyEvent.VK_SPACE && pressedSpace == false)
        {
        	gameModel.newPlayerShot();
        	pressedSpace = true;
        }
        
        if (key == KeyEvent.VK_S && begin == true)
        {	
        	begin = false;
        	inGame = true;
        	gameModel.startGame();
        	
        }
        
        if (key == KeyEvent.VK_Q)
        {
        	System.exit(0);
        }
    }
    
    /**
	 * KeyEvent handler.
	 * Handles program steering gained from user when he or she releases a key.
	 * @param e event type sent from action listener in JPanel.
	 */
    public void keyReleased(KeyEvent e) 
    {
    	int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT)
        {
        	gameModel.playerMoveLeft(false);
        }

        if (key == KeyEvent.VK_RIGHT)
        {
        	gameModel.playerMoveRight(false);
        }
        
        if (key == KeyEvent.VK_SPACE)
        {	
        	pressedSpace = false;
        	
        }
    }
    
    /**
     * Getter of private field inGame.
     * @return inGame value
     */
    public boolean isInGame()
    {
    	return inGame;
    }
    
    /**
     * Setter of private field inGame.
     * @param b value to set
     */
    public void setInGame(boolean b)
    {
    	inGame = b;
    }

	/**
	 * Ends game, initiates proper screening of the game result.
	 * @param win true if player won the game, false if aliens won the game
	 */
	public void endWinGame(boolean win) 
	{
		gameView.endWinScreen(win);
	}
}