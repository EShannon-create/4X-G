package com.evanshannon.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class TestClient {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connected = false;
    private Scanner sc = new Scanner(System.in);
    private String name;

    public void startConnection(String ip, int port) throws IOException {
        clientSocket = new Socket(ip, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(String msg) throws IOException {
        out.println(name+": "+msg);
        System.out.println(name+": "+msg);
    }
    public String readMessage() throws IOException{
        return in.readLine();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
    public static void main(String[] args){
        TestClient testClient = new TestClient();

        testClient.name = testClient.getName();
        String ip = testClient.getIP();

        try{
            testClient.startConnection(ip,1490);
            System.out.println("Connection established!");
        }
        catch(IOException e){
            System.out.println("There was a problem in receiving the connection. Goodbye!");
            return;
        }
        Thread receive = new Thread(testClient::receiveMessages);
        Thread send = new Thread(() -> testClient.sendMessages(testClient.sc));

        receive.start();
        send.start();
    }
    public String getIP(){
        System.out.print("Enter IP: ");
        return sc.nextLine();
    }
    public String getName(){
        System.out.print("Enter name: ");
        return sc.nextLine();
    }
    public void receiveMessages() {
        connected = true;
        while(connected){
            try {
                System.out.println(readMessage());
            } catch (IOException e) {
                System.out.println("The client has experienced a critical failure and will not terminate. Goodbye!");
                connected = false;
            }
        }
        try {
            stopConnection();
        } catch (IOException e) {
            System.out.println("The client has experienced a critical failure and will not terminate. Goodbye!");
        }
    }
    public void sendMessages(Scanner sc){
        while(connected){
            //System.out.print("Send Message: ");
            String s = sc.nextLine();

            if(s.equals("quit")){
                connected = false;
            }

            try {
                sendMessage(s);
            } catch (IOException e) {
                System.out.println("The client has experienced a critical failure and will not terminate. Goodbye!");
                connected = false;
            }
        }
    }
}
