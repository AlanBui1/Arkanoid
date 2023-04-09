/*
Powerup.java
Alan Bui
Simple Game Assignment ICS4U-01
*/

import java.awt.*;

//class with location, width, height, type, and velocity of a Powerup, with methods able to move it, display it on a screen, and return field values
public class Powerup {
    public static final String[] powerNames = {"extraLife", "catch", "enlarge", "laser"}; //array of the possible Powerup names/types
    public static final int POWERWIDTH = 100, POWERHEIGHT = 50; //width and height of all Powerups

    private int x, y, vy, width, height; //x coordinate and y coordinate of the top left corner of the Powerup, velocity in the y direction, width and height of the Powerup
    private double frameCnt; //the current frame the animation is on
    private String powerType; //the type of powerup which determines the abilities granted by receiving the Powerup

    Powerup(int xx, int yy, String name){ //initializes values for a Powerup
        width = POWERWIDTH;
        height = POWERHEIGHT;
        vy = 5;
        x = xx;
        y = yy;
        powerType = name;
        frameCnt = 0;
    }

    public void move(){
        y += vy; //move the y by how much velocity it has
        frameCnt += .1; //increase the frame the Powerup is on
        frameCnt %= 8; //since the framw images are from 0-7, the frames loop back to the beginning (0) when it reaches 8
    }

    public Rectangle getRect(){ //returns the rectangle for the Powerup
        return new Rectangle(x, y, width, height);
    }

    public void draw(LoadedImages l, Graphics g){ //draws the Powerup 
        g.drawImage(l.getPowerImage(powerType)[(int)frameCnt], x, y, null);
    }

    public String getName(){ //returns the name/type of the Powerup
        return powerType;
    }

}