package com.komdosh.pral.System.Words;

import android.content.Context;
import android.content.res.Resources;

import com.komdosh.pral.R;

import java.util.Random;

/**
 * Created by Komdosh on 24.09.2016.
 */

public class Words {
    public WordTask generateTask(Context context) {
       // String[] strings = {"123", "Привет","Пока","Пошёл","Идёт","Сегодня","Завтра","Вчера"};
        Resources res = context.getResources();
        String[] strings = res.getStringArray(R.array.taskWords);
        Random rand = new Random();
        int count = rand.nextInt(3)+1;
        String[] good = new String[count];
        for(int i = 0; i<count;++i)
            good[i]=strings[rand.nextInt(strings.length)];
        WordTask wt = new WordTask(good);
        return  wt;
    }
}
