package com.hero.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor {
    int health = 100;
    NewUserData userData;
    private Vector2 moveVect;
    float velocity;
    private float speed = 100.0F;
    float width, height;
    private Vector2 pos;
    private Texture playerTextL = new Texture("shadow.png");
    private Texture playerTextR = new Texture("shadowright.png");
    Boolean right = false;
    Boolean left = true;
    public void attack(Player player){
        player.injured();
    }
    public void injured(int damage){
        health  -= damage;
    }
    public Enemy(){
        this.width = 20.0F;
        this.height = 30.0F;
    }
    Body body;
    public void makeEnemy(World world){
        BodyDef enemy = new BodyDef();
        PolygonShape enemyShape = new PolygonShape();
        FixtureDef fixture = new FixtureDef();

        enemy.type = BodyDef.BodyType.DynamicBody;
        enemy.position.set(new Vector2(150F, 30F));
        Body body = world.createBody(enemy);
        //body.setLinearVelocity(500, 500);
        enemyShape.setAsBox(4.2F, 12F);


        //fixture.density = 10.0F;
        //fixture.friction = 0.0F;
        //fixture.restitution = 0.0F;
        fixture.shape = enemyShape;

        this.userData = new NewUserData(this.width, this.height, 4);
        body.setUserData(this.userData); //dataType 4 for enemy
        body.createFixture(fixture);
        body.resetMassData();
        body.setGravityScale(5F);
        enemyShape.dispose();

        this.body = body;
        pos = this.body.getPosition();
    }
    public void right(){
        //float X = pos.x + 5;
        //pos = new Vector2(X, pos.y);
        right = true;
        left = false;
        velocity = this.speed - this.body.getLinearVelocity().x;
        moveVect = new Vector2(velocity, 0.0F);
        this.body.applyLinearImpulse(moveVect, this.body.getWorldCenter(), true);
    }

    public void left(){
        left = true;
        right = false;
        velocity = -this.speed - this.body.getLinearVelocity().x;
        moveVect = new Vector2(velocity, 0.0F);
        this.body.applyLinearImpulse(moveVect, this.body.getWorldCenter(), true);
    }
    public void stop(){
        velocity = 0.0F - this.body.getLinearVelocity().x;
        moveVect = new Vector2(velocity, 0.0F);
        this.body.applyLinearImpulse(moveVect, this.body.getWorldCenter(), true);
    }
    public UserData getUserData(){
        return userData;
    }
    public Body getBody() {
        return this.body;
    }
    public int getHealth(){ return this.health; }
    @Override
    public void draw(Batch batch, float alpha){
        pos = this.body.getPosition();
        super.draw(batch, alpha);
        if(left){
            batch.draw(playerTextL, pos.x-this.width/2, pos.y-this.height/2 - 0.5F, this.width, this.height);
        }
        else if(right){
            batch.draw(playerTextR, pos.x- this.width/2, pos.y-this.height/2 - 0.5F, this.width, this.height);
        }
    }

}
