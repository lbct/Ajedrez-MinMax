/**
 * Esta clase es la Posicion que puede o no contener una ficha en el tablero
 * @author: Luis Bernardo Caussin Torrez
 * @version: 25/09/2015/A
 */
package modelo;

@SuppressWarnings("serial")
public class Posicion implements java.io.Serializable {

	private Ficha ficha;
	private boolean tieneFicha;
	private int posX;
	private int posY;

	/**
	 * Constructor
	 * @param ficha Guarda una Ficha
	 * @param posX Posicion en X
	 * @param posY Posicion en Y
	 */
	public Posicion(Ficha ficha, int posX, int posY) {
		this.ficha = ficha;
		this.posX = posX;
		this.posY = posY;

		if (ficha == null)
			tieneFicha = false;
		else
			tieneFicha = true;
	}

	/**
	 * 
	 * @return Retorna la posicion en X
	 */
	public int darPosX() {
		return posX;
	}

	/**
	 * 
	 * @return Retorna la posicion en Y
	 */
	public int darPosY() {
		return posY;
	}

	/**
	 * 
	 * @return Retorna True si existe una ficha
	 */
	public boolean posicionConFicha() {
		return tieneFicha;
	}
	
	/**
	 * 
	 * @return Retorna la Ficha de la posicion
	 */

	public Ficha darFicha() {
		return ficha;
	}

	/**
	 * Elimina la ficha existente
	 */
	public void eliminarFicha() {
		this.ficha = null;
		tieneFicha = false;
	}

	/**
	 * Establece una ficha
	 * @param ficha Ficha a establecer
	 */
	public void establecerFicha(Ficha ficha) {
		this.ficha = ficha;
		tieneFicha = true;
	}
}
