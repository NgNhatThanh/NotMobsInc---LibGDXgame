package btck.com;

import btck.com.common.io.MouseHandler;
import btck.com.model.constant.GameState;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import screens.MainMenuScreen;

public class MyGdxGame extends Game {

	public static SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		Gdx.input.setInputProcessor(MouseHandler.getInstance());
		GameManager.getInstance().gameState = GameState.MENU;
		this.setScreen(new MainMenuScreen(this));
	}

	public void render () {
		super.render();
	}

	public void dispose () {
		batch.dispose();
	}
}