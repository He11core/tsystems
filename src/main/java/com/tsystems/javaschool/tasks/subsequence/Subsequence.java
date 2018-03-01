package com.tsystems.javaschool.tasks.subsequence;

import java.util.ArrayList;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        // TODO: Implement the logic here



        if (x == null || y == null) {
            throw new IllegalArgumentException();
        }

        if (x.equals(y)) {
            return true;
        }

        if (x.isEmpty() && !y.isEmpty()) {
            return true;
        }

        if ((!x.isEmpty() && y.isEmpty())) {
            return false;
        }

        ArrayList checker = new ArrayList();

        if (y.contains(x.get(0))) {
            checker.add(x.get(0));
        }

        for (int i = 1; i < x.size(); i++) {
            if (y.contains(x.get(i))) {
                if (y.indexOf(x.get(i)) >= y.indexOf(x.get(i-1))) {
                    checker.add(x.get(i));
                } else {
                    checker.add(i-1, x.get(i));
                }
            }
        }


        if (checker.equals(x)) {
            return true;
        }


        return false;
    }
}
