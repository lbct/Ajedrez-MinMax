package Test;

import static org.junit.Assert.*;
import modelo.Ficha;
import modelo.Jugador;
import modelo.Posicion;
import modelo.Tablero;

import org.junit.Test;

public class JugadorTest {

	@Test
	public void testMoverFicha() {
		Jugador jug = new Jugador();
		boolean turnoB = jug.turnoFichasBlancas();
		if(turnoB){
			assertEquals(jug.moverFicha(2, 0, 3, 0),false);
			assertEquals(jug.moverFicha(1, 0, 3, 0), true);
			assertEquals(jug.darPosicion(3, 0).posicionConFicha(),true);
		}
		else{
			assertEquals(jug.moverFicha(5, 0, 4, 0),false);
			assertEquals(jug.moverFicha(6, 0, 4, 0), true);
			assertEquals(jug.darPosicion(4, 0).posicionConFicha(),true);
		}
	}

	@Test
	public void testVerificarJaqueMate() {
		Tablero tab = new Tablero();
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++)
				tab.darPosicionTablero(i, j).eliminarFicha();
		}
		Posicion[][] tabPos = tab.copiar();
		tabPos[0][0] = new Posicion(new Ficha(100,false,"REY"),0,0);
		tabPos[0][2] = new Posicion(new Ficha(9,true,"REINA"),0,2);
		tabPos[1][2] = new Posicion(new Ficha(9,true,"REINA"),1,2);
		tabPos[0][3] = new Posicion(new Ficha(100,true,"REY"),0,3);
		tab = new Tablero(tabPos, new int[]{0,3,0,0});
		Jugador jug = new Jugador(tab);
		jug.verificarJaqueMate();
		assertEquals(jug.juegoTerminado(),true);
	}

	@Test
	public void testMover() {
		Jugador jug = new Jugador();
		assertEquals(jug.darPosicion(2, 0).posicionConFicha(),false);
		jug.mover(1, 0, 2, 0);
		assertEquals(jug.darPosicion(2, 0).posicionConFicha(),true);
	}
}
