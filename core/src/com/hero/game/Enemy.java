package com.hero.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

public class Enemy {
    int health = 100;

    public void attack(Player player){
        player.injured();
    }
    public void injured(int damage){
        health  -= damage;
    }
}
