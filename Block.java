/*
Block.java
Alan Bui
Simple Game Assignment ICS4U-01
*/

import java.awt.*;
import java.util.Arrays;

//class with location, hitbox, point value, health, and color of a Block, able to draw it on a screen, lower health points of the Block, and return field values
public class Block {
    //array of all possible block colors colors
    public static final String[] colors = {"White", "Orange", "Cyan", "Green", "Red", "Blue", "Violet", "Yellow", "Gold", "Silver"}; 

    //array of points gotten from the colors, the array is parallel to the array of colors
    //pointVals[Arrays.asList(colors).indexOf(color)] is the point value of the color of block
    public static final int[] pointVals = {50, 60, 70, 80, 90, 100, 110, 120, 0, 50}; 

    private int x, y, width, height; 
    /*
    x is the x coordinate of the top left corner of the Block
    y is the y coordinate of the top left corner of the Block
    width is the width of the Block
    height is the height of the Block
     */

    private Image img; //image drawn for this Block
    private int pts, health; //points gained from breaking this Block and how many hits it takes to break the Block
    private String col; //color of the Block

    public Block(int xx, int yy, int w, int h, String color){ //initializes values for the Block
        x = xx;
        y = yy;
        width = w;
        height = h;
        pts = pointVals[Arrays.asList(colors).indexOf(color)];
        col = color;
        img = Util.loadScaledImg("images/arkanoid_blocks/Arkanoid_Brick_"+color+".jpg", width, height);
        health = 1;
        if (color.equals("Silver")) health = 2; //silver blocks have 2 health
    }

    public Rectangle getRect(){ //returns the rectangle the block takes up
        return new Rectangle(x, y, width, height);
    }

    public Rectangle getBottom(){ //returns the bottom side of the block
        return new Rectangle(x, y+height-1, width, 1);
    }
    public Rectangle getTop(){ //returns the top side of the block
        return new Rectangle(x, y, width, 1);
    }
    public Rectangle getLeft(){ //returns the left side of the block
        return new Rectangle(x, y, 1, height);
    }
    public Rectangle getRight(){//returns the right side of the block
        return new Rectangle(x+width-1, y, 1, height);
    }

    public int getX(){ //returns the x value of the top left corner of the block
        return x;
    }
    public int getY(){ //returns the y value of the top left corner of the block
        return y;
    }
    public String getColor(){ //returns the color of the block
        return col; 
    }
    public int getPoints(){ //returns the points the block gives for breaking it
        return pts;
    }
    public int getHealth(){ //returns the health left on the block
        return health;
    } 
    public void lowerHealth(int h){ //lowers the block's health by h
        health -= h;
    }

    public void draw(Graphics g){ //displays the blocks image at it's location
        g.drawImage(img, x, y, null);
    }

}
