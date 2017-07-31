/**
 * Esta clase implementa el algoritmo MinMax para obtener la mejor posicion
 * @author: Luis Bernardo Caussin Torrez
 * @version: 25/09/2015/A
 */

package modelo;

import java.util.ArrayList;
import java.util.Random;

@SuppressWarnings("serial")
public class ArbolDeJugadas implements java.io.Serializable {

	private int profundidadMaxima = 4; // Controla la profundidad maxima del
										// arbol
	private Tablero tablero; // Se utiliza para realizar los movimintos
	private boolean turnoJugador; // Indica el turno del jugador
	private int profundidad; // Indica la profundidad actual del arbol
	private int cantidad; // Es la cantidad de posibles movimientos
	private float puntajeTotal; // Guarda el puntaje de la jugada
	private int indice; // Indica el indice del mejor movimiento
	private ArrayList<Integer[]> movimiento; // Guarda los posibles movimientos
	private boolean mate; //Controla si existe un movimiento que coloque en JaqueMate al oponente
	private float puntajeTablero; //Guarda el valor del tablero

	/**
	 * Constructor
	 * 
	 * @param tablero
	 *            Se utiliza para realizar los movimintos
	 */

	public ArbolDeJugadas(Tablero tablero) {
		movimiento = new ArrayList<Integer[]>();
		this.tablero = tablero;
		this.turnoJugador = true;
		this.profundidad = 0;
		this.mate = false;
		puntajeTotal = 0;
		puntajeTablero = 0;
		inicializar();
	}
	
	/**
	 * 
	 * @param tablero Se utiliza para realizar los movimientos
	 * @param turnoJugador Indica si es el turno del jugador
	 * @param profundidad Controla la profundidad del arbol
	 * @param puntaje Guarda el puntaje que tiene el ultimo movimiento
	 * @param mate Controla si se hizo un JaqueMate
	 * @param puntajeTablero Guarda el valor del tablero actual
	 */

	public ArbolDeJugadas(Tablero tablero, boolean turnoJugador,
			int profundidad, float puntaje, boolean mate, float puntajeTablero) {
		movimiento = new ArrayList<Integer[]>();
		this.tablero = tablero;
		this.turnoJugador = turnoJugador;
		this.profundidad = profundidad;
		this.mate = mate;
		this.puntajeTablero = puntajeTablero;
		puntajeTotal = puntaje;
		inicializar();
	}
	
	/**
	 * Inicializa todas las variables en comun de los Constructors
	 */

	private void inicializar() {
		// Se califica la jugada actual mientras se guardan los posibles
		// movimientos
		float max = Integer.MIN_VALUE;
		float min = Integer.MAX_VALUE;
		for (int i = 0; i < 64; i++) {
			// Se verifica si es una posicion con ficha y si pertenece al turno
			if (tablero.darPosicionTablero(i / 8, i - 8 * (i / 8))
					.posicionConFicha()
					&& tablero.darPosicionTablero(i / 8, i - 8 * (i / 8))
							.darFicha().blanca() == turnoJugador) {
				// Por cada ficha encontrada se busca una posicion final
				for (int k = 0; k < 64; k++) {
					// Se verifica si la posicion final es valida
					if ((tablero.posicionValida(i / 8, i - 8 * (i / 8), k / 8,
							k - 8 * (k / 8)) || tablero.enroqueValido(i / 8, i
							- 8 * (i / 8), k / 8, k - 8 * (k / 8)))
							&& !tablero.reyEnJaque(i / 8, i - 8 * (i / 8),
									k / 8, k - 8 * (k / 8))) {

						movimiento.add(new Integer[] { i / 8, i - 8 * (i / 8),
								k / 8, k - 8 * (k / 8) });

						if (profundidad == profundidadMaxima - 1) {
							int j = movimiento.size() - 1;
							float punt = darPuntaje(j);
							if (!mate && turnoJugador)
								mate = verificarSiJaqueMate(darNuevaJugada(j));
							if (turnoJugador && punt > max) {
								max = punt;
							} else if (!turnoJugador && punt < min) {
								min = punt;
							}
						}
					}
				}
			}
		}
		if (profundidad == profundidadMaxima - 1)
			this.puntajeTotal += turnoJugador ? max : min;
		this.cantidad = movimiento.size();
		if (profundidad < profundidadMaxima - 1 && cantidad > 0 && !mate)
			generarJugadas();
	}
	
	/**
	 * @param i Indice del movimiento a utilizar
 	 * @return Devuelve el puntaje del movimiento elegido
	 */

	public float darPuntaje(int i) {
		float punt = 0;
		if (tablero.darPosicionTablero(movimiento.get(i)[2],
				movimiento.get(i)[3]).posicionConFicha()
				&& tablero
						.darPosicionTablero(movimiento.get(i)[2],
								movimiento.get(i)[3]).darFicha().blanca() != tablero
						.darPosicionTablero(movimiento.get(i)[0],
								movimiento.get(i)[1]).darFicha().blanca()) {
			punt = tablero
					.darPosicionTablero(movimiento.get(i)[2],
							movimiento.get(i)[3]).darFicha().darValor()
					* (turnoJugador ? 1 : -1);
		}
		return puntajeTablero + punt;
	}

	/**
	 * Método recursivo que genera las posibles jugadas
	 */
	private void generarJugadas() {
		// Se obtiene el maximo o el minimo puntaje dependiendo del turno
		// del jugador
		float max = Integer.MIN_VALUE;
		float min = Integer.MAX_VALUE;
		indice = -1;
		ArbolDeJugadas posibleJugada;
		for (int i = 0; i < cantidad; i++) {
			float punt = 0;
			Tablero tab = darNuevaJugada(i);
			punt = darPuntaje(i);
			posibleJugada = new ArbolDeJugadas(tab, !turnoJugador,
					profundidad + 1, punt, verificarSiJaqueMate(tab), punt);
			if (turnoJugador && posibleJugada.puntajeTotal > max) {
				max = posibleJugada.puntajeTotal;
				if (!mate && profundidad == 0)
					indice = i;
			}
			else if(profundidad == 0 && turnoJugador && posibleJugada.puntajeTotal == max){
				if (!mate && new Random().nextBoolean())
					indice = i;
			}
			else if (!turnoJugador && posibleJugada.puntajeTotal < min) {
				min = posibleJugada.puntajeTotal;
			}
			if (!mate && turnoJugador) {
				mate = posibleJugada.mate;
				if (mate && profundidad == 0)
					indice = i;
			}
		}
		this.tablero = null;
		this.puntajeTotal += turnoJugador ? max : min;
	}

	/**
	 * Funcion que verifica si existe un JaqueMate para el oponente
	 * @param tablero Tablero a analizar
	 * @return devuelve True si existe JaqueMate
	 */

	public boolean verificarSiJaqueMate(Tablero tablero) {
		boolean jaqueMate = true;
		for (int i = 0; i < 8 && jaqueMate; i++)
			for (int j = 0; j < 8 && jaqueMate; j++) {
				if (tablero.darPosicionTablero(i, j).posicionConFicha()
						&& tablero.darPosicionTablero(i, j).darFicha().blanca() == false) {
					jaqueMate = !tablero.fichaMovible(i, j);
				}
			}
		return jaqueMate;
	}

	/**
	 * Funcion para obtener la posicion inicial en X del mejor movimiento
	 * @return retorna posicion inicial en X del tablero
	 */
	public int darXi() {
		if (movimiento.size() == 0)
			indice = -1;
		return indice == -1 ? -1 : movimiento.get(indice)[0];
	}

	/**
	 * Funcion para obtener la posicion inicial en Y del mejor movimiento
	 * @return retorna posicion inicial en Y del tablero
	 */
	public int darYi() {
		if (movimiento.size() == 0)
			indice = -1;
		return indice == -1 ? -1 : movimiento.get(indice)[1];
	}

	/**
	 * Funcion para obtener la posicion final en X del mejor movimiento
	 * @return retorna posicion final en X del tablero
	 */
	public int darXf() {
		if (movimiento.size() == 0)
			indice = -1;
		return indice == -1 ? -1 : movimiento.get(indice)[2];
	}

	/**
	 * Funcion para obtener la posicion final en Y del mejor movimiento
	 * @return retorna posicion final en Y del tablero
	 */
	public int darYf() {
		if (movimiento.size() == 0)
			indice = -1;
		return indice == -1 ? -1 : movimiento.get(indice)[3];
	}

	/**
	 * Crea la posible jugada a base del movimiento encontrado
	 * 
	 * @param n
	 *            Es el indice del movimiento encontrado
	 * @return Retorna la jugada creada
	 */

	private Tablero darNuevaJugada(int n) {
		int xi = movimiento.get(n)[0], yi = movimiento.get(n)[1];
		int xf = movimiento.get(n)[2], yf = movimiento.get(n)[3];
		Tablero nueva = new Tablero(tablero.copiar(), tablero.darPosicionReyes());
		if (!tablero.enroqueValido(xi, yi, xf, yf)) {
			Ficha ficha = nueva.darPosicionTablero(xi, yi).darFicha();
			if(ficha.darNombre().equals("REY")){
				nueva.actualizarPosicionRey(ficha.blanca(), xf, yf);
			}
			if (ficha.darNombre().equals("PEON") && (xf == 0 || xf == 7))
				nueva.darPosicionTablero(xf, yf).establecerFicha(
						new Ficha(9, ficha.blanca(), "REINA"));
			else
				nueva.darPosicionTablero(xf, yf).establecerFicha(ficha);
			nueva.darPosicionTablero(xf, yf).darFicha().movimientoRealizado();
			nueva.darPosicionTablero(xi, yi).eliminarFicha();
		} else {
			nueva.actualizarPosicionRey(nueva.darPosicionTablero(xi, yi).darFicha().blanca(), xf, yf);
			nueva.darPosicionTablero(xf, yf).establecerFicha(
					nueva.darPosicionTablero(xi, yi).darFicha());
			nueva.darPosicionTablero(xi, yi + (yf > yi ? 1 : -1))
					.establecerFicha(
							nueva.darPosicionTablero(xi, (yf > yi ? 7 : 0))
									.darFicha());
			nueva.darPosicionTablero(xi, (yf > yi ? 7 : 0)).eliminarFicha();
			nueva.darPosicionTablero(xi, yi).eliminarFicha();
		}
		return nueva;
	}
}