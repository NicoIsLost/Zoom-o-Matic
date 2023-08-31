package com.nicoislost;

import java.util.ArrayList;
import java.util.List;

public enum TestEnum {

    TEST_ENTRY;

    public static List<Integer> list;

    TestEnum() {
        init();
    }

    private static void init() {
        if (list == null) {
            list = new ArrayList<>(TestEnum.values().length);
        }
    }

    public void load() {
        list.add(1);
    }

}
