/*
Arkanoid.java
Alan Bui
Simple Game Assignment ICS4U-01
*/

import javax.swing.*;

//class that starts the arkanoid game
//Arkanoid is a game played where the user controls a vaus (paddle) which bounces a ball around the screen.
//The ball collides with blocks on the screen.
//The goal of the game is to clear all of the blocks on the screen while not losing lives (by the ball falling out of the screen)
//Powerups drop randomly when non-silver blocks break

public class Arkanoid extends JFrame{ 
    public static final int WIDTH = 800, HEIGHT = 800; //width and height of the window
    Gamepanel game = new Gamepanel();
    public static Arkanoid frame;

    public Arkanoid(){
        super("ARKANOID");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		add(game);
		pack();  // set the size of my Frame exactly big enough to hold the contents
		setVisible(true);	
    }

    public static void main(String [] args){
        frame = new Arkanoid(); //creates a new Arkanoid object
    }
}