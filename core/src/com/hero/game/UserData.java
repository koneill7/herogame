package com.hero.game;

public class UserData {
    private float width;
    private float height;
    private int dataType;
    //set dataType to 0 = ground, 1 = leftWall, 2 = rightWall
    public UserData(float width, float height, int dataType) {
        this.width = width;
        this.height = height;
        this.dataType = dataType;
    }

    public int getDataType() {
        return dataType;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
