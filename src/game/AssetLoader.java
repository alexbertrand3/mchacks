package game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import engine.gfx.Image;
import engine.gfx.ImageTile;

public class AssetLoader {
	
	public static HashMap<String, Tile> loadTiles(String file) throws IOException
	{
			FileReader fr = new FileReader(file);
			BufferedReader br = new BufferedReader(fr);
			HashMap<String, Tile> tiles = new HashMap<String, Tile>();
			String line = br.readLine();
			boolean reading = false;
			
			//skips all lines before start point
			while (!reading) {
				if (line.equals(".start")) {
					reading = true;
				}
				line = br.readLine();
				
			}
			while (line != null) {
				String[] traits = line.split("\t");
				// WILL REQUIRE MORE TILE STATS ONCE THEY ARE IMPLEMENTED!!!
				String id = traits[0];
				String title = traits[1];
				int[] arr = new int[4];
				arr[0] = Integer.parseInt(traits[2]);
				arr[1] = Integer.parseInt(traits[3]);
				arr[2] = Integer.parseInt(traits[4]);
				arr[3] = Integer.parseInt(traits[5]);
				tiles.put(id, new Tile(id, title, arr));
				line = br.readLine();
			}
			br.close();
			fr.close();
			return tiles;
			
	}
	
	public static HashMap<String, Unit> loadUnits(String file) throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		HashMap<String, Unit> units = new HashMap<String, Unit>();
		String line = br.readLine();
		boolean reading = false;
		
		//skips all lines before start point
		while (!reading) {
			if (line.equals(".start")) {
				reading = true;
			}
			line = br.readLine();
		}
		while (line != null) {
			String[] traits = line.split("\t");
			// WILL REQUIRE MORE UNIT STATS ONCE THEY ARE IMPLEMENTED!!!
			String id = traits[0];
			String name = traits[1];
			int[] arr = new int[9];
			arr[0] = Integer.parseInt(traits[2]);
			arr[1] = Integer.parseInt(traits[3]);
			arr[2] = Integer.parseInt(traits[4]);
			arr[3] = Integer.parseInt(traits[5]);
			arr[4] = Integer.parseInt(traits[6]);
			arr[5] = Integer.parseInt(traits[7]);
			arr[6] = Integer.parseInt(traits[8]);
			arr[7] = Integer.parseInt(traits[9]);
			arr[8] = Integer.parseInt(traits[10]);
			
			
			units.put(id, new Unit(id, name, arr));
			line = br.readLine();
		}
		br.close();
		fr.close();
		return units;
	}
	
	// file inputs are a bit janky, mapFile seems to require res/(mapFile).txt, tileSet requires /(tileSet)
	public static Map loadMap(String mapFile, String tileSet) throws IOException {
		FileReader fr = new FileReader(mapFile);
		BufferedReader br = new BufferedReader(fr);
		String line = br.readLine();
		boolean reading = false;
		
		//skips all lines before start point
		while (!reading) {
			if (line.equals(".start")) {
				reading = true;
			}
			line = br.readLine();
		}
		int width = Integer.parseInt(line);
		line = br.readLine();
		int height = Integer.parseInt(line);
		line = br.readLine();
		Tile[][] tileArray = new Tile[width][height];
		for (int y = 0; y < height; y++) {
			String[] readStrings = line.split("\t");
			for (int x = 0; x < width; x++) {
				tileArray[x][y] = Tile.tileDB.get(readStrings[x]).copy();
				}
			line = br.readLine();
		}
		
		// Loads graphics into tiles
		ImageTile imageTile = new ImageTile(tileSet, 16, 16);
		for (int y = 0; y < height; y++) {
			String[] readStrings = line.split("\t");
			String[][] coords = new String[readStrings.length][2];
			for (int i = 0; i < readStrings.length; i++) {
				coords[i] = readStrings[i].split(",");
			}
			for (int x = 0; x < width; x++) {
				tileArray[x][y].tileGFX = imageTile.getTileImage(Integer.parseInt(coords[x][0]),Integer.parseInt(coords[x][1]));
				}
			line = br.readLine();
		}
		br.close();
		fr.close();
		Map map = new Map(tileArray);
		System.out.println(Map.width);
		System.out.println(Map.height);
		return map;
	}
	
	public static void main(String[] args) {
		
		try {
			HashMap<String, Tile> output = loadTiles("res/tiles.txt");
			Tile.tileDB = output;
			Map map = loadMap("res/TestMap.txt", "/TileSet.png");
			System.out.println(map.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
}
