/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package scara;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import java.awt.event.KeyEvent;
import java.awt.event.ActionEvent;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.*;
import java.util.Timer;
import java.util.TimerTask;


public class SCARA extends Applet implements ActionListener, KeyListener {
    
    private Transform3D obrot1 = new Transform3D();
    private Transform3D przes1 = new Transform3D();
    private TransformGroup trans_ramie1 = new TransformGroup();
    private float kat1 = 0.0f;

    private SimpleUniverse u;
    
    //konstruktor klasy
    public SCARA(){
    GraphicsConfiguration config =
    SimpleUniverse.getPreferredConfiguration();
    
    Canvas3D canvas3D = new Canvas3D(config);
    canvas3D.addKeyListener(this);
    u = new SimpleUniverse(canvas3D);
    
    Transform3D przesuniecie_obserwatora = new Transform3D();
    przesuniecie_obserwatora.set(new Vector3f(0.0f,0.2f,3.0f));

    u.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);
    setLayout(new BorderLayout());
  
    add(BorderLayout.CENTER, canvas3D);
                
    BranchGroup scena = stworzenieSceny();
       
    u.addBranchGraph(scena);
    
    }
    
    private BranchGroup stworzenieSceny(){
        BranchGroup tworzonaScena = new BranchGroup();
        
        AmbientLight swiatlo  = new AmbientLight();
        swiatlo.setInfluencingBounds(new BoundingSphere());
        
        Material material = new Material();
        material.setAmbientColor(new Color3f(0.4f, 0.2f, 1.0f));
        
        Appearance wyglad = new Appearance();
        wyglad.setColoringAttributes(new ColoringAttributes(0.5f,0.5f,0.9f,ColoringAttributes.NICEST));
        wyglad.setMaterial(material);
        
        /**
         * Stworzenie podstawy, na której znajduje się robot typu SCARA.
         */
        Box podstawa = new Box(0.25f,0.01f,0.25f, wyglad);
        TransformGroup podstawaTG = new TransformGroup();
        podstawaTG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        podstawaTG.addChild(podstawa);
         
        //WALEC
        Cylinder walec = new Cylinder(0.1f, 0.4f, wyglad);
        Transform3D poz_walec = new Transform3D();
        poz_walec.set(new Vector3f(0.0f, 0.2f, 0));
        TransformGroup przes_walec = new TransformGroup(poz_walec);
        przes_walec.addChild(walec);
        podstawaTG.addChild(przes_walec);
        
        //Ramie 1
        Box ramie1 = new Box(0.20f, 0.05f, 0.1f, wyglad);
        Cylinder walecram11 = new Cylinder(0.1f, 0.1f, wyglad);
        Cylinder walecram12 = new Cylinder(0.1f, 0.1f, wyglad);
         
        Transform3D  poz_ramie1   = new Transform3D();
        poz_ramie1.set(new Vector3f(0.2f,0.25f,0.0f));
         
        Transform3D poz_walecram11 = new Transform3D();
        poz_walecram11.set(new Vector3f(-0.2f, 0.0f, 0));
        
        TransformGroup trans_walecram11 = new TransformGroup(poz_walecram11);
        trans_walecram11.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans_walecram11.addChild(walecram11);
         
        Transform3D poz_walecram12 = new Transform3D();
        poz_walecram12.set(new Vector3f(0.2f, 0.0f, 0));
        TransformGroup trans_walecram12 = new TransformGroup(poz_walecram12);
        trans_walecram12.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans_walecram12.addChild(walecram12);
         
        trans_ramie1 = new TransformGroup(poz_ramie1);
        trans_ramie1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trans_ramie1.addChild(ramie1);
        trans_ramie1.addChild(trans_walecram11);
        trans_ramie1.addChild(trans_walecram12);
        
        przes_walec.addChild(trans_ramie1);
        
         //ŚWIATŁO KIERUNKOWE
        BoundingSphere bounds = new BoundingSphere (new Point3d(0.0,0.0,0.0),80.0);
        Color3f light1Color = new Color3f(0.5f,0.8f,1.0f);
        Vector3f light1Direction = new Vector3f(4.0f,-7.0f,-12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        tworzonaScena.addChild(light1);
        
        tworzonaScena.addChild(podstawaTG);
        tworzonaScena.addChild(swiatlo);
        tworzonaScena.compile();
        
        return tworzonaScena;
    }
    
    public void obrotPrawo_1(float krok)
    {
            kat1 -= krok;
            obrot1.rotY(kat1);
            przes1.setTranslation(new Vector3f(0.2f,0.25f,0.0f));
            obrot1.mul(przes1);
            trans_ramie1.setTransform(obrot1);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here       
       new MainFrame(new SCARA(), 700, 700);
    }    
    
    public void actionPerformed(ActionEvent e){} 
    public void keyReleased(KeyEvent e){}   
    public void keyTyped(KeyEvent e){}
    
    @Override
    public void keyPressed(KeyEvent e){
    if(e.getKeyCode() == KeyEvent.VK_RIGHT){
            obrotPrawo_1(0.02f);
        }
    }
}
