package Test;

import static org.junit.Assert.*;
import modelo.ArbolDeJugadas;
import modelo.Ficha;
import modelo.Posicion;
import modelo.Tablero;

import org.junit.Before;
import org.junit.Test;

public class ArbolDeJugadasTest {
	
	private ArbolDeJugadas arbol;
	private Tablero tab;
	
	@Before
	public void setUp() {
		Posicion[][] tabPos = new Posicion[8][8];
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				tabPos[i][j] = new Posicion(null,i,j);
		tabPos[0][1] = new Posicion(new Ficha(1,true,"PEON"),0,1);
		tabPos[1][0] = new Posicion(new Ficha(100,true,"REY"),1,0);
		tabPos[1][3] = new Posicion(new Ficha(9,false,"REINA"),1,3);
		tabPos[7][7] = new Posicion(new Ficha(100,false,"REY"),7,7);
		tab = new Tablero(tabPos, new int[]{1,0,7,7});
		arbol = new ArbolDeJugadas(tab);
	}

	@Test
	public void testArbol() {
		assertEquals(tab.reyEnJaque(arbol.darXi(), arbol.darYi(), arbol.darXf(), arbol.darYf()),false);
	}

}
