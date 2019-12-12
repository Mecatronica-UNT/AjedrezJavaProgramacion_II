package Piezas;

import Otros.Color;
import Otros.Herramientas;
import Tablero.Casilla;
import Tablero.Movimiento;
import Tablero.Tablero;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rey extends Pieza{
    
     private final static int[] CoordenadasMovimientosPosibles = {-9,-8,-7,-1,1,7,8,9};

    public Rey(final Color colorPieza,final int posiciónPieza) {
        super(Pieza.TipoDePieza.REY, posiciónPieza, colorPieza);
    }

    @Override
    public Collection<Movimiento> calcularMovimientosLegales(Tablero tablero) {
        
        final List<Movimiento> movimientosLegales = new ArrayList<>();
        
        for(final int coordenadaSeleccionada : CoordenadasMovimientosPosibles){
            final int coordenadaDeDestino = this.posiciónPieza + coordenadaSeleccionada;
            if(esExcepciónDePrimeraColumna(this.posiciónPieza,coordenadaSeleccionada)||
            esExcepciónDeOctavaColumna(this.posiciónPieza,coordenadaSeleccionada)){
                continue;
            }
            if(Herramientas.esCoordenadaVálida(coordenadaDeDestino)){
                final Casilla casillaDeDestino = tablero.getCasilla(coordenadaDeDestino);
                if(!casillaDeDestino.estáOcupadoPorPieza()){
                    movimientosLegales.add(new Movimiento.MovimientoPacífico(tablero,this,coordenadaDeDestino));
                }else{
                    final Pieza piezaEnLaCoordenadaDeDestino = casillaDeDestino.getPieza();
                    final Color colorPieza = piezaEnLaCoordenadaDeDestino.getColorPieza();
                    if(this.colorPieza != colorPieza){
                        movimientosLegales.add(new Movimiento.MovimientoAtaque(tablero,this,piezaEnLaCoordenadaDeDestino,coordenadaDeDestino));
                    }
                }
            }
        }
        return ImmutableList.copyOf(movimientosLegales);
    }
    
    @Override
    public Rey moverPieza(final Movimiento movimiento) {
        return new Rey(movimiento.getPiezaEnMovimiento().getColorPieza(), movimiento.getCoordenadaDeDestino());
    }
    
    private static boolean esExcepciónDePrimeraColumna(final int posiciónActual, final int coordenadaSeleccionada){
        return Herramientas.primeraColumna[posiciónActual] && (coordenadaSeleccionada == -9||coordenadaSeleccionada == -1 || coordenadaSeleccionada == 7);  
    }
    private static boolean esExcepciónDeOctavaColumna(final int posiciónActual, final int coordenadaSeleccionada){
        return Herramientas.octavaColumna[posiciónActual] && (coordenadaSeleccionada == -7||coordenadaSeleccionada == 1 || coordenadaSeleccionada == 9);  
    }
    
    @Override
    public String toString(){
        return tipoDePieza.REY.toString();
    }
}
