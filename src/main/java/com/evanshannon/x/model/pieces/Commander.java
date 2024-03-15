package com.evanshannon.x.model.pieces;

public interface Commander{

    int MAX_CONNECTED = 15;
    Piece[] getConnected();
    int countConnected();
    boolean register(Piece piece);
    boolean remove(Piece piece);
    boolean inBounds(int x, int y);
    void updateBounds();
}
