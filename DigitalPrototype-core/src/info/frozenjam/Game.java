package info.frozenjam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;

public class Game extends ApplicationAdapter {
	
	// Local Objects
	public static final AssetManager ASSETS = new AssetManager();
	private Core WORLD;
	
	@Override
	public void create () {
		// Instantiate Objects
		this.WORLD = new Core();
		
		// Load Assets
		Game.ASSETS.load(Assets.ORB);
		Game.ASSETS.load(Assets.TRUCK);
		Game.ASSETS.finishLoading(); // Block thread until loaded
		
		// Initialize Objects
		this.WORLD.create();
	}

	@Override
	public void render () {
		float delta = Gdx.graphics.getDeltaTime();
		Game.ASSETS.update();
		this.WORLD.update(delta);
	}
	
	@Override
	public void dispose () {
		Game.ASSETS.dispose();
	}
}
