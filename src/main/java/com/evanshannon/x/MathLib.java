package com.evanshannon.x;

import java.util.Random;

public class MathLib {

    //Java's native integer division always rounds the absolute value down (e.g., -1/4 = 0)
    //This function always rounds down, purely (e.g., -1/4 = -1)
    public static int divide(final int dividend, final int divisor){
        int quotient = dividend / divisor;
        if(dividend < 0 && dividend%divisor!=0) return quotient-1;
        else return quotient;
    }
    public static float divide(final float dividend, final float divisor){
        float quotient = dividend / divisor;
        if(dividend < 0 && dividend%divisor!=0) return quotient-1;
        else return quotient;
    }
    //Java's native modulo operator is incorrect mathematially. For negative numbers, it essentially returns
    //-mod(-x,y). For example, -1 mod 4 returns -1 when it should be 3. Here, x mod y is always positive.
    public static int mod(final int dividend, final int divisor){
        int modulus = dividend % divisor;
        if(modulus < 0) return modulus+divisor;
        else return modulus;
    }
    public static float mod(final float dividend, final float divisor){
       float modulus = dividend % divisor;
        if(modulus < 0) return modulus+divisor;
        else return modulus;
    }
    public static boolean checkDistance(final int x1, final int y1, final int x2, final int y2, final int distance){
        final int x = x2-x1;
        final int y = y2-y1;

        return x*x+y*y <= distance*distance;
    }
    public static int roll(int begin, int end){
        Random r = new Random();
        return r.nextInt(begin,end);
    }
    public static int roundDown(float value){
        if(value == 0.0f) return 0;
        if(value < 0f) return (int)value-1;
        else return (int)value;
    }
    public static void shuffle(Object[] list){
        for(int i = 0; i < list.length; i++){
            int j = roll(0,list.length);
            Object temp = list[j];
            list[j] = list[i];
            list[i] = temp;
        }
    }
    public static float abs(float value){
        return value > 0 ? value : -value;
    }
}
