
package clases;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class PanelSolitario extends JPanel implements MouseListener,MouseMotionListener{
	JLabel fondo = new JLabel(new ImageIcon("imagenes/fondo.png"));
	private static final long serialVersionUID = 1L;
	ListaCartas mazo = new ListaCartas(580,360,70,104);
	ListaCartas pila = new ListaCartas(668,360,70,104);
	ListaCartas otras[]= new ListaCartas[10];
	ListaCartas ganar[]=new ListaCartas[4];
	ListaCartas tmpL = null;
	
	NodoDoble seleccionada;
	int xm=0,ym=0;
	boolean mover=true;
	boolean volti[]={true,false,true,false,false,true,false,false,false,true,false,false,false,false,true,false,false,false,false,false,true,false,false,false,false,false,false,true,false,false,false,false,false,false,false,true,false,false,false,false,false,false,false,false};	
    int viejas=0,viejas2=0;
    Timer time;
    boolean ban=false;

    public PanelSolitario() {
    	int x1=0;
    	setLayout(null);
    	fondo.setBounds(0,0,797,490);
    	fondo.setSize(1070,600);
    	add(fondo);
    	time=new Timer(15,new ActionListener(){
    		public void actionPerformed(ActionEvent e)
    		{
    			repaint();
    		}
    	});
    		time.start();
    	
    	Random ram=new Random();
    	Random ram2=new Random();
    	
    	for (int i = 0; i<52; ) 
    	{
    		NodoDoble tmp=mazo.primero;
    		int valor=ram.nextInt(13)+1;
    		int valor2=ram2.nextInt(4)+1;
    		
    		boolean estado=false;
    		while(tmp!=null)
    			{
    				if(tmp.valor==valor&&tmp.palo==1)
    					estado=true;
    				tmp=tmp.next;
    			}
    			
    			if(estado==false){
    				if(i<volti.length)
    				mazo.int_inicio(new NodoDoble(valor,valor2,mazo.x,mazo.y,volti[i]));
    				else
    					mazo.int_inicio(new NodoDoble(valor,valor2,mazo.x,mazo.y,false));
    				i++;
    				if(i>35)
    				x1+=5;
    			}
		}
    	llenarListas();
    	barajear();
    	System.out.println ("ultimo valor: "+mazo.ultimo.valor+" palo : "+mazo.ultimo.palo);
    	setDoubleBuffered(true);
    	addMouseListener(this);
    	addMouseMotionListener(this);
    	repaint();
    	} 

    
    public void paint(Graphics g)
    {
    	super.paint(g);
    	
    	mazo.dibujaAdelante(g);
    	pila.dibujaAtras(g);
    	
    	for (int i = 0; i<otras.length; i++) {
    		otras[i].dibujaAdelante(g);
		}
		for (int i = 0; i<ganar.length; i++) {
    		ganar[i].dibujaAdelante(g);
		}
		if(ban==false){
		if(seleccionada!=null)
		seleccionada.dibujar(g);
		}
		 else{
			NodoDoble tmp=seleccionada;
			int x1=0;
			while(tmp!=null){
				if(tmp.dada_la_vuelta==false)
					return ;
				tmp.x=seleccionada.x;
				tmp.y=seleccionada.y+x1;
				tmp.dibujar(g);
				tmp=tmp.next;
					x1+=20;
			}
			
		}
		ganar();
    }
    public void mouseExited(MouseEvent e){
    	
   		mover=false;
    	
   		
   	}
    
    public void mouseEntered(MouseEvent e){
   		mover=true;
   	}
   	
   	public void mouseReleased(MouseEvent e){
   		if(seleccionada!=null)
   		if(cambiarDeLista(seleccionada.x,seleccionada.y)==false){
   		seleccionada.y=viejas2;
   		seleccionada.x=viejas;
   		
   		if(ban==true){
   			NodoDoble tmp=seleccionada;
			int x1=0;
			while(tmp!=null){
				if(tmp.dada_la_vuelta==false)
					return ;
				tmp.x=seleccionada.x;
				tmp.y=seleccionada.y-x1;
				
				tmp=tmp.next;
					x1-=20;
			}
   		}
   		}
   		seleccionada=null;
   		System.out.println ("Tamano de pila:"+pila.tamanio());
   		System.out.println ("Tamano de mazo:"+mazo.tamanio());
   		repaint();	
   	}
   	
   	public void mousePressed(MouseEvent e){
   		if(e.getX()>mazo.x&&e.getX()<mazo.x+mazo.max&&e.getY()>mazo.y&&e.getY()<mazo.y+mazo.may)
   		if(mazo.tamanio()==0){
   			
   			NodoDoble tmp = pila.ultimo;
   			int cont=pila.tamanio();
   			while(cont>0){
   				
   				tmp.dada_la_vuelta=false;
   				tmp.x=mazo.x;
   				tmp.y=mazo.y;
   				
   				mazo.int_fin(new NodoDoble(tmp.valor,tmp.palo,tmp.x,tmp.y,tmp.dada_la_vuelta));
   				cont--;
   				tmp = tmp.ant;
   			}
   			pila.vaciar();
   			return;
   		}
   		
   		obtieneCarta(e.getX(),e.getY());
   		
   		System.out.println (e.getX()+","+e.getY());
   	}
   	
   	public void mouseClicked(MouseEvent e){
   		
   	}
    
    public void mouseMoved(MouseEvent e)
    {
    	if(seleccionada!=null&&mover==true)
    	{
    		seleccionada.x=e.getX()-xm;
    		seleccionada.y=e.getY()-ym;
    		repaint();
    	}    	
    }
    
    public void mouseDragged(MouseEvent e){
    	mouseMoved(e);
    }
    
    public void obtieneCarta(int xMouse,int yMouse){
   		NodoDoble tmp = mazo.primero;
   		
   		while(tmp!=null)
   		{
   			if(xMouse>tmp.x&&xMouse<tmp.x+tmp.max&&yMouse>tmp.y&&yMouse<tmp.y+tmp.may)
   			{
   				xm=xMouse-tmp.x;
   				ym=yMouse-tmp.y;
   				viejas=pila.x;
   				viejas2=pila.y;
   				pila.int_inicio(new NodoDoble(tmp.valor,tmp.palo,tmp.x,tmp.y,tmp.dada_la_vuelta));
   				seleccionada=pila.primero;
   				seleccionada.dada_la_vuelta=true;
   				tmpL=pila;
   				mazo.eliminaEn_pos(mazo.ret_Pos(tmp.valor,tmp.palo));
   				ban=false;
   				return;
   			}
   			tmp=tmp.next;
   		}
   		
   		 tmp = pila.primero;
   		
   		while(tmp!=null)
   		{
   			if(xMouse>tmp.x&&xMouse<tmp.x+tmp.max&&yMouse>tmp.y&&yMouse<tmp.y+tmp.may)
   			{
   				
   				seleccionada=tmp;
   				xm=xMouse-tmp.x;
   				ym=yMouse-tmp.y;
   				viejas=pila.x;
   				viejas2=pila.y;
   				tmpL=pila;
   				ban=false;
   				return;
   				
   			}
   			tmp=tmp.next;
   		}
   		for (int i = 0; i<otras.length; i++) {
   			 tmp = otras[i].ultimo;
   		
   		while(tmp!=null)
   		{
   			if(xMouse>tmp.x&&xMouse<tmp.x+tmp.max&&yMouse>tmp.y&&yMouse<tmp.y+tmp.may)
   			{
   				if(otras[i].ultimo.dada_la_vuelta==false){
   					otras[i].ultimo.dada_la_vuelta=true;
   					return;
   				}
   				if(tmp.dada_la_vuelta==true){
   				seleccionada=tmp;
   				xm=xMouse-tmp.x;
   				ym=yMouse-tmp.y;
   				viejas=tmp.x;
   				viejas2=tmp.y;
   				tmpL=otras[i];
   				ban=true;
   				}
   				return;
   			}
   			tmp=tmp.ant;
   		}
		}
		
		for (int i = 0; i<ganar.length; i++) 
		{
			if(ganar[i].ultimo!=null){
			tmp =ganar[i].ultimo;
			if(xMouse>tmp.x&&xMouse<tmp.x+tmp.max&&yMouse>tmp.y&&yMouse<tmp.y+tmp.may)
   			{
   				seleccionada=tmp;
   				xm=xMouse-tmp.x;
   				ym=yMouse-tmp.y;
   				viejas=ganar[i].x;
   				viejas2=ganar[i].y;
   				tmpL=ganar[i];
   				ban=false;
   				return;
   			}
			}
		}
   	}
   	
   	public boolean cambiarDeLista(int xMouse,int yMouse){
   		
   		if(tmpL!=pila&&tmpL!=mazo){
   		for (int i = 0; i<otras.length; i++) 
   		{
   			if(otras[i].ultimo==null)
   				{
   			if((xMouse>otras[i].x&&xMouse<otras[i].x+otras[i].max&&yMouse>otras[i].y&&yMouse<otras[i].y+otras[i].may)||(xMouse+seleccionada.max>otras[i].x&&xMouse+seleccionada.max<otras[i].x+otras[i].max&&yMouse+seleccionada.may>otras[i].y&&yMouse+seleccionada.may<otras[i].y+otras[i].may)||(yMouse+seleccionada.may>otras[i].y&&yMouse+seleccionada.may<otras[i].y+otras[i].may&&xMouse>otras[i].x&&xMouse<otras[i].x+otras[i].max)||(xMouse+seleccionada.max>otras[i].x&&xMouse+seleccionada.max<otras[i].x+otras[i].max&&yMouse>otras[i].y&&yMouse<otras[i].y+otras[i].may))
   			{
   				
   					NodoDoble tmp2=seleccionada;
   					int x1=0;
   					while(tmp2!=null){
   					if(otras[i].int_OrdAsc(new NodoDoble(tmp2.valor,tmp2.palo,otras[i].x,otras[i].y+x1,tmp2.dada_la_vuelta)))
   					{
   					System.out.println ("Entro");
   					System.out.println ("posicion a eliminar: "+tmpL.ret_Pos(tmp2.valor,tmp2.palo));
   						
   						int v=tmp2.valor;
   						int p=tmp2.palo;					
   					tmpL.eliminaEn_pos(tmpL.ret_Pos(v,p));
   					
   					System.out.println ("tamanio "+	tmpL.tamanio());
   					
   					} else return false;
   					tmp2=tmp2.next;
   					x1+=20;
   					}return true;
   					}
   				}
   				 else{
   				 	if((xMouse>otras[i].ultimo.x&&xMouse<otras[i].ultimo.x+otras[i].ultimo.max&&yMouse>otras[i].ultimo.y&&yMouse<otras[i].ultimo.y+otras[i].ultimo.may)||(xMouse+seleccionada.max>otras[i].ultimo.x&&xMouse+seleccionada.max<otras[i].ultimo.x+otras[i].ultimo.max&&yMouse+seleccionada.may>otras[i].ultimo.y&&yMouse+seleccionada.may<otras[i].ultimo.y+otras[i].ultimo.may)||(yMouse+seleccionada.may>otras[i].ultimo.y&&yMouse+seleccionada.may<otras[i].ultimo.y+otras[i].ultimo.may&&xMouse>otras[i].ultimo.x&&xMouse<otras[i].ultimo.x+otras[i].ultimo.max)||(xMouse+seleccionada.max>otras[i].ultimo.x&&xMouse+seleccionada.max<otras[i].ultimo.x+otras[i].max&&yMouse>otras[i].ultimo.y&&yMouse<otras[i].ultimo.y+otras[i].ultimo.may))
   					{
   					NodoDoble tmp2=seleccionada;
   					while(tmp2!=null){
   					if(otras[i].int_OrdAsc(new NodoDoble(tmp2.valor,tmp2.palo,otras[i].x,otras[i].ultimo.y+20,tmp2.dada_la_vuelta)))
   					{
   					System.out.println ("Entro");
   					System.out.println ("posicion a eliminar: "+tmpL.ret_Pos(tmp2.valor,tmp2.palo));
   						
   						int v=tmp2.valor;
   						int p=tmp2.palo;					
   					tmpL.eliminaEn_pos(tmpL.ret_Pos(v,p));
   					
   					System.out.println ("tamanio "+	tmpL.tamanio());
   					} else return false;
   					tmp2=tmp2.next;
   					}
   					return true;
   					}
   				}
   				}	
   		}else{
   			for (int i = 0; i<otras.length; i++) 
   		{
   			if(otras[i].ultimo==null)
   				{
   			if((xMouse>otras[i].x&&xMouse<otras[i].x+otras[i].max&&yMouse>otras[i].y&&yMouse<otras[i].y+otras[i].may)||(xMouse+seleccionada.max>otras[i].x&&xMouse+seleccionada.max<otras[i].x+otras[i].max&&yMouse+seleccionada.may>otras[i].y&&yMouse+seleccionada.may<otras[i].y+otras[i].may)||(yMouse+seleccionada.may>otras[i].y&&yMouse+seleccionada.may<otras[i].y+otras[i].may&&xMouse>otras[i].x&&xMouse<otras[i].x+otras[i].max)||(xMouse+seleccionada.max>otras[i].x&&xMouse+seleccionada.max<otras[i].x+otras[i].max&&yMouse>otras[i].y&&yMouse<otras[i].y+otras[i].may))
   			{
   				
   					
   					if(otras[i].int_OrdAsc(new NodoDoble(seleccionada.valor,seleccionada.palo,otras[i].x,otras[i].y,seleccionada.dada_la_vuelta)))
   					{
   					System.out.println ("Entro");
   					System.out.println ("posicion a eliminar: "+tmpL.ret_Pos(seleccionada.valor,seleccionada.palo));
   						
   						int v=seleccionada.valor;
   						int p=seleccionada.palo;
   					    seleccionada=null;	
   					   	tmpL.eli_inicio();				
   					tmpL.eliminaEn_pos(tmpL.ret_Pos(v,p));
   					
   					System.out.println ("tamanio "+	tmpL.tamanio());
   					return true;
   					}
   			}
   				}
   				 else{
   				 	if((xMouse>otras[i].ultimo.x&&xMouse<otras[i].ultimo.x+otras[i].ultimo.max&&yMouse>otras[i].ultimo.y&&yMouse<otras[i].ultimo.y+otras[i].ultimo.may)||(xMouse+seleccionada.max>otras[i].ultimo.x&&xMouse+seleccionada.max<otras[i].ultimo.x+otras[i].ultimo.max&&yMouse+seleccionada.may>otras[i].ultimo.y&&yMouse+seleccionada.may<otras[i].ultimo.y+otras[i].ultimo.may)||(yMouse+seleccionada.may>otras[i].ultimo.y&&yMouse+seleccionada.may<otras[i].ultimo.y+otras[i].ultimo.may&&xMouse>otras[i].ultimo.x&&xMouse<otras[i].ultimo.x+otras[i].ultimo.max)||(xMouse+seleccionada.max>otras[i].ultimo.x&&xMouse+seleccionada.max<otras[i].ultimo.x+otras[i].max&&yMouse>otras[i].ultimo.y&&yMouse<otras[i].ultimo.y+otras[i].ultimo.may))
   					{
   				
   					if(otras[i].int_OrdAsc(new NodoDoble(seleccionada.valor,seleccionada.palo,otras[i].x,otras[i].ultimo.y+20,seleccionada.dada_la_vuelta)))
   					{
   					System.out.println ("Entro");
   					System.out.println ("posicion a eliminar: "+tmpL.ret_Pos(seleccionada.valor,seleccionada.palo));
   						
   						int v=seleccionada.valor;
   						int p=seleccionada.palo;
   					   seleccionada=null;	
   					   	tmpL.eli_inicio();				
   					tmpL.eliminaEn_pos(tmpL.ret_Pos(v,p));
   					
   					System.out.println ("tamanio "+	tmpL.tamanio());
   					return true;
   					}
   				}
   				}	
   		}
   		}
   		
   		for (int i = 0; i<ganar.length; i++) {
   			if((xMouse>ganar[i].x&&xMouse<ganar[i].x+ganar[i].max&&yMouse>ganar[i].y&&yMouse<ganar[i].y+ganar[i].may)||(xMouse+seleccionada.max>ganar[i].x&&xMouse+seleccionada.max<ganar[i].x+ganar[i].max&&yMouse+seleccionada.may>ganar[i].y&&yMouse+seleccionada.may<ganar[i].y+ganar[i].may)||(yMouse+seleccionada.may>ganar[i].y&&yMouse+seleccionada.may<ganar[i].y+ganar[i].may&&xMouse>ganar[i].x&&xMouse<ganar[i].x+ganar[i].max)||(xMouse+seleccionada.max>ganar[i].x&&xMouse+seleccionada.max<ganar[i].x+ganar[i].max&&yMouse>ganar[i].y&&yMouse<ganar[i].y+ganar[i].may))
   			{
   				if(ganar[i].int_OrdDesc(new NodoDoble(seleccionada.valor,seleccionada.palo,ganar[i].x,ganar[i].y,seleccionada.dada_la_vuelta)))
   				{
   					int v=seleccionada.valor;
   					int p=seleccionada.palo;
   					seleccionada=null;					
   					tmpL.eliminaEn_pos(tmpL.ret_Pos(v,p));
   					System.out.println ("Entra");
   					return true;
   				}
   			}
		}
		return false;
   		
   	}
   	
   	public void llenarListas()
   	{
   		int x1=41;
   		for (int i = 0; i<otras.length; i++) 
   		{
   			otras[i]=new ListaCartas(x1,70,70,104);
   			x1+=108;
		}
		x1=0;
   		for (int i = 0; i<ganar.length; i++) 
   		{
   			ganar[i]=new ListaCartas(365+x1,20,77,104);
   			x1+=108;	
		}
   	}
   	
   	public void barajear()
   	{
   		int x1=0;
   		for (int i = 0; i<41; i++) 
   		{
   			NodoDoble car=mazo.ret_Nodo(mazo.tamanio());
   			
   			if(i==0){
   				otras[0].int_fin(new NodoDoble(car.valor,car.palo,otras[0].x,otras[0].y+x1,car.dada_la_vuelta));
   			}
   			if(i>0&&i<=2){
   				otras[1].int_fin(new NodoDoble(car.valor,car.palo,otras[1].x,otras[1].y+x1,car.dada_la_vuelta));
   			}
   			if(i>2&&i<=5){
   				otras[2].int_fin(new NodoDoble(car.valor,car.palo,otras[2].x,otras[2].y+x1,car.dada_la_vuelta));
   			}
   			if(i>5&&i<=9){
   				otras[3].int_fin(new NodoDoble(car.valor,car.palo,otras[3].x,otras[3].y+x1,car.dada_la_vuelta));
   			}
   			if(i>9&&i<=14){
   				otras[4].int_fin(new NodoDoble(car.valor,car.palo,otras[4].x,otras[4].y+x1,car.dada_la_vuelta));
   			}
   			if(i>14&&i<=20){
   				otras[5].int_fin(new NodoDoble(car.valor,car.palo,otras[5].x,otras[5].y+x1,car.dada_la_vuelta));
   			}
   			if(i>20&&i<=27){
   				otras[6].int_fin(new NodoDoble(car.valor,car.palo,otras[6].x,otras[6].y+x1,car.dada_la_vuelta));
   			}//añadir filas del mismo tipo para añadir mas columnas
   			if(i>27&&i<=35){
   				otras[7].int_fin(new NodoDoble(car.valor,car.palo,otras[7].x,otras[7].y+x1,car.dada_la_vuelta));
   			}
   			if(i>35&&i<=42){
   				otras[8].int_fin(new NodoDoble(car.valor,car.palo,otras[8].x,otras[8].y+x1,car.dada_la_vuelta));
   			}
   			if(i>42&&i<=49){
   				otras[9].int_fin(new NodoDoble(car.valor,car.palo,otras[9].x,otras[9].y+x1,car.dada_la_vuelta));
   			}
   			x1+=10;
   		
   			
   			if(i==0) x1=0; if(i==2) x1=0; if(i==5) x1=0; if(i==9) x1=0;if(i==14) x1=0; if(i==20) x1=0; if(i==27) x1=0;if(i==35) x1=0;if(i==42) x1=0;if(i==49) x1=0;
   				
   			mazo.eliminaEn_pos(mazo.tamanio());
		}
   		
   	}
   	
   	public void ganar(){
   		if(ganar[0].tamanio()==13&&ganar[1].tamanio()==13&&ganar[2].tamanio()==13&&ganar[3].tamanio()==13)
   		{
   			time.stop();
   			JOptionPane.showMessageDialog(null,"Has Ganado, Felicidades.");
   			System.exit(0);
   		}
   	}
}
