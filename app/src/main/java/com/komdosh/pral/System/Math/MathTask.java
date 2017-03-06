package com.komdosh.pral.System.Math;

/**
 * Created by Komdosh on 23.09.2016.
 */

public class MathTask {
    public String[] task;
    public Integer answer;

    public MathTask(String[] task, int answer) {
        this.task = task;
        this.answer = answer;
    }

    public void set(MathTask t) {
        this.task = t.task;
        this.answer = t.answer;
    }


}
