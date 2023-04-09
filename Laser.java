/*
Laser.java
Alan Bui
Simple Game Assignment ICS4U-01
*/

import java.awt.*;

//class to store location, width, height, velocity, SoundEffect of a Laser and if its active
//methods are able to return fields, move the Laser, draw on a screen, play SoundEffect, and toggle if the Laser is active or not
public class Laser {
    private SoundEffect explodeSoundEffect; //sound the Laser makes when it collides with a Block
    private int x, y, vy, width, height; //x and y coordinates of the top left corner of the Laser, velocity in the y-direction, and width and height of the Laser
    private boolean active; //true if the Laser is activated (can hit Blocks), otherwise false

    public Laser(int xx, int yy){ //initializes values for a Laser
        x = xx;
        y = yy;
        vy = -5;
        width = 5;
        height = 10;
        active = true;
        explodeSoundEffect = new SoundEffect("music/bullet_hit.wav");
    }

    public Rectangle getRect(){ //returns the Rectangle of the Laser
        return new Rectangle(x, y, width, height);
    }
    public int getX(){ //returns the x coordinate of the top left corner of the Laser
        return x;
    }
    public int getY(){ //returns the y coordinate of the top left corner of the Laser
        return y;
    }

    public void move(){ //moves the Laser
        y += vy;
    }

    public boolean isActive(){ //checks if the Laser is active/inactive
        return active;
    }

    public void setActive(boolean b){ //sets the Laser active/inactive
        active = b;
    }

    public void draw(Graphics g){ //draws the Laser
        g.setColor(new Color(255,0,0));
        g.fillRect(x, y, width, height);
    }   

    public void playSound(){ //method to play sound the Laser makes when it collides with a Block
        explodeSoundEffect.play();
    }
}
