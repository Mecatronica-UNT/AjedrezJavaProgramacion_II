package Otros;

import com.google.common.collect.ImmutableMap;
import java.util.*;

public class Herramientas {
    
    public static boolean[] primeraColumna = iniciarColumna(0);
    public static boolean[] segundaColumna = iniciarColumna(1);
    public static boolean[] séptimaColumna = iniciarColumna(6);
    public static boolean[] octavaColumna = iniciarColumna(7);
    
    public static boolean[] primeraFila = iniciarFila(0);
    public static boolean[] segundaFila = iniciarFila(8);
    public static boolean[] terceraFila  = iniciarFila(16);
    public static boolean[] cuartaFila  = iniciarFila(24);
    public static boolean[] quintaFila = iniciarFila(32);
    public static boolean[] sextaFila  = iniciarFila(40);
    public static boolean[] séptimaFila= iniciarFila(48);
    public static boolean[] octavaFila = iniciarFila(56);
    
    public static final String[] NotaciónAlgebraica = iniciarNotaciónAlgebraica();
    public static final Map<String, Integer> PosiciónACoordenada = iniciarMapaPosiciónACoordenada();
    public static final int NUM_CASILLAS = 64;
    public static final int NUM_CASILLAS_COLUMNA = 8;
    public static final int INDEX_CASILLA_INICIAL = 0;
    
    private Herramientas(){
        throw new RuntimeException();
    }
    
    private static boolean[] iniciarColumna(int numeroColumna){
        final boolean[] columna = new boolean[NUM_CASILLAS];
        for(int i = 0; i < columna.length; i++) {
            columna[i] = false;
        }
        do{
            columna[numeroColumna] = true;
            numeroColumna +=NUM_CASILLAS_COLUMNA;
        }while(numeroColumna < NUM_CASILLAS);
        return columna;
    }
    
    private static boolean[] iniciarFila(int numeroFila){
        final boolean[] fila = new boolean[NUM_CASILLAS];
        for(int i = 0; i < fila.length; i++) {
            fila[i] = false;
        }
        do{
            fila[numeroFila] = true;
            numeroFila++;
        } while(numeroFila % NUM_CASILLAS_COLUMNA != 0);
        return fila;
    }
    
    private static Map<String, Integer> iniciarMapaPosiciónACoordenada() {
        final Map<String, Integer> PosiciónACoordenada = new HashMap<>();
        for (int i = INDEX_CASILLA_INICIAL; i < NUM_CASILLAS; i++) {
            PosiciónACoordenada.put(NotaciónAlgebraica[i], i);
        }
        return ImmutableMap.copyOf(PosiciónACoordenada);
    }
    
    private static String[] iniciarNotaciónAlgebraica() {
        return new String[]{
                "a8", "b8", "c8", "d8", "e8", "f8", "g8", "h8",
                "a7", "b7", "c7", "d7", "e7", "f7", "g7", "h7",
                "a6", "b6", "c6", "d6", "e6", "f6", "g6", "h6",
                "a5", "b5", "c5", "d5", "e5", "f5", "g5", "h5",
                "a4", "b4", "c4", "d4", "e4", "f4", "g4", "h4",
                "a3", "b3", "c3", "d3", "e3", "f3", "g3", "h3",
                "a2", "b2", "c2", "d2", "e2", "f2", "g2", "h2",
                "a1", "b1", "c1", "d1", "e1", "f1", "g1", "h1"};
    }
    public static boolean esCoordenadaVálida(int coordenada) {
        return coordenada >= 0 && coordenada < 64;
    }
    
    public static int getCoordenadaEnLaPosición(final String posición){
        return PosiciónACoordenada.get(posición);
    }
    
    public static String getPosiciónEnLaCoordenada(final int coordenada){
        return NotaciónAlgebraica[coordenada];
    }
    
}
