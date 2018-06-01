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


public class SCARA extends Applet implements ActionListener, KeyListener {
    
    private SimpleUniverse u;
    
    //konstruktor klasy
    public SCARA(){
    GraphicsConfiguration config =
    SimpleUniverse.getPreferredConfiguration();
    
    Canvas3D canvas3D = new Canvas3D(config);   
    u = new SimpleUniverse(canvas3D);
    
    Transform3D przesuniecie_obserwatora = new Transform3D();
    przesuniecie_obserwatora.set(new Vector3f(0.0f,0.0f,3.0f));

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
        
        tworzonaScena.addChild(podstawaTG);
        tworzonaScena.addChild(swiatlo);
        tworzonaScena.compile();
        
        return tworzonaScena;
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
       new MainFrame(new SCARA(), 700, 700);
    }    
    
    public void actionPerformed(ActionEvent e){}  
    public void keyReleased(KeyEvent e){}
    public void keyTyped(KeyEvent e){}
    public void keyPressed(KeyEvent e){}
}
