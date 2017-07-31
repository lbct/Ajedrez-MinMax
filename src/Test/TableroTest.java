package Test;

import static org.junit.Assert.*;
import modelo.Ficha;
import modelo.Posicion;
import modelo.Tablero;
import org.junit.Before;
import org.junit.Test;

public class TableroTest {

	Tablero tab;

	@Before
	public void setUp() {
		tab = new Tablero();
	}

	@Test
	public void testCopiar() {
		Posicion[][] tabCpy = tab.copiar();
		tabCpy[0][0].eliminarFicha();
		assertEquals(
				tabCpy[0][0].posicionConFicha() != tab.darPosicionTablero(0, 0)
						.posicionConFicha(), true);
	}

	@Test
	public void testPosicionValida() {
		// Test peones
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				for (int k = 0; k < 8; k++) {
					boolean res = tab.posicionValida(1, i, j, k);
					res = res ? (j == 2 || j == 3) && k == i
							: (j != 2 && j != 3)
									|| ((j == 2 || j == 3) && k != i);
					assertEquals(res, true);
					res = tab.posicionValida(6, i, j, k);
					res = res ? (j == 5 || j == 4) && k == i
							: (j != 5 && j != 4)
									|| ((j == 5 || j == 4) && k != i);
					assertEquals(res, true);
				}
			}
		}

		tab.darTableroPosicion()[5][0] = new Posicion(
				new Ficha(1, true, "PEON"), 5, 0);
		assertEquals(tab.posicionValida(6, 0, 4, 0), false);
		assertEquals(tab.posicionValida(6, 1, 5, 0), true);
		tab.darTableroPosicion()[5][0].eliminarFicha();
		assertEquals(tab.posicionValida(6, 0, 4, 0), true);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				// Test Torres
				boolean res = !tab.posicionValida(0, 0, i, j)
						&& !tab.posicionValida(0, 7, i, j)
						&& !tab.posicionValida(7, 7, i, j)
						&& !tab.posicionValida(7, 0, i, j);
				assertEquals(res, true);
				// Test Caballo
				for (int c = 0; c < 4; c++) {
					int k = c == 0 || c == 1 ? 0 : c == 2 || c == 3 ? 7 : -1;
					int l = c == 0 || c == 2 ? 1 : c == 1 || c == 3 ? 6 : -1;
					boolean valido = (Math.abs(i - k) == 1 && Math.abs(j - l) == 2)
							|| (Math.abs(i - k) == 2 && Math.abs(j - l) == 1)
							|| (Math.abs(i - k) == 2 && Math.abs(j - l) == 1);
					res = tab.posicionValida(k, l, i, j);

					res = (res && valido) || (!res && !valido) || i == 1
							|| i == 6;
					assertEquals(res, true);
					// Test Alfil
					l = l == 1 ? l + 1 : l - 1;
					res = !tab.posicionValida(k, l, i, j);
					assertEquals(res, true);
					// Test Rey Reina
					res = !tab.posicionValida(0, 3, i, j)
							&& !tab.posicionValida(0, 4, i, j)
							&& !tab.posicionValida(7, 3, i, j)
							&& !tab.posicionValida(7, 4, i, j);
				}
			}
		}

		// Test Torre
		tab.darTableroPosicion()[6][0].eliminarFicha();
		assertEquals(tab.posicionValida(7, 0, 6, 0), true);
		tab.darTableroPosicion()[6][0] = new Posicion(
				new Ficha(1, true, "PEON"), 6, 0);
		assertEquals(tab.posicionValida(7, 0, 6, 0), true);
		assertEquals(tab.posicionValida(7, 0, 5, 0), false);
		tab.darTableroPosicion()[6][0] = new Posicion(new Ficha(1, false,
				"PEON"), 6, 0);
		assertEquals(tab.posicionValida(7, 0, 6, 0), false);
		// Test Alfil
		tab.darTableroPosicion()[6][1].eliminarFicha();
		assertEquals(tab.posicionValida(7, 2, 6, 1), true);
		assertEquals(tab.posicionValida(7, 2, 5, 0), true);
		tab.darTableroPosicion()[6][1] = new Posicion(
				new Ficha(1, true, "PEON"), 6, 1);
		assertEquals(tab.posicionValida(7, 2, 6, 1), true);
		assertEquals(tab.posicionValida(7, 2, 5, 0), false);
		tab.darTableroPosicion()[6][1] = new Posicion(new Ficha(1, false,
				"PEON"), 6, 1);
		assertEquals(tab.posicionValida(7, 2, 6, 1), false);
		assertEquals(tab.posicionValida(7, 2, 5, 0), false);
		// Test Caballo
		tab.darTableroPosicion()[5][0] = new Posicion(
				new Ficha(1, true, "PEON"), 5, 0);
		assertEquals(tab.posicionValida(7, 1, 5, 0), true);
		tab.darTableroPosicion()[5][0] = new Posicion(new Ficha(1, false,
				"PEON"), 5, 0);
		assertEquals(tab.posicionValida(7, 1, 5, 0), false);
		tab.darTableroPosicion()[5][0].eliminarFicha();
		assertEquals(tab.posicionValida(7, 1, 5, 0), true);
		// Test Reina
		tab.darTableroPosicion()[6][3] = new Posicion(
				new Ficha(1, true, "PEON"), 6, 3);
		tab.darTableroPosicion()[6][4] = new Posicion(
				new Ficha(1, true, "PEON"), 6, 4);
		tab.darTableroPosicion()[6][5] = new Posicion(
				new Ficha(1, true, "PEON"), 6, 5);
		assertEquals(tab.posicionValida(7, 4, 6, 3), true);
		assertEquals(tab.posicionValida(7, 4, 6, 4), true);
		assertEquals(tab.posicionValida(7, 4, 6, 5), true);
		assertEquals(tab.posicionValida(7, 4, 5, 2), false);
		assertEquals(tab.posicionValida(7, 4, 5, 4), false);
		assertEquals(tab.posicionValida(7, 4, 5, 6), false);
		tab.darTableroPosicion()[6][3].eliminarFicha();
		tab.darTableroPosicion()[6][4].eliminarFicha();
		tab.darTableroPosicion()[6][5].eliminarFicha();
		assertEquals(tab.posicionValida(7, 4, 6, 3), true);
		assertEquals(tab.posicionValida(7, 4, 6, 4), true);
		assertEquals(tab.posicionValida(7, 4, 6, 5), true);
		assertEquals(tab.posicionValida(7, 4, 5, 2), true);
		assertEquals(tab.posicionValida(7, 4, 5, 4), true);
		assertEquals(tab.posicionValida(7, 4, 5, 6), true);
		tab.darTableroPosicion()[6][3] = new Posicion(new Ficha(1, false,
				"PEON"), 6, 3);
		tab.darTableroPosicion()[6][4] = new Posicion(new Ficha(1, false,
				"PEON"), 6, 4);
		tab.darTableroPosicion()[6][5] = new Posicion(new Ficha(1, false,
				"PEON"), 6, 5);
		assertEquals(tab.posicionValida(7, 4, 6, 3), false);
		assertEquals(tab.posicionValida(7, 4, 6, 4), false);
		assertEquals(tab.posicionValida(7, 4, 6, 5), false);
		assertEquals(tab.posicionValida(7, 4, 5, 2), false);
		assertEquals(tab.posicionValida(7, 4, 5, 4), false);
		assertEquals(tab.posicionValida(7, 4, 5, 6), false);
		// Test Rey
		tab.darTableroPosicion()[6][2] = new Posicion(
				new Ficha(1, true, "PEON"), 6, 2);
		tab.darTableroPosicion()[6][3] = new Posicion(
				new Ficha(1, true, "PEON"), 6, 3);
		tab.darTableroPosicion()[6][4] = new Posicion(
				new Ficha(1, true, "PEON"), 6, 4);
		assertEquals(tab.posicionValida(7, 3, 6, 2), true);
		assertEquals(tab.posicionValida(7, 3, 6, 3), true);
		assertEquals(tab.posicionValida(7, 3, 6, 4), true);
		tab.darTableroPosicion()[6][2].eliminarFicha();
		tab.darTableroPosicion()[6][3].eliminarFicha();
		tab.darTableroPosicion()[6][4].eliminarFicha();
		assertEquals(tab.posicionValida(7, 3, 6, 2), true);
		assertEquals(tab.posicionValida(7, 3, 6, 3), true);
		assertEquals(tab.posicionValida(7, 3, 6, 4), true);
		tab.darTableroPosicion()[6][2] = new Posicion(new Ficha(1, false,
				"PEON"), 6, 2);
		tab.darTableroPosicion()[6][3] = new Posicion(new Ficha(1, false,
				"PEON"), 6, 3);
		tab.darTableroPosicion()[6][4] = new Posicion(new Ficha(1, false,
				"PEON"), 6, 4);
		assertEquals(tab.posicionValida(7, 3, 6, 2), false);
		assertEquals(tab.posicionValida(7, 3, 6, 3), false);
		assertEquals(tab.posicionValida(7, 3, 6, 4), false);
	}

	@Test
	public void testDarPosicionRey() {
		int x = tab.darPosicionReyes()[0];
		int y = tab.darPosicionReyes()[1];
		Posicion rey = tab.darPosicionTablero(x, y);
		assertEquals(rey.darFicha().blanca()
				&& rey.darFicha().darNombre().equals("REY"), true);
	}

	@Test
	public void testDarPosicionTablero() {
		Posicion[][] tabCpy = tab.copiar();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				boolean prueba1 = tabCpy[i][j].posicionConFicha() == tab
						.darPosicionTablero(i, j).posicionConFicha();
				boolean prueba2 = false;
				boolean prueba3 = false;
				if (tabCpy[i][j].posicionConFicha()) {
					prueba2 = tabCpy[i][j]
							.darFicha()
							.darNombre()
							.equals(tab.darPosicionTablero(i, j).darFicha()
									.darNombre());
					prueba3 = tabCpy[i][j].darFicha().blanca() == tab
							.darPosicionTablero(i, j).darFicha().blanca();
				} else {
					prueba2 = true;
					prueba3 = true;
				}
				assertEquals(prueba1 == prueba2 == prueba3 == true, true);
			}
		}
	}

	@Test
	public void testReyEnJaque() {
		assertEquals(tab.reyEnJaque(7, 3, 2, 3), true);
	}

}
