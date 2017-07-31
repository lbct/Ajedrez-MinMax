/**
 * Esta clase realiza los movimientos del Jugador
 * @author: Luis Bernardo Caussin Torrez
 * @version: 25/09/2015/A
 */
package modelo;

import java.util.Random;

@SuppressWarnings("serial")
public class Jugador implements java.io.Serializable {

	private Tablero tablero;
	private boolean turnoBlancas;
	private boolean termina;

	/**
	 * Constructor
	 */
	public Jugador() {
		turnoBlancas = new Random().nextBoolean();
		termina = false;
		tablero = new Tablero();
	}
	
	/**
	 * Constructor que Inicializa la clase con un tablero ya existente
	 * @param tab Tablero utilizado para inicializar la clase
	 */
	public Jugador(Tablero tab){
		turnoBlancas = new Random().nextBoolean();
		termina = false;
		tablero = tab;
	}
	
	/**
	 * Verifica si en una posicion especifica existe una Ficha peon
	 * @param x Posicion del peon en X
	 * @param y Posicion del peon en Y
	 * @return Devuelve True si la posicion pertenece a un Peon
	 */
	public boolean esPeon(int x, int y){
		boolean valido = false;
		if(tablero.darPosicionTablero(x, y).posicionConFicha() && tablero.darPosicionTablero(x, y).darFicha().darNombre().equals("PEON"))
			valido = true;
		return valido;
	}
	
	/**
	 * Verifica si en una posicion especifica existe una Ficha torre
	 * @param x Posicion de la torre en X
	 * @param y Posicion de la torre Y
	 * @return Devuelve True si la posicion pertenece a una Torre
	 */
	public boolean esTorre(int x, int y){
		boolean valido = false;
		if(tablero.darPosicionTablero(x, y).posicionConFicha() && tablero.darPosicionTablero(x, y).darFicha().darNombre().equals("TORRE"))
			valido = true;
		return valido;
	}

	/**
	 * Realiza las operaciones necesarias para mover una ficha en el tablero
	 * @param xi Posicion inicial de la ficha en X
	 * @param yi Posicion inicial de la ficha en Y
	 * @param xf Posicion final de la ficha en X
	 * @param yf Posicion final de la ficha en Y
	 * @return Devuelve True si el movimiento es valido
	 */
	public boolean moverFicha(int xi, int yi, int xf, int yf) {
		boolean valido = false;
		if (tablero.darPosicionTablero(xi, yi).posicionConFicha()
				&& tablero.darPosicionTablero(xi, yi).darFicha().blanca() == turnoBlancas
				&& tablero.posicionValida(xi, yi, xf, yf) && !reyEnJaque(xi, yi, xf, yf)) {
			if (tablero.darPosicionTablero(xf, yf).posicionConFicha()
					&& tablero.darPosicionTablero(xf, yf).darFicha()
							.darNombre().equals("REY")) {
				termina = true;
			} else {
				realizarMovimiento(xi, yi, xf, yf);
				valido = true;
			}
			turnoBlancas = !turnoBlancas;
		}
		else if(tablero.enroqueValido(xi, yi, xf, yf) && tablero.darPosicionTablero(xi, yi).darFicha().blanca() == turnoBlancas
				&& !reyEnJaque(xi, yi, xf, yf)){
			tablero.darPosicionTablero(xf, yf).establecerFicha(tablero.darPosicionTablero(xi, yi).darFicha());
			tablero.actualizarPosicionRey(tablero.darPosicionTablero(xi, yi).darFicha().blanca(), xf, yf);
			tablero.darPosicionTablero(xi, yi+(yf>yi?1:-1)).establecerFicha(tablero.darPosicionTablero(xi, (yf>yi?7:0)).darFicha());
			tablero.darPosicionTablero(xi, (yf>yi?7:0)).eliminarFicha();
			tablero.darPosicionTablero(xi, yi).eliminarFicha();
			valido = true;
			turnoBlancas = !turnoBlancas;
		}
		return valido;
	}
	
	/**
	 * Verifica si existe JaqueMate
	 */
	
	public void verificarJaqueMate(){
		boolean jaqueMate = true;
		int i=0,j=0;
		while(i<8 && jaqueMate){
			while(j<8 && jaqueMate){
				if(tablero.darPosicionTablero(i, j).posicionConFicha() && tablero.darPosicionTablero(i, j).darFicha().blanca() == false){
					jaqueMate = !tablero.fichaMovible(i, j);
				}
				j++;
			}
				j=0;
				i++;
		}
		this.termina = jaqueMate;
	}
	
	/**
	 * Mueve una ficha a la fuerza, sin verificar si es valido
	 * @param xi Posicion inicial de la ficha en X
	 * @param yi Posicion inicial de la ficha en Y
	 * @param xf Posicion final de la ficha en X
	 * @param yf Posicion final de la ficha en Y
	 */

	public void mover(int xi, int yi, int xf, int yf) {
		if (tablero.darPosicionTablero(xi, yi).posicionConFicha())
			realizarMovimiento(xi, yi, xf, yf, false);
		else {
			tablero.darPosicionTablero(xf, yf).eliminarFicha();
		}
	}
	
	/**
	 * Realiza el movimiento de la ficha que ya ha sido controlada
	 * @param xi Posicion inicial de la ficha en X
	 * @param yi Posicion inicial de la ficha en Y
	 * @param xf Posicion final de la ficha en X
	 * @param yf Posicion final de la ficha en Y
	 */
	
	private void realizarMovimiento(int xi, int yi, int xf, int yf){
		realizarMovimiento(xi,yi,xf,yf,true);
	}

	/**
	 * Realiza el movimiento de la ficha que ya ha sido controlada con la opcion de establecer o no el primer movimiento en la ficha
	 * @param xi Posicion inicial de la ficha en X
	 * @param yi Posicion inicial de la ficha en Y
	 * @param xf Posicion final de la ficha en X
	 * @param yf Posicion final de la ficha en Y
	 * @param realizar indica si se quiere establecer el primer movimiento en la ficha
	 */
	private void realizarMovimiento(int xi, int yi, int xf, int yf, boolean realizar) {
		Ficha ficha = tablero.darPosicionTablero(xi, yi).darFicha();
		//tablero.darPosicionTablero(xf, yf).establecerFicha(ficha);
		if(ficha.darNombre().equals("REY")){
			tablero.actualizarPosicionRey(ficha.blanca(), xf, yf);
		}
		if(ficha.darNombre().equals("PEON") && (xf == 0 || xf == 7))
			tablero.darPosicionTablero(xf, yf)
			.establecerFicha(new Ficha(9, ficha.blanca(), "REINA"));
		else
			tablero.darPosicionTablero(xf, yf).establecerFicha(ficha);
		
		if(realizar)
			tablero.darPosicionTablero(xf, yf).darFicha().movimientoRealizado();
		tablero.darPosicionTablero(xi, yi).eliminarFicha();
	}
	
	/**
	 * @return Retorna True si el juego termino
	 */

	public boolean juegoTerminado() {
		return termina;
	}
	
	/**
	 * Establece el estado del juego
	 * @param termino Indica si el juego termina
	 */
	public void juegoTerminado(boolean termino){
		this.termina = termino;
	}
	
	/**
	 * @return Retorna true si es el turno de las fichas blancas
	 */

	public boolean turnoFichasBlancas() {
		return turnoBlancas;
	}
	
	/**
	 * @param i Posicion en X de la ficha
	 * @param j Posicion en Y de la ficha
	 * @return Retorna una posicion en el tablero
	 */

	public Posicion darPosicion(int i, int j) {
		return tablero.darPosicionTablero(i, j);
	}
	
	/**
	 * @return Devuelve el tablero del juego
	 */

	public Tablero darTablero() {
		return this.tablero;
	}
	
	/**
	 * Verifica si es una posicion valida
	 * @param xi Posicion inicial de la ficha en X
	 * @param yi Posicion inicial de la ficha en Y
	 * @param xf Posicion final de la ficha en X
	 * @param yf Posicion final de la ficha en Y
	 * @return Devuelve True si la posicion es valida
	 */

	public boolean posicionValida(int xi, int yi, int xf, int yf) {
		return tablero.posicionValida(xi, yi, xf, yf) || tablero.enroqueValido(xi, yi, xf, yf);
	}
	
	/**
	 * Verifica si el movimiento es un Enroque
	 * @param xi Posicion inicial de la ficha en X
	 * @param yi Posicion inicial de la ficha en Y
	 * @param xf Posicion final de la ficha en X
	 * @param yf Posicion final de la ficha en Y
	 * @return Retorna True si es un Enroque
	 */
	public boolean enroqueValido(int xi, int yi, int xf, int yf){
		return tablero.enroqueValido(xi, yi, xf, yf);
	}

	/**
	 * Verifica si el Rey esta en Jaque cuando se realiza determinado movimiento
	 * @param xi Posicion inicial de la ficha en X
	 * @param yi Posicion inicial de la ficha en Y
	 * @param xf Posicion final de la ficha en X
	 * @param yf Posicion final de la ficha en Y
	 * @return Retorna True si el Rey Esta en Jaque
	 */
	public boolean reyEnJaque(int xi, int yi, int xf, int yf) {
		return tablero.reyEnJaque(xi, yi, xf, yf);
	}
	
	/**
	 * Verifica si una ficha esta amenazando al Rey
	 * @param x Posicion de la ficha en X
	 * @param y Posicion de la ficha en Y
	 * @return Devuelve True si el Rey esta en Jaque
	 */
	public boolean verificarSiExisteJaque(int x, int y){
		return tablero.verificarSiExisteJaque(x, y);
	}
}
