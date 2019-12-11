package Tablero;

import Otros.Herramientas;
import Piezas.Pieza;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public abstract class Casilla {
    protected final int coordenadaCasilla;
    
    private static Map<Integer, CasillaVacía> crearTodosLasCasillasVacíosPosibles() {
        
        final Map<Integer, CasillaVacía> mapaDeCasillasVacías = new HashMap<>();
        
        for(int i = 0; i<Herramientas.numeroCasillas; i++){
            mapaDeCasillasVacías.put(i, new CasillaVacía(i));
        }
        return Collections.unmodifiableMap(mapaDeCasillasVacías);
    }
    
    public static Casilla crearEspacio(final int coordenadaCasilla, final Pieza pieza){
        return pieza !=null ? new CasillaOcupada(coordenadaCasilla,pieza) : casillasVacías.get(coordenadaCasilla);
    }

    
    private static final Map<Integer, CasillaVacía> casillasVacías = crearTodosLasCasillasVacíosPosibles();
    
    Casilla(final int coordenadaCasilla){
        this.coordenadaCasilla = coordenadaCasilla;
    }
    
    public abstract boolean estáOcupadoPorPieza();
    
    public abstract Pieza getPieza();
    
    public int getCoordenadaDeCasilla(){
        return this.coordenadaCasilla;
    }
    
    public static final class CasillaVacía extends Casilla{
        
        private CasillaVacía(final int coordenadaCasilla){
            super(coordenadaCasilla);
        }
        
        
        @Override
        public String toString(){
            return "-";
        }
        @Override
        public boolean estáOcupadoPorPieza(){
            return false;
        }
        @Override
        public Pieza getPieza(){
            return null;
        }
    }
    public static final class CasillaOcupada extends Casilla{
        
        private final Pieza piezaEnCasilla;
        
        private CasillaOcupada(final int coordenadaEspacio,final Pieza piezaEnCasilla){
            super(coordenadaEspacio);
            this.piezaEnCasilla = piezaEnCasilla;
        }
        
        @Override
        public String toString(){
            return getPieza().getColorPieza().esNegro() ? getPieza().toString().toLowerCase() : getPieza().toString();
        }
        
        @Override
        public boolean estáOcupadoPorPieza(){
            return true;
        }
        
        @Override
        public Pieza getPieza(){
            return this.piezaEnCasilla;
        }
    }
}


    
