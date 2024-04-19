package com.evanshannon.x.model;

import com.evanshannon.x.model.pieces.Piece;

public interface PieceContainer {
    boolean addPiece(Piece piece);
    boolean removePiece(Piece piece);
    boolean isFull();
    int countPieces();
}
