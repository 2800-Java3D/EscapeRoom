package main;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jogamp.java3d.*;
import org.jogamp.java3d.utils.image.TextureLoader;
import org.jogamp.java3d.utils.picking.PickResult;
import org.jogamp.java3d.utils.picking.PickTool;
import org.jogamp.java3d.utils.universe.SimpleUniverse;
import org.jogamp.vecmath.*;

public class Intro extends JPanel implements MouseListener {
    private static final long serialVersionUID = 1L;
    private static JFrame frame;
    private Canvas3D canvas;
    private static PickTool pickTool;

    private static SoundUtilityJOAL soundJOAL;
    private static String snd_pt = "intro5";

    public static void addLights(BranchGroup sceneBG, Color3f clr) {
        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
        AmbientLight amLgt = new AmbientLight(new Color3f(0.2f, 0.2f, 0.2f));
        amLgt.setInfluencingBounds(bounds);
        sceneBG.addChild(amLgt);
        Point3f pt = new Point3f(2.0f, 2.0f, 2.0f);
        Point3f atn = new Point3f(1.0f, 0.0f, 0.0f);
        PointLight ptLight = new PointLight(clr, pt, atn);
        ptLight.setInfluencingBounds(bounds);
        sceneBG.addChild(ptLight);
        Background bg = new Background();
        bg.setImage(new TextureLoader("images/Intro-1.jpeg", null).getImage());
        bg.setImageScaleMode(Background.SCALE_FIT_ALL);
        bg.setApplicationBounds(bounds);
        bg.setColor(clr);
/*
        BackgroundSound back_sound = new BackgroundSound();
        MediaContainer pointMedia = new MediaContainer(locateSound("intro5"));
        pointMedia.setCacheEnable(true);
        back_sound.setSoundData(pointMedia);

        sceneBG.addChild(back_sound);
*/
        sceneBG.addChild(bg);
    }

    static PointSound point_sound = new PointSound();

    Point2f[] Attenuation_Strong = { new Point2f(1, 1), new Point2f(5, (float) 0.5), new Point2f(10, 0) };
    Point2f[] Attenuation_Average = { new Point2f(5, 1), new Point2f(15, (float) 0.5), new Point2f(30, 0) };
    static Point2f[] Attenuation_Weak = { new Point2f(20, 1), new Point2f(40, (float) 0.5), new Point2f(60, 0) };
/*
    public static void addSound() {
        MediaContainer sample1 = new MediaContainer(locateSound("intro5"));
        sample1.setCacheEnable(true);

        point_sound.setCapability(ConeSound.ALLOW_SOUND_DATA_READ);
        point_sound.setCapability(ConeSound.ALLOW_SOUND_DATA_WRITE);
        point_sound.setCapability(ConeSound.ALLOW_ENABLE_READ);
        point_sound.setCapability(ConeSound.ALLOW_ENABLE_WRITE);
        point_sound.setCapability(ConeSound.ALLOW_LOOP_READ);
        point_sound.setCapability(ConeSound.ALLOW_LOOP_WRITE);
        point_sound.setCapability(ConeSound.ALLOW_RELEASE_READ);
        point_sound.setCapability(ConeSound.ALLOW_RELEASE_WRITE);
        point_sound.setCapability(ConeSound.ALLOW_CONT_PLAY_READ);
        point_sound.setCapability(ConeSound.ALLOW_CONT_PLAY_WRITE);
        point_sound.setCapability(ConeSound.ALLOW_DISTANCE_GAIN_READ);
        point_sound.setCapability(ConeSound.ALLOW_DISTANCE_GAIN_WRITE);

        point_sound.setSoundData(sample1);
        point_sound.setInitialGain(2.0f);
        point_sound.setLoop(0);
        point_sound.setReleaseEnable(false);
        point_sound.setContinuousEnable(false);
        point_sound.setEnable(false);
        point_sound.setPriority(1.0f);
        point_sound.setPosition(0.0f, 0.0f, 0.0f);
        point_sound.setDistanceGain(Attenuation_Weak);
    }

    public static URL locateSound(String fn) {
        URL url = null;
        String filename = "sounds/intro5.wav";
        try {
            url = new URL("file", "localhost", filename);

        } catch (Exception e) {
            System.out.println("Can't open" + filename);
        }
        return url;
    }
*/
    public static Appearance createAppearance() {
        Appearance Appear = new Appearance();

        TexCoordGeneration tcg = new TexCoordGeneration();
        tcg.setEnable(false);

        TextureAttributes ta = new TextureAttributes();
        ta.setTextureMode(TextureAttributes.MODULATE);
        PolygonAttributes polyAttrib = new PolygonAttributes();
        polyAttrib.setCullFace(PolygonAttributes.CULL_NONE);
        Appear.setPolygonAttributes(polyAttrib);

        return Appear;
    }

    public void mouseEntered(MouseEvent arg0) {
    }

    public void mouseExited(MouseEvent arg0) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    private static TransformGroup txt1(String txt, float scl, Color3f clr, Point3f p, String click) {
        Font my2DFont = new Font("New Tegomin", Font.BOLD, 1);
        FontExtrusion myExtrude = new FontExtrusion();
        Font3D font3D = new Font3D(my2DFont, myExtrude);
        Text3D text3D = new Text3D(font3D, txt, p);

        Transform3D scalar = new Transform3D();
        scalar.setScale(scl);
        Appearance look = new Appearance();
        look.setMaterial(new Material(clr, clr, clr, clr, 1));
        TransformGroup text = new TransformGroup(scalar);
        Shape3D s2 = new Shape3D(text3D, look);
        s2.setUserData(0);
        s2.setName(click);
        text.addChild(s2);

        return text;
    }

    private static TransformGroup txt(String txt, float scl, Color3f clr, Point3f p, String click) {
        Font my2DFont = new Font("Times", Font.PLAIN, 1);
        FontExtrusion myExtrude = new FontExtrusion();
        Font3D font3D = new Font3D(my2DFont, myExtrude);
        Text3D text3D = new Text3D(font3D, txt, p);

        Transform3D scalar = new Transform3D();
        scalar.setScale(scl);
        Appearance look = new Appearance();
        look.setMaterial(new Material(clr, clr, clr, clr, 1));
        TransformGroup text = new TransformGroup(scalar);
        Shape3D s1 = new Shape3D(text3D, look);
        s1.setUserData(0);
        s1.setName(click);
        text.addChild(s1);

        return text;
    }

    /*
     * private static TransformGroup txt2(String txt, float scl, Color3f clr,
     * Point3f p) { Font my2DFont = new Font("Times", Font.PLAIN, 1); FontExtrusion
     * myExtrude = new FontExtrusion(); Font3D font3D = new Font3D(my2DFont,
     * myExtrude); Text3D text3D = new Text3D(font3D,txt,p);
     * 
     * Transform3D scalar = new Transform3D(); scalar.setScale(scl); Appearance look
     * = new Appearance(); look.setMaterial(new Material(clr, clr, clr, clr, 1));
     * TransformGroup text = new TransformGroup(scalar); text.addChild(new
     * Shape3D(text3D, look));
     * 
     * return text; }
     * 
     * 
     * public static TransformGroup button1(String txt, String name) { Point3f []
     * front = { new Point3f(-3f,-1.5f,0f), new Point3f(-3f,-2.5f,0f), new
     * Point3f(-1f,-2.5f,0f),new Point3f(-1f,-1.5f,0f)}; QuadArray sqr = new
     * QuadArray(4, GeometryArray.NORMALS| GeometryArray.COORDINATES |
     * GeometryArray.COLOR_3); sqr.setCoordinates(0, front); // 1/sqrt(3) as there
     * are 3 normals float normal = (float)(1/Math.sqrt(3)); float [] f1 =
     * {normal,normal,normal}; for(int i =0; i<4;i++) { sqr.setNormal(i, f1);
     * sqr.setColor(i, Commons.Grey); }
     * 
     * Shape3D shape = new Shape3D(sqr); shape.setUserData(0); shape.setName(name);
     * Transform3D scence = new Transform3D(); TransformGroup stg = null; // Using
     * vector3f to define the position of the quadarray scence.setTranslation(new
     * Vector3f(0,0,0.4f)); stg = new TransformGroup(scence); stg.addChild(shape);
     * Point3f p =new Point3f(-6.5f,-5.5f,0f);
     * stg.addChild(txt2(txt,0.4f,Commons.Green,p)); return stg; }
     * 
     * private static TransformGroup button2(String txt, String name) {
     * TransformGroup stg = button1(txt,name); Transform3D rotation = new
     * Transform3D(); TransformGroup tg = null; rotation.rotY(Math.PI); tg = new
     * TransformGroup(rotation); tg.addChild(stg);
     * 
     * return tg; }
     */

    private static TransformGroup front() {

        TransformGroup stg = new TransformGroup();
        // Writing Instruction
        /*
         * if (!soundJOAL.load(snd_pt, 0f, 0f, 1f, true)) // fix 'snd_pt' at cow
         * location System.out.println("Could not load " + snd_pt); else { // start
         * 'snd_pt' soundJOAL.setPos(snd_pt, 0f, 0f, 1f); soundJOAL.play(snd_pt); }
         */
        // With txt() we are inputing the text string, scale of the text, color of the
        // text,
        // Point3f of the text and "no-effect" is for mouse click action, to identify
        // the shape
        Point3f H = new Point3f(-4f, 4f, 0f);
        stg.addChild(txt("ESCAPE ROOM", 0.6f, Commons.White, H, "no-effect"));
        Point3f one = new Point3f(-10f, 3f, 0f);
        stg.addChild(txt1("INSTRUCTION", 0.4f, Commons.White, one, "no-effect"));
        Point3f two = new Point3f(1f, 3f, 0f);
        stg.addChild(txt1("HOW TO NAVIGATE", 0.4f, Commons.White, two, "no-effect"));
        Point3f HtN1 = new Point3f(1f, 3f, 0f);
        stg.addChild(txt("-Arrow keys for movement", 0.3f, Commons.White, HtN1, "no-effect"));
        Point3f HtN2 = new Point3f(1f, 2f, 0f);
        stg.addChild(txt("-And your mouse to interact", 0.3f, Commons.White, HtN2, "no-effect"));

        Point3f I1 = new Point3f(-13f, 3f, 0f);
        stg.addChild(txt("You will be locked in an ", 0.3f, Commons.White, I1, "no-effect"));
        Point3f I2 = new Point3f(-13f, 2f, 0f);
        stg.addChild(txt("abondoned living room, while", 0.3f, Commons.White, I2, "no-effect"));
        Point3f I3 = new Point3f(-13f, 1f, 0f);
        stg.addChild(txt("it's very likely you may never", 0.3f, Commons.White, I3, "no-effect"));
        Point3f I4 = new Point3f(-13f, 0f, 0f);
        stg.addChild(txt("leave the place. It's up to you to", 0.3f, Commons.White, I4, "no-effect"));
        Point3f I5 = new Point3f(-13f, -1f, 0f);
        stg.addChild(txt("solve the clues and let yourself out", 0.3f, Commons.White, I5, "no-effect"));

        Point3f b1 = new Point3f(-4.5f, -6.5f, 0f);
        stg.addChild(txt1("START", 0.4f, Commons.White, b1, "start"));

        Point3f b2 = new Point3f(2f, -6.5f, 0f);
        stg.addChild(txt1("EXIT", 0.4f, Commons.White, b2, "stop"));
        // Defining name and text of the button
        // stg.addChild(button1("START","start"));
        // stg.addChild(button2("END","end"));

        return stg;
    }

    public static BranchGroup createScene() {
        BranchGroup scene = new BranchGroup();

        // Calling the function to check its functionality
        scene.addChild(front());
        pickTool = new PickTool(scene); // allow picking of objs in 'sceneBG'
        pickTool.setMode(PickTool.BOUNDS);
        //
       // addSound();
        //
        addLights(scene, Commons.White);
        scene.compile(); // optimize scene BG

        return scene;
    }

    public Intro(BranchGroup sceneBG) {
        GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
        canvas = new Canvas3D(config);
        canvas.addMouseListener(this); // NOTE: enable mouse clicking

        SimpleUniverse su = new SimpleUniverse(canvas); // create a SimpleUniverse
        Commons.setEye(new Point3d(0, 0, 10.0));
        Commons.defineViewer(su); // set the viewer's location

        sceneBG.compile();
        su.addBranchGraph(sceneBG); // attach the scene to SimpleUniverse

        setLayout(new BorderLayout());
        add("Center", canvas);
        frame.setSize(600, 600); // set the size of the JFrame
        frame.setVisible(true);
    }

    /* the main entrance of the application */
    public static void main(String[] args) {
        frame = new JFrame("XY's Assignment 4");
        frame.getContentPane().add(new Intro(createScene()));
    }

    public void mouseClicked(MouseEvent event) {
        int x = event.getX();
        int y = event.getY(); // mouse coordinates
        Point3d point3d = new Point3d(), center = new Point3d();
        canvas.getPixelLocationInImagePlate(x, y, point3d); // obtain AWT pixel in ImagePlate coordinates
        canvas.getCenterEyeInImagePlate(center); // obtain eye's position in IP coordinates

        Transform3D transform3D = new Transform3D(); // matrix to relate ImagePlate coordinates~
        canvas.getImagePlateToVworld(transform3D); // to Virtual World coordinates
        transform3D.transform(point3d); // transform 'point3d' with 'transform3D'
        transform3D.transform(center); // transform 'center' with 'transform3D'

        Vector3d mouseVec = new Vector3d();
        mouseVec.sub(point3d, center);
        mouseVec.normalize();
        pickTool.setShapeRay(point3d, mouseVec); // send a PickRay for intersection

        if (pickTool.pickClosest() != null) {
            PickResult pickResult = pickTool.pickClosest(); // obtain the closest hit
            Shape3D shape = (Shape3D) pickResult.getNode(PickResult.SHAPE3D);
            if ((int) shape.getUserData() == 0) { // retrieve 'UserData'
                // Change according to when the mouse is clicked or which sphere its clicked on
                // We differentiate based on the name
                if (shape.getName() == "start") {
                    // Using System.out to keep track of the actions of the program and check for
                    // breakpoints
                    System.out.println("START");
                } else if (shape.getName() == "stop") {
                    // Using System.out to keep track of the actions of the program and check for
                    // breakpoints
                    System.out.println("STOP");
                    System.exit(0);
                }
            }

        }
    }
}