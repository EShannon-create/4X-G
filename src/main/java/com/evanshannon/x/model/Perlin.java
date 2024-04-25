package com.evanshannon.x.model;

import com.evanshannon.x.MathLib;
import com.evanshannon.x.X;

import java.util.Random;

public class Perlin {
    private static final int[] p = new int[512];

    public Perlin(){
        Random r = new Random(X.seed);
        for(int i = 0; i < 256; i++){
            p[i] = i;
        }
        for(int i = 0; i < 256; i++){
            int x = r.nextInt(0,256);
            int save = p[i];
            p[i] = p[x];
            p[i+256] = p[x];
            p[x] = save;
            p[x+256] = save;
        }
    }

    public float at(float x, float y){
        final float nx = MathLib.mod(x,1.0f);
        final float ny = MathLib.mod(y,1.0f);

        final int ix = (int)x & 255;
        final int iy = (int)y & 255;

        final float u = fade(nx);
        final float v = fade(ny);

        final int aa = p[p[ix]+iy];
        final int ab = p[p[ix+1]+iy];
        final int ba = p[p[ix]+iy+1];
        final int bb = p[p[ix+1]+iy+1];

        final float x1 = lerp(grad(aa,nx,ny),grad(ab,nx-1,ny),u);
        final float x2 = lerp(grad(ba,nx,ny-1),grad(bb,nx-1,ny-1),u);

        return lerp(x1,x2,v);
    }
    private float fade(float val){
        return val*val*val*(val*(val*6-15)+10);
    }
    private float grad(int hash, float x, float y){
        switch(hash & 7){
            case 0: return x+y;
            case 1: return x-y;
            case 2: return -x+y;
            case 3: return -x-y;
            case 4: return x;
            case 5: return y;
            case 6: return -x;
            case 7: return -y;
            default: return 0;
        }
    }
    private float lerp(float a, float b, float x){
        return a+x*(b-a);
    }
}
