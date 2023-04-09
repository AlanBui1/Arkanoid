/*
LoadedImages.java
Alan Bui
Simple Game Assignment ICS4U-01
*/

import java.awt.*;
import java.util.Map;
import java.util.HashMap;

//class to store images that need to be loaded for the game
public class LoadedImages {
    private Map <String, Image[]> powerImages = new HashMap <String, Image[]>(); 
    //powerImages.get(PowerupName) will store an Image array of the frames the Powerup will go through in its animation
    private Map <Boolean, Image[]> paddleImages = new HashMap <Boolean, Image[]>(), shooterImages = new HashMap <Boolean, Image[]>(); 
    //powerImage.get(enlarge) will store an Image array of the frames the paddle will go through in its animation (enlarge/regular size)
    private Image verticalBorder, horizontalBorder, life, levelBG, titleText, winText, loseText, explosion;
    private Image[] startImages = new Image[240], loadingImages = new Image[101];
    //startImages are the frames of the starting screen, loadingImages are the frames of the loading screen

    LoadedImages(){
        //loads all images using methods from Util
        verticalBorder = Util.loadScaledImg("images/silver.png", 20, 20);
        horizontalBorder = Util.loadScaledImg("images/silver-rotated.png", 20, 20);
        levelBG = Util.loadImg("images/bg.png");
        life = Util.loadScaledImg("images/lifeIcon.png", 40, 40);
        titleText = Util.loadImg("images/start_screen/title.png");
        winText = Util.loadImg("images/win_screen/endscreen.png");
        loseText = Util.loadImg("images/losescreen.png");
        explosion = Util.loadImg("images/star-explosion.png");

        for (int i=0; i<Powerup.powerNames.length; i++){
            powerImages.put(Powerup.powerNames[i], new Image[8]);
            for (int k=0; k<8; k++){   
                powerImages.get(Powerup.powerNames[i])[k] = Util.loadScaledImg("images/powerups/"+Powerup.powerNames[i]+k+".png", 100, 50);
            }
        }

        paddleImages.put(false, new Image[6]);
        paddleImages.put(true, new Image[6]);
        shooterImages.put(false, new Image[6]);
        shooterImages.put(true, new Image[6]);
        for (int i=0; i<6; i++){
            Image img = Util.loadImg("images/paddle_img/paddle_"+i+".jpg");
            paddleImages.get(false)[i] = img.getScaledInstance(100, 20, Image.SCALE_SMOOTH);
            paddleImages.get(true)[i] = img.getScaledInstance(150, 30, Image.SCALE_SMOOTH);
            
            img = Util.loadImg("images/paddle_img/shooter"+i+".png");
            shooterImages.get(false)[i] = img.getScaledInstance(100, 20, Image.SCALE_SMOOTH);
            shooterImages.get(true)[i] = img.getScaledInstance(150, 30, Image.SCALE_SMOOTH);
        }

        for (int i=0; i<240; i++){
            startImages[i] =  Util.loadImg("images/start_screen/start"+i+".jpg");
        }

        for (int i=0; i<101; i++){
            loadingImages[i] = Util.loadImg("images/loading_screen/load"+i+".jpg");
        }
    }

    public Image[] getPowerImage(String name){ //returns the Image array of powerUp Images given the name of the Powerup
        return powerImages.get(name);
    }
    public Image[] getPaddleImage(boolean enlarge){ //returns the Image array of paddle Images given if it is enlarged or not
        return paddleImages.get(enlarge);
    }
    public Image[] getShooterImage(boolean enlarge){ //returns the Image array of shooter Images given if it is enlarged or not
        return shooterImages.get(enlarge);
    }
    public Image getHBorderImage(){ //returns the Image of the horizontal border image
        return horizontalBorder;
    }
    public Image getVBorderImage(){ //returns the Image of the vertical border image
        return verticalBorder;
    }
    public Image getStartBG(int frame){ //returns the Image of a certain frame of the start background
        return startImages[frame];
    }
    public Image getLifeIcon(){ //returns the Image of the life icon
        return life;
    }
    public Image getLevelBG(){ //returns the Image of the background for levels
        return levelBG;
    }
    public Image getLoadImage(int frame){ //returns a certain frame of the loading screen
        return loadingImages[frame];
    }
    public Image getTitleText(){ //returns an Image of the title text for the start screen
        return titleText;
    }
    public Image getWinText(){ //returns an Image of the title text for the win screen
        return winText;
    }
    public Image getLoseText(){ //returns an Image of the title text for the losing screen
        return loseText;
    }
    public Image getExplosion(){ //returns an Image of explosion 
        return explosion;
    }

    public void drawAllImages(Graphics g){ //draws all images
        //this is useful to do at the start of the game so there is no flickering later on
        for (int i=0; i<4; i++){
            for (int k=0; k<8; k++){
                g.drawImage(powerImages.get(Powerup.powerNames[i])[k], 0, 0, null);
            }
        }

        for (int i=0; i<6; i++){
            g.drawImage(paddleImages.get(true)[i], 0, 0, null);
            g.drawImage(paddleImages.get(false)[i], 0, 0, null);
            g.drawImage(shooterImages.get(true)[i], 0, 0, null);
            g.drawImage(shooterImages.get(false)[i], 0, 0, null);
        }

        for (int i=0; i<240; i++){
            g.drawImage(startImages[i], 0, 0, null);
        }

        for (int i=0; i<101; i++){
            g.drawImage(loadingImages[i], 0,0, null);
        }

        g.drawImage(verticalBorder, 0, 0, null);
        g.drawImage(horizontalBorder, 0 ,0, null);
    }
}
