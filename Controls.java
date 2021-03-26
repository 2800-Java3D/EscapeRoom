package termreport;

import java.awt.event.MouseEvent;
import java.util.Iterator;

import org.jogamp.java3d.Appearance;
import org.jogamp.java3d.Behavior;
import org.jogamp.java3d.Canvas3D;
import org.jogamp.java3d.RotationInterpolator;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.WakeupOnAWTEvent;
import org.jogamp.java3d.utils.geometry.Sphere;
import org.jogamp.java3d.utils.picking.PickResult;
import org.jogamp.java3d.utils.picking.PickTool;
import org.jogamp.vecmath.Point3d;
import org.jogamp.vecmath.Vector3d;
import org.jogamp.vecmath.Vector3f;
import org.jogamp.java3d.WakeupCriterion;

public class Control_wheelPuzzle extends Behavior{
	private TransformGroup targetTG;
	private Transform3D trans = new Transform3D();
	private float transY = 0.0f;
	private WakeupOnAWTEvent wEnter;
	
	private Boolean paused;
	private RotationInterpolator r;
	private int key;

	private Canvas3D canvas;
	private static PickTool pickTool;

	public Control_wheelPuzzle(RotationInterpolator r, int key) {
		this.r = r;
		this.key = key;
		paused = false;
	}
	
	///set initial wakeup condition
	public void initialize() {
		wEnter = new WakeupOnAWTEvent(MouseEvent.MOUSE_PRESSED);
		wakeupOn(wEnter);
	}

	@Override
	public void processStimulus(Iterator<WakeupCriterion> criteria) {
		if(++transY > 6.0f) {
			transY = -2.0f;
		}
		trans.set(new Vector3f(0.0f, transY/10.0f, 0.0f));
		targetTG.setTransform(trans);
		wakeupOn(wEnter);	
	}

	public void mouseClicked(MouseEvent event) {
		int x = event.getX(); int y = event.getY();           // mouse coordinates
		Point3d point3d = new Point3d(), center = new Point3d();
		canvas.getPixelLocationInImagePlate(x, y, point3d);   // obtain AWT pixel in ImagePlate coordinates
		canvas.getCenterEyeInImagePlate(center);              // obtain eye's position in IP coordinates
		
		Transform3D transform3D = new Transform3D();          // matrix to relate ImagePlate coordinates~
		canvas.getImagePlateToVworld(transform3D);            // to Virtual World coordinates
		transform3D.transform(point3d);                       // transform 'point3d' with 'transform3D'
		transform3D.transform(center);                        // transform 'center' with 'transform3D'

		Vector3d mouseVec = new Vector3d();
		mouseVec.sub(point3d, center);
		mouseVec.normalize();
		pickTool.setShapeRay(point3d, mouseVec);              // send a PickRay for intersection

		if (pickTool.pickClosest() != null) {
			PickResult pickResult = pickTool.pickClosest();   // obtain the closest hit
			Sphere s = (Sphere) pickResult.getNode(PickResult.PRIMITIVE);
			Appearance app = new Appearance();                // originally a PRIMITIVE as a box
			if ((int) s.getUserData() == 0) {               // retrieve 'UserData'
				if(s.getName() == "Back"){					//change the texture an duser data based on the name of the shape clicked - Back Front Mid
					app.setTexture(AppearanceExtra.texturedApp("green"));
				}
				else if(s.getName() == "Mid") {
					
				}
				else if(s.getName() == "Front") {
					
				}
			}
			else {
				
				s.setUserData(0);                           // reset 'UserData'
			}
			s.setAppearance(app);                           // change box's appearance
		} 
	}
}