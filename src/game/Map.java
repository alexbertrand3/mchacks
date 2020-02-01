package game;
import java.util.ArrayList;

import engine.GameContainer;
import engine.Renderer;
import engine.audio.SoundClip;
import engine.gfx.Image;
import engine.gfx.ImageTile;
import game.pathfinding.Pathfinding;
import game.pathfinding.pfOutput;


// TO DO: separate unit actions from map, separate cursor from map. Potentially make scroll its own thing or put it with cursor?

public class Map {
	static int width;
	static int height;
	
	private Cursor cursor;
	//private Camera cam;
	
	Tile[][] tiles;
	// may not need
	Image[] tileGfx;
	Unit[][] units;
	
	// Cursor Data
	private ImageTile imaget = new ImageTile("/cursor.png", 20, 20);
	
	// Tile overlays
	Unit unitSelected = null;
	pfOutput pfo;
	ArrayList<Tile> path;
	
	boolean[] moveOverlay;
	Image movable = new ImageTile("/TileOverlays.png", 16, 16).getTileImage(1, 0);
	Image attackable = new ImageTile("/TileOverlays.png", 16, 16).getTileImage(0, 0);
	Image staffable = new ImageTile("/TileOverlays.png", 16, 16).getTileImage(2, 0);
	Image moveLR = new ImageTile("/moveArrows.png", 16, 16).getTileImage(0, 0);
	Image moveUD = new ImageTile("/moveArrows.png", 16, 16).getTileImage(1, 0);
	Image moveLU = new ImageTile("/moveArrows.png", 16, 16).getTileImage(2, 0);
	Image moveRU = new ImageTile("/moveArrows.png", 16, 16).getTileImage(3, 0);
	Image moveLD = new ImageTile("/moveArrows.png", 16, 16).getTileImage(4, 0);
	Image moveRD = new ImageTile("/moveArrows.png", 16, 16).getTileImage(5, 0);
	Image moveEndR = new ImageTile("/moveArrows.png", 16, 16).getTileImage(6, 0);
	Image moveEndD = new ImageTile("/moveArrows.png", 16, 16).getTileImage(7, 0);
	Image moveEndL = new ImageTile("/moveArrows.png", 16, 16).getTileImage(8, 0);
	Image moveEndU = new ImageTile("/moveArrows.png", 16, 16).getTileImage(9, 0);
	
	
	public Map(Tile[][] tiles) {
		this.tiles = tiles;
		width = tiles.length;
		height = tiles[0].length;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				tiles[x][y].index = x + (y * width);
				if (x != width - 1) {
					tiles[x][y].adjacent.add(tiles[x + 1][y]);
				}
				if (x != 0) {
					tiles[x][y].adjacent.add(tiles[x - 1][y]);
				}
				if (y != height - 1) {
					tiles[x][y].adjacent.add(tiles[x][y + 1]);
				}
				if (y != 0) {
					tiles[x][y].adjacent.add(tiles[x][y - 1]);
				}
			}
		}
		cursor = new Cursor(3, 3);
		Camera.cursor = cursor;
		units = new Unit[width][height];
	}
	
	public void update(GameContainer gc, Controls controls)
	{	
		Camera.update();
		if (gc.getInput().isKey(controls.A)) {
			selectUnit(cursor.cursorX, cursor.cursorY);
		}
		if (gc.getInput().isKeyDown(controls.B) && unitSelected != null) {
			unitSelected = null;
		}
		if (gc.getInput().isKey(controls.B)){
			cursor.updateFast(gc, controls);
		} else {
			cursor.update(gc, controls);
		}
		if (unitSelected != null && moveOverlay[cursor.cursorX + width * cursor.cursorY]) {
			path = Pathfinding.shortestPath(pfo.getPrev(), tiles[cursor.cursorX][cursor.cursorY]);
		}
		Camera.scroll();
		
	}
	
	public void draw(Renderer r, float temp) {
		drawTiles(r);
		drawMoveRange(r);
		drawUnits(r, temp);
		drawMove(r, path);
		drawCursor(r, temp);
	}
	
	public void drawTiles(Renderer r) {
		for(int x = Camera.offX; x < width; x++) {
			for (int y = Camera.offY; y < height; y++) {
				if (Camera.xScrolling  && x != 0) {
					r.drawImage(tiles[x - 1][y].tileGFX, ((x - 1) - Camera.offX)*16 + Camera.xScrollAnim, (y - Camera.offY)*16 + Camera.yScrollAnim);
				}
				if (Camera.yScrolling && y != 0) {
					r.drawImage(tiles[x][y - 1].tileGFX, (x -Camera.offX)*16 + Camera.xScrollAnim, ((y - 1) - Camera.offY)*16 + Camera.yScrollAnim);
				}
				r.drawImage(tiles[x][y].tileGFX, (x - Camera.offX)*16 + Camera.xScrollAnim, (y - Camera.offY)*16 + Camera.yScrollAnim);
			}
		}
	}
	
	public void drawUnits(Renderer r, float temp) {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				if (units[x][y] != null) {
					if (Camera.xScrolling  && x != 0) {
						if (units[x - 1][y] != null)
						r.drawImageTile(units[x - 1][y].mapSprite, ((x - 1) - Camera.offX)*16 + Camera.xScrollAnim, (y - Camera.offY)*16 + Camera.yScrollAnim, (int) temp, 0);
					}
					if (Camera.yScrolling && y != 0) {
						if (units[x][y - 1] != null)
						r.drawImageTile(units[x][y - 1].mapSprite, (x -Camera.offX)*16 + Camera.xScrollAnim, ((y - 1) - Camera.offY)*16 + Camera.yScrollAnim, (int) temp, 0);
					}
					r.drawImageTile(units[x][y].mapSprite, (x - Camera.offX)*16 + Camera.xScrollAnim, (y - Camera.offY)*16 + Camera.yScrollAnim, (int) temp, 0);
				}
			}
		}	
	}
	
	public void drawCursor(Renderer r, float frame) {
		r.drawImageTile(imaget, (cursor.cursorX - Camera.offX) * 16 - 2, (cursor.cursorY - Camera.offY) * 16 - 2, (int) frame + 1, 0);
	}
	
	public void drawMoveRange(Renderer r) {
		if (unitSelected != null) {
			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					if (moveOverlay[x + width * y] == true) {
						r.drawImage(movable, (x - Camera.offX)*16 + Camera.xScrollAnim, (y - Camera.offY)*16 + Camera.yScrollAnim);
					}
				}
			}
		}
	}
	
	public void drawMove(Renderer r, ArrayList<Tile> path) {
		if (unitSelected != null) {
			for (int i = path.size() - 2; i >= 0; i--) {
				Image toDisplay = null;
				Tile t = path.get(i);
				int x = t.index % width;
				int y = t.index / width;
				if (i == 0 && path.get(i + 1).isLeft(t, width)) toDisplay = moveEndR;
				else if (i == 0 && path.get(i + 1).isRight(t, width)) toDisplay = moveEndL;
				else if (i == 0 && path.get(i + 1).isAbove(t, width)) toDisplay = moveEndD;
				else if (i == 0 && path.get(i + 1).isBelow(t, width)) toDisplay = moveEndU;
				else if ((path.get(i+1).isLeft(t, width) && path.get(i - 1).isRight(t, width)) || (path.get(i+1).isRight(t, width) && path.get(i - 1).isLeft(t, width))) toDisplay = moveLR;
				else if ((path.get(i+1).isBelow(t, width) && path.get(i - 1).isAbove(t, width)) || (path.get(i+1).isAbove(t, width) && path.get(i - 1).isBelow(t, width))) toDisplay = moveUD;
				else if ((path.get(i + 1).isLeft(t, width) && path.get(i - 1).isAbove(t, width)) || (path.get(i + 1).isAbove(t, width) && path.get(i - 1).isLeft(t, width))) toDisplay = moveLU;
				else if ((path.get(i + 1).isRight(t, width) && path.get(i - 1).isAbove(t, width)) || (path.get(i + 1).isAbove(t, width) && path.get(i - 1).isRight(t, width))) toDisplay = moveRU;
				else if ((path.get(i + 1).isLeft(t, width) && path.get(i - 1).isBelow(t, width)) || (path.get(i + 1).isBelow(t, width) && path.get(i - 1).isLeft(t, width))) toDisplay = moveLD;
				else if ((path.get(i + 1).isRight(t, width) && path.get(i - 1).isBelow(t, width)) || (path.get(i + 1).isBelow(t, width) && path.get(i - 1).isRight(t, width))) toDisplay = moveRD;
				r.drawImage(toDisplay, (x - Camera.offX)*16 + Camera.xScrollAnim, (y - Camera.offY)*16 + Camera.yScrollAnim);
			}
		}
	}
	
	public String toString() {
		String s = "";
		for (int i = 0; i < tiles[0].length; i++) {
			for (int j = 0; j < tiles.length; j++) {
				s = s.concat(tiles[j][i].id + "\t");
			}
			s = s.concat("\n");
		}
		return s;
	}
	
	public void selectUnit(int x, int y) {
		if (units[x][y] != null) {
			Unit u = units[x][y];
			unitSelected = u;
			if (u.team == 0) {
				pfo = Pathfinding.dijkstra(tiles, tiles[x][y], u);
			} else if (u.team == 1) {
				pfo = Pathfinding.dijkstra(tiles, tiles[x][y], u);
			}
			moveOverlay = Pathfinding.getValid(pfo.getDist(), u);
		}
	}
	
	public void moveUnit(Unit u, int x, int y) {
		if (moveOverlay[x + width * y]) {
			units[x][y] = u;
			
		}
	}
}
