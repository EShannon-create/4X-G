package com.evanshannon.x.model;

public class Octave {
    private Perlin[] perlins;
    private static final int perlinCount = 8;
    public Octave(){
        perlins = new Perlin[perlinCount];
        for(int i = 0; i < perlinCount; i++){
            perlins[i] = new Perlin();
        }
    }

    public float at(float x, float y){
        final float scale = 0.05f;

        int by = 1;
        float val = 0f;
        for(int i = 0; i < perlinCount; i++){
            val += perlins[i].at(x*scale*by,y*scale*by)/by;
            by *= 2;
        }

        return val/(2f-1f/perlinCount);
    }
}
