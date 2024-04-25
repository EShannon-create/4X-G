package com.evanshannon.x;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

public class Server {
    private ServerSocket serverSocket;
    private ServerConnection[] connections;
    private Thread[] cThreads;
    private static final int CAPACITY = 6;

    public void start(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        connections = new ServerConnection[CAPACITY];

        for(int i = 0; i < CAPACITY; i++){
            final int index = i;
            Thread thread = new Thread(() -> accept(index));
            thread.start();
        }
    }
    private void accept(int index){
        try {
            connections[index] = new ServerConnection(serverSocket.accept());
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
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
