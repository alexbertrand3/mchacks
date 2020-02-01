package game.pathfinding;

import game.Tile;

public class pfOutput {
	private int[] dist;
	private Tile[] prev;
	
	public pfOutput(int[] dist, Tile[] prev) {
		this.dist = dist;
		this.prev = prev;
	}

	public int[] getDist() {
		return dist;
	}

	public Tile[] getPrev() {
		return prev;
	}
	
}
