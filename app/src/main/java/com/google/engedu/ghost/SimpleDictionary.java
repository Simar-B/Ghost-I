/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.round;

public class SimpleDictionary implements GhostDictionary {
    private ArrayList<String> words;
    public static boolean userStarted = false;

    public SimpleDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        words = new ArrayList<>();
        String line = null;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            if (word.length() >= MIN_WORD_LENGTH)
              words.add(line.trim());
        }
    }

    @Override
    public boolean isWord(String word) {
        return words.contains(word);
    }

    @Override
    public String getAnyWordStartingWith(String prefix) {
        if(prefix.equals("") || prefix == null){
            Random random = new Random();
            int randomNum = random.nextInt(words.size());
            return words.get(randomNum);
        }else {
            int l = 0, r = words.size() - 1;
            while (l <= r) {
                int m = (l + r) / 2;
                Log.i("loop",m +"");

                int res = prefix.compareToIgnoreCase(words.get(m));
                if(words.get(m).startsWith(prefix)){
                    return words.get(m);
                }

                // Check if x is present at mid
                if (res == 0)
                    return words.get(m);

                // If x greater, ignore left half
                if (res > 0){
                    l = m + 1;
                }


                    // If x is smaller, ignore right half
                else{
                    r = m - 1;
                }

            }

            return null;
        }
    }

    @Override
    public String getGoodWordStartingWith(String prefix) {
        Log.i("here","hi");
        Log.i("word",words.get(62023));

        int l = 0, r = words.size() - 1;
        while (l < r) {
            Log.i("log",l + " " + r + " , " + Math.ceil((r+l)/2));
            int sum = r + l;
            Log.i("sum",sum + "");


            int m = (int)Math.floor(((((float)l+r)/2)));
            Log.i("m",m + "");


            int res = prefix.compareToIgnoreCase(words.get(m));
            Log.i("infinite loop",l + words.get(l) + " " + r + words.get(r) + " " + words.get(m) + " " + res + words.get(m).startsWith(prefix) + m);

            // Check if x is present at mid
            if (res == 0 || words.get(m).startsWith(prefix))
                r = m;

            // If x greater, ignore left half
            else if (res > 0)
                l = m + 1;

                // If x is smaller, ignore right half
            else
                r = m - 1;

            Log.i("After",l + words.get(l) + " " + r + words.get(r) + " " + words.get(m) + " " + res + words.get(m).startsWith(prefix) + m);
        }

        int lowerBound = l;
        Log.i("bound",lowerBound + " " + words.get(lowerBound));
        int a = 13;
        Log.i("bounds",Math.round(a/2) + "");

        l = 0;
        r = words.size() - 1;

        while (l < r) {
            int m = Math.round(((float)l + r)/2);

            int res = prefix.compareToIgnoreCase(words.get(m));

            // Check if x is present at mid
            if (res == 0 || words.get(m).startsWith(prefix))
                l = m;

            // If x greater, ignore left half
            else if (res > 0)
                l = m + 1;

                // If x is smaller, ignore right half
            else
                r = m - 1;
        }

        int upperBound = l;
        Log.i("uppderbound",upperBound + " " + words.get(upperBound));

        ArrayList<String> evenWords = new ArrayList<>();
        ArrayList<String> oddWords = new ArrayList<>();
        for(int i = lowerBound;i <= upperBound;i++){
            if (words.get(i).length() % 2 == 0) {
                evenWords.add(words.get(i));
            } else{
                oddWords.add(words.get(i));
            }

        }
        if(userStarted){
            Random random = new Random();
            int randomNum = random.nextInt(oddWords.size());
            Log.i("return","user started");
            return oddWords.get(randomNum);
        } else{
            Random random = new Random();
            int randomNum = random.nextInt(evenWords.size());
            Log.i("return","not user started");
            return evenWords.get(randomNum);
        }


    }
}
