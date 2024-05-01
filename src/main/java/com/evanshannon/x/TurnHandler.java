package com.evanshannon.x;

import com.evanshannon.x.model.Player;

public class TurnHandler {
    private int turn;
    Player[] players;
    private int playerIndex;
    boolean turnEnd = false;

    public TurnHandler(Player... players){
        this.players = players;
        turn = 1;
        playerIndex = 0;
    }

    public String getTurn(){
        return turn + (turnEnd ? " (Ended)" : "");
    }
    public void endTurn(){
        final boolean was = turnEnd;
        turnEnd = true;
        if(X.CLIENT != null && !was) X.CLIENT.broadcast("end");
        else if(X.SERVER != null && !was) X.SERVER.endTurn();
    }
    private void next(){
        playerIndex++;
        if(playerIndex >= players.length){
            playerIndex = 0;
            turn++;
        }
    }
    public Player getPOV(){
        return players[playerIndex];
    }
    public void setPOV(String color){
        playerIndex = switch(color){
            default -> -1;
            case "Yellow" -> 0;
            case "Red" -> 1;
            case "Blue" -> 2;
            case "Green" -> 3;
            case "Magenta" -> 4;
            case "Cyan" -> 5;
        };
    }
    public Player[] getPlayers(){
        return players;
    }
    public void newTurn(){
        IO.print("New turn!");
        turnEnd = false;
        for(int i = 0; i < players.length; i++){
            next();
        }
        getPOV().beginTurn();
        IO.print("Turn End: " + turnEnd);
    }
}
