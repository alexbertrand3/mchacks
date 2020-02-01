package game;

public class Camera {
	
	// Scrolling
	static Cursor cursor;
	static int width = Map.width;
	static int height = Map.height;
	static boolean xScrolling;
	static boolean yScrolling;
	static int xScrollAnim;
	static int yScrollAnim;
	static int offX;
	static int offY;
	private static int camWidth = 14; //tiles displayed horizontally - 1 (ie tiles 0 - 14)
	private static int camHeight = 9;
	private static int scrollThresholdX = 2; //scrolls when cursor ENTERS tile this far from screen edge.
	private static int scrollThresholdY = 1; //Visually, n + 1 tiles will stay at edge of screen
	
	/*
	// Scrolling
	Cursor cursor;
	private int width;
	private int height;
	boolean xScrolling;
	boolean yScrolling;
	int xScrollAnim;
	int yScrollAnim;
	int offX;
	int offY;
	private int camWidth = 14; //tiles displayed horizontally - 1 (ie tiles 0 - 14)
	private int camHeight = 9;
	private int scrollThresholdX = 2; //scrolls when cursor ENTERS tile this far from screen edge.
	private int scrollThresholdY = 1; //Visually, n + 1 tiles will stay at edge of screen
	
	public Camera(Cursor c, int offX, int offY, int mapWidth, int mapHeight) {
		this.cursor = c;
		this.offX = offX;
		this.offY = offY;
		this.width = mapWidth;
		this.height = mapHeight;
	}*/
	
	public static void scroll() {
		
		if (cursor.fastFrames > 0) {
			// scroll right
			if (cursor.cursorX - offX >= camWidth - scrollThresholdX) {
				if (camWidth + offX != width - 1) {
					offX++;
					xScrollAnim = 8;
				}
			}
			// scroll left
			if (cursor.cursorX - offX <= scrollThresholdX) {
				if (offX != 0) {
					offX--;
					xScrollAnim = -8;
				}
			}
			// scroll down
			if (cursor.cursorY - offY >= camHeight - scrollThresholdY) {
				if (camHeight + offY != height - 1) {
					offY++;
					yScrollAnim = 8;
				}
			}
			// scroll up
			if (cursor.cursorY - offY <= scrollThresholdY) {
				if (offY != 0) {
					offY--;
					yScrollAnim = -8;
				}
			}
		} else {
			// scroll right
			if (cursor.cursorX - offX >= camWidth - scrollThresholdX) {
				if (camWidth + offX != width - 1) {
					offX++;
					xScrollAnim = 12;
					xScrolling = true;
				}
			}
			// scroll left
			if (cursor.cursorX - offX <= scrollThresholdX) {
				if (offX != 0) {
					offX--;
					xScrollAnim = -12;
					xScrolling = true;
				}
			}
			// scroll down
			if (cursor.cursorY - offY >= camHeight - scrollThresholdY) {
				if (camHeight + offY != height - 1) {
					offY++;
					yScrollAnim = 12;
					yScrolling = true;
				}
			}
			// scroll up
			if (cursor.cursorY - offY <= scrollThresholdY) {
				if (offY != 0) {
					offY--;
					yScrollAnim = -12;
					yScrolling = true;
				}
			}
		}
	}
	
	// run this at beginning of update cycle (before scroll()).
	public static void update() {
		
		if (cursor.fastFrames > 0) {
			xScrollAnim = 0;
			yScrollAnim = 0;
		} else if (xScrolling || yScrolling) {
			if (xScrolling && (xScrollAnim > 0)) {
				xScrollAnim -= 4; // (Right) OK
			}
			if (xScrolling && (xScrollAnim < 0)) {
				xScrollAnim += 4; // (Left)
			}
			if (yScrolling && (yScrollAnim > 0)) {
				yScrollAnim -= 4; // (Down) OK
			}
			if (yScrolling && (yScrollAnim < 0)) {
				yScrollAnim += 4; // Up
			}
		}
		if (xScrollAnim == 16 || xScrollAnim == -16 || xScrollAnim == 0) {
			xScrolling = false;
			xScrollAnim = 0;
		}
		if (yScrollAnim == 16 || yScrollAnim == -16 || yScrollAnim == 0) {
			yScrolling = false;
			yScrollAnim = 0;
		}
	}
	
}
