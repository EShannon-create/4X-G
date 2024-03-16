package com.evanshannon.x.model.pieces;

import com.evanshannon.x.model.Player;
import com.jme3.scene.Node;

public class Admiral extends SeaPiece implements Commander{
    public Admiral(Player player) {
        super(player);
    }

    @Override
    public Node getModel() {
        return null;
    }

    @Override
    public int[][] moveMap() {
        return new int[][]{
                {GOTO,GOTO,GOTO,GOTO,GOTO},
                {GOTO,GOTO,GOTO,GOTO,GOTO},
                {GOTO,GOTO,NONE,GOTO,GOTO},
                {GOTO,GOTO,GOTO,GOTO,GOTO},
                {GOTO,GOTO,GOTO,GOTO,GOTO}
        };
    }

    @Override
    public Piece[] getConnected() {
        return new Piece[0];
    }

    @Override
    public int countConnected() {
        return 0;
    }

    @Override
    public boolean register(Piece piece) {
        return false;
    }

    @Override
    public boolean remove(Piece piece) {
        return false;
    }

    @Override
    public boolean inBounds(int x, int y) {
        return false;
    }

    @Override
    public void updateBounds() {

    }
}
