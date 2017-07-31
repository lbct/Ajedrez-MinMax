/**
 * Esta clase es el Tablero de Ajedrez
 * @author: Luis Bernardo Caussin Torrez
 * @version: 25/09/2015/A
 */
package modelo;

@SuppressWarnings("serial")
public class Tablero implements java.io.Serializable {

	private Posicion[][] tablero;
	private int[] posicionRey;

	/**
	 * Constructor
	 */
	public Tablero() {
		posicionRey = new int[4];
		tablero = new Posicion[8][8];
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tablero[i][j] = darPosicion(i, j);
			}
		}
		posicionRey[0] = 0;
		posicionRey[1] = 3;
		posicionRey[2] = 7;
		posicionRey[3] = 3;
	}

	/**
	 * Constructor con opcion de cargar una jugada anterior
	 * @param tablero Tablero a cargar
	 * @param posicionRey Posiciones de los Reyes en el nuevo tablero
	 */
	public Tablero(Posicion[][] tablero, int[] posicionRey) {
		this.posicionRey = posicionRey;
		this.tablero = tablero;
	}
	
	/**
	 * Retorna una posicion del tablero
	 * @param x Posicion en X del tablero
	 * @param y Posicion en Y del tablero
	 * @return Devuelve la posicion del tablero
	 */

	private Posicion darPosicion(int x, int y) {
		Ficha ficha = null;
		if (x == 1 || x == 6)
			ficha = new Ficha(1, x == 1, "PEON");
		else if ((x == 0 && (y == 0 || y == 7))
				|| (x == 7 && (y == 0 || y == 7)))
			ficha = new Ficha(5, x == 0, "TORRE");
		else if ((x == 0 && (y == 1 || y == 6))
				|| (x == 7 && (y == 1 || y == 6)))
			ficha = new Ficha(3, x == 0, "CABALLO");
		else if ((x == 0 && (y == 2 || y == 5))
				|| (x == 7 && (y == 2 || y == 5)))
			ficha = new Ficha(3, x == 0, "ALFIL");
		else if ((x == 0 || x == 7) && y == 3)
			ficha = new Ficha(100, x == 0, "REY");
		else if ((x == 0 || x == 7) && y == 4)
			ficha = new Ficha(9, x == 0, "REINA");
		return new Posicion(ficha, x, y);
	}

	/**
	 * Realiza una copia del tablero
	 * @return Devuelve la copia
	 */
	public Posicion[][] copiar() {
		return copiar(this.tablero);
	}

	/**
	 * Actualiza la posicion del Rey
	 * @param blanco indica si es un rey blanco o negro
	 * @param x Posicion en X del Rey
	 * @param y Posicion en Y del Rey
	 */
	public void actualizarPosicionRey(boolean blanco, int x, int y) {
		posicionRey[blanco ? 0 : 2] = x;
		posicionRey[blanco ? 1 : 3] = y;
	}

	/**
	 * Copia el tablero dado
	 * @param tablero Tablero a copiar
	 * @return Retorna la copia
	 */
	public Posicion[][] copiar(Posicion[][] tablero) {
		Posicion[][] tab = new Posicion[8][8];
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				tab[i][j] = new Posicion(
						tablero[i][j].posicionConFicha() ? new Ficha(
								tablero[i][j].darFicha().darValor(),
								tablero[i][j].darFicha().blanca(),
								tablero[i][j].darFicha().darNombre()) : null,
						i, j);
		return tab;
	}
	
	/**
	 * @return Devuelve las posiciones de los reyes
	 */

	public int[] darPosicionReyes() {
		int[] posicion = new int[4];
		for (int i = 0; i < 4; i++)
			posicion[i] = posicionRey[i];
		return posicion;
	}
	
	/**
	 * Verifica si es una posicion valida
	 * @param x Posicion inicial de la ficha en X
	 * @param y Posicion inicial de la ficha en Y
	 * @param i Posicion final de la ficha en X
	 * @param j Posicion final de la ficha en Y
	 * @return Devuelve True si la posicion es valida
	 */

	public boolean posicionValida(int x, int y, int i, int j) {
		return posicionValida(x, y, i, j, tablero);
	}

	/**
	 * Verifica si el movimiento es un Enroque
	 * @param x Posicion inicial de la ficha en X
	 * @param y Posicion inicial de la ficha en Y
	 * @param i Posicion final de la ficha en X
	 * @param j Posicion final de la ficha en Y
	 * @return Retorna True si es un Enroque
	 */
	public boolean enroqueValido(int x, int y, int i, int j) {
		return enroqueValido(x, y, i, j, tablero);
	}
	
	/**
	 * Verifica si el movimiento es un Enroque en un determinado tablero
	 * @param x Posicion inicial de la ficha en X
	 * @param y Posicion inicial de la ficha en Y
	 * @param i Posicion final de la ficha en X
	 * @param j Posicion final de la ficha en Y
	 * @param tablero Tablero en el cual se verificara
	 * @return Retorna True si es un Enroque
	 */

	public boolean enroqueValido(int x, int y, int i, int j,
			Posicion[][] tablero) {
		boolean valido = false;
		if (tablero[x][y].posicionConFicha()
				&& tablero[x][y].darFicha().darNombre().equals("REY")
				&& !tablero[x][y].darFicha().primerMovimientoRealizado()) {
			if ((y - j) == 2 && x == i && tablero[i][0].posicionConFicha()
					&& tablero[i][0].darFicha().darNombre().equals("TORRE")
					&& !tablero[i][0].darFicha().primerMovimientoRealizado()
					&& !buscarFichas(x, 0, x, y, tablero))
				valido = true;
			else if ((j - y) == 2 && x == i && tablero[i][7].posicionConFicha()
					&& tablero[i][7].darFicha().darNombre().equals("TORRE")
					&& !tablero[i][7].darFicha().primerMovimientoRealizado()
					&& !buscarFichas(i, 7, x, y, tablero))
				valido = true;
		}
		return valido;
	}

	/**
	 * Verifica si es una posicion valida en un determinado tablero
	 * @param x Posicion inicial de la ficha en X
	 * @param y Posicion inicial de la ficha en Y
	 * @param i Posicion final de la ficha en X
	 * @param j Posicion final de la ficha en Y
	 * @param tablero Tablero en el cual se verificara
	 * @return Devuelve True si la posicion es valida
	 */
	public boolean posicionValida(int x, int y, int i, int j,
			Posicion[][] tablero) {
		boolean valido = false;
		if (tablero[x][y].darFicha().movimientoValido(x, y, i, j)) {
			if (tablero[x][y].darFicha().darNombre().equals("PEON")) {
				valido = !(y == j && Math.abs(i - x) == 2
						&& posValida(x + 1 * ((i > x) ? 1 : -1), y) && tablero[x
						+ 1 * ((i > x) ? 1 : -1)][y].posicionConFicha());
				if (y != j) {
					valido = tablero[i][j].posicionConFicha()
							&& tablero[x][y].darFicha().blanca() != tablero[i][j]
									.darFicha().blanca();
				} else if (valido) {
					valido = !tablero[i][j].posicionConFicha();
				}
			} else {
				if (!tablero[x][y].darFicha().darNombre().equals("CABALLO"))
					valido = !buscarFichas(x, y, i, j, tablero);
				else
					valido = true;
				if (valido) {
					if (!tablero[i][j].posicionConFicha())
						valido = true;
					else if (tablero[x][y].darFicha().blanca() != tablero[i][j]
							.darFicha().blanca())
						valido = true;
					else
						valido = false;
				}
			}
		}
		return valido;
	}

	/**
	 * Busca si existen fichas de un extremo a otro en un determinado tablero
	 * @param xi Posicion inicial en X
	 * @param yi Posicion inicial en X
	 * @param xf Posicion final en X
	 * @param yf Posicion final en Y
	 * @param tablero Tablero usado para el analisis
	 * @return Devuelve True si existe alguna ficha
	 */
	private boolean buscarFichas(int xi, int yi, int xf, int yf,
			Posicion[][] tablero) {
		boolean existe = false;
		int i = xi == xf ? 0 : xf > xi ? 1 : -1;
		int j = yi == yf ? 0 : yf > yi ? 1 : -1;
		while (posValida(xi, yi) && !(xi == xf && yi == yf) && !existe) {
			xi += i;
			yi += j;
			if (posValida(xi, yi) && !(xi == xf && yi == yf)
					&& tablero[xi][yi].posicionConFicha())
				existe = true;
		}
		return existe;
	}
	
	/**
	 * @param i Posicion en X
	 * @param j Posicion en Y
	 * @return Devuelve una posicion del tablero
	 */

	public Posicion darPosicionTablero(int i, int j) {
		return tablero[i][j];
	}
	
	/**
	 * Controla si la posicion en el tablero no se desborda
	 * @param i Posicion en X
	 * @param j Posicion en Y
	 * @return Devuelve True si la posicion es correcta
	 */

	private boolean posValida(int i, int j) {
		return i < 8 && i > -1 && j < 8 && j > -1;
	}

	/**
	 * Verifica si determinada ficha se puede mover
	 * @param x Posicion en X de la ficha
	 * @param y Posicion en Y de la ficha
	 * @return Devuelve True si la ficha se puede mover
	 */
	public boolean fichaMovible(int x, int y) {
		boolean movible = false;
		int i = 0, j = 0;
		while (i < 8 && !movible) {
			while (j < 8 && !movible) {
				movible = (posicionValida(x, y, i, j) && !reyEnJaque(x, y, i, j));
				j++;
			}
			j = 0;
			i++;
		}
		return movible;
	}
	
	/**
	 * Verifica si el Rey esta en Jaque cuando se realiza un determinado movimiento
	 * @param xi Posicion inicial de la ficha en X
	 * @param yi Posicion inicial de la ficha en Y
	 * @param xf Posicion final de la ficha en X
	 * @param yf Posicion final de la ficha en Y
	 * @return Retorna True si el Rey Esta en Jaque
	 */

	public boolean reyEnJaque(int xi, int yi, int xf, int yf) {
		return reyEnJaque(xi, yi, xf, yf, tablero, posicionRey);
	}

	/**
	 * Verifica si el Rey esta en Jaque cuando se realiza un determinado movimiento en un determinado tablero
	 * @param xi Posicion inicial de la ficha en X
	 * @param yi Posicion inicial de la ficha en Y
	 * @param xf Posicion final de la ficha en X
	 * @param yf Posicion final de la ficha en Y
	 * @param tablero Tablero a verificar
	 * @param posicionRey Posiciones de los reyes en el tablero
	 * @return Retorna True si el Rey Esta en Jaque
	 */
	public boolean reyEnJaque(int xi, int yi, int xf, int yf,
			Posicion[][] tablero, int[] posicionRey) {

		boolean blanca = tablero[xi][yi].darFicha().blanca();

		Posicion[][] tab = copiar(tablero);
		int xRey = posicionRey[blanca ? 0 : 2];
		int yRey = posicionRey[blanca ? 1 : 3];

		if (tablero[xi][yi].darFicha().darNombre().equals("REY")) {
			xRey = xf;
			yRey = yf;
		}
		tab[xf][yf].establecerFicha(tab[xi][yi].darFicha());
		if (!enroqueValido(xi, yi, xf, yf, tab)) {
			tab[xf][yf].darFicha().movimientoRealizado();
			tab[xi][yi].eliminarFicha();
		} else {
			tab[xi][yi + (yf > yi ? 1 : -1)]
					.establecerFicha(tab[xi][(yf > yi ? 7 : 0)].darFicha());
			tab[xi][(yf > yi ? 7 : 0)].eliminarFicha();
			tab[xi][yi].eliminarFicha();
		}
		Posicion rey = tab[xRey][yRey];
		int i = 0, j = 0;
		boolean enJaque = false;

		while (i < 8 && !enJaque) {
			while (j < 8 && !enJaque) {
				if (tab[i][j].posicionConFicha()
						&& tab[i][j].darFicha().blanca() != blanca
						&& posicionValida(i, j, rey.darPosX(), rey.darPosY(),
								tab)) {
					enJaque = true;
				}
				j++;
			}
			i++;
			j = 0;
		}
		return enJaque;
	}

	/**
	 * Verifica si una ficha esta amenazando al Rey
	 * @param x Posicion de la ficha en X
	 * @param y Posicion de la ficha en Y
	 * @return Devuelve True si el Rey esta en Jaque
	 */
	public boolean verificarSiExisteJaque(int x, int y) {
		return posicionValida(x, y, posicionRey[!tablero[x][y].darFicha()
				.blanca() ? 0 : 2], posicionRey[!tablero[x][y].darFicha()
				.blanca() ? 1 : 3]);
	}

	/**
	 * @return Devuelve las posiciones del tablero
	 */
	public Posicion[][] darTableroPosicion() {
		return tablero;
	}
}
