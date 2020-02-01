package game;

import java.awt.event.KeyEvent;

public class Controls {
	
	// represents keyCodes used by Input.java
	// find way to set multiple keys to an action. Probably will just be creating separate variables (ie public int mUAlt)
	// movement
	public int mU = KeyEvent.VK_W;
	public int mD = KeyEvent.VK_S;
	public int mL = KeyEvent.VK_A;
	public int mR = KeyEvent.VK_D;
	
	
	// menu
	public int A = KeyEvent.VK_Z;
	public int B = KeyEvent.VK_X;
	public int R;
	public int L;
	public int X;
	public int Y;
	
	public Controls()
	{
		mU = KeyEvent.VK_UP;
		mD = KeyEvent.VK_DOWN;
		mL = KeyEvent.VK_LEFT;
		mR = KeyEvent.VK_RIGHT;
	}
	
}
