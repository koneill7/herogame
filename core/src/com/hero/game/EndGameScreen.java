package com.hero.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class EndGameScreen  extends ScreenAdapter {

    private Preferences preferences;
    private int score;
    private Batch batch = new SpriteBatch();
    private BitmapFont bitmapFont;
    public static float SCREEN_WIDTH = 300;
    public static float SCREEN_HEIGHT = 100;
    private OrthographicCamera orthoCamera;
    private Texture screenText;
    private Image screenImage;
    private Stage endScreen;
    Music music = Gdx.audio.newMusic(Gdx.files.internal("skyward.mp3"));

    EndGameScreen(Preferences preferences){
        this.preferences = preferences;
        bitmapFont = new BitmapFont();
    }

    @Override
    public void show(){
        orthoCamera = new OrthographicCamera(SCREEN_WIDTH, SCREEN_HEIGHT);
        endScreen = new Stage(new FitViewport(SCREEN_WIDTH,SCREEN_HEIGHT, orthoCamera));
        screenText = new Texture("endscreen.jpg");
        screenImage = new Image(screenText);
        endScreen.addActor(screenImage);
        this.score = preferences.getInteger("High score: ", 0);
        music.play();
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        endScreen.act();
        endScreen.draw();

        batch.begin();
        bitmapFont.setColor(0.0F, 0.5F, 0.0F, 1.0F);
        bitmapFont.draw(batch, "Game Over!", 800, 500);
        bitmapFont.getData().setScale(7.5F);
        bitmapFont.setColor(0.0F, 1.0F, 0.0F, 1.0F);
        bitmapFont.draw(batch, "High Score: "+ this.score, 800, 400);
        bitmapFont.getData().setScale(7F);
        batch.end();
    }
    @Override
    public void resize(int width, int height){
        endScreen.getViewport().update(width, height, true);
    }
    @Override
    public void dispose(){
        music.dispose();
    }
}
