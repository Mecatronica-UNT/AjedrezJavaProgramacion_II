package Piezas;

import Otros.Color;
import Otros.Herramientas;
import Tablero.Casilla;
import Tablero.Movimiento;
import Tablero.Movimiento.MovimientoOrdinario;
import Tablero.Movimiento.MovimientoOrdinarioAtaque;
import Tablero.Tablero;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Torre extends Pieza{

    public Torre(final Color colorPieza,final int posiciónPieza) {
        super(Pieza.TipoDePieza.TORRE, posiciónPieza, colorPieza, true);
    }
    
    public Torre(final Color colorPieza, final int posiciónPieza, final Boolean esPrimerMovimiento){
        super(Pieza.TipoDePieza.TORRE, posiciónPieza, colorPieza, esPrimerMovimiento);
    }
    private final static int[] CoordenadasMovimientosPosibles = {-8,-1,1,8};
    
    @Override
    public Collection<Movimiento> calcularMovimientosLegales(Tablero tablero) {
        
        final List<Movimiento> movimientosLegales = new ArrayList<>();
        
        for(final int coordenadaSeleccionada : CoordenadasMovimientosPosibles){
            int coordenadaDeDestino = this.posiciónPieza;
            while(Herramientas.esCoordenadaVálida(coordenadaDeDestino)){
                if(esExcepciónDePrimeraColumna(coordenadaDeDestino, coordenadaSeleccionada)||
                        esExcepciónDeOctavaColumna(coordenadaDeDestino, coordenadaSeleccionada)){
                    break;
                }
                coordenadaDeDestino += coordenadaSeleccionada;
                if(Herramientas.esCoordenadaVálida(coordenadaDeDestino)){
                    final Casilla casillaDeDestino = tablero.getCasilla(coordenadaDeDestino);
                    if(!casillaDeDestino.estáOcupadoPorPieza()){
                        movimientosLegales.add(new MovimientoOrdinario(tablero,this,coordenadaDeDestino));
                    }else{
                        final Pieza piezaEnLaCoordenadaDeDestino = casillaDeDestino.getPieza();
                        final Color bandoPieza = piezaEnLaCoordenadaDeDestino.getColorPieza();
                        if(this.colorPieza != bandoPieza){
                            movimientosLegales.add(new MovimientoOrdinarioAtaque(tablero,this,piezaEnLaCoordenadaDeDestino,coordenadaDeDestino));
                        }
                        break;
                    } 
                }
            }
        }
        return  ImmutableList.copyOf(movimientosLegales);    
    }
    
    @Override
    public Torre moverPieza(final Movimiento movimiento) {
        return new Torre(movimiento.getPiezaEnMovimiento().getColorPieza(), movimiento.getCoordenadaDeDestino());
    }
    
    private static boolean esExcepciónDePrimeraColumna(final int posiciónActual, final int coordenadaSeleccionada){
        return Herramientas.primeraColumna[posiciónActual] && (coordenadaSeleccionada == -1);  
    }
    private static boolean esExcepciónDeOctavaColumna(final int posiciónActual, final int coordenadaSeleccionada){
        return Herramientas.octavaColumna[posiciónActual] && (coordenadaSeleccionada == 1);  
    }
    
    @Override
    public String toString(){
        return TipoDePieza.TORRE.toString();
    }
}
