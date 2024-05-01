package com.evanshannon.x;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;

public class ServerConnection {
    private PrintWriter out;
    private BufferedReader in;
    Socket clientSocket;

    public ServerConnection(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void start(){
        try {
            String color = in.readLine();
            if(X.SERVER.claim(color,this)) out.println("accept");
            else{
                out.println("reject");
                stop();
                X.SERVER.restart(this);
            }
        }
        catch(IOException e){
            out.println("reject");
        }

        out.println(X.getInstance().getInitString());

        receiveMessages();
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
    private void receiveMessages(){
        while(X.running) try{
            String message = in.readLine();
            if(message == null) continue;
            String[] splitMessage = message.split(" ");
            String[] args = Arrays.copyOfRange(splitMessage,1,splitMessage.length);
            String command = splitMessage[0];
            MessageParser.parse(command,args);

            X.SERVER.relay(message,this);
        }
        catch(IOException e){
            e.printStackTrace();
        }
        stop();
    }
    protected void sendMessage(String message){
        out.println(message);
    }
}
