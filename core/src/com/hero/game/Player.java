package com.hero.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    int health = 1000;
    NewUserData userData;
    private Vector2 moveVect;
    float velocity;
    private float speed = 500.0F;
    private Vector2 jump = new Vector2(0,300F);
    float width, height;
    private Vector2 pos;
    private Texture playerTextL = new Texture("playerswordleft.png");
    private Texture playerTextR = new Texture("playersword.png");
    Boolean right = false;
    Boolean left = false;
    Boolean start = true;
    private Boolean landed = false;
    private Boolean jumping = false;

    public Player(){
        this.width = 10.0F;
        this.height = 20.0F;
    }
    Body playerBod;
    public void injured(){
        health -= 5;
    }

    public void jump(){
        if(!jumping){
            playerBod.applyLinearImpulse(jump, playerBod.getWorldCenter(), true);
            jumping = true;
            landed = false;
        }
    }
    public void land(){
        jumping = false;
        landed = true;
    }

    public void makePlayer(World world){
        BodyDef player = new BodyDef();
        PolygonShape playerShape = new PolygonShape();
        FixtureDef fixture = new FixtureDef();

        player.type = BodyDef.BodyType.DynamicBody;
        player.position.set(new Vector2(10F, 25F));
        Body body = world.createBody(player);

        playerShape.setAsBox(0.8F, 8.0F);

        fixture.shape = playerShape;

        this.userData = new NewUserData(this.width, this.height, 3);
        body.setUserData(this.userData); //dataType 3 for player
        body.createFixture(fixture);
        body.resetMassData();
        body.setGravityScale(140F);
        playerShape.dispose();

        playerBod = body;
        pos = playerBod.getPosition();
    }

    public void right(){
        start = false;
        right = true;
        left = false;
        velocity = this.speed - playerBod.getLinearVelocity().x;
        moveVect = new Vector2(velocity, 0.0F);
        playerBod.applyLinearImpulse(moveVect, playerBod.getWorldCenter(), true);
    }

    public void left(){
        left = true;
        right = false;
        start = false;
        velocity = -this.speed - playerBod.getLinearVelocity().x;
        moveVect = new Vector2(velocity, 0.0F);
        playerBod.applyLinearImpulse(moveVect, playerBod.getWorldCenter(), true);
    }
    public void stop(){
        velocity = 0.0F- playerBod.getLinearVelocity().x;
        moveVect = new Vector2(velocity, 0.0F);
        playerBod.applyLinearImpulse(moveVect, playerBod.getWorldCenter(), true);
    }
    public UserData getUserData(){
        return userData;
    }
    public Body getBody() {
        return playerBod;
    }
    @Override
    public void draw(Batch batch, float alpha){
        pos = playerBod.getPosition();
        super.draw(batch, alpha);
        if(left){
            batch.draw(playerTextL, pos.x-this.width/2, pos.y-this.height/2 - 0.5F, this.width, this.height);
        }
        else if(right){
            batch.draw(playerTextR, pos.x- this.width/2, pos.y-this.height/2 - 0.5F, this.width, this.height);
        }
        else if(start){
            batch.draw(playerTextR, pos.x-this.width/2, pos.y-this.height/2 - 0.5F, this.width, this.height);
        }
    }
}

