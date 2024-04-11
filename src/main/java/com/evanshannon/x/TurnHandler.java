package com.evanshannon.x;

import com.evanshannon.x.model.Player;

public class TurnHandler {
    private int turn;
    Player[] players;
    private int playerIndex;

    public TurnHandler(Player... players){
        this.players = players;
        turn = 1;
        playerIndex = 0;
    }

    public int getTurn(){
        return turn;
    }
    public void endTurn(){
        getPOV().endTurn();
        do{
            next();
        } while(!getPOV().beginTurn());
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
    public Player[] getPlayers(){
        return players;
    }
}
