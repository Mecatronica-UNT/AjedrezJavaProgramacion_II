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

public class Alfil extends Pieza{
    
    private final static int[] CoordenadasMovimientosPosibles = {-9,-7,7,9};

    public Alfil(Color colorPieza, int posiciónPieza) {
        super(Pieza.TipoDePieza.ALFIL,posiciónPieza, colorPieza);
    }

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
                        movimientosLegales.add(new Movimiento.MovimientoPacífico(tablero,this,coordenadaDeDestino));
                    }else{
                        final Pieza piezaEnLaCoordenadaDeDestino = casillaDeDestino.getPieza();
                        final Color bandoPieza = piezaEnLaCoordenadaDeDestino.getColorPieza();
                        if(this.colorPieza != bandoPieza){
                            movimientosLegales.add(new Movimiento.MovimientoAtaque(tablero,this,piezaEnLaCoordenadaDeDestino,coordenadaDeDestino));
                        }
                        break;
                    } 
                }
            }
        }
        return  ImmutableList.copyOf(movimientosLegales);    
    }
    
    @Override
    public Alfil moverPieza(final Movimiento movimiento) {
        return new Alfil(movimiento.getPiezaEnMovimiento().getColorPieza(), movimiento.getCoordenadaDeDestino());
    }
    
    @Override
    public String toString(){
        return tipoDePieza.ALFIL.toString();
    }

    private static boolean esExcepciónDePrimeraColumna(final int posiciónActual, final int coordenadaSeleccionada){
        return Herramientas.primeraColumna[posiciónActual] && (coordenadaSeleccionada == -9||coordenadaSeleccionada == 7);  
    }
    private static boolean esExcepciónDeOctavaColumna(final int posiciónActual, final int coordenadaSeleccionada){
        return Herramientas.octavaColumna[posiciónActual] && (coordenadaSeleccionada == -7||coordenadaSeleccionada == 9);  
    }
}