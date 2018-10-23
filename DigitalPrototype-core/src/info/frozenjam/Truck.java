package info.frozenjam;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

import info.frozenjam.GraphMap.MapNode;

public class Truck extends Actor {
	
	// Local Fields
	private MapNode LOCATION;
	private Texture TEXTURE;
	private int MONEY = 50;
	private int FUEL;
	private int TIME = 12;
	
	public Truck() {
		this.TEXTURE = Game.ASSETS.get(Assets.TRUCK);
	}
	
	public void draw(Batch passedBatch, float passedAlpha) {
		passedBatch.draw(this.TEXTURE, this.getX(), this.getY());
	}
	
	public int getMoney() {
		return this.MONEY;
	}
	
	public int getFuel() {
		return this.FUEL;
	}
	
	public int getTime() {
		return this.TIME;
	}
	
	public boolean canMove() {
		return (this.MONEY >= 5);
	}
	
	public MapNode getLocation() {
		return this.LOCATION;
	}

	public void setLocation(MapNode target) {
		this.LOCATION = target;
		this.MONEY -= 5;
		this.TIME -= 1;
	}
}
