/**
 * Esta clase es la interfaz grafica del tablero
 * @author: Luis Bernardo Caussin Torrez
 * @version: 27/04/2015/A
 */
package vista;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class TableroGrafico extends JFrame implements java.io.Serializable {

	JButton salirGuardar;
	JButton reiniciar;
	JButton salir;
	JButton editar;
	private JPanel contentPane;
	private BotonPosicion[][] posiciones;
	private JLabel mensaje;

	/**
	 * Constructor
	 */
	public TableroGrafico() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 608);
		salirGuardar = new JButton("Guardar");
		salirGuardar.setBackground(Color.WHITE);
		salirGuardar.setBounds(10, 500, 100, 60);
		editar = new JButton("Editar");
		editar.setBackground(Color.WHITE);
		editar.setBounds(120, 500, 100, 60);
		reiniciar = new JButton("Reiniciar");
		reiniciar.setBackground(Color.WHITE);
		reiniciar.setBounds(260, 500, 100, 60);

		salir = new JButton("Salir");
		salir.setBackground(Color.WHITE);
		salir.setBounds(370, 500, 100, 60);

		contentPane = new JPanel();
		contentPane.add(salir);
		contentPane.add(reiniciar);
		contentPane.add(salirGuardar);
		contentPane.add(editar);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		BufferedImage image = null;
		String barra = "/";
		boolean termina = false;
		while(!termina)
		{
			try {
				image = ImageIO.read(new File("Iconos"+barra+"creditos.png"));
				termina  = true;
			} catch (IOException e) {
				if(barra.equals("/"))
					barra = "\\";
				else{
					termina = true;
					System.out.println("Directorio de imagenes no encontrado");
				}
			}
		}
		JLabel fondo = new JLabel(new ImageIcon(image));
		fondo.setBounds(480, 50, 535, 477);
		contentPane.add(fondo);
		
		mensaje = new JLabel("Procesando...");
		mensaje.setFont(new Font("Tahoma", Font.PLAIN, 20));
		mensaje.setBounds(500, 10, 130, 35);
		mensaje.setVisible(false);
		contentPane.add(mensaje);
		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		posiciones = new BotonPosicion[8][8];
		boolean negro = true;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				posiciones[i][j] = new BotonPosicion(i, j, negro);
				contentPane.add(posiciones[i][j]);
				negro = !negro;
			}
			negro = !negro;
		}
	}

	/**
	 * Devuelve un boton del tablero grafico
	 * 
	 * @param x
	 *            posicion en X del boton
	 * @param y
	 *            posicion en Y del boton
	 * @return devuelve una variable de tipo BotonPosicion
	 */
	public BotonPosicion darPosicion(int x, int y) {
		return posiciones[x][y];
	}
	
	/**
	 * Devuelve un boton de control de juego especifico
	 * @param tipo Tipo de boton
	 * @return Devuelve el boton elegido
	 */

	public JButton darBoton(String tipo) {
		JButton boton = null;
		if (tipo.equals("salir"))
			boton = salir;
		else if (tipo.equals("reiniciar"))
			boton = reiniciar;
		else if (tipo.equals("salirguardar"))
			boton = salirGuardar;
		else if (tipo.equals("editar"))
			boton = editar;
		return boton;
	}
	
	public void mostrarMensaje(boolean mostrar){
		mensaje.setVisible(mostrar);
	}
}
