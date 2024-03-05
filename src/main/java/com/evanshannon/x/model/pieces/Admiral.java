package com.evanshannon.x.model.pieces;

import com.jme3.scene.Node;

public class Admiral extends SeaPiece implements Commander{
    @Override
    public Node getModel() {
        return null;
    }

    @Override
    public int[][] canMove() {
        return new int[][]{
                {GOTO,GOTO,GOTO,GOTO,GOTO},
                {GOTO,GOTO,GOTO,GOTO,GOTO},
                {GOTO,GOTO,NONE,GOTO,GOTO},
                {GOTO,GOTO,GOTO,GOTO,GOTO},
                {GOTO,GOTO,GOTO,GOTO,GOTO}
        };
    }
}
