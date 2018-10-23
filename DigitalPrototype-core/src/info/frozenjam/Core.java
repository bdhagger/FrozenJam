package info.frozenjam;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import info.frozenjam.GraphMap.MapEdge;
import info.frozenjam.GraphMap.MapNode;

public class Core {
	
	// Local Objects
	private Camera CAMERA;
	private Viewport VIEWPORT;
	private Batch BATCH;
	private Stage STAGE;
	private GraphMap MAP;
	private Truck TRUCK;
	private BitmapFont TEXT;
	private ShapeRenderer SHAPES;
	
	// Local Fields
	public static final int VIEWPORT_WIDTH = 400;
	public static final int VIEWPORT_HEIGHT = 300;
	
	public Core() {
		this.CAMERA = new OrthographicCamera();
		this.VIEWPORT = new FillViewport(VIEWPORT_WIDTH, VIEWPORT_HEIGHT, this.CAMERA);
		this.BATCH = new SpriteBatch();
		this.STAGE = new Stage(this.VIEWPORT, this.BATCH);
		this.MAP = new GraphMap();
		this.SHAPES = new ShapeRenderer();
		this.SHAPES.setAutoShapeType(true);
		this.SHAPES.setColor(Color.BLACK);
	}
	
	public void create() {
		// Initialize Internals
		this.TEXT = new BitmapFont();
		
		// Initialize Map
		MapNode first = new MapNode("A");
		first.setPosition(100, 100);
		
		MapNode second = new MapNode("B");
		second.setPosition(300, 200);
		second.setColor(Color.YELLOW);
		
		MapNode third = new MapNode("C");
		third.setPosition(150, 250);
		third.setColor(Color.RED);
		
		MapEdge firstEdge = new MapEdge(first, second);
		MapEdge secondEdge = new MapEdge(second, third);
		MapEdge thirdEdge = new MapEdge(third, first);
		this.MAP.addEdge(firstEdge);
		this.MAP.addEdge(secondEdge);
		this.MAP.addEdge(thirdEdge);
		this.MAP.addNode(first);
		this.MAP.addNode(second);
		this.MAP.addNode(third);
		
		// Initialize Truck
		this.TRUCK = new Truck();
		this.STAGE.addActor(this.TRUCK);
		this.TRUCK.setPosition(first.getX(), first.getY() + 16);
		
		// Set textures of nodes
		for (MapNode iteratedNode : this.MAP.getNodes()) {
			iteratedNode.setTexture(Game.ASSETS.get(Assets.ORB));
			this.STAGE.addActor(iteratedNode);
			iteratedNode.setTouchable(Touchable.enabled);
			iteratedNode.addListener(new ClickListener() {
				public void clicked (InputEvent event, float x, float y) {
					Actor target = event.getTarget();
					target.addAction(Actions.sequence(Actions.scaleTo(1.15F, 1.15F, 0.25F), Actions.scaleTo(1.0F, 1.0F, 0.25F)));
					if (!target.equals(TRUCK.getLocation()) && TRUCK.canMove()) {
						TRUCK.addAction(Actions.moveTo(target.getX(), target.getY() + 15, 1, Interpolation.smooth));
						TRUCK.setLocation((MapNode)target);
					}
				}
			});
		}
		
		// Initialize Input
		Gdx.input.setInputProcessor(this.STAGE);
	}
	
	public Stage getStage() {
		return this.STAGE;
	}
	
	public void drawEdges() {
		this.SHAPES.setProjectionMatrix(this.CAMERA.combined);
		this.SHAPES.begin();
		for (MapEdge iteratedEdge : this.MAP.getEdges()) {
			Vector2 first = new Vector2(iteratedEdge.getFirst().getX() + 8, iteratedEdge.getFirst().getY() + 8);
			Vector2 second = new Vector2(iteratedEdge.getSecond().getX() + 8, iteratedEdge.getSecond().getY() + 8);
			this.SHAPES.rectLine(first, second, 5);
		}
		this.SHAPES.end();
	}
	
	public void drawUI() {
		this.TEXT.setColor(Color.BLACK);
		for (MapNode iteratedNode : this.MAP.getNodes()) {
			this.TEXT.draw(this.BATCH, iteratedNode.getName(), iteratedNode.getX() + 3, iteratedNode.getY() - 2);
		}
		
		this.TEXT.setColor(Color.BROWN);
		this.TEXT.draw(this.BATCH, String.format("Fuel: %d", this.TRUCK.getFuel()), 10, 20);
		this.TEXT.draw(this.BATCH, String.format("Money: $%d", this.TRUCK.getMoney()), 10, 40);
		this.TEXT.draw(this.BATCH, String.format("Time: %dhr", this.TRUCK.getTime()), 10, 60);
	}
	
	public void update(float passedDeltaTime) {
		this.prepareRender();
		this.drawEdges();
		this.BATCH.begin();
		this.drawUI();
		this.BATCH.end();
		this.STAGE.act();
		this.STAGE.draw();
	}
	
	// Internal Methods
	
	private void prepareRender() {
		Gdx.gl.glClearColor(0.45F, 0.45F, 0.45F, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		this.VIEWPORT.apply(true);
	}
}
