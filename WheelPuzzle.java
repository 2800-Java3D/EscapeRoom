package termreport;

import java.awt.event.KeyEvent;
import javax.swing.JPanel;
import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.geometry.Cylinder;
import org.jogamp.vecmath.*;




	public class WheelPuzzle extends JPanel {
	private static final long serialVersionUID = 1L;
	private static int count=0;

	private static Point3d pt_zero = new Point3d(0d, 0d, 0d);

	
	private static Shape3D line(Point3f pt) {
		LineArray lineArr = new LineArray(2, LineArray.COLOR_3 | LineArray.COORDINATES);
		///y
		lineArr.setCoordinate(0, pt);
		lineArr.setCoordinate(1, new Point3f(0.0f, 0.0f, 0.0f));
		lineArr.setColor(0, new Color3f(0.0f,0.0f,0.0f));
		lineArr.setColor(1, new Color3f(0.0f,0.0f,0.0f));
		
		Appearance app = new Appearance();
		ColoringAttributes ca = new ColoringAttributes(new Color3f(0.0f,0.0f,0.0f), ColoringAttributes.FASTEST);
		app.setColoringAttributes(ca);
		app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		
		
		return new Shape3D(lineArr, app);

	}
	
	private static TransformGroup triangle() {
		int n=3;
		float r = 0.6f, x, y;                             // vertex at 0.06 away from origin
		Point3f coor[] = new Point3f[n];
		TriangleArray tri = new TriangleArray(n*3 , TriangleArray.COORDINATES | TriangleArray.COLOR_3);
		
		for (int i = 0; i < n; i++) {
			x = (float) -Math.cos(Math.PI / 180 * (90 + (360)/n *i)) * r;		///360 divided by the number of sides 
			y = (float) -Math.sin(Math.PI / 180 * (90 + (360)/n  *i)) * r;
			coor[i] = new Point3f(x, y, 0.0f);
		}
		
		for (int i = 0; i < n; i++) {
			tri.setCoordinate(i*3 , new Point3f(0, 0, 0.0f) );
			tri.setCoordinate(i*3 + 1 , coor[(i)]);
			tri.setCoordinate(i*3 + 2 , coor[(i + 1) % n]);
		}
		//translate upwards
		Transform3D t = new Transform3D();
		t.setTranslation(new Vector3f(0.0f,2.3f,0.0f));
		TransformGroup TG = new TransformGroup(t);
		
		Appearance app = new Appearance();
		ColoringAttributes ca = new ColoringAttributes();
		ca.setColor(0.6f, 0.3f, 0.0f); // set column's color and make changeable
		app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		app.setColoringAttributes(ca);
		Shape3D shape = new Shape3D(tri, app);
		TG.addChild(shape);

		CollisionDetectLines cd = new CollisionDetectLines(shape);
		cd.setSchedulingBounds(new BoundingSphere(pt_zero, 10d)); // detect column's collision
		
		TG.addChild(cd);
		return TG;
	}
	
	private static BranchGroup Cylinder(float scale) {
		BranchGroup BG = new BranchGroup();
		
		String names[] = {
				"Back",
				"Front"
		};
		Color3f colors[] = {
				new Color3f(0.0f,0.0f,1.0f),
				new Color3f(1.0f,0.0f,0.0f)
		};
		
		Appearance app = new Appearance();
		ColoringAttributes ca = new ColoringAttributes(Commons.Grey, ColoringAttributes.FASTEST);
		app.setColoringAttributes(ca);
		app.setCapability(Appearance.ALLOW_COLORING_ATTRIBUTES_WRITE);
		app.setMaterial(AppearanceExtra.setMaterial(colors[count]));
		
		Cylinder cylinder = new Cylinder((1f)*scale, 0.5f, app);
		
		cylinder.setUserData(0);				///original 
		cylinder.setName(names[count++]);		///static variable to change the name based on how many times sphere is called
		
		
		Transform3D rotate = new Transform3D();
		rotate.rotX(Math.PI/2);

		TransformGroup tg1 = new TransformGroup();
		TransformGroup tg2 = new TransformGroup(rotate);
		tg2.addChild(cylinder);
		tg1.addChild(tg2);
		BG.addChild(tg1);
		
		return BG;
		
	}
	public static BranchGroup createMeasure() {
		BranchGroup BG = new BranchGroup();
		BG.addChild(line(new Point3f( 1.0f,5.0f, 0)));
		BG.addChild(line(new Point3f( -1.0f,5.0f, 0)));
		BG.addChild(triangle());
		BG.addChild(Cylinder(3));
		return BG;
	}

	
	public static BranchGroup createDial() {
		BranchGroup BG = new BranchGroup();
		
		Transform3D trans = new Transform3D();
		//trans.setTranslation(new Vector3f(0,0,1));
		
		TransformGroup tg1 = new TransformGroup(trans);
		TransformGroup tg2 = new TransformGroup();
		tg2.addChild(Cylinder(2));
		
		TransformGroup Col = new TransformGroup();
		Shape3D shape = line(new Point3f( 0.0f,5.0f, 0.0f));
		
		CollisionDetectLines cd = new CollisionDetectLines(shape);
		cd.setSchedulingBounds(new BoundingSphere(pt_zero, 10d)); // detect column's collision
		
		Col.addChild(cd); // add column with behavior of CollisionDector
		
		tg2.addChild(Col);
		tg2.addChild(shape);
		RotationInterpolator ri1 = ri(1000, tg2, 'x', new Point3d(0,0,0));
		BG.addChild(ri1);
		///key presses that pause or un-pause the rotations depending on key pressed
		///press z to stop the rotation
		DialBehavior sb1 = new DialBehavior(ri1, KeyEvent.VK_Z);
		sb1.setSchedulingBounds(new BoundingSphere());
		BG.addChild(sb1);
				
		tg1.addChild(tg2);
		BG.addChild(tg1);
		
		return BG;
	}
	
	public static RotationInterpolator ri(int rotationnumber, TransformGroup tg, char option, Point3d pos) {
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		Transform3D axis = new Transform3D();
		switch(option) {
		case 'x':
			axis.rotX(Math.PI/2);
			break;
		case 'z':
			axis.rotZ(Math.PI/2);
			break;
		default:
			///case Y
			axis.rotY(Math.PI/2);
			break;
		}
		
		Alpha a = new Alpha(-1, rotationnumber);
		RotationInterpolator rot = new RotationInterpolator(a, tg, axis, 0.0f, (float) Math.PI*2);
		rot.setSchedulingBounds(new BoundingSphere(pos, 100));
		
		return rot;
	}
	
	public static BranchGroup buildWin() {
		BranchGroup BG = new BranchGroup();
		TransformGroup TG = new TransformGroup();
		if(CollisionDetectLines.collided && DialBehavior.stopped) {
			AppearanceExtra.addptLights(TG, Commons.Green);
			
			RenderText.letters3D("Unlocked", 1.0d , new Color3f(0.0f,0.0f,0.0f));
		}
		return BG;
	}

	/* a function to create and return the scene BranchGroup */
	public static BranchGroup createScene() {
		BranchGroup sceneBG = new BranchGroup();		     // create 'objsBG' for content
		TransformGroup sceneTG = new TransformGroup();       // create a TransformGroup (TG)
		sceneBG.addChild(sceneTG);	                         // add TG to the scene BranchGroup
		
		sceneBG.addChild(AppearanceExtra.createBackground("background.jpg"));

		AppearanceExtra.addLights(sceneTG);
		///sceneBG.addChild(createSceneGraph(Commons.canvas_3D));
		sceneBG.addChild(createMeasure());
		sceneBG.addChild(createDial());
		sceneBG.addChild(buildWin());
		///createContent(sceneBG);
		sceneBG.compile(); 		// optimize objsBG
		return sceneBG;
	}
	
		/* a function to allow key navigation with the ViewingPlateform */

		/* the main entrance of the application via 'MyGUI()' of "CommonXY.java" */
		public static void main(String[] args) {
			java.awt.EventQueue.invokeLater(new Runnable() {
				public void run() {
					Commons.setEye(new Point3d(0.0, 0.35, 15.0));
					new Commons.MyGUI(createScene(), "AP's Lab 5");
				}
			});
		}
	}


