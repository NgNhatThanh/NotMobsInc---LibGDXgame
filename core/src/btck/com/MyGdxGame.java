package btck.com;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import screens.MainMenuScreen;

public class MyGdxGame extends Game {
	public SpriteBatch batch;

	@Override
	public void create () {
		batch = new SpriteBatch();
		this.setScreen(new MainMenuScreen(this));
	}

	public void render () {
		super.render();
		ScreenUtils.clear(0, 0, 0, 1);
		batch.begin();

		batch.end();
	}
	
	public void dispose () {
		batch.dispose();
	}
}
