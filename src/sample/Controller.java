package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Controller {
    public static String reservationString="m";
    public static String cancelReservationString="n";
    @FXML
    public ListView<String> priceList;
    public ListView<String> hourPick;
    public ListView<String> yourReservations;
    public ChoiceBox pickHaircut;
    public Button reservationButton;
    public DatePicker datePick;
    public Label infoLabel;
    public static int flag=0;
    public static int deleteFlag=0;
    public String toDelete="none";




    public void generateListPrices(){

        ObservableList<String> items = FXCollections.observableArrayList("Strzyżenie na glace - 20 zł", "Strzyżenie na garnek - 30 zł", "Strzyżenie męskie - 50 zł", "Strzyżenie włosów i brody - 80 zł",
                "Strzyżenie brody - 40 zł", "Trymowanie wąsa - 20 zł");
        priceList.setItems(items);

        hourPick.getItems().add("10.00");
        hourPick.getItems().add("11.00");
        hourPick.getItems().add("12.00");
        hourPick.getItems().add("13.00");
        hourPick.getItems().add("14.00");
        hourPick.getItems().add("15.00");
        hourPick.getItems().add("16.00");
        hourPick.getItems().add("17.00");
        hourPick.getItems().add("18.00");
        //hourPick.setCellFactory(tv -> new Cell(){
        //});
        for (int i=0;i<priceList.getItems().size();i++){
            pickHaircut.getItems().add(priceList.getItems().get(i));
        }
    }

    public void refresh(){

        Task<Void> task = new Task<Void>() {
            @Override
            public Void call() throws Exception {
                while(true){
                    //updateMessage("...");
                    Thread.sleep(1000);
                    //updateMessage("refreshing...");
                    Thread.sleep(1000);
                    Platform.runLater(() -> {
                        cancelReservation2(toDelete);
                    });

                }

            }
        };
        task.messageProperty().addListener((obs, oldMessage, newMessage) -> infoLabel.setText(newMessage));
        new Thread(task).start();

    }

    public static void start(String name){
        System.out.println("Connecting to server...");
        try {
            Scanner scn = new Scanner(System.in);
            InetAddress ip = InetAddress.getByName("localhost");
            Socket s = new Socket(ip, 5056);
            ServerHandler myRunnable = new ServerHandler(s, scn, name, reservationString);
            Thread t = new Thread(myRunnable);
            t.start();
            /**/
            scn.close();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void sendReservation(){
        String selectedItem = hourPick.getSelectionModel().getSelectedItem();
        LocalDate picker = datePick.getValue();
        String date = String.valueOf(picker);
        String haircut = (String) pickHaircut.getValue();
        yourReservations.getItems().add(date +" "+ selectedItem);
        flag++;
        reservationString = date +" "+ selectedItem;
    }


    public void cancelReservation(){
            String selectedItem = yourReservations.getSelectionModel().getSelectedItem();
            cancelReservationString = "delete " + selectedItem;
            deleteFlag++;
            yourReservations.getItems().remove(selectedItem);
            toDelete="none";
    }

    public void cancelReservation2(String item){
        if (!toDelete.equals("none")){
            infoLabel.setText("This date is already reservated!");
        }
            toDelete = ServerHandler.toDelete;
            yourReservations.getItems().remove(toDelete);
            toDelete = "none";
            ServerHandler.toDelete = "none";

    }

}
