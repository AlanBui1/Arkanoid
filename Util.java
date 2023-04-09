/*
Util.java
Alan Bui
Simple Game Assignment ICS4U-01
*/

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.util.*;

//class with useful methods that are used throughout the project
public class Util { 

	public static int randint(int low, int high){ //returns a random integer in the range [low, high]
		return (int)(Math.random()*(high-low+1)+low);
	}
	public static double randDouble(double low, double high){ //returns a random double in the range [low, high]
		return Math.random()*(high-low+1)+low;
	}

	public static Image loadImg(String fileName){ //returns Image with file name fileName
		return new ImageIcon(fileName).getImage();
	}

	public static Image loadScaledImg(String fileName, int width, int height){ //returns scaled Image with file name fileName, width, and height
		Image img = loadImg(fileName);
		return img.getScaledInstance(width, height, Image.SCALE_SMOOTH); //returns scaled image
	}

	public static int updateHighScore(int highScore){ //updates highScore.txt and returns the highest score
		try{
			Scanner inFile = new Scanner(new BufferedReader(new FileReader("highScore.txt"))); //opens the highScore file which contains a single integer, the highest score so far
			highScore = Integer.max(inFile.nextInt(), highScore); //updates the highScore as the max of the current highScore and the highScore in the file
			inFile.close(); //closes the file
		}
		catch (IOException ex){
			System.out.println("PROBLEM IN FILE");
			System.out.println(ex);
		}

		try{
			PrintWriter outFile = new PrintWriter(new BufferedWriter (new FileWriter ("highScore.txt"))); //opens the highScore file to write the highest score so far
			outFile.write(""+highScore); //writes the highScore in the file
			outFile.close(); //closes the file
		}
		catch (IOException ex){
			System.out.println("PROBLEM OUT FILE");
			System.out.println(ex);
		}

		return highScore; 
	}

	public static String paddedNumber(int num, int digits){ //returns a number as a String padded to a certain number of digits
		String ret = ""+num; //String to return
		while (ret.length() < digits){
			ret = "0"+ret; //adds "0" to the front until there are enough digits
		}
		return ret;
	}
	
}