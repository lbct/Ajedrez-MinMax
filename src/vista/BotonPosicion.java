/**
 * Esta clase es la interfaz grafica de cada ficha
 * @author: Luis Bernardo Caussin Torrez
 * @version: 27/04/2015/A
 */
package vista;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class BotonPosicion extends JButton implements java.io.Serializable {

	private int x, y;
	private boolean botonConFicha;
	private String id;
	private Color color;
	private boolean fichaBlanca;
	private boolean dirWindows;
	private ImageIcon icono;
	/**
	 * Constructor
	 * 
	 * @param x
	 *            posicion en X del boton
	 * @param y
	 *            posicion en Y del boton
	 * @param colorNegro
	 *            variable logica que indica el color del boton
	 */

	public BotonPosicion(int x, int y, boolean colorNegro) {
		super();
		setBounds(y * 60, x * 60, 60, 60);
		this.x = x;
		this.y = y;
		this.color = colorNegro ? Color.BLACK : Color.WHITE;
		setBackground(color);
		icono = darIcono(this.x, this.y);
		setIcon(icono);
		if (icono != null)
			botonConFicha = true;
		else
			botonConFicha = false;
		if (!id.equals(""))
			fichaBlanca = id.charAt(id.length() - 1) == 'B' ? true : false;
	}
	
	/**
	 * Cambia la imagen por una Reina
	 */
	public void cambiarAReina(){
		ImageIcon icono = darIcono(fichaBlanca?0:7, 4);
		setIcon(icono);
	}
	
	/**
	 * 
	 * @return Devuelve el icono del boton
	 */
	
	public ImageIcon obtenerIcono(){
		return icono;
	}

	/**
	 * Cambia el color del boton a rojo si existe una ficha o anaranjado si no
	 */

	public void colorear() {
		setBackground(botonConFicha?Color.RED:Color.ORANGE);
	}
	
	/**
	 * Cambia el color del boton a Amarillo o Anaranjado
	 * @param color Indica que color se usara
	 */
	
	public void colorear(boolean color){
		setBackground(color?Color.YELLOW:Color.ORANGE);
	}
	

	/**
	 * Restaura el color de la ficha
	 */

	public void restaurarColor() {
		setBackground(color);
	}

	/**
	 * Devuelve el tipo de ficha del boton
	 * 
	 * @return devuelve una variable de tipo String
	 */
	public String id() {
		return id;
	}

	/**
	 * Verifica si tiene ficha
	 * 
	 * @return devuelve true si existe una ficha
	 */

	public boolean tieneFicha() {
		return botonConFicha;
	}

	/**
	 * Devuelve la posicion en X
	 * 
	 * @return devuelve un entero positivo
	 */
	public int darX() {
		return x;
	}

	/**
	 * Devuelve la posicion en Y
	 * 
	 * @return devuelve un entero positivo
	 */
	public int darY() {
		return y;
	}

	/**
	 * Verifica si la ficha es blanca
	 * 
	 * @return devuelve true si la ficha es blanca
	 */
	public boolean fichaBlanca() {
		return fichaBlanca;
	}

	/**
	 * Establece una ficha en el Boton
	 * 
	 * @param id
	 *            indica el tipo de ficha
	 */
	public void establecerFicha(String id) {
		this.id = id;
		setIcon(new ImageIcon("Iconos" + (dirWindows ? "\\" : "/") + id
				+ ".png"));
		botonConFicha = true;
		fichaBlanca = id.charAt(id.length() - 1) == 'B' ? true : false;
	}

	/**
	 * borra la ficha del boton
	 */
	public void borrarFicha() {
		id = "";
		setIcon(null);
		botonConFicha = false;
	}

	/**
	 * reemplaza la ficha actual del boton por otra
	 * 
	 * @param nuevo
	 *            nuevo boton a reemplazar
	 */
	public void reemplazar(BotonPosicion nuevo) {
		if (botonConFicha)
			nuevo.establecerFicha(id);
		else
			nuevo.borrarFicha();
		this.borrarFicha();
	}

	/**
	 * devuelve una direccion para el icono del boton
	 * 
	 * @param x
	 *            posicion en X de la ficha
	 * @param y
	 *            posicion en Y de la ficha
	 * @param windows
	 *            variable logica que indica si la direccion es del Sistema
	 *            Operativo Windows o no
	 * @return devuelve un String con la direccion
	 */
	private String direccionIcono(int x, int y, boolean windows) {
		id = "";
		if (x == 1 || x == 6)
			id = x == 1 ? "PEONB" : "PEONN";
		else if ((x == 0 && (y == 0 || y == 7))
				|| (x == 7 && (y == 0 || y == 7)))
			id = x == 0 ? "TORREB" : "TORREN";
		else if ((x == 0 && (y == 1 || y == 6))
				|| (x == 7 && (y == 1 || y == 6)))
			id = x == 0 ? "CABALLOB" : "CABALLON";
		else if ((x == 0 && (y == 2 || y == 5))
				|| (x == 7 && (y == 2 || y == 5)))
			id = x == 0 ? "ALFILB" : "ALFILN";
		else if ((x == 0 || x == 7) && y == 3)
			id = x == 0 ? "REYB" : "REYN";
		else if ((x == 0 || x == 7) && y == 4)
			id = x == 0 ? "REINAB" : "REINAN";
		return id.equals("") ? "" : "Iconos" + (windows ? "\\" : "/") + id
				+ ".png";
	}

	/**
	 * Devuelve una imagen para el icono del boton
	 * 
	 * @param x
	 *            Posicion en X de la ficha
	 * @param y
	 *            posicion en Y de la ficha
	 * @return Devuelve una variable de tipo ImageIcon
	 */
	private ImageIcon darIcono(int x, int y) {
		ImageIcon icono = null;
		boolean terminar = false;
		boolean windows = true;
		String dirIcono = direccionIcono(x, y, windows);
		if (dirIcono.equals(""))
			terminar = true;
		else {
			BufferedImage image = null;
			while (!terminar) {
				try {
					image = ImageIO.read(new File(dirIcono));
					terminar = true;
				} catch (IOException e) {
					if (windows) {
						windows = false;
						dirWindows = false;
						dirIcono = direccionIcono(x, y, false);
					} else {
						System.out
								.println("Error, no se encuentra la carpeta de imagenes: "
										+ dirIcono);
						terminar = true;
					}
					e.printStackTrace();
				}
			}
			if (windows)
				dirWindows = true;
			icono = new ImageIcon(image);
		}
		return icono;
	}
}
