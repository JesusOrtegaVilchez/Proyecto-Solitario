
package clases;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
public class Ejecuta extends JFrame
{
	
	
	
	private static final long serialVersionUID = 1L;
	JMenuBar menu=  new JMenuBar();
	PanelSolitario panel = new PanelSolitario();
	JMenuItem men= new JMenuItem("Partida Nueva");
	JMenuItem men3= new JMenuItem("Versión");	
	JMenuItem men4= new JMenuItem("Manual de Usuario");
	JMenuItem men2= new JMenuItem("Salir");
	
    public Ejecuta() 
    {
    	Image icon = new ImageIcon(getClass().getResource("dorsal_spider.png")).getImage();
    	setIconImage(icon);
    	setLayout(new BorderLayout());
    	add(panel,BorderLayout.CENTER);
    	this.setJMenuBar(menu);
    	JMenu menu1=new JMenu("Opciones");
    	men.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e)
    		{
    			new Ejecuta();
    			dispose();
    		}
    	});
    	JMenu menu2=new JMenu("Acerca del juego");
    	men.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e)
    		{
    			new Ejecuta();
    			dispose();
    		}
    	});
    	menu1.add(men);
    	men2.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e)
    		{
    		System.exit(0);
    		}
    	});
    	men3.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e)
    		{
    			JOptionPane.showMessageDialog(null,"DAM software corporation\nSolitario Spider en Java versión Sprint_3");
    		}
    	});
    	men4.addActionListener(new ActionListener(){
    		public void actionPerformed(ActionEvent e)
    		{
    			JOptionPane.showMessageDialog(null,"Bienvenido al Solitario Spider version alternativa.\n\t Desarrollada por DAM Software Corporation"
    					+ "\n\tInstrucciones: "
    					+ "\n\t\t\t-Dispones de 7 mazos"
    					+ "\n\t\t\t-Forma tantos como puedas en busca de las 4 ases y posicionalas en los 4 espacios"
    					+ "\n\t\t\t-Movimientos Ilimitados"
    					+ "\n\t\t\t-Para ganar posiciona los 4 AS en las posiciones"
    					+ "\n\t\t\t-¡¡SUERTE!!");
    		}
    	});
    	setResizable(false);
    	menu2.add(men3);
    	menu2.add(men4);
    	menu1.add(men2);
    	menu.add(menu1);
    	menu.add(menu2);
    	panel.setBackground(Color.green);
    	setSize(795,490);//795
    	
    	setLocationRelativeTo(null);
    	
    	setVisible(true);
    	this.setTitle("Solitario Spider");
    	
    	
        
    }
    
    public static void main (String[] args) {
    	
	new Ejecuta();
	}
}
