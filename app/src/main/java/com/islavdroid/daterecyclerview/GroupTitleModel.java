package com.islavdroid.daterecyclerview;

/**
 * Created by islav on 17.11.2016.
 */

public class GroupTitleModel implements ItemInterface {
    public String title;

    public GroupTitleModel(String title) {
        this.title = title;
    }

    @Override
    public boolean isSection() {
        return true;
    }
}