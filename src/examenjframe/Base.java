
/**
 * Base
 *
 * Modela la definición de todos los objetos de tipo
 * <code>Base</code>
 *
 * @author Hugo Gonzalez
 */

package examenjframe;

import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Rectangle;

public class Base {

    private int posX;    //posicion en x.       
    private int posY;     //posicion en y.
    private ImageIcon imiIcono;	//icono.
    private int ObjId;        //Identifica a cada objeto

    /**
     * Base
     * 
     * Metodo constructor usado para crear el objeto animal
     * creando el icono a partir de una imagen
     * 
     * @param posX es la <code>posicion en x</code> del objeto.
     * @param posY es la <code>posicion en y</code> del objeto.
     * @param imaImagen es la <code>imagen</code> del objeto.
     * @param ObjId es la <code>Identificacion unica</code> del objeto. 
     * 
     */
    public Base(int posX, int posY ,Image imaImagen, int ObjId) {
        this.posX = posX;
        this.posY = posY;
        imiIcono = new ImageIcon(imaImagen);
        this.ObjId = ObjId;
    }

    /**
     * Base
     * 
     * Metodo constructor usado para crear el objeto animal
     * creando el icono de imagen de un objeto igual
     * 
     * @param posX es la <code>posicion en x</code> del objeto.
     * @param posY es la <code>posicion en y</code> del objeto.
     * @param icoImagen es la <code>imagen tipo icono</code> del objeto.
     * @param ObjId es la <code>identificacion unica</code> del objeto.
     * 
     */
    public Base(int posX, int posY ,ImageIcon icoImagen, int ObjId) {
        this.posX = posX;
        this.posY = posY;
        imiIcono = icoImagen;
        this.ObjId = ObjId;
    }
    
    /**
     * setX
     * 
     * Metodo modificador usado para cambiar la posicion en x del objeto
     * 
     * @param posX es la <code>posicion en x</code> del objeto.
     * 
     */
    public void setX(int posX) {
        this.posX = posX;
    }

    /**
     * getX
     * 
     * Metodo de acceso que regresa la posicion en x del objeto 
     * 
     * @return posX es la <code>posicion en x</code> del objeto.
     * 
     */
    public int getX() {
        return posX;
    }

    /**
     * setY
     * 
     * Metodo modificador usado para cambiar la posicion en y del objeto 
     * 
     * @param posY es la <code>posicion en y</code> del objeto.
     * 
     */
    public void setY(int posY) {
            this.posY = posY;
    }

    /**
     * getY
     * 
     * Metodo de acceso que regresa la posicion en y del objeto 
     * 
     * @return posY es la <code>posicion en y</code> del objeto.
     * 
     */
    public int getY() {
        return posY;
    }

    /**
     * setImageIcon
     * 
     * Metodo modificador usado para cambiar el icono del objeto
     * 
     * @param imiIcono es el <code>icono</code> del objeto.
     * 
     */
    public void setImageIcon(ImageIcon imiIcono) {
        this.imiIcono = imiIcono;
    }

    /**
     * getImageIcon
     * 
     * Metodo de acceso que regresa el icono del objeto 
     * 
     * @return imiIcono es el <code>icono</code> del objeto.
     * 
     */
    public ImageIcon getImageIcon() {
        return imiIcono;
    }

    /**
     * setImagen
     * 
     * Metodo modificador usado para cambiar el icono de imagen del objeto
     * tomandolo de un objeto imagen
     * 
     * @param imaImagen es la <code>imagen</code> del objeto.
     * 
     */
    public void setImagen(Image imaImagen) {
        this.imiIcono = new ImageIcon(imaImagen);
    }

    /**
     * getImagen
     * 
     * Metodo de acceso que regresa la imagen que representa el icono del objeto
     * 
     * @return la imagen a partide del <code>icono</code> del objeto.
     * 
     */
    public Image getImagen() {
        return imiIcono.getImage();
    }
    
    public void setId(int ObjId) {
        this.ObjId = ObjId;
    }
    public int getObjId() {
        return ObjId;
    }
    /**
     * getAncho
     * 
     * Metodo de acceso que regresa el ancho del icono 
     * 
     * @return un <code>entero</code> que es el ancho del icono.
     * 
     */
    public int getAncho() {
        return imiIcono.getIconWidth();
    }

    /**
     * getAlto
     * 
     * Metodo que  da el alto del icono 
     * 
     * @return un <code>entero</code> que es el alto del icono.
     * 
     */
    public int getAlto() {
        return imiIcono.getIconHeight();
    }
    
    
    /** 
     * colisiona
     * 
     * Metodo para revisar si un objeto <code>Base</code> colisiona con otro
     * esto se logra con un objeto temporal de la clase <code>Rectangle</code>
     * 
     * @param aniObjeto es el objeto <code>Base</code> con el que se compara
     * @return  un valor true si esta colisionando y false si no
     * 
     */
    public boolean colisiona(Base aniParametro) {
        // creo un objeto rectangulo a partir de este objeto Base
        Rectangle recObjeto = new Rectangle(this.getX(),this.getY(),
                this.getAncho(), this.getAlto());
        
        // creo un objeto rectangulo a partir del objeto Base parametro
        Rectangle recParametro = new Rectangle(aniParametro.getX(),
                aniParametro.getY(), aniParametro.getAncho(),
                aniParametro.getAlto());
        
        // si se colisionan regreso verdadero, sino regreso falso
        return recObjeto.intersects(recParametro);
    }
    
    /** 
     * colisiona
     * 
     * Metodo para revisar si un objeto <code>Base</code> colisiona con una
     * coordenada que tiene valor de x y valor de y
     * 
     * @param posX es el valor <code>entero</code> de x
     * @param posY es el valor <code>entero</code> de x
     * @return  un valor true si esta colisionando y false si no
     * 
     */
    public boolean colisiona(int posX, int posY) {
        // creo un objeto rectangulo a partir de este objeto Base
        Rectangle recObjeto = new Rectangle(this.getX(),this.getY(),
                this.getAncho(), this.getAlto());
               
        // si se colisionan regreso verdadero, sino regreso falso
        return recObjeto.contains(posX, posY);
    }    
}