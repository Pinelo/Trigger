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
    private int iVidas;                 // Cantidad al azar de vidas
    private Personaje perNena;          //Se crea la changuita
    private int dirNena;                //direccion de Nena;
    private LinkedList encCaminadores;  //lista de caminadores
    private Personaje perCaminador;     //objeto para obtener caminadores indiviualmente
    private LinkedList encCorredores;   //lista de corredores
    private Personaje perCorredor;      //objeto para obtener corredores individualmente
    private int iScore;                 //score del juego
    private int iColisiones;            //contador para disminuir vidas cada 5 colisiones
    private SoundClip aucSonidoCaminador; //sonido de impacto con caminador
    private SoundClip aucSonidoCorredor;  //sonido de impacto con corredor
    private String nombreArchivo;         //nombre del archivo donde se guarda la info   
    private Boolean bPausa;               //el juego esta pausado o no

    
     public Juego() {
        init();
        start();
    }
     
    public void init() {
        //nombre de archivo donde se guarda la informacion
        nombreArchivo = "datosJuego";
        // hago el applet de un tamaño 500,500
        setSize(800, 600);
        //el juego no esta pausado por defecto
        bPausa = false;
        //por defecto score empieza en 0
        iScore =0;
        
        //se empieza con 0 colisiones
        iColisiones = 0;
        
        
        //se crea la cantidad de vidas con un numero al azar entre 3-5
        iVidas = (int) (Math.random() * (5 - 3) + 3);
        
        //se crea sonido de impacto con caminador

        aucSonidoCaminador = new SoundClip("wooho.wav");
        
        //se crea sonido de impacto con corredor
        aucSonidoCorredor = new SoundClip("d_oh.wav");
        
        
       // se crea imagen de la nena
        URL urlImagenNena = this.getClass().getResource("nena.gif");
        
        // se crea la Nena
	perNena = new Personaje(getWidth() / 2, getHeight(),
                Toolkit.getDefaultToolkit().getImage(urlImagenNena));
        perNena.setY(getHeight()/2-perNena.getAlto());
        perNena.setX(getWidth()/2-perNena.getAncho());
        //la Nena no se mueve inicialmente
        dirNena = -1;
         //velocidad de nena es 5
        perNena.setVelocidad(5);
        
        //
        // se obtiene la imagen para los caminadores    
	URL urlImagenCaminador = this.getClass().getResource("alien1Camina.gif");

        // se crea el arreglo de caminadores con 8-10 caminadores
        encCaminadores = new LinkedList();
        int cantCaminadores = (int) (Math.random() * (10 - 8) + 8);
        while(cantCaminadores != 0) {
            cantCaminadores -=1;
            int posX = (int) (Math.random() *(0 + 600) - 600);    
            int posY = (int) (Math.random() *(getHeight()));
            perCaminador = new Personaje(posX,posY,
                    Toolkit.getDefaultToolkit().getImage(urlImagenCaminador));
            //cada caminador tiene una velocidad al azar
            perCaminador.setVelocidad((int) (Math.random() * (5 -3) + 3));
            encCaminadores.add(perCaminador);
        }
        //
        
        // se obtiene la imagen para los corredores    
	URL urlImagenCorredor = this.getClass().getResource("alien2Corre.gif");

        // se crea el arreglo de corredores con 10-15 corredores
        encCorredores = new LinkedList();
        int cantCorredores = (int) (Math.random() * (15 - 10) + 10);
        while(cantCorredores != 0) {
            cantCorredores -=1;
            int posX = (int) (Math.random() *(getWidth()));    
            int posY = (int) (Math.random() *(0 + 1000) - 1000);
            perCorredor = new Personaje(posX,posY,
                Toolkit.getDefaultToolkit().getImage(urlImagenCorredor));
            encCorredores.add(perCorredor);
        }
        
        //se agrega keylistener para poder detectar el teclado
        addKeyListener(this);
    }
	
    public void start () {
        // Declaras un hilo
        Thread th = new Thread ((Runnable) this);
        // Empieza el hilo
        th.start ();
    }
    
   
    
    public void actualiza(){
        // instrucciones para actualizar personajes
        
        //se cambia la posicion de la nena dependiendo de su direccion
        if(dirNena == 1) {
            perNena.setY(perNena.getY()-perNena.getVelocidad());
        }
        else if(dirNena == 2) {
            perNena.setY(perNena.getY()+perNena.getVelocidad());
        }
        else if(dirNena == 3) {
            perNena.setX(perNena.getX()-perNena.getVelocidad());
        }
        else if(dirNena == 4) {
            perNena.setX(perNena.getX()+perNena.getVelocidad());
        }
        
        //se mueve a cada caminador
        for (Object encCaminador : encCaminadores) {
                Personaje Caminador = (Personaje)encCaminador;
                Caminador.setX(Caminador.getX()+Caminador.getVelocidad());
        }
        
        //se mueve a cada corredor
        for (Object encCorredor : encCorredores) {
                Personaje Corredor = (Personaje)encCorredor;
                Corredor.setY(Corredor.getY()+Corredor.getVelocidad()-iVidas+5); //mientras menos vidas, mas rapido
        }
    }
    
    public void run () {
        // se realiza el ciclo del juego en este caso nunca termina
        while (iVidas>0) {//mientras no se haya perdido
            /* mientras dure el juego, se actualizan posiciones de jugadores
               se checa si hubo colisiones para desaparecer jugadores o corregir
               movimientos y se vuelve a pintar todo
            */ 
            actualiza();
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
        // es necesario
        //la nena no se puede salir del cuadro
        if(perNena.getX()+perNena.getAncho()>getWidth()) {
            perNena.setX(getWidth()-perNena.getAncho());
        }
        else if(perNena.getY()+perNena.getAlto()>getHeight()) {
            perNena.setY(getHeight()-perNena.getAlto());
        }
        else if(perNena.getX() < 0) {
            perNena.setX(0);
        }
        if(perNena.getY() < 0) {
            perNena.setY(0);
        }
        
        //si caminador choca con Nena se aumenta el score
        for (Object encCaminador : encCaminadores) {
                Personaje Caminador = (Personaje)encCaminador;
                if(perNena.colisiona(Caminador)) {
                    iScore++;   //se aumenta el score
                    //se reposiciona al caminador
                    Caminador.setX((int) (Math.random() *(0 + 600) - 600)); 
                    Caminador.setY((int) (Math.random() *(getHeight())));
                    aucSonidoCaminador.play();      //emite sonido
                }
                //se reposiciona al caminador cuando se sale del applet
                else if(Caminador.getX()+Caminador.getAncho()>getWidth()) {
                    Caminador.setX((int) (Math.random() *(0 + 600) - 600)); 
                    Caminador.setY((int) (Math.random() *(getHeight())));
                }
        }
        
        //si corredor choca con Nena se disminuye las vidas
        for (Object encCorredor : encCorredores) {
                Personaje Corredor = (Personaje)encCorredor;
                if(perNena.colisiona(Corredor)) {
                    iColisiones++;  //se aumenta el contador de colisiones
                    if(iColisiones >=5) { //si llega a cinco colisiones, se disminuye una vida
                        iVidas--;
                        iColisiones = 0;    //se reinicia el contador
                    }
                    //se reposiciona al corredor
                    Corredor.setX((int) (Math.random() *(getWidth()))); 
                    Corredor.setY((int) (Math.random() *(0 + 1000) - 1000));
                    aucSonidoCorredor.play();   //emite sonido
                }
                //se reposiciona al corredor cuando se sale del applet
                else if(Corredor.getY()+Corredor.getAlto()>getHeight()) {
                    Corredor.setX((int) (Math.random() *(getWidth()))); 
                    Corredor.setY((int) (Math.random() *(0 + 1000) - 1000));
                }
        }
    }
    
    public void keyReleased(KeyEvent e) {
        // si presiono flecha para arriba
        if(e.getKeyCode() == KeyEvent.VK_W) {    
                dirNena = 1;  // cambio la dirección arriba
        }
        // si presiono flecha para abajo
        else if(e.getKeyCode() == KeyEvent.VK_S) {    
                dirNena = 2;   // cambio la direccion para abajo
        }
        else if(e.getKeyCode() == KeyEvent.VK_A) {    
                dirNena = 3;   // cambio la direccion para izquierda
        }
        else if(e.getKeyCode() == KeyEvent.VK_D) {    
                dirNena = 4;   // cambio la direccion para derecha
        }
        else if(e.getKeyCode() == KeyEvent.VK_P) {
            bPausa = !bPausa;
        }
        else if(e.getKeyCode() == KeyEvent.VK_G) {
            try {
                grabaArchivo();
            } catch (IOException ex) {
                Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_C) {
            try {
                leeArchivo();
            } catch (IOException ex) {
                Logger.getLogger(Juego.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void paint (Graphics graGrafico){
        // Inicializan el DoubleBuffer
        if (imaImagenApplet == null){
                imaImagenApplet = createImage (this.getSize().width, 
                        this.getSize().height);
                graGraficaApplet = imaImagenApplet.getGraphics ();
        }
        
        
        //crea imagen para el background
        URL urlImagenFondo;
        Image imaImagenFondo;
        if(iVidas>0){
             urlImagenFondo = this.getClass().getResource("espacio.jpg");
            imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
        }
        else{   //imagen de game over cuando se pierde el juego
            urlImagenFondo = this.getClass().getResource("gameOver.jpg");
            imaImagenFondo = Toolkit.getDefaultToolkit().getImage(urlImagenFondo);
        }
        //despliega la imagen
        graGraficaApplet.drawImage(imaImagenFondo, 0, 0, 
                getWidth(), getHeight(), this);
        
       // Actualiza el Foreground.
        graGraficaApplet.setColor (getForeground());
        paintAux(graGraficaApplet);

        // Dibuja la imagen actualizada
        graGrafico.drawImage (imaImagenApplet, 0, 0, this);
        
        
    }
    
    
    public void paintAux(Graphics g) {
        //se despliegan las vidas en la esquina superior izquierda
        g.setColor(Color.RED);
        g.drawString("Vidas: "+ iVidas, 20, 35);
        g.drawString("Score: " + iScore, 20, 50);
        if(bPausa) {
            g.drawString("PAUSA", getWidth()/2, 50);
        }
        if (perNena != null && perCaminador != null && perCorredor != null && iVidas >0) {
                //Dibuja la imagen de la nena en la posicion actualizada
                g.drawImage(perNena.getImagen(), perNena.getX(),
                        perNena.getY(), this);
                
                //Dibuja los caminadores en la posicion actualizada
                for (Object encCaminador : encCaminadores) {
                    Personaje Caminador = (Personaje)encCaminador;
                    g.drawImage(Caminador.getImagen(), Caminador.getX(),
                            Caminador.getY(), this);
                }
                
                //Dibuja lso corredores en la posicion actualizada
                for (Object encCorredor : encCorredores) {
                    Personaje Corredor = (Personaje)encCorredor;
                    g.drawImage(Corredor.getImagen(), Corredor.getX(),
                            Corredor.getY(), this);
                }
        }

    }
    
    public void grabaArchivo() throws IOException {
                                                          
                PrintWriter fileOut = new PrintWriter(new FileWriter(nombreArchivo));
                    fileOut.println(iVidas);
                    fileOut.println(iScore);
                    fileOut.println(iColisiones);
                    fileOut.println(perNena.getX());
                    fileOut.println(perNena.getY());
                    fileOut.println(encCaminadores.size());
                    for (Object encCaminador : encCaminadores) {
                        Personaje Caminador = (Personaje)encCaminador;
                        fileOut.println(Caminador.getX());
                        fileOut.println(Caminador.getY());
                        fileOut.println(Caminador.getVelocidad());
                    }
                    fileOut.println(encCorredores.size());
                    for (Object encCorredor : encCorredores) {
                        Personaje Corredor = (Personaje)encCorredor;
                        fileOut.println(Corredor.getX());
                        fileOut.println(Corredor.getY());
                        fileOut.println(Corredor.getVelocidad());
                    }
                fileOut.close();
        }
    
     public void leeArchivo() throws IOException {
                                                          
                BufferedReader fileIn;
                fileIn = new BufferedReader(new FileReader(nombreArchivo));
                String dato = fileIn.readLine();
                iVidas = Integer.valueOf(dato);
                dato = fileIn.readLine();
                iScore = Integer.valueOf(dato);
                dato = fileIn.readLine();
                iColisiones = Integer.valueOf(dato);
                dato = fileIn.readLine();
                perNena.setX(Integer.valueOf(dato));
                dato = fileIn.readLine();
                perNena.setY(Integer.valueOf(dato));
                dato = fileIn.readLine();
                int cantCaminadores = Integer.valueOf(dato);
                //crea imagen para el nuevo arreglo de caminadores
                URL urlImagenCaminador = this.getClass().getResource("alien1Camina.gif");
                //borra el arreglo antiguo con caminadores para crear el nuevo 
                encCaminadores.clear();
                for(int i = cantCaminadores; i > 0; i--) {
                    dato = fileIn.readLine();
                        int posX = Integer.valueOf(dato);
                        dato = fileIn.readLine();
                        int posY = Integer.valueOf(dato);
                        perCaminador = new Personaje(posX,posY,
                                Toolkit.getDefaultToolkit().getImage(urlImagenCaminador));
                        dato = fileIn.readLine();
                        perCaminador.setVelocidad((Integer.valueOf(dato)));
                        encCaminadores.add(perCaminador);
                }
                dato = fileIn.readLine();
                int cantCorredores = Integer.valueOf(dato);
                //crea imagen para el nuevo arreglo de corredors
                URL urlImagenCorredor = this.getClass().getResource("alien2Corre.gif");
                //borra el arreglo antiguo con corredores para crear el nuevo 
                encCorredores.clear();
                for(int i = cantCorredores; i > 0; i--) {
                    dato = fileIn.readLine();
                        int posX = Integer.valueOf(dato);
                        dato = fileIn.readLine();
                        int posY = Integer.valueOf(dato);
                        perCorredor = new Personaje(posX,posY,
                                Toolkit.getDefaultToolkit().getImage(urlImagenCorredor));
                        dato = fileIn.readLine();
                        perCorredor.setVelocidad((Integer.valueOf(dato)));
                        encCorredores.add(perCorredor);
                }
                
                
                
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
