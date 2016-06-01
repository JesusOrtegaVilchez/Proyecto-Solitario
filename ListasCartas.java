
package clases;

import java.awt.Graphics;
public class ListaCartas
{
	NodoDoble primero=null;
	NodoDoble ultimo=null;
	int x=0,y;
	int max,may;
	public ListaCartas(int x1,int y1,int mx,int my)
	{
		x=x1;
		y=y1;
		max=mx;
		may=my;
	}
	void int_inicio(NodoDoble nuevo)
	{
		if(vacia()==true)
		{
			primero=ultimo=nuevo;
		}
		else
		{
			nuevo.next=primero;
			primero.ant=nuevo;
			primero=nuevo;
		}
	}
	
	void int_fin(NodoDoble nuevo)
	{
		if(vacia()==true)
		{
			int_inicio(nuevo);
		}
		else
		{
			ultimo.next = nuevo;
			nuevo.ant = ultimo;
			ultimo = nuevo;
		}
	}
	
	void eli_inicio()
	{
		if(primero==ultimo)
			primero=ultimo=null;
		else
		{
			NodoDoble tmp = primero.next;
			primero = null;
			primero = tmp;
		}
	}
	
	void eli_final()
	{
		if(primero==ultimo)
			ultimo=primero=null;
		else
		{
			NodoDoble tmp = ultimo.ant;
			tmp.next = null;
			ultimo = tmp;
		}
	}
	
	int tamanio()
	{
		int i=0;
		
		NodoDoble tmp = primero;
		while(tmp!=null)
		{
			i++;
			tmp = tmp.next;
		}
		return i;
	}
	
	NodoDoble ret_Nodo(int pos) //Retorna el Nodo(valor) segun posicion
	{
		NodoDoble tmp = primero;
		int cuenta=1;
		NodoDoble vNod=null;
		
		while(tmp!=null)
		{
			if(cuenta==pos)
			{
				return tmp;
			}
			cuenta++;
			tmp = tmp.next;
		}
		return vNod;
	}
	
	int ret_Pos(int valor,int palo)
	{
		NodoDoble tmp = primero;
		int cuenta=1, vPos=0;
		
		while(tmp!=null)
		{
			if(tmp.valor==valor&&tmp.palo==palo)
			{
				vPos = cuenta;
			}
			cuenta++;
			tmp = tmp.next;
		}
		return vPos;
	}
	
	void imprime()
	{
		NodoDoble tmp=primero;
		
		while(tmp!=null)
		{
			System.out.println(tmp.valor+"Valor Carta,"+tmp.palo+"Palo");
			tmp = tmp.next;
		}
	}
	
	void imp_Inverso()
	{
		NodoDoble tmp=ultimo;
		
		while(tmp!=null)
		{
			System.out.println(tmp.valor);
			tmp = tmp.ant;
		}
	}
	
	void eliminaEn_pos(int pos)
	{
		if(primero==ultimo||pos ==1)
		{
			eli_inicio();
		}
		else if(primero!=null&&pos==tamanio())
		{
			eli_final();
		}
		else if(primero!=null&&pos<tamanio())
		{
			NodoDoble tmp = primero;
			int cuenta=1;
			
			while(tmp!=null)
			{
				if(cuenta==pos)
				{
					NodoDoble tmp2 = tmp.next;
					tmp2.ant = tmp.ant;
					tmp2.ant.next = tmp2;
					tmp =tmp2;
					System.out.println ("Eliminado");
					return ;
				}
				tmp=tmp.next;
				cuenta++;
			}
		}
		else{System.out.println("Nodo No Ingresado");}
	}
	
	void int_pos(int pos, NodoDoble info)
	{
		if(primero==null&&pos==1||primero!=null&&pos==1)
		{
			int_inicio(info);
		}
		else if(primero!=null&&pos==tamanio()+1)
		{
			int_fin(info);
		}
		else if(primero!=null&&pos<=tamanio())
		{
			NodoDoble tmp = primero;
			int cuenta=1;
			
			while(tmp!=null)
			{
				if(cuenta==pos)
				{
					info.next=tmp;
					info.ant=tmp.ant;
					tmp.ant.next=info;
					tmp.ant=info;
				}
				tmp=tmp.next;
				cuenta++;
			}
		}
		else{System.out.println("Nodo No Ingresado");}
	}
	
	boolean  int_OrdDesc(NodoDoble info)
	{
		if(primero==null)
		{
			if(info.valor==1){
			int_inicio(info);
			return true;
			} else return false;
		}
		else
		{
						if(info.valor==ultimo.valor+1&&ultimo.palo==info.palo)
						{
						int_fin(info);
						return true;
						}
					else
					{
						if(info.valor==ultimo.valor-1&&ultimo.palo==info.palo)
						{
						int_fin(info);
						return true;
						}
					}
				}
			
		
		return false;		
	}
	
	boolean int_OrdAsc(NodoDoble info)
	{
		
		if(primero==null)
		{
			if(info.valor==13){
			int_inicio(info);
			return true;
			} 
			else return false;
		}
			
	
		if(info.palo==2)
					{
						if(info.valor==ultimo.valor-1)
						{
						int_fin(info);
						return true;
						}
					} 
					
		return false;
}


	
	boolean vacia()
	{
		if(primero==null&&ultimo==null)
			return true;
		return false;
	}
	
	void vaciar()
	{
		primero = ultimo = null;
	}
	
	void dibujaAtras(Graphics g)
	{
		NodoDoble tmp=ultimo;
		
		while(tmp!=null)
		{
			tmp.dibujar(g);
			tmp = tmp.ant;
		}
	}
	
	void dibujaAdelante(Graphics g)
	{
		NodoDoble tmp=primero;
		
		while(tmp!=null)
		{
			tmp.dibujar(g);
			tmp = tmp.next;
		}
	}
}
