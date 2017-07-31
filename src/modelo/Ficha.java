/**
 * Esta clase es la que contiene los metodos para la ficha de ajedrez
 * @author: Luis Bernardo Caussin Torrez
 * @version: 25/09/2015/A
 */
package modelo;

@SuppressWarnings("serial")
public class Ficha implements java.io.Serializable {

	private float valor;
	private boolean fichaBlanca;
	private boolean primerMovimiento;
	private String nombre;

	/**
	 * Constructor
	 * @param valor Guarda el valor de la ficha
	 * @param fichaBlanca Indica si la ficha es Blanca o negra
	 * @param nombre Guarda el nombre de la ficha
	 */
	
	public Ficha(float valor, boolean fichaBlanca, String nombre) {
		this.valor = valor;
		this.fichaBlanca = fichaBlanca;
		this.nombre = nombre;
		this.primerMovimiento = false;
	}
	
	/**
	 * @return Retorna True si el primer movimiento de la ficha fue realizado
	 */
	
	public boolean primerMovimientoRealizado(){
		return primerMovimiento;
	}
	
	/**
	 * Metodo que verifica si el movimiento dado es valido para la ficha
	 * @param xi Indica la posicion inicial en X
	 * @param yi Indica la posicion inicial en Y
	 * @param xf Indica la posicion final en X
	 * @param yf Indica la posicion final en Y
	 * @return Devuelve true si el movimiento es valido
	 */

	public boolean movimientoValido(int xi, int yi, int xf, int yf) {
		boolean valido = false;
		switch (nombre) {
		case "PEON":
			valido = (((Math.abs(xf - xi) == 1 && (Math.abs(yf - yi) == 1 || yf == yi)) || (Math
					.abs(xf - xi) == 2 && !primerMovimiento && yi == yf)))
					&& ((fichaBlanca && (xf > xi)) || (!fichaBlanca && (xf < xi)));
			break;
		case "TORRE":
			valido = (xi == xf && yi != yf) || (yi == yf && xi != xf);
			break;
		case "CABALLO":
			valido = (Math.abs(xf - xi) == 1 && Math.abs(yf - yi) == 2)
			|| (Math.abs(xf - xi) == 2 && Math.abs(yf - yi) == 1);
			break;
		case "ALFIL":
			valido = Math.abs(xf - xi) == Math.abs(yf - yi);
			break;
		case "REY":
			valido = Math.abs(xf - xi) < 2 && Math.abs(yf - yi) < 2;
			break;
		case "REINA":
			valido = (Math.abs(xf - xi) == Math.abs(yf - yi))
					|| ((xi == xf && yi != yf) || (yi == yf && xi != xf));
			break;
		}
		return valido;
	}
	
	/**
	 * @return Retorna el valor de la ficha
	 */

	public float darValor() {
		return valor;
	}

	/**
	 * @return Retorna True si la ficha es de color blanco
	 */
	public boolean blanca() {
		return fichaBlanca;
	}
	
	/**
	 * @return Devuelve el nombre de la ficha
	 */

	public String darNombre() {
		return nombre;
	}

	/**
	 * Metodo que establece el primer movimiento de la ficha
	 */
	public void movimientoRealizado() {
		this.primerMovimiento = true;
	}
}
