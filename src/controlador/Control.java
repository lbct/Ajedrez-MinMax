/**
 * Esta clase controla el modelo con la interfaz grafica
 * @author: Luis Bernardo Caussin Torrez
 * @version: 25/09/2015/A
 */

package controlador;

import modelo.*;
import vista.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class Control implements java.io.Serializable {

	private int x, y;
	private boolean fichaSeleccionada;
	private Jugador juego;
	private TableroGrafico tablero;
	private ArbolDeJugadas arbol;
	private boolean editar;

	/**
	 * Constructor
	 */
	public Control() {
		x = 0;
		y = 0;
		editar = false;
		fichaSeleccionada = false;
		juego = new Jugador();
		tablero = new TableroGrafico();
	}

	/**
	 * metodo que inicia el juego
	 */
	public void iniciarJuego() {
		setListeners();
		setListenersControl();
		tablero.setVisible(true);
		mostrarMensaje(true);
		if (juego.turnoFichasBlancas())
			movimientoArbol();
	}

	/**
	 * Se utiliza para cargar una partida guardada
	 * 
	 * @param juego
	 *            El estado del juego
	 * @param tablero
	 *            Las posiciones visuales del juego
	 */

	private void cargar(Jugador juego, TableroGrafico tablero) {
		this.juego = juego;
		this.tablero = tablero;
	}

	/**
	 * Establece los Listeners de los botones de control del juego
	 */
	public void setListenersControl() {
		tablero.darBoton("salir").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					File baseDeDatos = new File("BaseDeDatos.dat");
					baseDeDatos.delete();
				} catch (Exception e) {
					System.out.println("Nada que borrar");
				}
				System.exit(0);
			}
		});
		tablero.darBoton("editar").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JButton boton = (JButton) arg0.getSource();
				if (boton.getBackground().equals(Color.WHITE)) {
					editar = true;
					boton.setBackground(Color.ORANGE);
				} else {
					editar = false;
					boton.setBackground(Color.WHITE);
				}
			}
		});
		tablero.darBoton("reiniciar").addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tablero.setVisible(false);
				x = 0;
				y = 0;
				fichaSeleccionada = false;
				juego = new Jugador();
				tablero = new TableroGrafico();
				tablero.setVisible(true);
				setListeners();
				setListenersControl();
				mostrarMensaje(true);
				if (juego.turnoFichasBlancas())
					movimientoArbol();
			}
		});
		tablero.darBoton("salirguardar").addActionListener(
				new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						ObjectOutputStream escribir;
						try {
							escribir = new ObjectOutputStream(
									new FileOutputStream("baseDeDatos.dat"));
							Control nuevo = new Control();
							nuevo.cargar(juego, tablero);
							escribir.writeObject(nuevo);
						} catch (IOException e) {
							e.printStackTrace();
						}
						System.exit(0);
					}
				});
	}

	/**
	 * Establece, inicializa los Listeners de los botones
	 */

	public void setListeners() {
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				tablero.darPosicion(i, j).addActionListener(
						new ActionListener() {
							public void actionPerformed(ActionEvent arg0) {
								BotonPosicion boton = (BotonPosicion) arg0
										.getSource();
								if (boton.tieneFicha()
										&& !juego.juegoTerminado() && !editar) {
									if (fichaSeleccionada
											&& boton.fichaBlanca() == juego
													.turnoFichasBlancas()
											&& !juego.enroqueValido(x, y,
													boton.darX(), boton.darY())) {
										restaurarColorUltimaPosicion();
										guardarPosicion(boton.darX(),
												boton.darY());
										colorear();
									} else if (!fichaSeleccionada) {
										if (boton.fichaBlanca() == juego
												.turnoFichasBlancas()) {
											guardarPosicion(boton.darX(),
													boton.darY());
											colorear();
										}
									} else {
										realizarMovimiento(boton);
									}
								} else if (!juego.juegoTerminado() && !editar) {
									realizarMovimiento(boton);
								} else if (editar) {
									if (!fichaSeleccionada) {
										guardarPosicion(boton.darX(),
												boton.darY());
										colorear();
									} else {
										fichaSeleccionada = false;
										juego.mover(x, y, boton.darX(),
												boton.darY());
										tablero.darPosicion(x, y).reemplazar(
												boton);
										restaurarColorUltimaPosicion();
									}
								}
							}
						});
			}
		}
	}

	/**
	 * Guarda la posicion
	 * 
	 * @param x
	 *            Posicion en X
	 * @param y
	 *            Posicion en Y
	 */
	private void guardarPosicion(int x, int y) {
		this.x = x;
		this.y = y;

		fichaSeleccionada = true;
	}

	/**
	 * Realiza las acciones necesarias para mover las fichas
	 * 
	 * @param boton
	 *            es el lugar donde se precionó la ficha
	 */
	private void realizarMovimiento(BotonPosicion boton) {
		boolean peon = juego.esPeon(x, y);
		boolean enroque = juego.enroqueValido(x, y, boton.darX(), boton.darY());
		boolean valido = juego.moverFicha(x, y, boton.darX(), boton.darY());
		if (valido) {
			if (!enroque) {
				tablero.darPosicion(x, y).reemplazar(boton);
				if (peon && (boton.darX() == 0 || boton.darX() == 7))
					tablero.darPosicion(boton.darX(), boton.darY())
							.cambiarAReina();
			} else {
				tablero.darPosicion(x, y).reemplazar(boton);
				tablero.darPosicion(x, boton.darY() > y ? 7 : 0)
						.reemplazar(
								tablero.darPosicion(x, y
										+ (boton.darY() > y ? 1 : -1)));
			}
		}
		mostrarMensaje(valido);
		restaurarColorUltimaPosicion();
		fichaSeleccionada = false;
		if (valido) {
			movimientoArbol();
			juego.verificarJaqueMate();
			if (juego.juegoTerminado())
				mostrarMensaje(true);
		}
	}

	/**
	 * Realiza el movimiento de la computadora
	 */
	private void movimientoArbol() {
		arbol = new ArbolDeJugadas(juego.darTablero());
		tablero.mostrarMensaje(false);
		if (arbol.darXi() != -1) {
			tablero.darPosicion(arbol.darXi(), arbol.darYi()).colorear(false);
			boolean enroque = juego.enroqueValido(arbol.darXi(), arbol.darYi(),
					arbol.darXf(), arbol.darYf());
			if (!enroque)
				tablero.darPosicion(arbol.darXf(), arbol.darYf()).colorear();
			else
				tablero.darPosicion(arbol.darXf(), arbol.darYf()).colorear(
						false);
			boolean peon = juego.esPeon(arbol.darXi(), arbol.darYi());

			juego.moverFicha(arbol.darXi(), arbol.darYi(), arbol.darXf(),
					arbol.darYf());
			if (!juego.juegoTerminado()) {
				if (!enroque) {
					tablero.darPosicion(arbol.darXi(), arbol.darYi())
							.reemplazar(
									tablero.darPosicion(arbol.darXf(),
											arbol.darYf()));
					if (peon && (arbol.darXf() == 0 || arbol.darXf() == 7))
						tablero.darPosicion(arbol.darXf(), arbol.darYf())
								.cambiarAReina();
				} else {
					tablero.darPosicion(arbol.darXi(), arbol.darYi())
							.reemplazar(
									tablero.darPosicion(arbol.darXf(),
											arbol.darYf()));
					tablero.darPosicion(arbol.darXi(),
							arbol.darYf() > arbol.darYi() ? 7 : 0)
							.reemplazar(
									tablero.darPosicion(
											arbol.darXi(),
											arbol.darYi()
													+ (arbol.darYf() > arbol
															.darYi() ? 1 : -1)));
				}
			}
			if(arbol.darXi()!=-1 && juego.verificarSiExisteJaque(arbol.darXf(),arbol.darYf()))
				JOptionPane.showMessageDialog(null,"Jaque");
			mostrarMensaje(true);
			restaurarColorUltimaPosicion();
			fichaSeleccionada = false;
		} else {
			juego.juegoTerminado(true);
			mostrarMensaje(true);
		}
	}

	/**
	 * Colorea los botones del movimiento
	 */
	private void colorear() {
		tablero.darPosicion(x, y).colorear(false);
		if (!editar && tablero.darPosicion(x, y).tieneFicha()) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					if (juego.posicionValida(x, y, i, j)
							&& !juego.reyEnJaque(x, y, i, j)) {
						if (!juego.enroqueValido(x, y, i, j))
							tablero.darPosicion(i, j).colorear();
						else
							tablero.darPosicion(i, j).colorear(true);
					}
				}
			}
		}
	}

	/**
	 * Restaura los colores del movimiento anterior
	 */
	private void restaurarColorUltimaPosicion() {
		for (int i = 0; i < 8; i++)
			for (int j = 0; j < 8; j++)
				tablero.darPosicion(i, j).restaurarColor();
	}

	/**
	 * Muestra cuadros de mensaje del movimiento realizado
	 * 
	 * @param correcto
	 *            se utiliza para saber si el movimiento fue valido
	 */
	private void mostrarMensaje(boolean correcto) {
		if (!juego.juegoTerminado()) {
			if(juego.turnoFichasBlancas())
				tablero.mostrarMensaje(true);
			BufferedImage image = null;
			String barra = "/";
			boolean imagenCargada = false;
			while(!imagenCargada)
			{
				try {
					image = ImageIO.read(new File("Iconos"+barra+"REY"+(juego.turnoFichasBlancas()?"B":"N")+".png"));
					imagenCargada  = true;
				} catch (IOException e) {
					if(barra.equals("/"))
						barra = "\\";
					else{
						imagenCargada = true;
						System.out.println("Directorio de imagenes no encontrado");
					}
				}
			}
			JOptionPane
					.showMessageDialog(
							null,
							(!correcto ? "Movimiento No valido\n" : "")
									+ "Turno fichas "
									+ (juego.turnoFichasBlancas() ? "blanca"
											: "negras"), "", JOptionPane.DEFAULT_OPTION, new ImageIcon(image));
		} else {
			JOptionPane.showMessageDialog(
					null,
					"Mate\nGanaron las fichas "
							+ (!juego.turnoFichasBlancas() ? "blancas"
									: "negras"));
		}
	}
}
