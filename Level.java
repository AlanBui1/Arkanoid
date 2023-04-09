/*
Level.java
Alan Bui
Simple Game Assignment ICS4U-01
*/

import java.util.ArrayList;
import java.util.Arrays;

//class that stores default values of a level
public class Level {
    private int totalPoints, lives;
	/*
	totalPoints is the total points achieveable in the level 
	lives is the starting number of lives
	*/
    private String curLevel; //the name of the level
    private ArrayList<Block> blocks; //arraylist of Block that stores all Block objects in this level

    Level(int lvl){ //initializes values for a Level
        blocks = new ArrayList<Block>();

		//depending on the level number, adds different Blocks to the level's arraylist of Block objects
        if (lvl == 1){
            lives = 3; 
            totalPoints = 0;
            curLevel = "LEVEL_1";

			//adds Blocks to the Level's blocks and increases totalPoints according to the value of the Blocks in the level
            for (int k = 0; k<8; k++){
				for (int i=100; i<700; i+= 100){
					String blockColor = Block.colors[k];
					if (k == 7 && (i == 300 || i == 400)){
						blockColor = "Gold";
					}
					else if (k == 5 && (200 == i || i == 500)){
						blockColor = "Silver";
					}
					else if (k == 4 && (i == 300 || i == 400)){
						blockColor = "Silver";
					}

					totalPoints += Block.pointVals[Arrays.asList(Block.colors).indexOf(blockColor)];
					
					blocks.add(new Block(i, (8-k)*50 +100, 100, 50, blockColor));
				}
			}
			
        }

        else if (lvl == 2){
            totalPoints = 0;
            curLevel = "LEVEL_2";

            //adds Blocks to the Level's blocks and increases totalPoints according to the value of the Blocks in the level
			for (int k = 0; k<8; k++){
				for (int i=100; i<700; i+= 100){
					String blockColor = Block.colors[7-k];
					if (k == 3 && (i == 300 || i == 400)){
						blockColor = "Gold";
					}
					else if (k <= 3 && (200 == i || i == 500)){
						blockColor = "Silver";
					}
					else if ((k == 4 || k == 2) && (i == 300 || i == 400)){
						blockColor = "Silver";
					}
					else if (k == 5 && (i == 300 || i == 400)){
						continue;
					}

					totalPoints += Block.pointVals[Arrays.asList(Block.colors).indexOf(blockColor)];
					blocks.add(new Block(i, (8-k)*50 +50, 100, 50, blockColor));
				}
			}
			blocks.add(new Block(350, 200, 100, 50, "Silver"));
			totalPoints += 50;
        }
    }

	//getter methods
	public String getCurLevel(){
		return curLevel;
	}

	public int getTotalPoints(){
		return totalPoints;
	}

	public int getLives(){
		return lives;
	}

	public ArrayList<Block> getBlocks(){
		return blocks;
	}
}
