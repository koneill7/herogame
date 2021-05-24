package com.hero.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Listener implements ContactListener {
    private Boolean contactMade = false;
    @Override
    public void beginContact(Contact contact){
        if(contact.getFixtureA().getBody().getUserData() != null){
            NewUserData fixA = (NewUserData)contact.getFixtureA().getBody().getUserData();
            UserDataType typeA = fixA.getUserDataType();
            if(contact.getFixtureB().getBody().getUserData() != null){
                NewUserData fixB = (NewUserData)contact.getFixtureB().getBody().getUserData();
                UserDataType typeB = fixB.userDataType;

                if((typeA == UserDataType.HERO && typeB == UserDataType.ENEMY) || (typeA == UserDataType.ENEMY && typeB == UserDataType.HERO)){
                    System.out.println("collided with enemy");
                    contactMade = true;

                }

            }
        }

    }
    @Override
    public void endContact(Contact contact){
        contactMade = false;
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

    public Boolean getContactMade() {
        return contactMade;
    }
}
