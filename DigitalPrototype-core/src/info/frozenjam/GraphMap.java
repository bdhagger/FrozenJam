package info.frozenjam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class GraphMap {
	
	// Local Objects
	private List<MapNode> NODES;
	private List<MapEdge> EDGES;
	
	public GraphMap() {
		this.NODES = new ArrayList<MapNode>();
		this.EDGES = new ArrayList<MapEdge>();
	}
	
	public void addNode(MapNode passedNode) {
		this.NODES.add(Objects.requireNonNull(passedNode));
	}
	
	public void addEdge(MapEdge passedEdge) {
		this.EDGES.add(Objects.requireNonNull(passedEdge));
	}
	
	public List<MapNode> getNodes() {
		return Collections.unmodifiableList(this.NODES);
	}
	
	public List<MapEdge> getEdges() {
		return Collections.unmodifiableList(this.EDGES);
	}
	
	public static class MapEdge {
		
		// Local References
		private final MapNode FIRST;
		private final MapNode SECOND;
		private float WEIGHT;
		
		public MapEdge(MapNode passedFirst, MapNode passedSecond) {
			this.FIRST = Objects.requireNonNull(passedFirst);
			this.SECOND = Objects.requireNonNull(passedSecond);
		}
		
		public MapEdge setWeight(float passedWeight) {
			this.WEIGHT = passedWeight;
			return this;
		}
		
		public void draw(Batch passedBatch) {
			
		}
		
		public float getWeight() {
			return this.WEIGHT;
		}
		
		public MapNode getFirst() {
			return this.FIRST;
		}
		
		public MapNode getSecond() {
			return this.SECOND;
		}
		
	}
	
	public static class MapNode extends Actor {
		
		// Local Fields
		private final String NAME;
		private Texture TEXTURE;
		
		public MapNode(String passedName) {
			this.NAME = passedName;
		}
		
		public MapNode setTexture(Texture passedTexture) {
			this.TEXTURE = passedTexture;
			this.setWidth(passedTexture.getWidth());
			this.setHeight(passedTexture.getHeight());
			return this;
		}
		
		public void draw(Batch passedBatch, float passedAlpha) {
			Color oldColor = passedBatch.getColor();
			passedBatch.setColor(this.getColor());
			passedBatch.draw(this.TEXTURE, this.getX(), this.getY(), this.getWidth() * this.getScaleX(), this.getHeight() * this.getScaleY());
			passedBatch.setColor(oldColor);
		}
		
		public String getName() {
			return this.NAME;
		}
	}
}
