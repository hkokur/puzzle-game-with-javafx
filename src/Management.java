import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Management{
    private ArrayList<Block> blocks;
    private int level;

    // preserve the blocks to switch each other.
    private Block firstBlock;
    private Block secondBlock;

    Management(){}

    public void main(String[] args){
        Main main = new Main();
    }

    public void prime(){
        this.level = 1;
        try {
            blocks =  readFile();

        } catch (Exception e) {
            System.out.print(e.toString());
            blocks = new ArrayList<>();
        }
    }

    public boolean switchBlocks(){
        // check the blocks whether is movable and convetion to switch
        if(firstBlock.isMovable() && secondBlock.isMovable()){

            // check whether first block is next to second block
            // burası düzgün işlemiyor
            int sumFirst = firstBlock.getColumn() + firstBlock.getRow();
            int sumSecond = secondBlock.getColumn() + secondBlock.getRow();
            if ( (sumFirst ==  sumSecond + 1) || (sumFirst == sumSecond -1)){
        
                // create temp object from firstBlock by cloning
                int[] position = {firstBlock.getColumn(),firstBlock.getRow()};
                Block temp = new  Block(position, firstBlock.getType(), firstBlock.getProperty());

                firstBlock.setColumn(secondBlock.getColumn());
                firstBlock.setRow(secondBlock.getRow());
                secondBlock.setColumn(temp.getColumn());
                secondBlock.setRow(temp.getRow());

                blocks.set(blocks.indexOf(firstBlock), firstBlock);
                blocks.set(blocks.indexOf(secondBlock), secondBlock);
                firstBlock = null;
                return true;
            }
        }
        return false;

    }

    
    public ArrayList<Block> readFile() throws FileNotFoundException{
        ArrayList<Block> arrayList= new ArrayList<>();
        
        // reading file
        File file= new File("src/levels/level"+level +".txt");
        Scanner input = new Scanner(file);

        // creating objects according to level(contains 16(4x4) blocks) file and add it to arrayList
        int count = 1;
        while(input.hasNextLine()){
            String line = input.nextLine();
            if(line.startsWith("" + count++)){
                String[] properties = line.split(",");
                int number = Integer.parseInt(properties[0]);
                int row = (int)(number/4.0-0.1);
                int col = (number%4 != 0) ? number%4-1 : 3;
                int[] position = {col,row};
                Block newBlock = new Block(position,properties[1],properties[2]);
                arrayList.add(newBlock);
            }
        } 
        input.close();
        return arrayList;
    }

    public void setLevel(int level){
        this.level = level;
    }

    public void setFirstBlock(Block firstBlock){
        this.firstBlock = firstBlock;
    }

    public void setSecondBlock(Block secondBlock){
        this.secondBlock = secondBlock;
    }

    public ArrayList<Block> getBlocks(){
        return blocks;
    }

    public Block getFirstBlock(){
        return firstBlock;
    }

}