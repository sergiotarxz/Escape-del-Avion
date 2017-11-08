package org.sergiotarxz.avion;

import java.util.List;
import java.util.ListIterator;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    public static void main (String[] args) {
	//Declaraciones
	
	List<List<Integer>> mapa = tratarArgumentos(args);
	List<List<int[]>> ListaRutasPorProcesar = new ArrayList<List<int[]>>();
	List<List<int[]>> ListaRutasProcesadas = new ArrayList<List<int[]>>();

	int[] posInicial = new int[2];

	//Punto inicial
	posInicial[0] = mapa.size()/2;
	posInicial[1] = mapa.get(0).size()/2;

	//Incializamos listas
	List<int[]> RutaInicial = new ArrayList<int[]>();
	RutaInicial.add(posInicial);
	ListaRutasPorProcesar.add(RutaInicial);
	/* Este doble bucle es necesario para no generar una excepcion (Introducir referencia) */
	while (ListaRutasPorProcesar.size() != 0) {
	    //Declaramos una Lista de rutas temporal para añadirlas despues a otra lista 
	    List<List<int[]>> tmpRutas = new ArrayList<List<int[]>>();
	    //Iteramos ListaRutasPorProcesar usando un iterador explicito (Para poder borrar elementos de la lista mientras la iteramos)
	    for (ListIterator<List<int[]>> li = ListaRutasPorProcesar.listIterator();li.hasNext();) {
		//Pedimos siguiente elemento del iterador y lo añadimos a una lista
		List<int[]> ruta = li.next();
		//Lo borramos de la lista :)
		li.remove();
		//Usamos un metodo que devuelve las posiciones de los hijos en una Lista y lo iteramos (Ver metodo posHijos)
		for (int[] posHijos : posHijos(ruta.get(ruta.size()-1), mapa, ruta)) {
		    //Declaramos una Lista para cada hijo
		    List<int[]> posHijo = new ArrayList<int[]>();
		    //Añadimos las rutas de su padre
		    for (int[] posPadre : ruta) {
			posHijo.add(posPadre);
		    }
		    //Añadimos la posicion correspondiente al hijo
		    posHijo.add(posHijos);
		    //Si no se sale del borde o se encuentra en el mismo...
		    if (!(posHijos[0] >= mapa.size()-1 || posHijos[0] <= 0 || posHijos[1] >= mapa.get(0).size()-1 || posHijos[1] <= 0)) {
			//Añadimos esta lista a la lista temporal 
			tmpRutas.add(posHijo);
		    }
		    //Si alguna posicion queda en el borde se añade a listas procesadas la ruta
		    else if (posHijos[0] == mapa.size()-1 || posHijos[0] == 0 || posHijos[1] == mapa.get(0).size()-1 || posHijos[1] == 0) {
			ListaRutasProcesadas.add(posHijo);
		    }
		}
	    }
	    //Se añaden todas las rutas temporales a la Lista de rutas sin procesar
	    ListaRutasPorProcesar.addAll(tmpRutas);
	}
	//Dibujo de mapas con las rutas
	for (List<int[]> hen : ListaRutasProcesadas) {
	    for (int a = 0; a < mapa.size(); a++) {
		for (int b = 0 ; b < mapa.get(a).size(); b++) {
		    int[] posi = new int[2];
		    posi[0] = a;
		    posi[1] = b;
		    boolean conta = false;
		    for (int[] posid : hen) {
			if (posi[0] == posid[0] && posi[1] == posid[1]) {
			    conta = true;
			}
		    }
		    if (conta) {
			System.out.printf("*");
		    } else {
			System.out.printf("%d", mapa.get(a).get(b));
		    }
		}
		System.out.printf("\n");
	    }
	    System.out.printf("\n");
	}
    }
    /*Este metodo retorna una lista que contiene.-dado un padre con su ruta y un mapa.-
      la lista de hijos posibles.
     */
    public static List<int[]> posHijos(int[] posPadre, List<List<Integer>> mapa, List<int[]> ruta) {
	//Inicializamos la variable que vamos a retornar.
	List<int[]> posHijos = new ArrayList<int[]>();
	//De 0 a 3... 
	for (int i = 0; i < 4; i++) {
	    //Declaramos una posicion para cada hijo
	    int [] posHijo = new int[2];
	    //La iteramos
	    for (int e = 0; e < 2 ; e++) {
		//El numero i tiene un significado posicional (Consulte Main.traducir())
		posHijo[e] = posPadre[e] + traducir(i)[e];
	    }
	    //Validamos la posicion y si es cierta añadimos posHijo a posHijos
	    if (posicionValidaHijo(posHijo, mapa, ruta)) {
		posHijos.add(posHijo);
	    }
	}
	return posHijos;
    }
    //Formas de obtener la entrada, no relevante.
    public static List<List<Integer>> tratarArgumentos(String[] args){
	List<List<Integer>> mapa = new ArrayList<List<Integer>>();
	if (args.length != 0) {
	    for (String i : args){
		mapa.add(new ArrayList<Integer>());
		char[] fila = i.toCharArray();
		for (char e : fila) {
		    char[] ass = new char[1];
		    ass[0] = e;
		    mapa.get(mapa.size() - 1).add(Integer.parseInt(new String(ass)));
		}
	    }
	} else {
	    mapa = new ArrayList<List<Integer>>() {{
		    add(new ArrayList<Integer>(Arrays.asList(1, 0, 1, 0, 1, 0, 0)));
		    add(new ArrayList<Integer>(Arrays.asList(0, 1, 0, 0, 0, 1, 0)));
		    add(new ArrayList<Integer>(Arrays.asList(0, 0, 0, 1, 0, 1, 0)));
		    add(new ArrayList<Integer>(Arrays.asList(1, 0, 1, 0, 1, 0, 0)));
		    add(new ArrayList<Integer>(Arrays.asList(1, 0, 0, 0, 0, 1, 0)));
		    add(new ArrayList<Integer>(Arrays.asList(0, 1, 0, 1, 0, 0, 1)));
		    add(new ArrayList<Integer>(Arrays.asList(0, 0, 1, 0, 1, 0, 0)));
		}};
	}
	return mapa;
    }
    //La magia detras de Main.traducir()
    public static int[] traducir(int direccion) {
	switch (direccion) {
	case 0:
	    return new int[]{ -1, 0 };
	case 1:
	    return new int[]{ 0, 1 };
	case 2:
	    return new int[]{ 1, 0 };
	case 3:
	    return new int[]{ 0, -1 };
	}
	return null;
    }
    
    public static boolean posicionValidaHijo(int[] pos, List<List<Integer>> mapa, List<int[]> ruta) {
	// Si una posicion anterior coincide con la actual la posicion no es valida
	for (int[] i : ruta) {
	    if ((i[0] == pos[0]) && (i[1] == pos[1])) {
		return false;
	    }
	}
	// Si la posicion cae en un 1 no es valida
	try {
	    if (mapa.get(pos[0]).get(pos[1]) == 1) {
		return false;
	    }
	} catch (IndexOutOfBoundsException ex) {
	    return false;
	}
	return true;
    }
	
}
