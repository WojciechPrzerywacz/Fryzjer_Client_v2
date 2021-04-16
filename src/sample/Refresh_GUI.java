package sample;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class Refresh_GUI extends Thread {
    Controller obj;

    @FXML
    public ListView<String> priceList;
    public ListView<String> hourPick;
    public ChoiceBox pickHaircut;
    public Button reservationButton;
    public DatePicker datePick;
    public Label infoLabel;

    public Refresh_GUI(Controller s){
        obj = s;
    }

    @Override
    public void run(){
        while(true){
            try {
                //Odswiezaj rzeczy z GUI *WYłącznie*
                Thread.sleep(1000);
                obj.infoLabel.setText("");
                //obj.infoLabel.setText("refreshing...");
                //obj.UpdateTree(obj.get_main_node(), obj.get_main_file());
                //obj.refresh_tree();
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}