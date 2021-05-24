package com.hero.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Listener implements ContactListener {
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
                }

            }
        }

    }
    @Override
    public void endContact(Contact contact){
        /*UserData fixA = (UserData)contact.getFixtureA().getUserData();
        int typeA = fixA.getUserDataType();
        UserData fixB = (UserData)contact.getFixtureB().getUserData();
        int typeB = fixB.getUserDataType();
        if((typeA == 3 && typeB == 4) || (typeA == 4 && typeB == 3)) {
            System.out.println("finished collision");
        }*/
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }

}
