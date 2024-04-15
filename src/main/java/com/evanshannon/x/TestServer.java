package com.evanshannon.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TestServer {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean connected = false;
    private Scanner sc;
    private String name;

    public void start(int port) throws IOException {
        sc = new Scanner(System.in);
        System.out.print("Enter name: ");
        name = sc.nextLine();

        serverSocket = new ServerSocket(port);
        clientSocket = serverSocket.accept();

        System.out.println("Connection established!");

        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));


        Thread receive = new Thread(() -> receiveMessages());
        Thread send = new Thread(() -> sendMessages());

        receive.start();
        send.start();
    }
    public void sendMessage(String msg) throws IOException {
        out.println(name+": "+msg);
        System.out.println(name+": "+msg);
    }
    public String readMessage() throws IOException{
        return in.readLine();
    }
    public void receiveMessages() {
        connected = true;
        while(connected){
            try {
                System.out.println(readMessage());
            } catch (IOException e) {
                System.out.println("The server has experienced a critical failure and will not terminate. Goodbye!");
                connected = false;
            }
        }
        try {
            stop();
        } catch (IOException e) {
            System.out.println("The server has experienced a critical failure and will not terminate. Goodbye!");
        }
    }
    public void sendMessages(){
        while(connected){
            //System.out.print("Send Message: ");
            String s = sc.nextLine();

            if(s.equals("quit")){
                connected = false;
            }

            try {
                sendMessage(s);
            } catch (IOException e) {
                System.out.println("The server has experienced a critical failure and will not terminate. Goodbye!");
                connected = false;
            }
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
    public static void main(String[] args) {
        TestServer server = new TestServer();
        try {
            server.start(1490);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
