package com.hero.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Hero extends Game {
	SpriteBatch batch;
	Texture img;

	@Override
	public void create () {
		//batch = new SpriteBatch();
		//img = new Texture("badlogic.jpg");
		Preferences preferences = Gdx.app.getPreferences("gamePreferences");
		setScreen(new HeroScreen(this, preferences));
	}

	@Override
	public void render () {
		super.render();
		/*batch.begin();
		batch.draw(img, 0, 0);
		batch.end();*/
	}
	
	@Override
	public void dispose () {
		//batch.dispose();
		//img.dispose();
	}
}