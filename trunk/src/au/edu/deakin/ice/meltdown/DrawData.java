package au.edu.deakin.ice.meltdown;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/** The set of transformations and bitmap needed to draw any object at an
 *  arbitrary position. */
public class DrawData{
	
	/** The bitmap to draw */
	public Bitmap b;
	
	/** The matrix describing where to draw the bitmap */
	public Matrix m;
}

