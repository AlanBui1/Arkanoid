/*
Sound.java
Alan Bui
Simple Game Assignment ICS4U-01
*/

import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioSystem;

//class with Sounds and methods to play SoundEffects
class Sound extends JFrame implements ActionListener
{
  private SoundEffect startSound, levelSound, power, loseSound, death, shoot; 
  /*
   startSound is the sound effect for the starting screen
   levelSound is the sound effect for the level screen
   explosion is the sound effect for explosions
   power is the sound effect for gaining a Powerup
   loseSound is the sound effect for the losing screen
   death is the sound effect for losing a life
   shoot is the sound effect for shooting a laser
   bulletHit is the sound effect for a bullet hitting a Block
   */
  
  public Sound() //initializes sounds
  {
    startSound = new SoundEffect("music/START_SCREEN_compressed.wav");	
    levelSound = new SoundEffect("music/LEVEL_compressed.wav");
    loseSound = new SoundEffect("music/GAME_OVER_compressed.wav");	
    power = new SoundEffect("music/powerup.wav");
    death = new SoundEffect("music/player_dies.wav");
    shoot = new SoundEffect("music/shoot.wav");
  }
  
  public void actionPerformed(ActionEvent ae){
  }

  public void stop(String s){ //method to stop music
    if (s.equals("startSound")){
      startSound.stop();
    }
  }
  public void playPowerUp(){ //plays gaining powerup sound
    power.play();
  }
  public void playDeath(){ //plays death sound
    death.play();
  }
  public void playShoot(){//plays shooting sound
    shoot.play();
  }

  public void loopSong(SoundEffect se){ //loops sound effect
    se.loop();
  }

  public void stopAll(){ //stops all sound effects from being played
    startSound.stop();
    levelSound.stop();
    loseSound.stop();
    power.stop();
    death.stop();
    shoot.stop();
  }

  public void playMusic(String curScreen){ //plays music according to the current screen
    if (curScreen.equals("START_SCREEN")){
      levelSound.stop();
      loseSound.stop();
      loopSong(startSound);
    }
    else if (curScreen.contains("LEVEL")){
      startSound.stop();
      loseSound.stop();
      loopSong(levelSound);
    }
    else if (curScreen.contains("OVER")){
      startSound.stop();
      levelSound.stop();
      loopSong(loseSound);
    }
  }

  public static void main(String args[]){
  	new Sound().setVisible(true);
  }
}

//class with Clips that can play, stop, and loop
class SoundEffect{
    private Clip c;
    public SoundEffect(String filename){ //initializes clip from filename
        setClip(filename); 
    }
    public void setClip(String filename){ //sets clip from filename
        try{
            File f = new File(filename);
            c = AudioSystem.getClip();
            c.open(AudioSystem.getAudioInputStream(f));
        } catch(Exception e){ 
         System.out.println("error"); 
        }
    }
    public void play(){ //plays SoundEffect from the start
        c.setFramePosition(0);
        c.start();
    }
    public void stop(){ //stops SoundEffect
        c.stop();
    }
    public void loop(){ //loops SoundEffect continuously
      c.setFramePosition(0);
      c.loop(Clip.LOOP_CONTINUOUSLY);
    }
}