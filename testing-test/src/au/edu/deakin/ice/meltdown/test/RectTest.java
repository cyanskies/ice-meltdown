package au.edu.deakin.ice.meltdown.test;
import au.edu.deakin.ice.meltdown.Rect;
import au.edu.deakin.ice.meltdown.Vector2;

public class RectTest extends android.test.AndroidTestCase {
	
	public RectTest()
	{
		super();
	}
	
	public void testPreConditions()
	{
		
	}

	public void testRectIntersects()
	{
		Vector2 a = new Vector2(0.f, 0.f);
		Vector2 b = new Vector2(1.f, 1.f);
		Vector2 c = new Vector2(-1.f, 0.f);
		Vector2 d = new Vector2(1.f, 0.f);
		Vector2 e = new Vector2(2.f, 2.f);
		Vector2 f = new Vector2(-0.5f, -0.5f);
		Vector2 g = new Vector2(-1.f, -1.f);
		
		Vector2 size = new Vector2(1.f, 1.f);
		Vector2 size2 = new Vector2(2.f, 2.f);
		
		// No overlap
		Rect rectTest1_1 = new Rect(a, size);
		Rect rectTest1_2 = new Rect(e, size);
		assertFalse(rectTest1_1.intersects(rectTest1_2));
		
		// Same rectangle - Should register as overlapped
		Rect rectTest2_1 = new Rect(a, size);
		Rect rectTest2_2 = new Rect(a, size);
		assertTrue(rectTest2_1.intersects(rectTest2_2));
		
		// Rectangle 2 should completely encapsulate Rectangle 1
		Rect rectTest3_1 = new Rect(a, size);
		Rect rectTest3_2 = new Rect(f, size2);
		assertTrue(rectTest3_1.intersects(rectTest3_2));
		
		// Rectangle 2 should overlap the bottom left of rectangle 1
		Rect rectTest4_1 = new Rect(a, size);
		Rect rectTest4_2 = new Rect(f, size);
		assertTrue(rectTest4_1.intersects(rectTest4_2));
		
		// Rectangle 2 should overlap the top right of rectangle 1
		Rect rectTest5_1 = new Rect(f, size);
		Rect rectTest5_2 = new Rect(a, size);
		assertTrue(rectTest5_1.intersects(rectTest5_2));
		
		// Rectangles with negative position values should still overlap
		Rect rectTest6_1 = new Rect(f, size);
		Rect rectTest6_2 = new Rect(g, size);
		assertTrue(rectTest6_1.intersects(rectTest6_2));
		
		// Rectangles should not overlap if the corners are connected
		Rect rectTest7_1 = new Rect(a, size);
		Rect rectTest7_2 = new Rect(b, size);
		assertFalse(rectTest7_1.intersects(rectTest7_2));
		
		// Rectangles should not overlap if the boundaries are connected
		Rect rectTest8_1 = new Rect(a, size);
		Rect rectTest8_2 = new Rect(d, size);
		assertFalse(rectTest8_1.intersects(rectTest8_2));
		
		// Rectangles should not overlap if the boundaries are connected
		Rect rectTest9_1 = new Rect(a, size);
		Rect rectTest9_2 = new Rect(c, size);
		assertFalse(rectTest9_1.intersects(rectTest9_2));
	}
}
