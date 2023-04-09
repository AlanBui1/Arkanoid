/*
Ball.java
Alan Bui
Simple Game Assignment ICS4U-01
*/

import java.awt.*;

//class with location of a Ball, move it, draw it on the screen, play a sound effect, and return field values
public class Ball {
    private double x, y, vx, vy, r; 
    /*
    x coordinate of the left corner of the ball,
    y coordinate of the right corner of the ball,
    velocity in the x direction,
    velocity in the y direction,
    radius of the circle the ball represents
    */
    private SoundEffect bounceSoundEffect; //SoundEffect when the ball bounces on a Block

    public Ball(int xx, int yy, int rr){ //initializes values for a Ball
        x = xx;
        y = yy;
        r = rr;

        //sets a random velocity in the x direction
        if(Util.randint(0,1)==0){
            vx = Util.randDouble(-5,-4); 
        }
        else{
            vx = Util.randDouble(4, 5);
        }
        vy = -6; //the ball starts with velocity going up the screen
        
        bounceSoundEffect = new SoundEffect("music/bounce.wav");
    }

    public double getX(){ //returns the x coordinate of the centre of the ball
        return x;
    }
    public double getY(){ //returns the y coordinate of the centre of the ball
        return y;
    }

    public void move(Paddle p, boolean start){ //moves the ball
        if (!start){
            if(y <= 20){ //if the ball hits the top, gets downward velocity
                vy *= -1;
            }
            if (x<=20 || x>=780){ //if the ball hits the side, reverses x velocity
                vx *= -1;
            }

            //moves the ball
            x += vx;
            y += vy;
            
            if (getRect().intersects(p.getTop())){ //bounces the ball off the paddle if they intersect
                vy = -vy;
                double distFromRight = (p.getWidth()+ p.getX()-x)/p.getWidth()*180;
                vx = Math.cos(Math.toRadians(distFromRight)) *5;
            }   
        }

        else{ //if the game is at the start, it sticks to the paddle
            x = p.getX() + (p.getWidth()/2);
            y = p.getY() - r;
        }
        
    }

    public Rectangle getRect(){ //returns the rectangle that circumscribes the circle
        return new Rectangle((int)(x-r), (int)(y-r), (int)r*2, (int)r*2);
    }
    public double getVX(){ //returns the velocity of the ball in the x direction
        return vx;
    }
    public double getVY(){ //returns the velocity of the ball in the y direction
        return vy;
    }
    public void setVX(double v){ //sets the velocity of the ball in the x direction
        vx = v;
    }
    public void setVY(double v){ //sets the velocity of the ball in the y direction
        vy = v;
    }

    public void draw(Graphics g){ //draws the ball on the screen
        g.setColor(Color.RED);
        g.fillOval((int)(x-r), (int)(y-r), (int)r*2, (int)r*2);
    }
    
    public void playBounce(){ //plays sound effect
        bounceSoundEffect.stop();
        bounceSoundEffect.play();
    }
}
