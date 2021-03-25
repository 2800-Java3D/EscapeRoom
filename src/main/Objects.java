package main;

import java.io.FileNotFoundException;

import org.jogamp.java3d.BranchGroup;
import org.jogamp.java3d.Shape3D;
import org.jogamp.java3d.Transform3D;
import org.jogamp.java3d.TransformGroup;
import org.jogamp.java3d.loaders.IncorrectFormatException;
import org.jogamp.java3d.loaders.ParsingErrorException;
import org.jogamp.java3d.loaders.Scene;
import org.jogamp.java3d.loaders.objectfile.ObjectFile;


public class Objects {
	//load object
	private static Scene loadShape(String obj) {
		int flags = ObjectFile.RESIZE | ObjectFile.TRIANGULATE | ObjectFile.STRIPIFY;
		ObjectFile f = new ObjectFile(flags, (float) (60 * Math.PI/ 180.0));
		Scene s = null;
		try {
			s = f.load(obj);
		}catch(FileNotFoundException e) {
			System.err.println(e);
			System.exit(1);
		}catch(ParsingErrorException e) {
			System.err.println(e);
			System.exit(1);
		}catch(IncorrectFormatException e) {
			System.err.println(e);
			System.exit(1);
		}
		
		return s;
	}
	//loads and transforms the object scene from loadShape
	public static BranchGroup loadObject(float scaler){
		Scene s = loadShape("images/CowTray.obj");
		
		BranchGroup objBG = s.getSceneGroup();
		Shape3D cowTray = (Shape3D) objBG.getChild(0);
		cowTray.setAppearance(AppearanceExtra.setApp(Commons.Orange));
		
		///scale and rotate the cow object
		Transform3D rotate = new Transform3D();
		rotate.rotX(Math.PI/2 * 3);
		TransformGroup T = new TransformGroup(rotate);
		Transform3D scale = new Transform3D();
		scale.setScale(scaler);
		TransformGroup S = new TransformGroup(scale);
		T.addChild(S);
		S.addChild(objBG);
		BranchGroup B = new BranchGroup();
		B.addChild(T);
		
		///Creates an error because soundJOAL is null
		//soundJOAL.setPos(snd_pt, 0f, 0f, 0f);
		
		return B;
		
	}
}
