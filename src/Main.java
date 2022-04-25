import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.util.Scanner;

public class Main extends Application{

    public static void main(String[] args) {
        // Launch the JavaFX application
        Application.launch(args);
    }


    @Override
    public void start(Stage primaryStage){
        GridPane gpane = new GridPane();
        gpane.setGridLinesVisible(true); // make grid lines visible
        gpane.setVgap(5);
        gpane.setHgap(5);
        gpane.setStyle(" -fx-background-color: black;");

        // creating arrayList from text file. 
        ArrayList<Block> blocks;
        try {
            blocks =  readFile(1);

        } catch (Exception e) {
            System.out.print(e.toString());
            blocks = new ArrayList<>();
        }

        // adding image nodes to gpane by position property
        for(int i = 0; i < blocks.size(); i++){
            Block block = blocks.get(i);
            if(block.getImg() != null){
                ImageView image = new ImageView(block.getImg());
                gpane.add(image, block.getColumn(), block.getRow());
            }

        }

        Scene scene = new Scene(gpane, 400, 310);
        primaryStage.setScene(scene);

        primaryStage.setTitle("this is a title");
        primaryStage.show();
        
    }

    public ArrayList<Block> readFile(int level) throws FileNotFoundException{
        ArrayList<Block> arrayList= new ArrayList<>();
        
        // reading file
        java.io.File file= new java.io.File("src/levels/level"+level +".txt");
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

        return arrayList;
    }



}
