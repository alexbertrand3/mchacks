package game;

import java.util.ArrayList;
import java.util.HashMap;
import engine.gfx.Image;

public class Tile implements Comparable {
	
	public static HashMap<String, Tile> tileDB;
	// may not need
	public static Image[] tileGFXDB;
	
	public Image tileGFX;
	public String id;
	public String title;
	public ArrayList<Tile> adjacent = new ArrayList<Tile>();
	public int index;
	
	public int[] stats;
	public int avoid;
	public int def;
	public int ddg;
	public int movCost = 1;
	
	// for pathfinding
	public int minValue;
	
	// define movement penalties for units
	public Tile(String id, String title, int[] stats)
	{
		this.id = id;
		this.title = title;
		this.stats = stats;
		this.avoid = stats[0];
		this.def = stats[1];
		this.ddg = stats[2];
		this.movCost = stats[3];
		
	}
	public String toString(){
		return (id + " " + title + " " + avoid + " " + def + " " + ddg);
	}
	
	public Tile copy() {
		Tile output = new Tile(this.id, this.title, this.stats);
		return output;
	}
	
	public boolean isAbove(Tile t, int width) {
		if (this.index == t.index - width)
			return true;
		else return false;
	}
	public boolean isBelow(Tile t, int width) {
		if (this.index == t.index + width)
			return true;
		else return false;
	}
	public boolean isRight(Tile t, int width) {
		if (this.index == t.index + 1 && this.index % width != 0)
			return true;
		else return false;
	}
	public boolean isLeft(Tile t , int width) {
		if (this.index == t.index - 1 && t.index % width != 0)
			return true;
		else return false;
	}
	
	public int compareTo(Object arg0) {
		Tile t = (Tile) arg0;
		if (t.minValue == this.minValue) {
			return 0;
		}
		if (t.minValue < this.minValue) {
			return 1;
		} else {
			return -1;
		}
	}
}
