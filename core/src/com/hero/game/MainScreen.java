package com.hero.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;


//Her-o the Shadow Slayer
public class MainScreen implements Screen {
    OrthographicCamera orthoCamera;
    public static float SCREEN_WIDTH = 1280;
    public static float SCREEN_HEIGHT = 720;
    public static float CAMERA_X  = SCREEN_WIDTH/2;
    public static float CAMERA_Y = SCREEN_HEIGHT/2;

    private Stage mainScreen;
    private Texture mainTexture;
    private Image mainImage;


    @Override
    public void show() {
        mainTexture = new Texture("background.png");
        mainImage = new Image(mainTexture);
        orthoCamera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        mainScreen = new Stage(new FitViewport(SCREEN_WIDTH,SCREEN_HEIGHT, orthoCamera));
        mainScreen.addActor(mainImage);

        Gdx.input.setInputProcessor(mainScreen);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        mainScreen.act();
        mainScreen.draw();
    }

    @Override
    public void resize(int width, int height) {
        mainScreen.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        mainTexture.dispose();
        mainScreen.dispose();

    }
}

