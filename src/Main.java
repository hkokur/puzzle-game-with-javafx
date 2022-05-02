import java.io.FileNotFoundException;
import java.util.ArrayList;

import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.Scanner;
import javax.swing.text.Position;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;

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
    	
    	String musicFile= "src/music/music.mp3";
    	Media music = new Media(new File(musicFile).toURI().toString());
    	MediaPlayer mediaPlayer = new MediaPlayer(music);
    	mediaPlayer.play();
    	mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.seek(Duration.ZERO);


		startPane.setOnMouseClicked(e ->  {
            management.prime();
            game(stage);
		});

        stage.setOnCloseRequest(windowEvent -> {
            mediaPlayer.stop();
        });
		
		startPane.getChildren().add(startgif);

		Scene scene = new Scene(startPane,1000,562);
		stage.setTitle("start");
		stage.setScene(scene);
		stage.show();
	}

    public void game(Stage primaryStage){
        ArrayList<Block> blocks = management.getBlocks();

        GridPane gpane = new GridPane();
        // make grid lines visible
        gpane.setGridLinesVisible(true);
        // set V and H gaps 
        gpane.setVgap(5); 
        gpane.setHgap(5);

        gpane.setAlignment(Pos.CENTER);
        
        gpane.setStyle("-fx-background-image: url('background/background.jpg')");
    
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
            gpane.add(image, block.getColumn(), block.getRow());
        }

        System.out.println(gpane.getChildren().get(1).getLayoutBounds());
        
        Button btn = new Button("Start Animation");
        gpane.add(btn, 5, 5);

        ImageView ball = new ImageView("images/ball.png");
        ball.setFitHeight(20);
        ball.setFitWidth(20);

        SequentialTransition seq;
        gpane.add(ball,0,0);
        btn.setOnMouseClicked(e -> {
            animation(152.5, 152.5,ball);
        });

        Scene scene = new Scene(gpane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("this is a title");
        primaryStage.show(); 



    }

    public void animation(double width, double height,ImageView image){

        Block[] blocks = new Block[3];
        findNextBlock(blocks);

        // the status of animation done or not
        boolean done = false;

        SequentialTransition seq = new SequentialTransition();

        // use to set the position of animation
        double relativeCenterX = width/2;
        double relativeCenterY = height/2;

        while(!done){
            // create animation for starter and end pipe.
            if(blocks[0].getType().equals("starter") || blocks[2].getType().equals("end")){
                Block block;
                TranslateTransition tt = new TranslateTransition(Duration.seconds(1), image);

                if ( blocks[0].getType().equals("starter")){
                    block = blocks[0];
                    tt.setInterpolator(Interpolator.EASE_IN);
                }
                else{
                    done = true;
                    block = blocks[2];
                    tt.setInterpolator(Interpolator.EASE_OUT);
                }

                double x = width * block.getColumn();
                double y = height * block.getRow();

                if(block.getProperty().equals("Vertical")){
                    tt.setFromX(x + relativeCenterX);
                    tt.setFromY(y + relativeCenterY);
                    tt.setByX(0);
                    tt.setByY(relativeCenterY);
                }
                else{
                    tt.setFromX(x);
                    tt.setFromY(y + relativeCenterY);
                    tt.setToX(x + relativeCenterX);
                    tt.setFromY(y + relativeCenterY);
                }
                seq.getChildren().add(tt);
            }
            else{
                Block block = blocks[1];

                double x = width * block.getColumn();
                double y = height * block.getRow();

                // create animation by pipe properties
                if (block.getProperty().equals("Vertical")){
                    TranslateTransition tt = new TranslateTransition(Duration.seconds(1), image);
                    tt.setInterpolator(Interpolator.LINEAR);
                    tt.setFromX(x + relativeCenterX/2);
                    tt.setToX(x + relativeCenterX/2);
                    if ( blocks[1].getRow() > blocks[0].getRow() ){
                        tt.setFromY(y);
                        tt.setToY(y + relativeCenterY*2);
                    }
                    else{
                        tt.setFromY(y + relativeCenterY*2);
                        tt.setToY(y);
                    }
                    seq.getChildren().add(tt);
                }
                else if(block.getProperty().equals("Horizontal")){
                    TranslateTransition tt = new TranslateTransition(Duration.seconds(1), image);
                    tt.setInterpolator(Interpolator.LINEAR);
                    tt.setFromY(y + relativeCenterY);
                    tt.setFromY(y + relativeCenterY);
                    if ( blocks[1].getColumn() > blocks[0].getColumn() ){
                        tt.setFromX(x);
                        tt.setToX(x+ relativeCenterX*2);
                    }
                    else{
                        tt.setFromX(x+ relativeCenterX*2);
                        tt.setToX(x);
                    }
                    seq.getChildren().add(tt);
                }
                else if( block.getProperty().equals("00") ){
                    if ( blocks[0].getRow() == blocks[1].getRow() ){
                        Arc arc = new Arc(x , y , relativeCenterX, relativeCenterY, 270, 360);
                        arc.setType(ArcType.OPEN);
                        PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                        pt.setInterpolator(Interpolator.LINEAR);
                        seq.getChildren().add(pt);
                    }
                    else{
                        // burası çalışmayabilir. Arc olarak aynı şeyi oluşturuyor sonuçta. 
                        Arc arc = new Arc(x , y , relativeCenterX, relativeCenterY, 0, -90);
                        arc.setType(ArcType.OPEN);
                        PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                        pt.setInterpolator(Interpolator.LINEAR);
                        seq.getChildren().add(pt);
                    }
                }
                else if(block.getProperty().equals("01")){
                    if ( blocks[0].getColumn() == blocks[1].getColumn() ){
                        Arc arc = new Arc(x + relativeCenterX*2, y , relativeCenterX, relativeCenterY, 180, 270);
                        arc.setType(ArcType.OPEN);
                        PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                        pt.setInterpolator(Interpolator.LINEAR);
                        seq.getChildren().add(pt);
                    }
                    else{
                        // burası çalışmayabilir. Arc olarak aynı şeyi oluşturuyor sonuçta. 
                        Arc arc = new Arc(x + relativeCenterX*2 , y , relativeCenterX, relativeCenterY, -90, -180);
                        arc.setType(ArcType.OPEN);
                        PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                        pt.setInterpolator(Interpolator.LINEAR);
                        seq.getChildren().add(pt);
                    }
                }
                else if(block.getProperty().equals("10")){
                    if ( blocks[0].getColumn() == blocks[1].getColumn() ){
                        Arc arc = new Arc(x , y + relativeCenterY*2,  relativeCenterX, relativeCenterY, 0, 90);
                        arc.setType(ArcType.OPEN);
                        PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                        pt.setInterpolator(Interpolator.LINEAR);
                        seq.getChildren().add(pt);
                    }
                    else{
                        // burası çalışmayabilir. Arc olarak aynı şeyi oluşturuyor sonuçta. 
                        Arc arc = new Arc(x , y + relativeCenterY*2 , relativeCenterX, relativeCenterY, -270, 0);
                        arc.setType(ArcType.OPEN);
                        PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                        pt.setInterpolator(Interpolator.LINEAR);
                        seq.getChildren().add(pt);
                    }
                }
                else if(block.getProperty().equals("11")){
                    if ( blocks[0].getColumn() == blocks[1].getColumn() ){
                        Arc arc = new Arc(x + relativeCenterX*2 , y + relativeCenterY*2,  relativeCenterX, relativeCenterY, 90, 180);
                        arc.setType(ArcType.OPEN);
                        PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                        pt.setInterpolator(Interpolator.LINEAR);
                        seq.getChildren().add(pt);
                    }
                    else{
                        // burası çalışmayabilir. Arc olarak aynı şeyi oluşturuyor sonuçta. 
                        Arc arc = new Arc(x + relativeCenterX*2 , y + relativeCenterY*2 , relativeCenterX, relativeCenterY, -180, -90);
                        arc.setType(ArcType.OPEN);
                        PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                        pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                        pt.setInterpolator(Interpolator.LINEAR);
                        seq.getChildren().add(pt);
                    }
                }
            }
            findNextBlock(blocks);
        }
        seq.play();
    }

    public void findNextBlock(Block[] blocks){
        Block block0 = blocks[0];
        Block block1 = blocks[1];
        Block block2 = blocks[2];

        // assign block0 if it is null
        if (block0 == null){
            for(int i = 0 ; i < management.getBlocks().size() ; i++){
                if (management.getBlocks().get(i).getType().equals("starter")){
                    block0 = management.getBlocks().get(i);
                }
            }
        }   
        
        // assign block1 by block0 if it is null
        if (block1 == null){
            if (block0.getProperty().equals("Vertical"))
                block1 = findBlockByCoordinate(block0.getColumn(), block0.getRow() + 1);
            else if (block0.getProperty().equals("Horizontal"))
                block1 = findBlockByCoordinate(block0.getColumn() + 1, block0.getRow());
        }

        // update blocks with new blocks
        if (blocks[0] == null && blocks[1] ==null){
            blocks[0] = block0;
            blocks[1] = block1;
        }
        else{
            blocks[0] = block1;
            blocks[1] = block2;
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
        blocks[2] = block2;
    }

    public Block findBlockByCoordinate(int col, int row){
        for(Block block : management.getBlocks()){
            if (block.getColumn() == col && block.getRow() == row){
                return block;
            }
        }
        return null;
    }
}
