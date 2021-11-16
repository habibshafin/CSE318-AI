package sample;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainView extends VBox {
    Button timeincrementButton;
    Button view;
    Button catchGhost;
    Button result;
    HBox[] Hboxes;
    Button[][] buttons;
    CatchTheGhost catchTheGhost;
    DropShadow shadow;
    int size;
    boolean isviewClicked = false;
    boolean isCatchClicked = false;
    public MainView(int size){
        this.size = size;
        Hboxes = new HBox[size+1];
        buttons = new Button[size][size];
        shadow = new DropShadow();
        /*for(int i=0; i<size; i++) {
            rowViews[i] = new RowView(size, i);
            this.getChildren().add(rowViews[i]);
        }*/
        catchTheGhost = new CatchTheGhost(size);
        for (int i=0; i<size; i++){
            Hboxes[i] = new HBox();
            for(int j=0; j<size; j++){
                buttons[i][j] = new Button();
                buttons[i][j].setPrefSize(60,40);
                buttons[i][j].setMnemonicParsing(false);
                //buttons[i][j].setStyle("-fx-background-color: #F83535; -fx-border-color: black");
                //buttons[i][j].setStyle("-fx-background-color: #1482EA; -fx-border-color: black");
                //buttons[i][j].setLayoutX(0+j*60);
                //buttons[i][j].setLayoutY(40+i*40);
                buttons[i][j].setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 14;");
                buttons[i][j].setEffect(shadow);
                Position p = new Position(i,j);
                buttons[i][j].setOnMouseClicked(new CustomEventHandler(p) {
                    @Override
                    public void handle(Event event) {
                        System.out.println(p.row+"  "+p.col);
                        if(isCatchClicked){
                            if(catchTheGhost.ghostPosition.row==p.row && catchTheGhost.ghostPosition.col==p.col){
                                System.out.println("Ghost caught");
                                result.setText("Ghost caught");
                                result.setStyle("-fx-background-color: lightgreen;-fx-font-family: 'Bodoni MT'; -fx-font-size: 18; ");

                            }else{
                                System.out.println("Ghost missed");
                                result.setText("Ghost missed");
                                result.setStyle("-fx-background-color: pink;-fx-font-family: 'Bodoni MT'; -fx-font-size: 18; ");
                            }
                            isCatchClicked = false;
                        }else{
                            String sensonReading = catchTheGhost.getSensorReading(p);
                            if(sensonReading.equalsIgnoreCase("Red")){
                                buttons[p.row][p.col].setStyle("-fx-background-color: red");
                            }else if(sensonReading.equalsIgnoreCase("Orange")){
                                buttons[p.row][p.col].setStyle("-fx-background-color: orange");
                            }else{
                                buttons[p.row][p.col].setStyle("-fx-background-color: lightgreen");
                            }
                            catchTheGhost.updateProbabilityAfterSensor(p,sensonReading);
                            if(isviewClicked){
                                for (int i=0; i<size; i++){
                                    for(int j=0; j<size; j++) {
                                        buttons[i][j].setText(String.format("%.04f", catchTheGhost.probabilityTable[i][j]));
                                    }
                                }
                            }
                        }
                    }
                });
                Hboxes[i].getChildren().add(buttons[i][j]);
            }
            this.getChildren().add(Hboxes[i]);
        }
        Hboxes[size] = new HBox();
        view = new Button("View");
        view.setPrefSize(100,40);
        view.setOnAction(this::viewClicked);
        view.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 14;");
        view.setEffect(shadow);
        Hboxes[size].getChildren().add(view);
        timeincrementButton = new Button("Time++");
        timeincrementButton.setPrefSize(100,40);
        timeincrementButton.setOnAction(this::timeIncClicked);
        timeincrementButton.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 14;");
        timeincrementButton.setEffect(shadow);
        Hboxes[size].getChildren().add(timeincrementButton);
        catchGhost = new Button("Catch");
        catchGhost.setPrefSize(100,40);
        catchGhost.setOnAction(this::catchClicked);
        catchGhost.setStyle("-fx-font-family: 'Bodoni MT'; -fx-font-size: 14;");
        catchGhost.setEffect(shadow);
        Hboxes[size].getChildren().add(catchGhost);
        result = new Button();
        result.setPrefSize(200,40);
        result.setEffect(shadow);
        Hboxes[size].getChildren().add(result);
        this.getChildren().add(Hboxes[size]);

    }

    private void catchClicked(ActionEvent actionEvent) {
        isCatchClicked = true;
    }

    private void timeIncClicked(ActionEvent actionEvent) {
        catchTheGhost.TimeIncrement();
        for (int i=0; i<size; i++){
            for(int j=0; j<size; j++) {
                if(isviewClicked)
                    buttons[i][j].setText(String.format("%.04f", catchTheGhost.probabilityTable[i][j]));
                buttons[i][j].setStyle("");
            }
        }
    }

    public void viewClicked(ActionEvent event) {
        if(isviewClicked==false){
            for (int i=0; i<size; i++){
                for(int j=0; j<size; j++) {
                    buttons[i][j].setText(String.format("%.04f", catchTheGhost.probabilityTable[i][j]));
                }
            }
            isviewClicked = true;
            view.setText("Hide");
        }else{
            for (int i=0; i<size; i++){
                for(int j=0; j<size; j++) {
                    buttons[i][j].setText("");
                }
            }
            isviewClicked = false;
            view.setText("View");
        }

    }

}
abstract class CustomEventHandler implements EventHandler<Event>
{
    private Position position;

    public CustomEventHandler(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return position;
    }
}