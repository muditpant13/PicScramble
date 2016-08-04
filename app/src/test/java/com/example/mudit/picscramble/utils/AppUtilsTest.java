package com.example.mudit.picscramble.utils;

import org.junit.Test;

import java.util.List;
import java.util.Set;

/**
 * Created by mudit on 2/8/16.
 */

public class AppUtilsTest {

    @Test
    public void getKRandomIntegersFromNTest(){
        Set<Integer> randomSetTest = AppUtils.getKRandomIntegersFromN(20,10);


        //check for count also
        int numItems = 0;

        for(Integer integer : randomSetTest){
            //check if all the items are in between range of 1 and 20
            assert ( integer <= 20 && integer >= 1);
            numItems ++;
        }

        //check if it returns 10 items
        assert (numItems == 10);

    }

    @Test
    public void getKRandomIntegerListTest(){
        List<Integer> randomIntegerList = AppUtils.getKRandomIntegerList(20, 10);

        int numItems = 0;

        for(Integer integer : randomIntegerList){
            //check if all the items are in between range of 1 and 20
            assert ( integer <= 20 && integer >= 1);
            numItems ++;
        }

        //check if it returns 10 items
        assert (numItems == 10);

    }

}
