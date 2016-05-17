package clases;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
public class PanelSolitario extends JPanel implements MouseListener,MouseMotionListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ListaCartas maso = new ListaCartas(35,35,70,104);
	ListaCartas pila = new ListaCartas(115,35,70,104);
	ListaCartas otras[]= new ListaCartas[7];
	ListaCartas ganar[]=new ListaCartas[4];
	ListaCartas tmpL = null;
	JLabel fondo = new JLabel(new ImageIcon("imagenes/fondo.jpg"));
	NodoDoble seleccionada;
	int xm=0,ym=0;
	boolean mover=true;
	boolean volti[]={true,false,true,false,false,true,false,false,false,true,false,false,false,false,true,false,false,false,false,false,true,false,false,false,false,false,false,true};	
    int viejas=0,viejas2=0;
    Timer time;
    boolean ban=false;
    
    public PanelSolitario() {
    	int x1=0;
    	setLayout(null);
    	fondo.setBounds(0,0,797,490);
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
    		NodoDoble tmp=maso.primero;
    		int valor=ram.nextInt(13)+1;
    		int valor2=ram2.nextInt(4)+1;
    		
    		boolean estado=false;
    			while(tmp!=null)
    			{
    				if(tmp.valor==valor&&tmp.palo==valor2)
    					estado=true;
    				tmp=tmp.next;
    			}
    			
    			if(estado==false){
    				if(i<volti.length)
    				maso.int_inicio(new NodoDoble(valor,valor2,maso.x,maso.y,volti[i]));
    				else
    					maso.int_inicio(new NodoDoble(valor,valor2,maso.x,maso.y,false));
    				i++;
    				if(i>27)
    				x1+=5;
    			}
		}
    	llenarListas();
    	varajiar();
    	System.out.println ("ultimo valor: "+maso.ultimo.valor+" palo : "+maso.ultimo.palo);
    	setDoubleBuffered(true);
    	addMouseListener(this);
    	addMouseMotionListener(this);
    	repaint();
    }
    
    public void paint(Graphics g)
    {
    	super.paint(g);
    	
    	maso.dibujaAdelante(g);
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
				if(tmp.voltiada==false)
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
				if(tmp.voltiada==false)
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
   		System.out.println ("Tamano de maso:"+maso.tamanio());
   		repaint();	
   	}
   	
   	public void mousePressed(MouseEvent e){
   		if(e.getX()>maso.x&&e.getX()<maso.x+maso.max&&e.getY()>maso.y&&e.getY()<maso.y+maso.may)
   		if(maso.tamanio()==0){
   			
   			NodoDoble tmp = pila.ultimo;
   			int cont=pila.tamanio();
   			while(cont>0){
   				
   				tmp.voltiada=false;
   				tmp.x=maso.x;
   				tmp.y=maso.y;
   				
   				maso.int_fin(new NodoDoble(tmp.valor,tmp.palo,tmp.x,tmp.y,tmp.voltiada));
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
   		NodoDoble tmp = maso.primero;
   		
   		while(tmp!=null)
   		{
   			if(xMouse>tmp.x&&xMouse<tmp.x+tmp.max&&yMouse>tmp.y&&yMouse<tmp.y+tmp.may)
   			{
   				xm=xMouse-tmp.x;
   				ym=yMouse-tmp.y;
   				viejas=pila.x;
   				viejas2=pila.y;
   				pila.int_inicio(new NodoDoble(tmp.valor,tmp.palo,tmp.x,tmp.y,tmp.voltiada));
   				seleccionada=pila.primero;
   				seleccionada.voltiada=true;
   				tmpL=pila;
   				maso.eliminaEn_pos(maso.ret_Pos(tmp.valor,tmp.palo));
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
   				if(otras[i].ultimo.voltiada==false){
   					otras[i].ultimo.voltiada=true;
   					return;
   				}
   				if(tmp.voltiada==true){
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
   		
   		if(tmpL!=pila&&tmpL!=maso){
   		for (int i = 0; i<otras.length; i++) 
   		{
   			if(otras[i].ultimo==null)
   				{
   			if((xMouse>otras[i].x&&xMouse<otras[i].x+otras[i].max&&yMouse>otras[i].y&&yMouse<otras[i].y+otras[i].may)||(xMouse+seleccionada.max>otras[i].x&&xMouse+seleccionada.max<otras[i].x+otras[i].max&&yMouse+seleccionada.may>otras[i].y&&yMouse+seleccionada.may<otras[i].y+otras[i].may)||(yMouse+seleccionada.may>otras[i].y&&yMouse+seleccionada.may<otras[i].y+otras[i].may&&xMouse>otras[i].x&&xMouse<otras[i].x+otras[i].max)||(xMouse+seleccionada.max>otras[i].x&&xMouse+seleccionada.max<otras[i].x+otras[i].max&&yMouse>otras[i].y&&yMouse<otras[i].y+otras[i].may))
   			{
   				
   					NodoDoble tmp2=seleccionada;
   					int x1=0;
   					while(tmp2!=null){
   					if(otras[i].int_OrdAsc(new NodoDoble(tmp2.valor,tmp2.palo,otras[i].x,otras[i].y+x1,tmp2.voltiada)))
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
   					if(otras[i].int_OrdAsc(new NodoDoble(tmp2.valor,tmp2.palo,otras[i].x,otras[i].ultimo.y+20,tmp2.voltiada)))
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
   				
   					
   					if(otras[i].int_OrdAsc(new NodoDoble(seleccionada.valor,seleccionada.palo,otras[i].x,otras[i].y,seleccionada.voltiada)))
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
   				
   					if(otras[i].int_OrdAsc(new NodoDoble(seleccionada.valor,seleccionada.palo,otras[i].x,otras[i].ultimo.y+20,seleccionada.voltiada)))
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
   				if(ganar[i].int_OrdDesc(new NodoDoble(seleccionada.valor,seleccionada.palo,ganar[i].x,ganar[i].y,seleccionada.voltiada)))
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
   			otras[i]=new ListaCartas(x1,148,70,104);
   			x1+=108;
		}
		x1=0;
   		for (int i = 0; i<ganar.length; i++) 
   		{
   			ganar[i]=new ListaCartas(365+x1,20,77,104);
   			x1+=108;	
		}
   	}
   	
   	public void varajiar()
   	{
   		int x1=0;
   		for (int i = 0; i<28; i++) 
   		{
   			NodoDoble car=maso.ret_Nodo(maso.tamanio());
   			
   			if(i==0){
   				otras[0].int_fin(new NodoDoble(car.valor,car.palo,otras[0].x,otras[0].y+x1,car.voltiada));
   			}
   			if(i>0&&i<=2){
   				otras[1].int_fin(new NodoDoble(car.valor,car.palo,otras[1].x,otras[1].y+x1,car.voltiada));
   			}
   			if(i>2&&i<=5){
   				otras[2].int_fin(new NodoDoble(car.valor,car.palo,otras[2].x,otras[2].y+x1,car.voltiada));
   			}
   			if(i>5&&i<=9){
   				otras[3].int_fin(new NodoDoble(car.valor,car.palo,otras[3].x,otras[3].y+x1,car.voltiada));
   			}
   			if(i>9&&i<=14){
   				otras[4].int_fin(new NodoDoble(car.valor,car.palo,otras[4].x,otras[4].y+x1,car.voltiada));
   			}
   			if(i>14&&i<=20){
   				otras[5].int_fin(new NodoDoble(car.valor,car.palo,otras[5].x,otras[5].y+x1,car.voltiada));
   			}
   			if(i>20&&i<=27){
   				otras[6].int_fin(new NodoDoble(car.valor,car.palo,otras[6].x,otras[6].y+x1,car.voltiada));
   			}
   			x1+=5;
   			
   			if(i==0) x1=0; if(i==2) x1=0; if(i==5) x1=0; if(i==9) x1=0; if(i==14) x1=0; if(i==20) x1=0;
   				
   			maso.eliminaEn_pos(maso.tamanio());
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
