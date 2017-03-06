package com.komdosh.pral.System.Math;

import java.util.Random;

/**
 * Created by Komdosh on 23.09.2016.
 *
 */

public class Mathematics {

    Random rand = new Random();
    Integer first, second, answer;
    Character sign;

    public MathTask generateTask(){
        generateExample();
        String[] task = new String[3];
        task[0] = first.toString();
        task[1] = sign.toString();
        task[2] = second.toString();

        return new MathTask(task, answer);
    }

    private void generateExample(){
        generateSign();
        switch(sign){
            case '/':
                generateDivide(30);
                break;
            case '*':
                generateArgs(20);
                break;
            default:
                generateArgs(50);
        }
        solve();
    }

    private Character generateSign(){
        Character[] signs = {'-','+','*','/'};
        sign = signs[rand.nextInt(signs.length)];
        return sign;
    }

    private void generateArgs(int maxNum){
        first = rand.nextInt(maxNum)+1;
        second = rand.nextInt(maxNum)+1;
    }

    private void generateDivide(int maxNum){
        second = rand.nextInt(maxNum/4)+1;
        first = second*(rand.nextInt(maxNum/4)+1);
    }

    private int solve(){
        switch (sign){
            case '-':
                answer = first - second;
                break;
            case '+':
                answer = first + second;
                break;
            case '/':
                answer = first / second;
                break;
            case '*':
                answer = first * second;
                break;
        }
        return answer;
    }
}
