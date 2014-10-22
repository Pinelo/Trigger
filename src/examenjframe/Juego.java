/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package examenjframe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 *
 * @author Casa
 */


public final class Juego extends JFrame implements Runnable, KeyListener{

    /**
     * @param args the command line arguments
     */
    
    /* objetos para manejar el buffer del Applet y este no parpadee */
    private Image    imaImagenFrame;   // Imagen a proyectar en Applet	
    private Graphics graGraficaJframe;  // Objeto grafico de la Imagen
    private String nombreArchivo;         //nombre del archivo donde se guarda la info   
    private String nombreArchivoWScript;  //archivo donde se guarda el world script
    private Boolean bPausa;               //el juego esta pausado o no
    private Boolean worldScriptNeeded;    // determina cuando se graban los datos del mundo
    private LinkedList arrPersonajes;        //lista encadenada que contiene todos los personajes
    private LinkedList arrIdentificaciones; //lista de identificacoin de objetos actuales
    private int iContadorIds;               //contador de todas las identificaciones
    //para medir el tiempo
    private long startTime;               //tiempo de inicio
    

    
     public Juego() {
        init();
        start();
    }
     
    public void init() {
        //nombre de archivo donde se guarda la informacion
        nombreArchivo = "datosJuego";
        nombreArchivoWScript = "worldScript.txt";
        
        //inicializa el contador en 0
        iContadorIds = 0;
        
        // hago el applet de un tamaño 500,500
        setSize(800, 600);
        //el juego no esta pausado por defecto
        bPausa = false;
        
        //se inicializa al arreglo de personajes
        arrPersonajes = new LinkedList();
        
        //se crea la imagen para el personaje principal
        URL urlImagenMC = this.getClass().getResource("GreenSwordSoldierSprite.png");
        
        //arreglo de todas las identificaciones
        LinkedList arrIdentificaciones = new LinkedList();
        
        //arreglo donde se contiene el script del personaje principal
        LinkedList listScriptMC = new LinkedList();
        
        //arreglo que contiene los poderes disponibles al personaje principal
        LinkedList listSpellsMC = new LinkedList();
        
        //arreglo que actuara como inventorio del personaje principal
        LinkedList listItemsMC = new LinkedList();
        
        //arreglo donde se contiene los ataques con espada del personaje
        LinkedList listMovesMC = new LinkedList();
        
        //se crea el personaje principal
        Personaje mainChar = new Personaje(getWidth()/2, getHeight()/2,
                Toolkit.getDefaultToolkit().getImage(urlImagenMC), iContadorIds, 
                true, true, false, 100, 100, 10, 100, 100, 0, 0, listScriptMC, 
                listItemsMC, listSpellsMC,listMovesMC);
        
        //agrega id al arreglo
        arrIdentificaciones.add(iContadorIds);
        
        //se agrega al personaje principal al arreglo de personajes
        arrPersonajes.add(mainChar);
        //se agrega keylistener para poder detectar el teclado
        addKeyListener(this);
    }
    
 	
    public void start () {
        // Declaras un hilo
        Thread th = new Thread ((Runnable) this);
        // Empieza el hilo
        th.start ();
    }
    
    //cada 0.1 segundo debe de ser necesario grabar el script del mundo
   public Boolean worldScriptDue() {
       if(System.currentTimeMillis() - startTime >= 100) {
           worldScriptNeeded = true;
           startTime = System.currentTimeMillis();
           return true;
       }
       return false;
   }
   
   //todas las variables de los personajes se guardan en un archivo
   public void writeWorldScript() throws IOException {
                                                          
        try (PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivoWScript))) {
                fileOut.println(System.currentTimeMillis());
            for(Object encPersona:arrPersonajes) {
                Personaje Persona = (Personaje)encPersona;
                fileOut.println(Persona.getX());
                fileOut.println(Persona.getY());
                fileOut.println(Persona.getKillable());
                fileOut.println(Persona.getAlive());
                fileOut.println(Persona.getTimeLock());
                fileOut.println(Persona.getInterrupt());
                fileOut.println(Persona.getIsPlayer());
                fileOut.println(Persona.getMaxHealth());
                fileOut.println(Persona.getCurHealth());
                fileOut.println(Persona.getCurSpeed());
                fileOut.println(Persona.getMaxMana());
                fileOut.println(Persona.getCurMana());
                fileOut.println(Persona.getCurDir());
                fileOut.println(Persona.getCurStrike());
                fileOut.println(Persona.getListScript());
                fileOut.println(Persona.getListItems());
                fileOut.println(Persona.getListSpells());
                fileOut.println(Persona.getMoveList());
            }
        }
        }
   
   
    
    public void actualiza(){
        // instrucciones para actualizar personajes
        
    }
    
    public void run () {
        // se realiza el ciclo del juego en este caso nunca termina
        startTime = System.currentTimeMillis();
        while (true) {
            /* mientras dure el juego, se actualizan posiciones de jugadores
               se checa si hubo colisiones para desaparecer jugadores o corregir
               movimientos y se vuelve a pintar todo
            */ 
            actualiza();
            if(worldScriptDue()) {
                try {
                    writeWorldScript();
                } catch (IOException ex) {
                    Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            checaColision();
            repaint();
            try	{
                // El thread se duerme.
                Thread.sleep (20);
            }
            catch (InterruptedException iexError)	{
                System.out.println("Hubo un error en el juego " + 
                        iexError.toString());
            }
            while(bPausa) {repaint();}
	}
    }
    
    public void checaColision(){
        // instrucciones para checar colision y reacomodar personajes si 
        
    }
    
    public void keyReleased(KeyEvent e) {

    }
    
    public void paint (Graphics graGrafico){
        //inicializa el DoubleBuffer
        if(imaImagenFrame == null) {
            imaImagenFrame = createImage(this.getSize().width,
                    this.getSize().height);
            graGraficaJframe = imaImagenFrame.getGraphics();
        }
        
        //crea imagen para el background
        URL urlImagenFondo;
        Image imaImagenFondo;
        //*****este if debe cambiar su condicion
        if(true) {
            urlImagenFondo = this.getClass().getResource("espacio.jpg");
            imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
        }
        else {
                //*********GAME OVER**************
        }
        //despliega la imagen
        graGraficaJframe.drawImage(imaImagenFondo, 0, 0, getWidth(), 
                getHeight(), this);
        
        //actualiza el foreground
        graGraficaJframe.setColor(getForeground());
        paintAux(graGraficaJframe);
        
        //dibuja la imagen actualizada
        graGrafico.drawImage(imaImagenFrame, 0, 0, this);
    }
    
    
    public void paintAux(Graphics g) {
        //*****DEBUG PARA VER EL TIEMPO
        g.drawString(String.valueOf(startTime), 20, 50);
        g.drawString(String.valueOf(System.currentTimeMillis()), 40, 50);
        g.drawString(String.valueOf(System.currentTimeMillis()-startTime), 60, 50);
        
        if(arrPersonajes != null) {
            for(Object encPersona:arrPersonajes) {
                Personaje Persona = (Personaje)encPersona;
                g.drawImage(Persona.getImagen(), Persona.getX(), Persona.getY(),
                        this);
            }
        }

    }
    
    public void grabaArchivo() throws IOException {
                                                          
                PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
                    
                fileOut.close();
        }
    
     public void leeArchivo() throws IOException {
                                                          
                BufferedReader fileIn;
                fileIn = new BufferedReader(new FileReader(nombreArchivo));
                
                fileIn.close();
        }

   

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    
    
}

/**/
