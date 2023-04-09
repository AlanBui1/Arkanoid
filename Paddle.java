/*
Paddle.java
Alan Bui
Simple Game Assignment ICS4U-01
*/

import java.awt.*;

//class with location, width, and height of a Paddle with methods able to move it, draw it, enlarge it, and return field values
public class Paddle {
    private int x, y, width, height; //x and y coordinates of the top left corner of the paddle, width and height of the paddle
    private double curFrame = 0; //frame of the animation the paddle is on
    private boolean enlarge; //true if the paddle is being enlarged, otherwise it is not enlarged

    public Paddle(int xx, int yy){ //initializes values for a Paddle
        enlarge = false; 
        x = xx;
        y = yy;
        width = 100;
        height = 20;
    }

    public void move(int location){ //moves the paddle according to the location of the mouse
		x = location;

        //makes sure the paddle doesn't go off the screen
        if (x <= 20){
            x = 20;
        }
        if (x >= 780 - width){
            x = 780 - width;
        }
        curFrame += .1; //increases the frame
        curFrame %= 6;
	}

    public int getX(){ //returns the x coordinate of the top left corner of the paddle
        return x;
    }
    public int getY(){ //returns the y coordinate of the top left corner of the paddle
        return y;
    }

    public int getWidth(){ //returns the width of the paddle
        return width;
    }

    public int getHeight(){ //returns the height of the paddle
        return height;
    }

    public Rectangle getRect(){ //returns the rectangle of the paddle
		return new Rectangle(x,y,width,height);
	}	
    public Rectangle getTop(){ //returns the top of the paddle
        return new Rectangle(x,y,width,1);
    }

    public void draw(LoadedImages l, Graphics g, boolean shooting){ //draws the paddle on the screen
        if (shooting){
            g.drawImage(l.getShooterImage(enlarge)[(int)curFrame], x, y, null);
        }
        else{
            g.drawImage(l.getPaddleImage(enlarge)[(int)curFrame], x, y, null);
        }
	}  	

    public void setEnlarge(boolean b){ //enlarges the paddle or sets the paddle back to normal size
        enlarge = b;
        if (b){
            width = 150;
            height = 30;
        }
        else{
            width = 100;
            height = 20;
        }
    }
}
