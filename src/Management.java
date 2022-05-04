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


    public void prime(){
        this.level = 6;
        try {
            blocks =  readFile();
        } catch (Exception e) {
            System.out.print(e.toString());
            blocks = new ArrayList<>();
        }
    }


    public boolean switchBlocks(){
        // check the blocks whether is movable and convetion to switch
        if (firstBlock.isMovable() && secondBlock.isMovable() && 
        (secondBlock.getType() + secondBlock.getProperty()).equals("emptyFree")){

            // check whether first block is next to second block
            Double subColumn = 0.0 + firstBlock.getColumn() - secondBlock.getColumn();
            Double subRow = 0.0 + firstBlock.getRow() - secondBlock.getRow();

            if ( (Math.abs(subColumn) == 1.0 && Math.abs(subRow) == 0.0) ||  (Math.abs(subColumn) == 0.0 && Math.abs(subRow) == 1.0) ){
                // create temp object from firstBlock by cloning
                int[] position = {firstBlock.getColumn(),firstBlock.getRow()};
                Block temp = new  Block(position, firstBlock.getType(), firstBlock.getProperty());

                firstBlock.setColumn(secondBlock.getColumn());
                firstBlock.setRow(secondBlock.getRow());
                secondBlock.setColumn(temp.getColumn());
                secondBlock.setRow(temp.getRow());

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
            if(line.startsWith("" + count)){
                String[] properties = line.split(",");
                int number = Integer.parseInt(properties[0]);
                int row = (int)(number/4.0-0.1);
                int col = (number%4 != 0) ? number%4-1 : 3;
                int[] position = {col,row};
                Block newBlock = new Block(position,properties[1],properties[2]);
                arrayList.add(newBlock);
                count++;
            }
        } 
        input.close();
        return arrayList;
    }


    public void findNextBlock(Block[] threeBlocks){
        Block block0 = threeBlocks[0];
        Block block1 = threeBlocks[1];
        Block block2 = threeBlocks[2];

        // assign block0 if it is null
        if (block0 == null){
            for(int i = 0 ; i < blocks.size() ; i++){
                if (blocks.get(i).getType().equals("starter")){
                    block0 = blocks.get(i);
                }
            }
        }   
        
        // assign block1 by block0 if it is null
        if (block1 == null){
            if (block0.getProperty().equals("Vertical"))
                block1 = findBlockByCoordinate(block0.getColumn(), block0.getRow() + 1);
            else if (block0.getProperty().equals("Horizontal"))
                block1 = findBlockByCoordinate(block0.getColumn() - 1, block0.getRow());
        }

        // update threeBlocks with new blocks
        if (threeBlocks[0] == null && threeBlocks[1] ==null){
            threeBlocks[0] = block0;
            threeBlocks[1] = block1;
        }
        else{
            threeBlocks[0] = block1;
            threeBlocks[1] = block2;
            block0 = block1;
            block1 = block2;
        }

        // assign block2 acording to block1
        if ( block1.getProperty().equals("Vertical")){
            if ( block1.getRow() > block0.getRow())
                block2 = findBlockByCoordinate(block1.getColumn(), block1.getRow() + 1);
            else
                block2 = findBlockByCoordinate(block1.getColumn(), block1.getRow() - 1);
        }
        else if (block1.getProperty().equals("Horizontal")){
            if ( block1.getColumn() > block0.getColumn())
                block2 = findBlockByCoordinate(block1.getColumn() + 1, block1.getRow());
            else
                block2 = findBlockByCoordinate(block1.getColumn() - 1, block1.getRow());
        }
        else if (block1.getProperty().equals("00")){
            if (block1.getColumn() ==  block0.getColumn())
                block2 = findBlockByCoordinate(block0.getColumn() - 1, block0.getRow() + 1);
            else
                block2 = findBlockByCoordinate(block0.getColumn() + 1, block0.getRow() - 1);
        }
        else if (block1.getProperty().equals("01")){
            if (block1.getColumn() ==  block0.getColumn())
                block2 = findBlockByCoordinate(block0.getColumn() + 1, block0.getRow() + 1);
            else
                block2 = findBlockByCoordinate(block0.getColumn() - 1, block0.getRow() - 1);
        }
        else if (block1.getProperty().equals("10")){
            if (block1.getColumn() ==  block0.getColumn())
                block2 = findBlockByCoordinate(block0.getColumn() - 1, block0.getRow() - 1);
            else
                block2 = findBlockByCoordinate(block0.getColumn() + 1, block0.getRow() + 1);
        }
        else if (block1.getProperty().equals("11")){
            if (block1.getColumn() ==  block0.getColumn())
                block2 = findBlockByCoordinate(block0.getColumn() + 1, block0.getRow() - 1);
            else
                block2 = findBlockByCoordinate(block0.getColumn() - 1, block0.getRow() + 1);
        }

        // update blocks with new blocks
        threeBlocks[2] = block2;
    }

    public Block findBlockByCoordinate(int col, int row){
        for(Block block : blocks){
            if (block.getColumn() == col && block.getRow() == row){
                return block;
            }
        }
        return null;
    }
}