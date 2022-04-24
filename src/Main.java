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

        // Creating image nodes
        ImageView emptyNone = new ImageView("images/emptyNone.png");
        // ImageView endHorizontal = new ImageView("images/endHorizontal.png");
        // ImageView endVertical = new ImageView("images/endVertical.png");
        // ImageView pipe00 = new ImageView("images/pipe00.png");
        // ImageView pipe01 = new ImageView("images/pipe01.png");
        // ImageView pipe10 = new ImageView("images/pipe10.png");
        // ImageView pipe11 = new ImageView("images/pipe11.png");


 
        Text msg = new Text("Hello World");

        gpane.setGridLinesVisible(true); // make grid lines visible
        Scene scene = new Scene(gpane, 400, 310);
        primaryStage.setScene(scene);

        primaryStage.setTitle("this is a title");
        primaryStage.show();
        
        // creating arrayList from text file. 
        ArrayList<Block> blocks;
        try {
            blocks =  readFile(1);

        } catch (Exception e) {
            System.out.print(e.toString());
            blocks = new ArrayList<>();
        }
        for(int i = 0; i < blocks.size();i++){
            System.out.println(blocks.get(i).getPosition() + blocks.get(i).getType() + blocks.get(i).getImg());
        }
        
    }

    public ArrayList<Block> readFile(int level) throws FileNotFoundException{
        ArrayList<Block> arrayList= new ArrayList<>();
        
        // reading file
        java.io.File file= new java.io.File("src/levels/level"+level +".txt");
        Scanner input = new Scanner(file);

        // creating objects according to level file and add it to arrayList
        int count = 1;
        while(input.hasNextLine()){
            String line = input.nextLine();
            if(line.startsWith("" + count++)){
                String[] properties = line.split(",");
                Block newBlock = new Block(Byte.parseByte(properties[0]),properties[1],properties[2]);
                arrayList.add(newBlock);
            }
        } 

        return arrayList;
    }



}
