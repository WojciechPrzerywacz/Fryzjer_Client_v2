package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Main extends Application {

    public static String name;
    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("sample.fxml"));
        Parent root = loader.load();
        Controller myController = loader.getController();

        primaryStage.setTitle("Pan Elegant");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("hairdressing.png")));
        primaryStage.setScene(new Scene(root, 800, 550));
        primaryStage.show();
        myController.generateListPrices();
        //Controller.start();
        myController.refresh();
        myController.start(name);

    }


    public static void main(String[] args) {
        name=args[0];
        launch(args);
    }


}
