package game;

import engine.AbstractGameState;


// not currently used. May be unnecessary
public class MapState extends AbstractGameState {
	
	private Map map;
	private Cursor cursor;
	private Camera camera;
	
	public void update() {
		camera.update();
		camera.scroll();
	}
	
	public void draw() {
		
	}
	
}
