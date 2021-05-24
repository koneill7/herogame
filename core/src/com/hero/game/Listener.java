package com.hero.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Listener implements ContactListener {
    @Override
    public void beginContact(Contact contact){
        UserData fixA = (UserData)contact.getFixtureA().getUserData();
        int typeA = fixA.getUserDataType();
        UserData fixB = (UserData)contact.getFixtureB().getUserData();
        int typeB = fixB.getUserDataType();
        if((typeA == 3 && typeB == 4) || (typeA == 4 && typeB == 3)){
            System.out.println("collided with enemy");
        }
    }
    @Override
    public void endContact(Contact contact){

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
