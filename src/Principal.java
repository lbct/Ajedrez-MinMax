/**
 * Esta clase es la que ejecuta el Juego
 * @author: Luis Bernardo Caussin Torrez
 * @version: 25/09/2015/A
 */

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import controlador.Control;

@SuppressWarnings("serial")
public class Principal implements java.io.Serializable{

	public static void main(String[] args) throws FileNotFoundException, IOException{
		
		Control control;
		ObjectInputStream leer;
		ObjectOutputStream escribir;
		
		try {
			leer = new ObjectInputStream(new FileInputStream("baseDeDatos.dat"));
			control = (Control) leer.readObject();
			leer.close();
		} catch (FileNotFoundException | ClassNotFoundException e) {
			escribir = new ObjectOutputStream(new FileOutputStream(
					"baseDeDatos.dat"));
			control = new Control();
			escribir.writeObject(control);
		}
		
		control.iniciarJuego();
	}
}
