## Animation Witdh Part by Parth

    public void animation(double width, double height,ImageView image){
        Block[] blocks = new Block[3];
        findNextBlock(blocks);

        SequentialTransition seq = new SequentialTransition();
        seq.setAutoReverse(true);
        seq.setCycleCount(SequentialTransition.INDEFINITE);

        // use to set the position of animation
        double relativeCenterX = width/2;
        double relativeCenterY = height/2;

        while(true){
            // create animation for starter and end pipe.
            if(blocks[0].getType().equals("starter")){
                Block block = blocks[0];
                TranslateTransition tt = new TranslateTransition(Duration.seconds(1), image);
                tt.setInterpolator(Interpolator.EASE_IN);
                double x = width * block.getColumn();
                double y = height * block.getRow();

                if(block.getProperty().equals("Vertical")){
                    tt.setFromX(pointConverter(x + relativeCenterX));
                    tt.setToX(pointConverter(x + relativeCenterX));
                    tt.setFromY(pointConverter(y + relativeCenterY));
                    tt.setToY(pointConverter(y + relativeCenterY*2));
                }
                else{
                    tt.setFromX(pointConverter(x + relativeCenterX));
                    tt.setToX(pointConverter(x + relativeCenterX*2));
                    tt.setFromY(pointConverter(y + relativeCenterY));
                    tt.setToY(pointConverter(y + relativeCenterY));
                }
                seq.getChildren().add(tt);
            }
            
            Block block = blocks[1];
            double x = width * block.getColumn();
            double y = height * block.getRow();
            // create animation by pipe properties
            if (block.getProperty().equals("Vertical")){
                TranslateTransition tt = new TranslateTransition(Duration.seconds(1), image);
                tt.setInterpolator(Interpolator.LINEAR);
                tt.setFromX(pointConverter(x + relativeCenterX));
                tt.setToX(pointConverter(x + relativeCenterX));
                if ( blocks[1].getRow() > blocks[0].getRow() ){
                    tt.setFromY(pointConverter(y));
                    tt.setToY(pointConverter(y + relativeCenterY*2));
                }
                else{
                    tt.setFromY(pointConverter(y + relativeCenterY*2));
                    tt.setToY(pointConverter(y));
                }
                seq.getChildren().add(tt);
            }
            else if(block.getProperty().equals("Horizontal")){
                TranslateTransition tt = new TranslateTransition(Duration.seconds(1), image);
                tt.setInterpolator(Interpolator.LINEAR);
                tt.setFromY(pointConverter(y + relativeCenterY));
                tt.setFromY(pointConverter(y + relativeCenterY));
                if ( blocks[1].getColumn() > blocks[0].getColumn() ){
                    tt.setFromX(pointConverter(x));
                    tt.setToX(pointConverter(x+ relativeCenterX*2));
                }
                else{
                    tt.setFromX(pointConverter(x+ relativeCenterX*2));
                    tt.setToX(pointConverter(x));
                }
                seq.getChildren().add(tt);
            }
            else if( block.getProperty().equals("00") ){
                if ( blocks[0].getRow() == blocks[1].getRow() ){
                    Arc arc = new Arc(pointConverter(x) , pointConverter(y) , relativeCenterX, relativeCenterY, 270, 90);
                    arc.setType(ArcType.OPEN);
                    PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                    pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                    pt.setInterpolator(Interpolator.LINEAR);
                    seq.getChildren().add(pt);
                }
                else{
                    // burası çalışmayabilir. Arc olarak aynı şeyi oluşturuyor sonuçta. 
                    Arc arc = new Arc(pointConverter(x) , pointConverter(y) , relativeCenterX, relativeCenterY, 0, -90);
                    arc.setType(ArcType.OPEN);
                    PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                    pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                    pt.setInterpolator(Interpolator.LINEAR);
                    seq.getChildren().add(pt);
                }
            }
            else if(block.getProperty().equals("01")){
                if ( blocks[0].getColumn() == blocks[1].getColumn() ){
                    Arc arc = new Arc(pointConverter(x + relativeCenterX*2), pointConverter(y) , relativeCenterX , relativeCenterY, 180, 90);
                    arc.setType(ArcType.OPEN);
                    PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                    pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                    pt.setInterpolator(Interpolator.LINEAR);
                    seq.getChildren().add(pt);
                }
                else{
                    // burası çalışmayabilir. Arc olarak aynı şeyi oluşturuyor sonuçta. 
                    Arc arc = new Arc(pointConverter(x) + pointConverter(relativeCenterX*2), pointConverter(y) , relativeCenterX, relativeCenterY, -90, -90);
                    arc.setType(ArcType.OPEN);
                    PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                    pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                    pt.setInterpolator(Interpolator.LINEAR);
                    seq.getChildren().add(pt);
                }
            }
            else if(block.getProperty().equals("10")){
                if ( blocks[0].getColumn() == blocks[1].getColumn() ){
                    Arc arc = new Arc(pointConverter(x) , pointConverter(y + relativeCenterY*2),  relativeCenterX, relativeCenterY, 0, 90);
                    arc.setType(ArcType.OPEN);
                    PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                    pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                    pt.setInterpolator(Interpolator.LINEAR);
                    seq.getChildren().add(pt);
                }
                else{
                    // burası çalışmayabilir. Arc olarak aynı şeyi oluşturuyor sonuçta. 
                    Arc arc = new Arc(pointConverter(x) , pointConverter(y + relativeCenterY*2) , relativeCenterX, relativeCenterY, -270, -90);
                    arc.setType(ArcType.OPEN);
                    PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                    pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                    pt.setInterpolator(Interpolator.LINEAR);
                    seq.getChildren().add(pt);
                }
            }
            else if(block.getProperty().equals("11")){
                if ( blocks[0].getColumn() == blocks[1].getColumn() ){
                    Arc arc = new Arc(pointConverter(x + relativeCenterX*2) , pointConverter(y + relativeCenterY*2),  relativeCenterX, relativeCenterY, 90, 90);
                    arc.setType(ArcType.OPEN);
                    PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                    pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                    pt.setInterpolator(Interpolator.LINEAR);
                    seq.getChildren().add(pt);
                }
                else{
                    // burası çalışmayabilir. Arc olarak aynı şeyi oluşturuyor sonuçta. 
                    Arc arc = new Arc(pointConverter(x + relativeCenterX*2) , pointConverter(y + relativeCenterY*2) , relativeCenterX, relativeCenterY, -180, -90);
                    arc.setType(ArcType.OPEN);
                    PathTransition pt = new PathTransition(Duration.seconds(1), arc, image);
                    pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                    pt.setInterpolator(Interpolator.LINEAR);
                    seq.getChildren().add(pt);
                }
            }

            if (blocks[2].getType().equals("end")){
                block = blocks[2];
                TranslateTransition tt = new TranslateTransition(Duration.seconds(1), image);
                tt.setInterpolator(Interpolator.EASE_OUT);

                x = width * block.getColumn();
                y = height * block.getRow();

                if(block.getProperty().equals("Vertical")){
                    tt.setFromX(pointConverter(x + relativeCenterX));
                    tt.setToX(pointConverter(x + relativeCenterX));
                    tt.setFromY(pointConverter(y + relativeCenterY*2));
                    tt.setToY(pointConverter(y + relativeCenterY));
                }
                else{
                    tt.setFromX(pointConverter(x));
                    tt.setToX(pointConverter(x + relativeCenterX));
                    tt.setFromY(pointConverter(y + relativeCenterY));
                    tt.setToY(pointConverter(y + relativeCenterY));
                }
                seq.getChildren().add(tt);
                break;
            }
            findNextBlock(blocks);
        }
        seq.play();
    }
