package sample;

import javafx.application.Platform;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class ServerHandler extends Thread {


    public HashMap<String, String> Reservations = new HashMap<String, String>();
    final Socket s;
    final Scanner scn;
    final String name;
    String reservationString;
    String cancelReservationString;
    public static String toDelete;
    int flag=0;
    int deleteFlag=0;

    public ServerHandler(Socket s, Scanner scn, String name, String reservationString){
        this.s = s;
        this.scn = scn;
        this.name = name;
        this.reservationString = Controller.reservationString;
    }

    @Override
    public void run(){
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(s.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintWriter out = null;
        try {
            out = new PrintWriter(new OutputStreamWriter(s.getOutputStream(), StandardCharsets.UTF_8), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {

            this.reservationString=Controller.reservationString;
            this.cancelReservationString=Controller.cancelReservationString;
            try {
                Thread.sleep(1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if(!(deleteFlag==Controller.deleteFlag)) {
                this.cancelReservationString = Controller.cancelReservationString;
                try {
                    ++deleteFlag;
                    out.println(this.cancelReservationString);
                    out.println(name);
                } catch (Exception e) {
                    System.out.println("Can't connect to server...");
                    break;
                }
                String received = null;
                try {
                    received = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (received.equals("already reservated")) {
                    toDelete=reservationString;

                    System.out.println(received+"!");
                } else {
                    System.out.println("Deleted: " + received);
                }
            }

        if(!(flag==Controller.flag)) {
            this.reservationString = Controller.reservationString;
            try {
                ++flag;
                out.println(this.reservationString);
                out.println(name);
            } catch (Exception e) {
                System.out.println("Can't connect to server...");
                break;
            }
            String received = null;
            try {
                received = in.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (received.equals("already reservated")) {
                toDelete=this.reservationString;
                System.out.println(received+"!");
            } else {
                System.out.println("Reserved: " + received);
            }
        }

        }
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        out.close();
    }



}

