package game;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;

import engine.AbstractGame;
import engine.GameContainer;
import engine.Renderer;
import engine.audio.SoundClip;
import engine.gfx.Image;
import engine.gfx.ImageTile;
import game.pathfinding.Pathfinding;
import game.pathfinding.pfOutput;

// look into JButton

//@SuppressWarnings("unused")
public class GameManager extends AbstractGame
{
	//private Image image;
	//private Image image2;
	//private ImageTile imaget;
	//private SoundClip clip;
	
	static Random rng = new Random(1337);
	private Controls controls;
	private Map map;
	private ImageTile spriteTest;
	private HashMap<String, Unit> units;
	
	/*private Tile[] prev;
	private ArrayList<Tile> path;
	private pfOutput pfo;
	private boolean renderMove;
	private int[] pfDist;
	private boolean[] pfBool;*/
	
	float temp = 0;
	
	public GameManager()
	{
		System.out.println("memes");
		controls = new Controls();
		spriteTest = new ImageTile("/spriteTest.png", 21, 16);
		
		try {
			HashMap<String, Tile> output = AssetLoader.loadTiles("res/tiles.txt");
			Tile.tileDB = output;
			Map map = AssetLoader.loadMap("res/testMap.txt", "/TileSet.png");
			units = AssetLoader.loadUnits("res/units.txt");
			//Map map = AssetLoader.loadMap("res/TestMap.txt", "/TileSet.png");
			this.map = map;
			map.units[5][5] = units.get("test");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(GameContainer gc, float dt) // dt = time per frame
	{
		
		map.update(gc, controls);
		
		// animation
		temp += dt * 1.8;
		if (temp > 2)
			temp = 0;
	}
	
	// look into "blitting"
	public void render(GameContainer gc, Renderer r)
	{
		//r.setzDepth(1);
		//r.drawImage(image2, 10, 10);
		//r.setzDepth(0);
		//r.drawImage(image, gc.getInput().getMouseX() - 32, gc.getInput().getMouseY() - 32);
		
		//r.drawFillRect(0, 0, 240, 160, 0xffaaaaaa);
		
		map.draw(r, temp);
		
	}
	
	public static void main(String[] args)
	{
		GameContainer gc = new GameContainer(new GameManager());
		gc.start();
		
		/*
		// Do this somewhere else later
		try {
			Tile.tileDB = AssetLoader.loadTiles("/tiles.txt");
			// Load tile graphics
			// Load units
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
		
	}

}
