package com.hero.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class Listener implements ContactListener {
    private Boolean contactMade = false;
    private Boolean groundContact = false;
    private Boolean leftContact = false;
    private Boolean rightContact = false;
    @Override
    public void beginContact(Contact contact){
        if(contact.getFixtureA().getBody().getUserData() != null){
            NewUserData fixA = (NewUserData)contact.getFixtureA().getBody().getUserData();
            UserDataType typeA = fixA.getUserDataType();
            if(contact.getFixtureB().getBody().getUserData() != null){
                NewUserData fixB = (NewUserData)contact.getFixtureB().getBody().getUserData();
                UserDataType typeB = fixB.userDataType;

                if((typeA == UserDataType.HERO && typeB == UserDataType.ENEMY) || (typeA == UserDataType.ENEMY && typeB == UserDataType.HERO)){
                    contactMade = true;
                }
                else if((typeA == UserDataType.HERO && typeB == UserDataType.GROUND) || (typeB == UserDataType.HERO && typeA == UserDataType.GROUND)){
                    groundContact = true;
                }
                else if((typeA == UserDataType.LEFTWALL && typeB == UserDataType.ENEMY) || (typeB == UserDataType.LEFTWALL && typeA == UserDataType.ENEMY))
                    leftContact = true;
                else if((typeA == UserDataType.RIGHTWALL && typeB == UserDataType.ENEMY) || (typeB == UserDataType.RIGHTWALL && typeA == UserDataType.ENEMY)){
                    rightContact = true;
                }
            }

        }

    }

    public Boolean getGroundContact() {
        return groundContact;
    }

    public Boolean getLeftContact() {
        return leftContact;
    }
    public Boolean getRightContact() {
        return rightContact;
    }
    @Override
    public void endContact(Contact contact){
        contactMade = false;
        groundContact = false;
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
