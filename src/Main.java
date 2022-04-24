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
        ImageView endHorizontal = new ImageView("images/endHorizontal.png");
        ImageView endVertical = new ImageView("images/endVertical.png");
        ImageView pipe00 = new ImageView("images/pipe00.png");
        ImageView pipe01 = new ImageView("images/pipe01.png");
        ImageView pipe10 = new ImageView("images/pipe10.png");
        ImageView pipe11 = new ImageView("images/pipe11.png");


 
        Text msg = new Text("Hello World");

        gpane.setGridLinesVisible(true); // make grid lines visible
        Scene scene = new Scene(gpane, 400, 310);
        primaryStage.setScene(scene);

        primaryStage.setTitle("this is a title");
        primaryStage.show();
    }

    public ArrayList<Blocks> readFile(int level){
        ArrayList<Blocks> arrayList= new ArrayList<>();
        
        // reading file
        java.io.File file= new java.io.File("levels/level" + level + ".txt");
        Scanner input = new Scanner(file);

        // creating objects according to level file and add it to arrayList
        int count = 1;
        while(input.hasNextLine()){
            String line = input.nextLine();
            if(line.startsWith("" + count)){
                String[] properties = line.split(",");

            }
        } 

        input.close();


        return arrayList;
    }



}
