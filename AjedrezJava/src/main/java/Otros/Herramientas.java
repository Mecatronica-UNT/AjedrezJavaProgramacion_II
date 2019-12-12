package Otros;

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
    
    public static final int numeroCasillas = 64;
    public static final int numeroCasillasPorColumna = 8;
    
    private Herramientas(){
        throw new RuntimeException();
    }
    
    private static boolean[] iniciarColumna(int numeroColumna){
        final boolean[] columna = new boolean[64];
        do{
            columna[numeroColumna] = true;
            numeroColumna +=numeroCasillasPorColumna;
        }while(numeroColumna < numeroCasillas);
        return columna;
    }
    
    private static boolean[] iniciarFila(int numeroFila){
        final boolean[] fila = new boolean[numeroCasillas];
        do{
            fila[numeroFila] = true;
            numeroFila++;
        } while(numeroFila % numeroCasillas != 0);
        return fila;
    }

    public static boolean esCoordenadaVálida(int coordenada) {
        return coordenada >= 0 && coordenada < 64;
    }
    
}
