/**
 * 
 */
package proyecto;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Lautaro
 *
 */

public class Project1 {

	/**
	 * @param args
	 */
	public static boolean isNumeric(String str) {
		try {
			double d = Double.parseDouble(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static boolean isInteger(String str) {
		try {
			int i = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}

	public static void main(String[] args) {

		// Path path = FileSystems.getDefault().getPath("data", "info.txt");
		Path path = Paths.get("C:\\prueba", "info.txt");
		Scanner sc = new Scanner(System.in);
		ArrayList<String[]> lista = new ArrayList<String[]>();
		try (BufferedReader reader = Files.newBufferedReader(path)) {
			String line = null;
			while ((line = reader.readLine()) != null) {
				String[] fields = line.split(", ");
				lista.add(fields);
			}
		} catch (IOException x) {
			x.printStackTrace();
		}
		int listSize = lista.size();
		if (listSize < 5) {
			System.out.println("Inserte por lo menos 5 alumnos.");
			return;
		}
		for (int i = 0; i < listSize; i++) {
			String[] info = lista.get(i);
			if (info.length != 6) {
				System.out.println("En la línea " + (i + 1) + " falta un dato o hay una nueva línea vacia.");
				return;
			}
			if (!info[2].equals("M") && !info[2].equals("F")) {
				System.out.println("En la posición " + (i + 1) + ":" + (info[0].length() + info[1].length() + 5)
						+ " está mal puesto el género.");
				return;
			}
			if (!isInteger(info[3]) || Integer.parseInt(info[3]) < 1 || Integer.parseInt(info[3]) > 10) {
				System.out.println(
						"En la posición " + (i + 1) + ":" + (info[0].length() + info[1].length() + info[2].length() + 7)
								+ " el número no esta entre 1-10 o no es un entero.");
				return;
			}
			if (!isInteger(info[4]) || Integer.parseInt(info[4]) < 1 || Integer.parseInt(info[4]) > 10) {
				System.out.println("En la posición " + (i + 1) + ":"
						+ (info[0].length() + info[1].length() + info[2].length() + info[3].length() + 9)
						+ " el número no esta entre 1-10 o no es un entero.");
				return;
			}
			if (!isNumeric(info[5])) {
				System.out
						.println("En la posición "
								+ (i + 1) + ":" + (info[0].length() + info[1].length() + info[2].length()
										+ info[3].length() + info[4].length() + 11)
								+ " las asistencias no son un numero.");
				return;
			}
		}
		double[] promedios = new double[listSize];
		for (int i = 0; i < listSize; i++) {
			String[] info = lista.get(i);
			promedios[i] = (double) Math.round((Double.parseDouble(info[3]) + Double.parseDouble(info[4])) / 2 * 100d)
					/ 100d;
		}
		double temp = 0;
		for (int i = 0; i < listSize; i++) {
			for (int j = 1; j < (listSize - i); j++) {
				if (promedios[j - 1] < promedios[j]) {
					temp = promedios[j - 1];
					String[] info = lista.get(j - 1);
					promedios[j - 1] = promedios[j];
					lista.set(j - 1, lista.get(j));
					promedios[j] = temp;
					lista.set(j, info);
				}
			}
		}
		for (int i = 0; i < 5; i++) {
			String[] info = lista.get(i);
			System.out.println(String.format("\nNombre: %s\nDNI: %s\nPromedio: %f", info[1], info[0], promedios[i]));
		}
		double general = 0;
		for (int i = 0; i < listSize; i++) {
			general += promedios[i];
		}
		general /= listSize;
		System.out.println("\nPromedio general de la clase: " + (double) Math.round(general * 1000d) / 1000d);
		int aprobados = 0;
		for (int i = 0; i < listSize; i++) {
			if (promedios[i] >= 6)
				aprobados++;
		}
		System.out.println("\nCantidad de aprobados: " + aprobados);
		int mCant = 0, fCant = 0;
		double mFaltas = 0, fFaltas = 0;
		for(int i=0; i<listSize; i++) {
			String[] info = lista.get(i);
			if(info[2].equals("M")) {
				mCant++;
				mFaltas += Double.parseDouble(info[5]);
			} else {
				fCant++;
				fFaltas += Double.parseDouble(info[5]);
			}
		}
		if(fCant > 0)
			System.out.println("\nAsistencias de Mujeres: " + ((double) Math.round((fFaltas / fCant * 100d)/ 100d)) + "%");
		if(mCant > 0) 
			System.out.println("\nAsistencias de Hombres: " + ((double) Math.round((mFaltas / mCant * 100d)/ 100d)) + "%");
		int cantDesap = 0;
 		for(int i=0; i<listSize; i++) {
			String[] info = lista.get(i);
			if(Double.parseDouble(info[5]) >= 75 && promedios[i] < 6) {
				cantDesap++;
			}
		}
 		System.out.println("\nLa cantidad de desaprobados son: " + cantDesap);
	}
}
