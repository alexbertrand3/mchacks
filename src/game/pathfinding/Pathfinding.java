package game.pathfinding;

import java.util.ArrayList;
import java.util.PriorityQueue;
import game.Tile;
import game.Unit;

public class Pathfinding {
	// convert 2 dimensional tile array to 1d array (map)
	// alternatively, tile array may not be necessary, only tile.adjacent to build 
	// also check that all tiles are distinct objects so that they don't replace each other when constructed
	
	
	
	public static pfOutput dijkstra(Tile[][] map, Tile origin, Unit u){
		int mov = u.move;
		boolean[] vis = new boolean[map.length * map[0].length];
		int[] dist = new int[map.length * map[0].length];
		for (int i = 0; i < dist.length; i ++) {
			dist[i] = Integer.MAX_VALUE;
		}
		Tile[] prev = new Tile[map.length * map[0].length];
		dist[origin.index] = 0;
		PriorityQueue<Tile> pq = new PriorityQueue<Tile>(map.length * map[0].length);
		pq.add(origin);
		while (pq.size() != 0) {
			Tile current = pq.poll();
			/* may be more efficient idk
			if (dist[current.index] > mov)
			continue;
			*/
			vis[current.index] = true;
			for (int i = 0; i < current.adjacent.size(); i++) {
				Tile adjacent = current.adjacent.get(i);
				if (vis[adjacent.index] == true) {
					continue;
				}
				int newDist = dist[current.index] + adjacent.movCost;
				if (newDist < dist[adjacent.index]) {
					dist[adjacent.index] = newDist;
					prev[adjacent.index] = current;
					adjacent.minValue = newDist; // this is a very dirty way of doing this and should probably be redone if possible
					pq.add(adjacent);
				}
			}
		}
		pfOutput output = new pfOutput(dist, prev);
		return output;
	}
	
	public static ArrayList<Tile> shortestPath(Tile[] prev, Tile destination) {
		ArrayList<Tile> path = new ArrayList<Tile>();
		if (destination.minValue == Integer.MAX_VALUE)
			return path;
		Tile current = destination;
		path.add(current);
		while (prev[current.index] != null) {
			path.add(prev[current.index]);
			current = prev[current.index];
		}
		return path;
	}
	
	public static boolean[] getValid(int[] dist, Unit u) {
		boolean[] valids = new boolean[dist.length];
		for (int i = 0; i < dist.length; i++) {
			if (dist[i] <= u.move) {
				valids[i] = true;
			}
		}
		return valids;
	}
	
	public static void followCursor() {
		//for (int i = 0; i < )
	}
}