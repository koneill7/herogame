package com.hero.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

public class WorldGen {
    private World world;
    WorldGen(World newWorld){
        //store world data upon creation
        this.world = newWorld;
    }
    public Body makeBody(float posX, float posY, float halfW, float halfH, float density, int dataType){
        //x and y position of body, half width/height, fixture density, data type (0 = ground, 1 = left, 2 = right )
        BodyDef body = new BodyDef();
        body.type = BodyDef.BodyType.StaticBody;
        body.position.set(new Vector2(posX, posY));
        Body newBody = world.createBody(body);
        PolygonShape box = new PolygonShape();
        box.setAsBox(halfW, halfH);
        newBody.createFixture(box, density);
        newBody.setUserData(new NewUserData(2* halfW, 2 * halfH, dataType));
        box.dispose();
        return newBody;
    }
}
