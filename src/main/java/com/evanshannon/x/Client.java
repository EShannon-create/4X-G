package com.evanshannon.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        Thread thread = new Thread(() -> start());
        thread.start();
    }
    private void start(){
        String message = "";
        while(!message.equals("quit")){
            try {
                message = in.readLine();
                MessageParser.parse(message);
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }

        receiveMessages();
    }
    private void receiveMessages(){
        while(X.running) try{
            String message = in.readLine();
            String[] splitMessage = message.split(" ");
            String[] args = splitMessage.length > 1 ? Arrays.copyOfRange(splitMessage,1,splitMessage.length) : new String[]{};
            String command = splitMessage[0];
            MessageParser.parse(command,args);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        stop();
    }
    public void broadcast(String message){
        out.println(message);
    }
    public void stop(){
        try {
            in.close();
            out.close();
            clientSocket.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
