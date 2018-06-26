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
import com.sun.j3d.utils.behaviors.vp.*;
import com.sun.j3d.utils.universe.ViewingPlatform;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Cylinder;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.geometry.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;


public class SCARA extends Applet implements ActionListener, KeyListener {
    
    private Button reset = new Button("Reset view");
    private Button poczatek = new Button("Reset robot");
    private Button poczatekNauki = new Button("Rozpocznij naukę");
    private Button koniecNauki = new Button("Zakończ naukę");
    private Button odtworzTrase = new Button("Odtwórz zapisaną trasę");
    
    private Transform3D obrot1 = new Transform3D();
    private Transform3D przes1 = new Transform3D();
    private Transform3D obrot2 = new Transform3D();
    private Transform3D przes2 = new Transform3D();
    private Transform3D przes3 = new Transform3D();
    private TransformGroup ramie_1_TG = new TransformGroup();
    private TransformGroup ramie_2_TG = new TransformGroup();
    private TransformGroup trzon_TG = new TransformGroup();
    private float kat1 = 0.0f;
    private float kat2 = 0.0f;
    private float wys = 0.0f;

    private SimpleUniverse u;
    
    //konstruktor klasy
    public SCARA(){
    GraphicsConfiguration config =
    SimpleUniverse.getPreferredConfiguration();
    
    Canvas3D canvas3D = new Canvas3D(config);
    canvas3D.addKeyListener(this);
    u = new SimpleUniverse(canvas3D);
    
    //MYSZ
    ////////////////////////////////////////////////////////
    ViewingPlatform viewingPlatform = u.getViewingPlatform();
    viewingPlatform.setNominalViewingTransform();
    
    OrbitBehavior orbit = new OrbitBehavior(canvas3D, OrbitBehavior.REVERSE_ROTATE | OrbitBehavior.DISABLE_TRANSLATE | OrbitBehavior.STOP_ZOOM);
        
    BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0), 100.0);
         
    orbit.setSchedulingBounds(bounds);
    orbit.setMinRadius(1.0d);
    viewingPlatform.setViewPlatformBehavior(orbit);
        
    
    Transform3D przesuniecie_obserwatora = new Transform3D();
    przesuniecie_obserwatora.set(new Vector3f(0.0f,0.3f,3.0f));

    u.getViewingPlatform().getViewPlatformTransform().setTransform(przesuniecie_obserwatora);
    setLayout(new BorderLayout());
  
    Panel p = new Panel();
    p.add(reset);
    add("South", p);
    reset.addActionListener(this);
    reset.addKeyListener(this);
    
    p.add(poczatek);
    add("South", p);
    poczatek.addActionListener(this);
    poczatek.addKeyListener(this);
    
    
        p.add(poczatekNauki);
        add("South",p);
        poczatekNauki.addActionListener(this);
        poczatekNauki.addKeyListener(this);
        
        p.add(koniecNauki);
        add("South",p);
        koniecNauki.addActionListener(this);
        koniecNauki.addKeyListener(this);
        
        p.add(odtworzTrase);
        add("South",p);
        odtworzTrase.addActionListener(this);
        odtworzTrase.addKeyListener(this);
        
    
    add(BorderLayout.CENTER, canvas3D);
                
    BranchGroup scena = stworzenieSceny();
       
    u.addBranchGraph(scena);
    
    }
    
    private BranchGroup stworzenieSceny(){
        BranchGroup tworzonaScena = new BranchGroup();
        
        TransformGroup viewing_platform_TG = null;
        viewing_platform_TG = u.getViewingPlatform().getViewPlatformTransform();
        
        AmbientLight swiatlo  = new AmbientLight();
        swiatlo.setInfluencingBounds(new BoundingSphere());
        
        Material material = new Material();
        material.setAmbientColor(new Color3f(0.7f, 0.3f, 0.6f));
        
        Appearance wyglad = new Appearance();
        wyglad.setColoringAttributes(new ColoringAttributes(0.5f,0.5f,0.2f,ColoringAttributes.NICEST));
        wyglad.setMaterial(material);
        
        //Podstawa
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
        ////////////////////////////////////////////////////
        Box ramie1 = new Box(0.20f, 0.05f, 0.1f, wyglad);
        Cylinder walecram11 = new Cylinder(0.1f, 0.1f, wyglad);
        Cylinder walecram12 = new Cylinder(0.1f, 0.1f, wyglad);
         
        Transform3D  poz_ramie1   = new Transform3D();
        poz_ramie1.set(new Vector3f(0.2f,0.25f,0.0f));
         
        Transform3D poz_walecram11 = new Transform3D();
        poz_walecram11.set(new Vector3f(-0.2f, 0.0f, 0));
        
        TransformGroup zaokraglenie11_TG = new TransformGroup(poz_walecram11);
        zaokraglenie11_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        zaokraglenie11_TG.addChild(walecram11);
         
        Transform3D poz_walecram12 = new Transform3D();
        poz_walecram12.set(new Vector3f(0.2f, 0.0f, 0));
        TransformGroup zaokraglenie12_TG = new TransformGroup(poz_walecram12);
        zaokraglenie12_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        zaokraglenie12_TG.addChild(walecram12);
         
        ramie_1_TG = new TransformGroup(poz_ramie1);
        ramie_1_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        ramie_1_TG.addChild(ramie1);
        ramie_1_TG.addChild(zaokraglenie11_TG);
        ramie_1_TG.addChild(zaokraglenie12_TG);
        
        przes_walec.addChild(ramie_1_TG);
        
        //TransformGroup do obrotu ramienia 2
        ///////////////////////////////////////////////////////////////
        Transform3D  poz_przegub1   = new Transform3D();   
        poz_przegub1.set(new Vector3f(0.21f,0.0f,0.0f));
        TransformGroup trans_przegub1 = new TransformGroup(poz_przegub1);
        trans_przegub1.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        ramie_1_TG.addChild(trans_przegub1);
        
        //Ramie 2
        //////////////////////////////////////////////////////////////
        Box ramie2 = new Box(0.2f, 0.05f, 0.1f, wyglad);
        Cylinder walecram21 = new Cylinder(0.1f, 0.1f, wyglad);
        Cylinder walecram22 = new Cylinder(0.1f, 0.1f, wyglad);
         
        Transform3D  poz_ramie2 = new Transform3D();
        poz_ramie2.set(new Vector3f(0.21f,0.1f,0.0f));
        
        Transform3D poz_walecram21 = new Transform3D();
        poz_walecram21.set(new Vector3f(-0.2f, 0.0f, 0));
        
        Transform3D poz_walecram22 = new Transform3D();
        poz_walecram22.set(new Vector3f(0.2f, 0.0f, 0));
        
        TransformGroup zaokraglenie21_TG = new TransformGroup(poz_walecram21);
        zaokraglenie21_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        zaokraglenie21_TG.addChild(walecram21);
        
        TransformGroup zaokraglenie22_TG = new TransformGroup(poz_walecram22);
        zaokraglenie22_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        zaokraglenie22_TG.addChild(walecram22);
         
        ramie_2_TG = new TransformGroup(poz_ramie2);
        ramie_2_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        ramie_2_TG.addChild(ramie2);
        ramie_2_TG.addChild(zaokraglenie21_TG);
        ramie_2_TG.addChild(zaokraglenie22_TG);
        
        trans_przegub1.addChild(ramie_2_TG);
           
        //Trzon
        /////////////////////////////////////////////////////////
        Cylinder chwytak = new Cylinder(0.02f, 0.5f, wyglad);
        Transform3D  poz_chwytak = new Transform3D();
        poz_chwytak.set(new Vector3f(0.25f,-0.1f,0.0f));
        trzon_TG = new TransformGroup(poz_chwytak);
        trzon_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trzon_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        trzon_TG.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
        trzon_TG.setCapability(TransformGroup.ALLOW_CHILDREN_WRITE);
        trzon_TG.setCapability(TransformGroup.ALLOW_CHILDREN_READ);
        trzon_TG.setCapability(TransformGroup.ALLOW_CHILDREN_EXTEND);
        trzon_TG.addChild(chwytak);
        
        ramie_2_TG.addChild(trzon_TG);
        
         //ŚWIATŁO KIERUNKOWE
        BoundingSphere bounds = new BoundingSphere (new Point3d(0.0,0.0,0.0),180.0);
        Color3f light1Color = new Color3f(0.2f,1.0f,0.0f);
        Vector3f light1Direction = new Vector3f(4.0f,-7.0f,-12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        tworzonaScena.addChild(light1);
       
        
        ///////////////////////////////////////
        
        tworzonaScena.addChild(podstawaTG);
        tworzonaScena.addChild(swiatlo);
        tworzonaScena.compile();
        
        return tworzonaScena;
    }
    
    public void Joint_1_Rot(float krok)
    {
            kat1 -= krok;
            obrot1.rotY(kat1);
            przes1.setTranslation(new Vector3f(0.2f,0.25f,0.0f));
            obrot1.mul(przes1);
            ramie_1_TG.setTransform(obrot1);
    }
    
    public void Joint_2_Rot(float krok){
        
            kat2 -= krok;
            obrot2.rotY(kat2);
            przes2.setTranslation(new Vector3f(0.21f,0.1f,0.0f));
            obrot2.mul(przes2);
            //obrot2.setRotation(new AxisAngle4f());
            ramie_2_TG.setTransform(obrot2);
    }
    
    public void podniesienieChwytak(float krok)
    {
        //if(wys > -0.17f && wys < 0.24f){
            wys += krok;
            przes3.setTranslation(new Vector3f(0.25f,-0.1f+wys,0.0f));
            trzon_TG.setTransform(przes3);
        //}
    }
    
    public static void main(String[] args) {
        // TODO code application logic here       
       new MainFrame(new SCARA(), 700, 700);
    }    
    
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==reset){
            Transform3D reset_kamery = new Transform3D();
            reset_kamery.set(new Vector3f(0.0f, 0.3f, 3.0f));
            u.getViewingPlatform().getViewPlatformTransform().setTransform(reset_kamery);
        }
        else if(e.getSource() == poczatek){
            kat1 = 0.0f; 
            kat2 = 0.0f; 
            wys = 0.0f;
            
            //USTAWIENIE WSZYSTKICH SKŁADOWYCH ROBOTA W POZYCJI STARTOWEJ
            obrot1.rotY(kat1);  
            przes1.setTranslation(new Vector3f(0.2f,0.25f,0.0f)); 
            obrot1.mul(przes1); 
            ramie_1_TG.setTransform(obrot1);
            obrot2.rotY(kat2);  
            przes2.setTranslation(new Vector3f(0.21f,0.1f,0.0f)); 
            obrot2.mul(przes2); 
            ramie_2_TG.setTransform(obrot2);
            przes3.setTranslation(new Vector3f(0.25f,-0.1f,0.0f)); 
            trzon_TG.setTransform(przes3);
        }
        else if(e.getSource() == poczatekNauki){
            Vector pamiec = new Vector();
        }
    } 
    public void keyReleased(KeyEvent e){}   
    public void keyTyped(KeyEvent e){}
    
    @Override
    public void keyPressed(KeyEvent e){
    if(e.getKeyCode() == KeyEvent.VK_A){
            Joint_1_Rot(0.02f);
        }
    if(e.getKeyCode() == KeyEvent.VK_D){
            Joint_1_Rot(-0.02f);
        }
    if(e.getKeyCode() == KeyEvent.VK_Q){
            Joint_2_Rot(0.02f);
        }
    if(e.getKeyCode() == KeyEvent.VK_E){
            Joint_2_Rot(-0.02f);
        }
    if(e.getKeyCode() == KeyEvent.VK_W && wys < 0.24f){
            podniesienieChwytak(0.01f);
        }
    if(e.getKeyCode() == KeyEvent.VK_S && wys > -0.17f){
           podniesienieChwytak(-0.01f);
        }
    }
}
