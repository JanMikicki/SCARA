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
    
    public SCARA(){
    GraphicsConfiguration config =
    SimpleUniverse.getPreferredConfiguration();
    
    Canvas3D canvas3D = new Canvas3D(config);   
    SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
    
    Transform3D przesuniecie_obserwatora = new Transform3D();
    przesuniecie_obserwatora.set(new Vector3f(0.0f,0.0f,3.0f));

    simpleU.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);
    setLayout(new BorderLayout());
  
    add(BorderLayout.CENTER, canvas3D);
    
    Color3f blop = new Color3f(255,255,255);
    
    AmbientLight swiatlo  = new AmbientLight(true, blop);
    swiatlo.setInfluencingBounds(new BoundingSphere());
    
    BranchGroup scena = new BranchGroup();
    scena.addChild(new ColorCube(0.4));
    scena.addChild(swiatlo);
    scena.compile();
    
    simpleU.addBranchGraph(scena);
    
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
