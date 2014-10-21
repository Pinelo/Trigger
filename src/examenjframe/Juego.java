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
    private Image    imaImagenApplet;   // Imagen a proyectar en Applet	
    private Graphics graGraficaApplet;  // Objeto grafico de la Imagen
    private String nombreArchivo;         //nombre del archivo donde se guarda la info   
    private String nombreArchivoWScript;  //archivo donde se guarda el world script
    private Boolean bPausa;               //el juego esta pausado o no
    private Boolean worldScriptNeeded;    // determina cuando se graban los datos del mundo
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
        // hago el applet de un tamaño 500,500
        setSize(800, 600);
        //el juego no esta pausado por defecto
        bPausa = false;
        
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
   
   public void writeWorldScript() throws IOException {
                                                          
                PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivoWScript));
                    
                fileOut.close();
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
                writeWorldScript();
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
        
    }
    
    
    public void paintAux(Graphics g) {
        //*****DEBUG PARA VER EL TIEMPO
        g.drawString(String.valueOf(startTime), 20, 50);
        g.drawString(String.valueOf(System.currentTimeMillis()), 40, 50);
        g.drawString(String.valueOf(System.currentTimeMillis()-startTime), 60, 50);

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
