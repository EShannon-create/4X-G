package com.evanshannon.x;

import com.evanshannon.x.model.pieces.Piece;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public boolean startConnection(String ip, int port) throws IOException {
        IO.print("Attempting to connect to " + ip + "!");
        clientSocket = new Socket(ip, port);
        IO.print("Connected successfully!");
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        out.println(X.COLOR);
        if(in.readLine().equals("reject")) return false;

        Thread thread = new Thread(() -> start());
        thread.start();
        return true;
    }
    private void start(){
        X.SUSPEND_COMMANDER_CHECKS = true;
        Queue<String> messages = new LinkedList<>();
        String message = null;
        try {
            message = in.readLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        while(!message.equals("quit")){
            try {
                IO.print("Message: " + message);
                messages.add(message);
                message = in.readLine();
            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
        MessageParser.parse(messages.poll());

        while(!X.running){
            //Busy waiting like a boss, networking has kind of been a slap job on this project I'm sorry to admit
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.print(" ");
        }

        IO.print("Executing...");

        while(!messages.isEmpty()){
            MessageParser.parse(messages.poll());
        }

        X.getInstance().updatePossessions();

        X.SUSPEND_COMMANDER_CHECKS = false;
        Piece.findCommanders();

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
