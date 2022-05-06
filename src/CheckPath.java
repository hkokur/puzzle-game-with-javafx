
import java.util.ArrayList;


public class CheckPath {
    CheckPath() {
    }

    public boolean checkPath(ArrayList<Block> blocks){

        int starterIndex = -1;
        int finishIndex = -1;

        //finding starter and end block
        for(int i=0; i < blocks.size(); i++) {
            if(blocks.get(i).getType().equals("starter")) {
                starterIndex = i;
            }
            else if(blocks.get(i).getType().equals("end")) {
                finishIndex = i;
            }
        }

        int j = starterIndex; //current index
        boolean isRight = true; // sağa bakıyorsa true; sola bakıyorsa false
        boolean isDown = true; // üstte ise false; aşağıda ise true

        if(finishIndex > starterIndex) {
            isDown = false;
        }

        if(blocks.get(starterIndex).getProperty().equals("Vertical")) {
            j = j + 4;
            if((checkVertical(blocks, j, isRight, isDown) == finishIndex)) {
                return true;
            }
            else {
                return false;
            }
        }
        else if(blocks.get(starterIndex).getProperty().equals("Horizontal")) {
            j = j - 1;
            isRight = false;
            if((checkHorizontal(blocks, j, isRight, isDown) == finishIndex)) {
                return true;
            }
            else {
                return false;
            }
        }
        else {
            return false;
        }

    }
    public int checkVertical(ArrayList<Block>blocks, int j, boolean isRight, boolean isDown) {
        while(true) {
            if(j > 14) {
                return j;
            } 
            else if(blocks.get(j).getProperty().equals("Vertical")) {
                if(blocks.get(j).getType().equals("end")) {
                    return j;
                }
                else if(isDown) {
                    j = j - 4;
                }
                else {
                    j = j + 4;
                }
            }
            else if(blocks.get(j).getProperty().equals("01") && !isDown) {
                j = j + 1;
                isRight = true;
                j = checkHorizontal(blocks, j, isRight, isDown);
            }
            else if(blocks.get(j).getProperty().equals("00") && !isDown) {
                j = j - 4;
                isRight = false;
                j = checkHorizontal(blocks, j, isRight, isDown);
            }
            else if(blocks.get(j).getProperty().equals("11") && isDown) {
                j = j + 1;
                isRight = true;
                j = checkHorizontal(blocks, j, isRight, isDown);
            }
            else if(blocks.get(j).getProperty().equals("10") && isDown) {
                j = j - 1;
                isRight = true;
                j = checkHorizontal(blocks, j, isRight, isDown);
            }
            else {
                return j;
            }
        }

    }

    public int checkHorizontal(ArrayList<Block>blocks, int j, boolean isRight, boolean isDown) {
        while(true) {
            if(j > 14) {
              return j;
            }
            else if(blocks.get(j).getProperty().equals("Horizontal")){
                if(blocks.get(j).getType().equals("end")) {
                    return j;
                }
                else if(isRight) {
                    j = j + 1;
                }
                else {
                    j = j - 1;
                }
            }
            else if(blocks.get(j).getProperty().equals("10") && isRight) {
                j = j + 4;
                j = checkVertical(blocks, j, isRight ,isDown);
            }
            else if(blocks.get(j).getProperty().equals("11") && !isRight) {
                j = j + 4;
                j = checkVertical(blocks, j, isRight, isDown);
            }
            else if(blocks.get(j).getProperty().equals("01") && !isRight) {
                j = j - 4;
                j = checkVertical(blocks, j, isRight, isDown);
            }
            else if(blocks.get(j).getProperty().equals("00") && isRight) {
                j = j -4;
                j = checkVertical(blocks, j, isRight, isDown);
            }
            else {
                return j;
            }
        }
    }

}
