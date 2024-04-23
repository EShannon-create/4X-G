package com.evanshannon.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port) throws IOException {
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
    private void receiveMessages(){

    }
    private void sendMessages(){

    }
}
