/**
 * 
 */
package pl.wiraszka.ZwirekSpaceInvaders;

import java.util.ArrayList;

/**
 *<h2>Model in Zwirek Space Invaders</h2>
 * MVC Model module for the game.
 * <ul>
 * 	<li>Implements game logic.</li> 
 * 	<li>Manages game during play.</li>
 *  <li>Informs view about content to show.</li>
 * </ul> 
 * @author Maciej Wiraszka
 *
 */
public class Model implements Commons
{	
	/**
	 * Game MVC component Controller.
	 */
	private Controller gameController;
	
	/**
	 * Game MVC component Model.
	 */
	private View gameView;
	
	/**
	 * Player class.
	 */
	private Player player;
	
	/**
	 * List containing all alive aliens.
	 */
	private ArrayList<Alien> aliens;
	
	/**
	 * List containing all aliens shots that are on battlefield.
	 */
	private ArrayList<Shot> alienShots;
	
	/**
	 * List containing all player's shots that are on battlefield.
	 */
	private ArrayList<Shot> playerShots;
	
	/**
	 * Thread used for running game loop.
	 */
	private Thread gLThread;
	
	/**
	 * True if player signalizes moving left in the moment.
	 */
	private boolean playerMoveLeft;
	
	/**
	 * True if player signalizes moving right in the moment.
	 */
	private boolean playerMoveRight;
	
	/**
	 * Actual player score.
	 */
	private int playerScore;
	
	/**
	 * True if now aliens should move left.
	 * False if now aliens should move right.
	 */
	private boolean aliensMoveLeft;
	
	/**
	 * Counts number of screen refreshes from last aliens move view refresh.
	 * Max defined in interface Commons.
	 */
	private int aliensMoveCnt;
	
	/**
	 * Model constructor.
	 * <ul>
	 *  <li>Initiate class fields</li>
	 * 	<li>Create initial game elements.</li> 
	 * </ul> 
	 * @param gameView MVC View of the program
	 */
	public Model(View gameView) 
	{
		this.gameView = gameView;
		aliensMoveCnt = 0;
		aliensMoveLeft = true;
		playerScore = 0;
		insertBoardElements();
		
	}

	/**
	 * Creates initial game elements:
	 * <ul>
	 *  <li>Player</li>
	 * 	<li>Aliens in list</li>
	 *  <li>List for shots</li>
	 * </ul>
	 */
	private void insertBoardElements() 
	{
		player = new Player();
		aliens = new ArrayList<Alien>();
		alienShots = new ArrayList<Shot>();
		playerShots = new ArrayList<Shot>();
		
		for (int i = 0; i < 4; ++i)
		{
			for (int j = 2; j < 9; ++j)
				aliens.add(new Alien(i, j));
		}	
	}
	
	/**
	 * @param controller MVC Controller of the program
	 */
	public void setController(Controller controller) 
	{
		gameController = controller;	
	}
	
	/**
	 * @return the gameController
	 */
	public Controller getController() 
	{
		return gameController;
	}

	/**
	 * Starts the game.
	 * <ul>
	 *  <li>Calls View functions for displaying first battlefield view in game.</li>
	 * 	<li>Starts new thread with game loop processed by it.</li>
	 * </ul>
	 */
	public void startGame() 
	{
		gameView.startGame();
		gameView.printGameView(new VisualBattlefield(player, aliens, alienShots, playerShots));
		
		Runnable gameLoop = new GameLoop();
		gLThread = new Thread(gameLoop);
		
		gLThread.start();
	}

	/**
	 * @return the playerScore
	 */
	public int getPlayerScore() 
	{
		return playerScore;
	}
	
	/**
	 * Function called from MVC Controller.
	 * Defines actual users's steering (move left) of player.
	 * @param b true - move left, false - don't move left
	 */
	public void playerMoveLeft(boolean b) 
	{
		playerMoveLeft = b;
	}

	/**
	 * Defines actual users's steering (move right) of player.
	 * Function called from MVC Controller.
	 * @param b true - move right, false - don't move right
	 */
	public void playerMoveRight(boolean b) 
	{
		playerMoveRight = b;
	}

	/**
	 * Create (if possible) new shot made by player.
	 * Function called from MVC Controller, when player requests a shot.
	 * Add new shot to list and initiate player's reloading.
	 */
	public void newPlayerShot() 
	{
		if(player.isCanShot())
		{
			playerShots.add(new Shot(BOARD_HEIGTH - 2, player.getY(), false));
			player.shot();
		}
	}
	
	/**
	 * Make every alien in aliens list start reloading.
	 */
	private void reloadAllAliens()
	{
		for (Alien alien : aliens)
		{
			alien.reloadAlien();
		}
	}
	
	/**
	 * Move (change coordinates) of player according to actual values of left-right player steering.
	 */
	private void movePlayer()
	{
		if (playerMoveLeft) player.moveLeft();
		if (playerMoveRight) player.moveRight();
	}

	/**
	 * Move (if possible, according to aliensMoveCnt) every alien in aliens list by one place.
	 * Frequency of changes defined in interface Commons.
	 */
	private void tryMoveAliens()
	{
		if(aliensMoveCnt == ALIENS_MOVE_FREQ) 
		{
			// move left
			for (Alien alien : aliens)
			{	
				if(aliensMoveLeft)
				{
					if(!alien.moveAlienLeft())
					{
						// undo changes in the list if moving left impossible for an alien
						for (int i = 0; i < aliens.indexOf(alien); ++i)
						{
							aliens.get(i).moveAlienRight();
						}
						aliensMoveLeft = false;
					}
				}
			}
				
				// move right
			for (Alien alien : aliens)
			{
				if(!aliensMoveLeft)
				{
					if(!alien.moveAlienRight())
					{
						// undo changes in the list if moving left impossible for an alien
						for (int i = 0; i < aliens.indexOf(alien); ++i)
						{
							aliens.get(i).moveAlienLeft();
						}
						aliensMoveLeft = true;
						tryMoveAliens();
					}
				}		
			}
			aliensMoveCnt = 0;
		}
		else ++aliensMoveCnt;		
	}
	
	/**
	 * Move every player shot in playerShots list.
	 * Remove form list every shot that is currently out of the battlefield.
	 */
	private void movePlayerShots()
	{
		for(Shot shot : playerShots)
		{
			shot.tryMoveShot();
		}
		
		for (int i = playerShots.size() - 1; i >= 0; --i)
		{
			if(playerShots.get(i).isOutOfBoard()) playerShots.remove(i);
		}
	}
	

	/**
	 * Check if there is any collision between any player's shot and alien.
	 * If so, remove the shot and the alien from game model and increase player's score.
	 * Is there is no more aliens in the list, inform controller and game loop logic that the game has ended.
	 */
	private void checkIfAliensDestroyed() 
	{
		Shot shot;
		Alien alien;
		
		for (int i = playerShots.size() - 1; i >= 0; --i)
		{
			shot = playerShots.get(i);
			
			for (int j = aliens.size() - 1; j >= 0; --j)
			{
				alien = aliens.get(j);
				if(shot.getX() == alien.getX() && shot.getY() == alien.getY())
				{
					playerShots.remove(shot);
					aliens.remove(alien);
					++playerScore;
					if(aliens.isEmpty()) gameController.setInGame(false);
					break;
				}
			}
		}
		
	}
	
	/**
	 * Shot (if possible, according to canShot field in alien class) every alien in aliens list.
	 * If alien shoot, initiate reloading.
	 */
	private void shotAliens()
	{
		for (Alien alien : aliens)
		{
			if(alien.isCanShot())
			{
				alienShots.add(new Shot(alien.getX() + 1, alien.getY(), true));
				alien.reloadAlien();
			}
		}
	}
	
	/**
	 * Try to move every existing alien shot.
	 * Remove alien shots that are out of the battlefield.
	 */
	private void moveAlienShots()
	{
		for(Shot shot : alienShots)
		{
			shot.tryMoveShot();
		}
		
		for (int i = alienShots.size() - 1; i >= 0; --i)
		{
			if(alienShots.get(i).isOutOfBoard()) alienShots.remove(i);
		}
	}
	
	/**
	 * Check if there is any collision between alien shots and player.
	 * If so:
	 * <ul>
	 *  <li>Remove the shot.</li>
	 * 	<li>Remove all shots from battlefield.</li>
	 *  <li>Call function isDestroyed() from player's class (After player is hit, decrease numOfLives and check if he still lives.)</li>
	 *  <li>If player is destroyed, inform controller and game loop logic that the game has ended.</li>
	 * </ul>
	 * @return true - player is destroyed, false - player is still alive
	 */
	private boolean checkIfPlayerDestroyed()
	{
		Shot shot;
		
		for (int i = alienShots.size() - 1; i >= 0; --i)
		{
			shot = alienShots.get(i);
			
			if(shot.getX() == BOARD_HEIGTH - 1 && shot.getY() == player.getY())
			{
				alienShots.remove(shot);
				
				alienShots = new ArrayList<Shot>();
				playerShots = new ArrayList<Shot>();
				
				if(player.isDestroyed()) gameController.setInGame(false);
				
				return true;
			}
		}
		return false;
	}

	/**
	 * Inner class with game loop logic.
	 * Is a thread action scheme.
	 * Game loop ends working, when Controller informs that game has ended.
	 * Control game view refresh time.
	 * 
	 * In game loop:
	 * <ul>
	 *  <li>Control game view refresh time.</li>
	 * 	<li>Do game logic actions (methods from the model class).</li>
	 * 	<li>Call View to refresh the game view.</li>
	 * 	<li>Check if the game has ended, if so escape from the loop.</li>
	 * 	<li>Calculate time to refresh to be just as that defined in field REFRESH_DELAY from interface Commons.</li>
	 *  <li>Sleep the thread for requested amount of time.</li>
	 *  <li>Set new relative time to calculate sleep time in next passing of the loop</li>
	 * </ul>
	 * 
	 * If escaped from the loop, check who (aliens on player) won the game.
	 * Inform controller about the winner and end executing game loop.
	 * @author Maciej Wiraszka
	 *
	 */
	private class GameLoop implements Runnable
	{

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() 
		{
			long beforeTime, timeDiff, sleepTime;
			
			boolean paused = true;
			
			
			beforeTime = System.currentTimeMillis();
			
			while(true)
			{
				if(paused) reloadAllAliens();
				movePlayer();
				tryMoveAliens();
				movePlayerShots();
				checkIfAliensDestroyed();
				shotAliens();
				moveAlienShots();
				paused = checkIfPlayerDestroyed();
				
				gameView.setInfoPanel(playerScore, player.getNumOfLives());
				gameView.printGameView(new VisualBattlefield(player, aliens, alienShots, playerShots));
				
				if(!getController().isInGame()) break;
				
				timeDiff = System.currentTimeMillis() - beforeTime;
	            if (paused) sleepTime = PAUSE_AFTER_HIT;
	            else sleepTime = REFRESH_DELAY - timeDiff;
	            
	            if (sleepTime < 0)
	            	sleepTime = 2;
	            
	            try {
	                Thread.sleep(sleepTime);
	            } catch (InterruptedException e) {
	                System.out.println("interrupted sleep");
	            }
	            beforeTime = System.currentTimeMillis();
			}
			
			if (player.isDestroyed()) gameController.endWinGame(false);
			else gameController.endWinGame(true);
		}		
	}
}
