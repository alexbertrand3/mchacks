package game;

import engine.gfx.ImageTile;

public class Unit {
	public String id;
	public String name;
	public ImageTile mapSprite = new ImageTile("/spriteTest.png", 21, 16);
	public int team; //0 = controlled, 1 = enemy
	
	Inventory inv;
	
	public boolean hovered;
	public boolean turnDone;
	int x;
	int y;
	
	
	public int hp;
	public int str;
	public int mag;
	public int skl;
	public int spd;
	public int lck;
	public int def;
	public int res;
	
	public int move = 5;
	
	// define movement penalties for units
	public Unit(String id, String name, int[] stats)
	{
		this.id = id;
		this.name = name;
		
		this.hp = stats[0];
		this.str = stats[1];
		this.mag = stats[2];
		this.skl = stats[3];
		this.spd = stats[4];
		this.lck = stats[5];
		this.def = stats[6];
		this.res = stats[7];
		this.move = stats[8];
		
	}
	public String toString(){
		return (id + " " + name);
	}
}
