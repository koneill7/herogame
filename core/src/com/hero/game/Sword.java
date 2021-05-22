package com.hero.game;

public class Sword {
    int damage;
    public Sword(int damage){
        this.damage = damage;
    }
    private void attack(Enemy enemy){
        enemy.injured(this.damage);
    }
}
