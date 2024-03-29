import java.util.ArrayList;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.HLineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.VLineTo;

import java.io.File;

public class Main extends Application{
    // class to manage levels and drag events
    private Management management = new Management();
    public static void main(String[] args) {
        // Launch the JavaFX application
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) {
		
		StackPane startPane = new StackPane();
		ImageView startgif = new ImageView(new Image("background/btngif.gif"));
		
		startgif.fitHeightProperty().bind(startPane.heightProperty());
    	startgif.fitWidthProperty().bind(startPane.widthProperty());

		startPane.setOnMouseClicked(e ->  {
            management.prime();
            game(stage);
		});
		
		startPane.getChildren().add(startgif);

		Scene scene = new Scene(startPane,1000,562);
		stage.setTitle("start");
		stage.setScene(scene);
		stage.show();
	}

    public void game(Stage primaryStage){
        ArrayList<Block> blocks = management.getBlocks();

        Pane pane = new Pane();

        // add image nodes to gpane by position property
        for(int i = 0; i < blocks.size(); i++){
            Block block = blocks.get(i);
            ImageView image = new ImageView(block.getImg());
            image.setOnDragDetected(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e){
                    management.setFirstBlock(block);            
                }
            });
            image.setOnMouseEntered(new EventHandler<MouseEvent>(){
                public void handle(MouseEvent e){
                    if (management.getFirstBlock() != null){
                        management.setSecondBlock(block);
                        if (management.switchBlocks())
                            game(primaryStage);
                    }
                }
            });
            image.setLayoutX(block.getColumn()*150);
            image.setLayoutY(block.getRow()*150); 
            pane.getChildren().add(image);
        }
        pane.setPrefSize(600, 600);

        Button btn = new Button("Next Level");
        btn.setStyle("-fx-background-color: #541657; -fx-background-insets: 0,1,2,3; -fx-background-radius: 3,2,2,2; -fx-padding: 12 30 12 30; -fx-text-fill: white; -fx-font-size: 12px;");
        btn.setLayoutX(250);
        btn.setLayoutY(650);

        ImageView ball = new ImageView("images/ball.png");
        ball.setFitHeight(25);
        ball.setFitWidth(25);


        Block starter;
        int x = 0;
        int y = 0;
        for(Block block: management.getBlocks()){
            if ( block.getType().equals("starter")){
                starter = block;
                x = starter.getColumn()*150;
                y = starter.getRow()*150;
            }
        }
        ball.setLayoutX(x + 65);
        ball.setLayoutY(y + 65);

        pane.getChildren().add(ball);

        HBox hBox = new HBox();

        Scene scene = new Scene(hBox);

        // set pane aligment
        HBox.setMargin(pane,new Insets((primaryStage.getHeight()-600)/2,(primaryStage.getWidth()-600)/2, (primaryStage.getHeight()-600)/2, (primaryStage.getWidth()-600)/2));
        hBox.getChildren().addAll(pane);

        hBox.setStyle("-fx-background-image: url('background/background.jpg');");

        primaryStage.setScene(scene);
        primaryStage.setTitle("game");
        primaryStage.show(); 

        if(management.checkPath(blocks)) {
            pane.getChildren().add(btn);
            PathTransition pt = animation(ball, 150, 150);
            pt.play();
        }
        btn.setOnMouseClicked(e -> {
            management.setLevel();
            management.prime();
            game(primaryStage);
        });

        
    }

    public PathTransition animation(Node ball,double width, double height){ 
        Block[] blocks = new Block[3];
        management.findNextBlock(blocks);

        Path path = new Path();
        // postpone path because path will be draw relatively
        path.setLayoutX(-ball.getBoundsInParent().getMinX());
        path.setLayoutY(-ball.getBoundsInParent().getMinY());

        // set starter point 
        path.getElements().add(new MoveTo(ball.getBoundsInParent().getMinX()+ 12.5 ,ball.getBoundsInParent().getMinY()+ 12.5));

        while(true){
            double relativeX;
            double relativeY ;

            if(blocks[0].getType().equals("starter")){
                relativeX = blocks[0].getColumn()* 150;
                relativeY = blocks[0].getRow() * 150;

                if( blocks[0].getProperty().equals("Vertical"))
                    path.getElements().add(new VLineTo(relativeY + height));
                else
                    path.getElements().add(new HLineTo(relativeX));
            }

            relativeX = blocks[1].getColumn() * 150;
            relativeY = blocks[1].getRow() * 150;
            String property = blocks[1].getProperty();

            if ( property.equals("Horizontal")){
                if ( blocks[0].getColumn() < blocks[1].getColumn())
                    path.getElements().add(new HLineTo(relativeX + width));
                else
                    path.getElements().add(new HLineTo(relativeX));
            }
            else if (property.equals("Vertical")){
                if (blocks[0].getRow() < blocks[1].getRow() )
                    path.getElements().add(new VLineTo(relativeY + height));
                else 
                    path.getElements().add(new VLineTo(relativeY));
            }
            else if ( property.equals("00") || property.equals("01") || property.equals("10") || property.equals("11")){
                ArcTo arcTo = new ArcTo();
                arcTo.setRadiusX(width/2);
                arcTo.setRadiusY(height/2);
                arcTo.setLargeArcFlag(false);

                switch(property){
                    case "00":
                        if (blocks[0].getColumn() == blocks[1].getColumn()){
                            arcTo.setX(relativeX);
                            arcTo.setY(relativeY + height/2);
                            arcTo.setSweepFlag(true);
                        }
                        else{
                            arcTo.setX(relativeX + width/2);
                            arcTo.setY(relativeY);
                            arcTo.setSweepFlag(false);
                        }
                        break;
                    case "01":
                        if ( blocks[0].getColumn() == blocks[1].getColumn() ){
                            arcTo.setSweepFlag(false);
                            arcTo.setX(relativeX + width);
                            arcTo.setY(relativeY + height/2);
                        }
                        else{
                            arcTo.setSweepFlag(true);
                            arcTo.setX(relativeX + width/2);
                            arcTo.setY(relativeY);
                        }
                        break;
                    case "10":
                        if (blocks[0].getRow() == blocks[1].getRow()){
                            arcTo.setSweepFlag(true);
                            arcTo.setX(relativeX + width/2);
                            arcTo.setY(relativeY + height);
                        }
                        else{
                            arcTo.setSweepFlag(false);
                            arcTo.setX(relativeX);
                            arcTo.setY(relativeY + width/2);
                        }
                        break;
                    case "11":
                        if ( blocks[0].getColumn() == blocks[1].getColumn()){
                            arcTo.setSweepFlag(true);
                            arcTo.setX(relativeX + width);
                            arcTo.setY(relativeY + height/2);
                        }
                        else{
                            arcTo.setSweepFlag(false);
                            arcTo.setX(relativeX + width/2);
                            arcTo.setY(relativeY + height);
                        }
                        break;
                }
                path.getElements().add(arcTo);
            }

            if ( blocks[2].getType().equals("end")){
                relativeX = blocks[2].getColumn() * 150;
                relativeY = blocks[2].getRow() * 150;

                if (blocks[2].getProperty().equals("Vertical")){
                    path.getElements().add(new VLineTo(relativeY + height/2));
                }
                else{
                    path.getElements().add(new HLineTo(relativeX + width/2));
                }
                break;
            }
            management.findNextBlock(blocks);
        }

        // creating animation part
        PathTransition pt = new PathTransition(Duration.seconds(5),path, ball);
        return pt;
    }
}
