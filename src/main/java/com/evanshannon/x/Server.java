package com.evanshannon.x;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class Server {
    private ServerSocket serverSocket;
    private ServerConnection[] connections;
    private static final int CAPACITY = 5;

    private String[] color = new String[6];
    private int connected = 1;//The player running the server is connected, of course!
    private int turnsEnded = 0;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        connections = new ServerConnection[CAPACITY];

        color[5] = X.COLOR;

        for(int i = 0; i < CAPACITY; i++){
            final int index = i;
            Thread thread = new Thread(() -> accept(index));
            thread.start();
        }
    }
    private void accept(int index){
        try {
            connections[index] = new ServerConnection(serverSocket.accept());
            IO.print("Connection established at index " + (index) + "!");
            connected++;
            connections[index].start();
        }
        catch(SocketException e){
            //Do nothing
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
    public void broadcast(String message){
        for(ServerConnection connection : connections){
            if(connection == null) continue;

            connection.sendMessage(message);
        }
    }
    public void relay(String message, ServerConnection connection){
        if(message.equals("end"));
        for(ServerConnection c : connections){
            if(connection == null || connection == c) continue;

            connection.sendMessage(message);
        }
    }
    public void stop(){
        try {
            for(ServerConnection c : connections){
                if(c != null) c.stop();
            }
            serverSocket.close();
            connected--;
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
    public void restart(ServerConnection c){
        for(int i = 0; i < connections.length; i++){
            if(connections[i] == c){
                this.color[i] = null;
                accept(i);
                return;
            }
        }
    }
    public boolean claim(String color, ServerConnection connection){
        for(String c : this.color){
            if(c != null && c.equals(color)) return false;
        }

        for(int i = 0; i < connections.length; i++){
            if(connections[i] == connection) {
                this.color[i] = color;
                return true;
            }
        }

        return false;
    }
    public void endTurn(){
        IO.print("End turn called! Connected: " + connected +  " Turns Ended: " + turnsEnded);
        turnsEnded++;
        IO.print("Connected: " + connected +  " Turns Ended: " + turnsEnded);
        if(turnsEnded == connected){
            IO.print("New turn! Connected: " + connected +  " Turns Ended: " + turnsEnded);
            broadcast("turn");
            X.getInstance().newTurn();
            turnsEnded = 0;
            IO.print("Connected: " + connected +  " Turns Ended: " + turnsEnded);
        }
    }
}
