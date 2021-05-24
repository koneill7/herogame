package com.hero.game;
import com.hero.game.UserDataType;
class UserData{
    private float width;
    private float height;

    public int getUserDataType() {
        return userDataType;
    }

    private int userDataType;
    //set dataType to 0 = ground, 1 = leftWall, 2 = rightWall
    public UserData(float width, float height, int datatype){
        this.width = width;
        this.height = height;
        this.userDataType = datatype;
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
