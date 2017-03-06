package com.komdosh.pral.System.Words;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Komdosh on 24.09.2016.
 */

public class WordTask {
    public Set<String> task = new HashSet<>();
    public String[] str;
    public WordTask(String[] task) {
        for(String t : task){
            this.task.add(t.toUpperCase());
        }
        str = task;
    }

    public boolean isContain(String str){
        if(this.task.contains(str.toLowerCase().toUpperCase())){
            this.task.remove(str);
            return true;
        }
        return false;
    }

    public boolean isEmpty(){
        return this.task.isEmpty();
    }
}
