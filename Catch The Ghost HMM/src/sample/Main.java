package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        int size =15;
        //Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        MainView mainView = new MainView(size);
        //MainViewFinal mainView = new MainViewFinal(size);
        primaryStage.setTitle("Hello World");
        double shape = 40 * (size);
        primaryStage.setScene(new Scene(mainView,60*size, shape+40));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
