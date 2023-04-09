/*
Gamepanel.java
Alan Bui
Simple Game Assignment ICS4U-01
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.Set;
import java.util.HashSet;

//main game class that connects all object interactions together
//has methods to move objects, display images on screen, check mouse location and if it's pressed, start levels, and check collisions
class Gamepanel extends JPanel implements ActionListener, MouseListener{	
	public static final int WIDTH = 800, HEIGHT = 800; //width and height of the panel
    private ArrayList<Block> blocks; //blocks of the current level
	private ArrayList<Powerup> powerUps; //powerups of the current level
	private ArrayList<Laser> lasers; //lasers of the current level
	private ArrayList<Point> explosions; //explosions to be displayed of the current level
	private Set<Block> blocksToDelete; //blocks that are broken and should be remvoed from blocks
	private LoadedImages images = new LoadedImages(); //images that are to be used
	private Timer timer; //keeps track of frames
    private Ball ball; //the current ball
    private Paddle paddle; //the current paddle
	private Point mouse, offset; 
	/*
	 mouse is the location the mouse is on the entire screen
	 offset is the location of the panel on the entire screen
	 */
	private int points, lives, totalPoints, highScore, startFrame, curLevel, loadFrame;
	/*
	 points is the number of points the player has on this run
	 lives is the number of lives the player has on this run
	 totalPoints is the total number of points required to clear the level
	 highScore is the highest score by any player of this game
	 startFrame is the current frame the starting screen animation is on
	 curLevel is the current level the player is playing
	 loadFrame is the current frame the loading screen animation is on
	 */
	private boolean start, catching, firstEver, laserActive;
	/*
	 start == true if the ball should be stuck to the paddle like at the start of the game, false otherwsie
	 catching == true if the catch Powerup is active, false otherwise
	 firstEver == true if it's the first time loading in the game, it is used so the program can load all images once at the start to avoid flickering later on
	 laserActive == true if the laser Powerup is active, false otherwise
	 */
	private String curScreen; //which screen the player is currently on
	private Level [] levels; //levels[i] stores starting info for the ith level
	private Font fontLocal; //Font used for text to be drawn on screen
	private Sound music; //Sound object with the sound effects to be played

	public Gamepanel(){ //initializes starting values of the game
		firstEver = true;
        
		curScreen = "START_SCREEN";
		curLevel = 0;
		loadFrame = -1;

		levels = new Level[3];
		for (int i=1; i<=2; i++){
			levels[i] = new Level(i); //stores data for the ith Level in levels[i]
		}

		explosions = new ArrayList<Point>();
		blocksToDelete = new HashSet<Block>();

		totalPoints = 0;
		highScore = -1;
		highScore = Util.updateHighScore(highScore);

		//loads font
		String fName = "NeonLight-Regular.ttf"; 
    	InputStream is = Gamepanel.class.getResourceAsStream(fName);
    	try{
    		fontLocal = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(32f);
    	}
    	catch(IOException ex){
    		System.out.println(ex);	
    	}
    	catch(FontFormatException ex){
    		System.out.println(ex);	
    	}
		
		setPreferredSize(new Dimension(Arkanoid.WIDTH, Arkanoid.HEIGHT));
		setFocusable(true);
		requestFocus();
		addMouseListener(this);
		
		//starts timer with delay
		timer = new Timer(20, this);
		timer.start();

		//starts music
		music = new Sound();
		music.playMusic(curScreen);

		//sets laser and catch Powerups off
		catching = false;
		laserActive = false;
	}

	public void startLevel(int lvlNum){ //method to start the lvlNumth level
		music.stopAll(); //stops all music
		
		//resets ArrayLists to empty to start the Level
		blocks = new ArrayList<Block>();
		powerUps = new ArrayList<Powerup>();
		lasers = new ArrayList<Laser>();
		explosions = new ArrayList<Point>();

		if (lvlNum == 2){
			totalPoints = levels[1].getTotalPoints() + levels[2].getTotalPoints(); //the total points to clear the second level is the sum of the points to clear the 1st and 2nd levels
		} 

		if (lvlNum == 1){ //resets the lives and points if starting the first level
			points = 0;
			lives = levels[lvlNum].getLives();
			totalPoints = levels[lvlNum].getTotalPoints();
		} 

		for (Block b : levels[lvlNum].getBlocks()){ //adds Blocks into the ArrayList<Block> blocks
			blocks.add(b);
		}

		ball = new Ball(400, 600, 5);

		//resets paddle and any activated Powerups on the last level's paddle
        paddle = new Paddle(400, 750); 
		laserActive = false;
		catching = false;

		start = true;
		curScreen = "LEVEL_"+lvlNum;

		music.playMusic(curScreen); //plays music
	}

	public void collideBlocks(){//method to check collisions with Blocks and respond accordingly
        double newVX = ball.getVX(), newVY = ball.getVY(); //the velocity in x and y directions after hitting zero or more blocks

        for (int i=0; i<blocks.size(); i++){
			Block b = blocks.get(i);
            if (ball.getRect().intersects(b.getRect())){ //checks if ball intersects with a Block
				ball.playBounce(); //makes sound effect for bouncing ball
				if (!b.getColor().equals("Gold")){
					
					blocks.get(i).lowerHealth(1); 
					if (blocks.get(i).getHealth() <= 0){ //if the Block is broken
						blocksToDelete.add(blocks.get(i));

						if (!b.getColor().equals("Silver")){ //no Powerups from Silver blocks
							//adds random Powerup or no Powerup for each Block that broke
							int chance = Util.randint(1, 30);
							if (chance <= 4){	
								powerUps.add(new Powerup(b.getX(), b.getY(),Powerup.powerNames[chance-1]));
							}
						}
					}
				}

				//checks which side of the Block, b, the ball hit and changes the velocity accordingly
                if (ball.getRect().intersects(b.getBottom()) || ball.getRect().intersects(b.getTop())){ 
                    newVY = -ball.getVY();
                }
                else if (ball.getRect().intersects(b.getLeft()) || ball.getRect().intersects(b.getRight())){
                    newVX = -ball.getVX();
                }
            }
        }
        
		//sets new velocity for ball
		ball.setVX(newVX);
		ball.setVY(newVY);
	}

	public void collidePowerUps(){ //method to check collisions with Powerups and respond accordingly
		ArrayList <Powerup> toDelete = new ArrayList<Powerup>(); //ArrayList of Powerups that need to be removed

		for (Powerup p : powerUps){
			if (p.getRect().intersects(paddle.getRect())){ //checks if paddle intersects with a Powerup
				toDelete.add(p); //adds the Powerup to the list to delete later
			}
		}

		for (Powerup p : toDelete){ //for each Powerup hit, activates special ability
			if (p.getName().equals("extraLife")){
				lives ++;
			}
			else if (p.getName().equals("catch")){
				catching = true;
			}
			else if (p.getName().equals("enlarge")){
				paddle.setEnlarge(true);
			}
			else if (p.getName().equals("laser")){
				laserActive = true;
			}
			powerUps.remove(p); //removes Powerup from ArrayList<Powerup> powerUps
			music.playPowerUp(); //plays sound effect for collecting Powerup
		}
	}

	public void checkDeath(){ //checks if the ball goes out and changes the game accordingly
		if (ball.getY() > 800){
			explosions.add(new Point(paddle.getX(), paddle.getY() - 30)); //add explosion to where the paddle was
			lives--; 
			music.playDeath(); //plays losing life SoundEffect
			
			//turns off Powerups
			catching = false;
			laserActive = false;
			paddle.setEnlarge(false);

			if (lives > 0){
				start = true; //sets start to true so that the ball goes back to where it should be at the start of a level (stuck to the paddle) 
			}
			else{ //no lives left, goes to end screen
				curScreen = "GAME_OVER";
				music.playMusic(curScreen);
			}
		}
	}

	public void shootLasers(){ //method to shoot, move, and check collisions for Lasers

		if (lasers.size() == 0 && !start && laserActive){ //if the ball is in movement and no Lasers are on the screen and the laser Powerup is active,
			//shoots 2 Lasers and plays sound effect for shooting
			lasers.add(new Laser(paddle.getX() +20, paddle.getY())); 
			lasers.add(new Laser(paddle.getX() + paddle.getWidth() -30, paddle.getY()));	
			music.playShoot();
		}

		ArrayList <Laser> delLasers = new ArrayList<Laser>(); //Lasers to delete after going off the screen
		
		for (Laser l : lasers){
			l.move(); //moves Laser
			if (l.isActive()){ //does not check for collisions if the Laser is inactive
				for (int i=0; i<blocks.size(); i++){
					Block b = blocks.get(i);
					if (l.getRect().intersects(b.getRect())){ //checks if the Laser intersects with a Block
					
						l.playSound(); //plays SoundEffect

						if (!b.getColor().equals("Gold")){
							blocks.get(i).lowerHealth(1);
							if (blocks.get(i).getHealth() <= 0){ //if the blocks is broken, adds it to the ArrayList to be deleted later
								blocksToDelete.add(blocks.get(i));
							}
						}	

						l.setActive(false);
						explosions.add(new Point(b.getX(), b.getY())); //adds Point to explosion ArrayList to later display an explosion at that Point
						
					}
				}
			}
			if (l.getY() < 0){ //if the Laser is off the screen
				delLasers.add(l); //adds it to the list to remove
			}
		}

		for (Laser l : delLasers){ //removes all Lasers that are off screen
			lasers.remove(l);
		}
	}

	public void deleteBlocks(){ //method to remove broken blocks
		for (Block b : blocksToDelete){
			points += b.getPoints(); //increases points 
			blocks.remove(b); //removes Block
		}
		blocksToDelete.clear(); //resets the set of broken blocks
	}

    public void moveStuff(){ //moves things on screen
		for (Powerup p : powerUps){
			p.move(); //moves Powerups
		}
		collidePowerUps(); //checks collisions with Powerups
		shootLasers(); //shoots lasers if laserActive == true

        paddle.move((int)(mouse.getX() - offset.getX() - paddle.getWidth()/2)); //moves paddle

		if (catching && paddle.getRect().intersects(ball.getRect())){ //activates catch Powerup ability when the ball meets the paddle 
			start = true;
			catching = false;
		}

		collideBlocks(); //checks ball collisions with blocks
		ball.move(paddle, start); //moves the ball
		checkDeath(); //checks if the ball goes off screen
		deleteBlocks(); //deletes broken blocks
    }
	
	@Override
	public void actionPerformed(ActionEvent e){		
		try{
			mouse = MouseInfo.getPointerInfo().getLocation(); //location of mouse on screen
        	offset = getLocationOnScreen(); //location of panel on screen
		}
		catch (IllegalComponentStateException ex){
			mouse = new Point(WIDTH/2, HEIGHT/2); //if the panel hasn't loaded in yet, sets the mouse to the centre of the screen by default
			offset = new Point(0, 0);
		}

		if (curScreen.contains("LEVEL")){
			moveStuff(); //move objects

			if (points == totalPoints){ //player completed the level
				loadFrame = Integer.max(0, (int)loadFrame); //sets loadFrame to play animation for loading screen

				if (curScreen.equals("LEVEL_1")){
					curScreen = "loading";
					curLevel = 2; //starts level 2
				}
				else{
					//the entire game was won
					highScore = Util.updateHighScore(levels[1].getTotalPoints() + levels[2].getTotalPoints());
					totalPoints = -1; 
					curScreen = "GAME_WIN";
					curLevel = -1;
				}
			}
		}

		startFrame ++;
		startFrame %= 240; //ensures the startFrame is an integer in the range [0, 239] because that's how many frames there are in the animation
		
		repaint(); //draws on screen
	}
	
	@Override
	public void	mouseClicked(MouseEvent e){}

	@Override
	public void	mouseEntered(MouseEvent e){}

	@Override
	public void	mouseExited(MouseEvent e){}

	@Override
	public void	mousePressed(MouseEvent e){ //method that changes the game if the mouse is pressed
		if (curScreen.contains("LEVEL")){
			start = false; //if the ball was stuck to the paddle, it's now released
			catching = false; //since the ball was released from the paddle, the catch Powerup is disabled
		}

		else if (curScreen.equals("START_SCREEN") || curScreen.equals("GAME_OVER")){ //starts from level 1 if game over or start screen is clicked
			loadFrame = Integer.max((int)loadFrame, 0); //sets loadFrame to play the loading animation
			curScreen = "loading";

			curLevel = 1; //sets curLevel = 1 to play the first level since the game is being started from the beginning
			music.stopAll();
		}

		else if (curScreen.equals("GAME_WIN")){
			curScreen = "START_SCREEN"; //goes back to the start screen
			music.playMusic(curScreen);
		}
	}

	@Override
	public void	mouseReleased(MouseEvent e){}

	@Override
	public void paint(Graphics g){
		if (firstEver){ //loads all images ONCE at the start to avoid flickering later on
			images.drawAllImages(g);
			firstEver = false;
		}

		else if (loadFrame >= 0){ //loading animation is active
			g.drawImage(images.getLoadImage((int)loadFrame), 0, 0, null);
			loadFrame++;
			if (loadFrame > 100){
				loadFrame = -1; //disables loading animation so the game can be played
				if (1 <= curLevel && curLevel <= 2) startLevel(curLevel); //starts the level if the loading animation leads to a level
			}
		}
		
		else if (curScreen.contains("LEVEL")){ //if a level is playing
			g.drawImage(images.getLevelBG(), 0, 0, null); //draws level background
			
			for (Block b : blocks){ //draws all the Blocks on the screen
				b.draw(g);
			}
			for (Powerup p : powerUps){ //draws all the Powerups on the screen
				p.draw(images, g);
			}
			for (Laser l : lasers){ //draws all active Lasers on the screen
				if (l.isActive()) l.draw(g);
			}

			if (!explosions.isEmpty()){ //if there are explosion animations to show on the screen, displays them 
				for (Point p : explosions){
					g.drawImage(images.getExplosion(), (int)p.getX(), (int)p.getY(), null);
				}
				explosions.clear(); //empties explosions to draw ArrayList
			}
			

			ball.draw(g); //draws the current ball
			paddle.draw(images, g, laserActive); //draws the paddle

			//displays the current points
			g.setColor(Color.WHITE); 
			g.setFont(fontLocal);
			g.drawString(Util.paddedNumber(points, 4),680,50);

			highScore = Util.updateHighScore(Integer.max(highScore, points)); //updates the highest score so far
			g.drawString("HIGH SCORE: "+Util.paddedNumber(highScore, 4), 25,50); //displays the highest score so far

			for (int i=0; i<800; i+=20){ //draws the border
				g.drawImage(images.getVBorderImage(), 0, i, null);
				g.drawImage(images.getHBorderImage(), i, 0, null);
				g.drawImage(images.getVBorderImage(), 780, i, null);
			}

			//displays the number of lives with icons
			for (int i=0; i<lives-1; i++){
				g.drawImage(images.getLifeIcon(), i*20 +20, 760, null);
			}
		}

		else if (curScreen.equals("GAME_OVER")){
			g.drawImage(images.getStartBG(startFrame), 0, 0, null); //displays background
			g.drawImage(images.getLoseText(), 0, 0, null); //displays losing text on screen
			
			//shows how many points were gained on the last run
			g.setFont(fontLocal);
			g.setColor(new Color(0, 110, 255));
			g.drawString("Your Score:", 300, 400);
			g.drawString(Util.paddedNumber(points, 4), 360, 430);
		}

		else if (curScreen.equals("START_SCREEN")){
			g.drawImage(images.getStartBG(startFrame), 0, 0, null); //displays background
			g.drawImage(images.getTitleText(), 0, 0, null); //displays title text on screen

			//shows the highest score a player got in this game
			g.setFont(fontLocal);
			g.setColor(new Color(0, 110, 255));
			g.drawString("High Score:", 300, 400);
			g.drawString(Util.paddedNumber(highScore, 4), 345, 430);
		}

		else if (curScreen.equals("GAME_WIN")){
			g.drawImage(images.getStartBG(startFrame), 0, 0, null); //displays background
			g.drawImage(images.getWinText(), 0, 0, null); //displays winning text on screen
		}
    }
}