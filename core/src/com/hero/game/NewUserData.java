package com.hero.game;
import com.badlogic.gdx.math.Vector2;
import com.hero.game.UserDataType;
class NewUserData extends UserData{
    private float width;
    private float height;

    public Vector2 getJumpingImpulse() {
        return jumpingImpulse;
    }

    private Vector2 jumpingImpulse;

    public UserDataType getUserDataType() {
        return userDataType;
    }

    //public int userDataType;
    //set dataType to 0 = ground, 1 = leftWall, 2 = rightWall
    public NewUserData(float width, float height, int datatype){
        super(width, height);
        if(datatype == 0){this.userDataType = UserDataType.GROUND;}
        else if(datatype == 1){this.userDataType = UserDataType.LEFTWALL;}
        else if(datatype == 2){this.userDataType = UserDataType.RIGHTWALL;}
        else if(datatype == 3){this.userDataType = UserDataType.HERO;}
        else if(datatype == 4){this.userDataType = UserDataType.ENEMY;}
        jumpingImpulse = new Vector2(0, 10F);
    }
    public void setJumpingImpulse(Vector2 jumpingImpulse){
        this.jumpingImpulse = jumpingImpulse;
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
