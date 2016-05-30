
 package clases;
 import javax.swing.*;
 import java.awt.*;


public class NodoDoble extends Canvas
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	NodoDoble ant;
	int valor;
	int palo = 2;
	NodoDoble next;
	Image carta;
	Image vuelta;
	boolean dada_la_vuelta;
	int x,y,max=78,may=98;//78 y 98
	boolean dibu=true;
	
	public NodoDoble(int v,int pal,int x1,int y1,boolean volteada)
	{
		valor = v;
		palo=2;
		pal=palo;
		x=x1;
		y=y1;
		dada_la_vuelta=volteada;
		cargarImagen();
	}
    
    public NodoDoble(NodoDoble a,int v,int pal,int x1,int y1,boolean volti, NodoDoble s)
    {
    	ant = a;
    	valor = v;
		palo=2;
    	pal=palo;
		x=x1;
		y=y1;
		dada_la_vuelta=volti;
    	next = s;
    	cargarImagen();
    }
    
    public void cargarImagen()
    {
    	String nombre="imagenes/palo"+palo+"_"+valor+".JPG";
    	System.out.println (nombre);
    	carta=new ImageIcon(nombre).getImage();
		vuelta=new ImageIcon("imagenes/dorsal_spider2.png").getImage();
    }
    
    public void dibujar(Graphics g)
    {
    	if(dibu)
    	if(dada_la_vuelta==false){
    		g.drawImage(vuelta,x,y,this);
    	}	else{
    		g.drawImage(carta,x,y,this);
    	}
    }
    
    public String nombreDeCarta()
    {
    	String nombre="";
    	if(valor==1)
    	{
    		nombre+="As";
    	}
    	
    	if(valor==2){
    		nombre+="Dos";
    	}
    	
    	if(palo==1){
    		nombre+="de Trebol";
    	}
    	/*else
    	if(palo==2){
    		nombre+="de Diamantes";
    	}else
    	if(palo==3){
    		nombre+="de Corazones";
    	}else
    	if(palo==4){
    		nombre+="de Espadas";
    	}*/
    	return nombre;	
    }
    
}
