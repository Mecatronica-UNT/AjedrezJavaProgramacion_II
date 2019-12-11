package Piezas;

import Otros.Color;
import Otros.Herramientas;
import Tablero.Movimiento;
import Tablero.Movimiento.MovimientoAtaque;
import Tablero.Movimiento.MovimientoPacífico;
import Tablero.Tablero;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class Peón extends Pieza{
    
    private final static int[] CoordenadasMovimientosPosibles = {8,16,7,9};

    public Peón(final Color colorPieza, final int posiciónPieza) {
        super( posiciónPieza, colorPieza);
    }

    @Override
    public Collection<Movimiento> calcularMovimientosLegales(final Tablero tablero) {
        final List<Movimiento> movimientosLegales = new ArrayList<>();
        for(final int coordenadaSeleccionada : CoordenadasMovimientosPosibles){
            int coordenadaDeDestino = this.posiciónPieza + (coordenadaSeleccionada)*this.colorPieza.getDirección();
            
            if(!Herramientas.esCoordenadaVálida(coordenadaDeDestino)){
                continue;
            }
            if(coordenadaSeleccionada == 8 && tablero.getCasilla(coordenadaDeDestino).estáOcupadoPorPieza()){
                //POR HACER MÁS EN ESTA PARTE
                movimientosLegales.add(new MovimientoPacífico(tablero, this,coordenadaDeDestino));
            }else if(coordenadaSeleccionada == 16 && this.esPrimerMovimiento()&&
                    (Herramientas.segundaFila[this.posiciónPieza]&&this.getColorPieza().esNegro())||
                    (Herramientas.séptimaFila[this.posiciónPieza]&&this.getColorPieza().esBlanco())){
                final int detrásDeCoordenadaDeDestino = this.posiciónPieza + (this.colorPieza.getDirección()*8);
                if(!tablero.getCasilla(detrásDeCoordenadaDeDestino).estáOcupadoPorPieza()&& 
                   !tablero.getCasilla(coordenadaDeDestino).estáOcupadoPorPieza()){
                    movimientosLegales.add(new MovimientoPacífico(tablero, this,coordenadaDeDestino));
                }
            }else if(coordenadaSeleccionada == 7 && !((Herramientas.octavaColumna[this.posiciónPieza] && this.colorPieza.esBlanco())||
                    (Herramientas.primeraColumna[this.posiciónPieza]&&this.colorPieza.esNegro()))){
                if(tablero.getCasilla(coordenadaDeDestino).estáOcupadoPorPieza()){
                    final Pieza pieceEnLaCasillaDeDestino = tablero.getCasilla(coordenadaDeDestino).getPieza();
                    if(this.colorPieza!=pieceEnLaCasillaDeDestino.getColorPieza()){
                        //POR HACER MÁS EN ESTA PARTE
                        movimientosLegales.add(new MovimientoAtaque(tablero, this,pieceEnLaCasillaDeDestino,coordenadaDeDestino));
                    }
                }
            }else if(coordenadaSeleccionada == 9 && !((Herramientas.primeraColumna[this.posiciónPieza] && this.colorPieza.esBlanco())||
                    (Herramientas.octavaColumna[this.posiciónPieza]&&this.colorPieza.esNegro()))){
                if(tablero.getCasilla(coordenadaDeDestino).estáOcupadoPorPieza()){
                    final Pieza pieceEnLaCasillaDeDestino = tablero.getCasilla(coordenadaDeDestino).getPieza();
                    if(this.colorPieza!=pieceEnLaCasillaDeDestino.getColorPieza()){
                        //POR HACER MÁS EN ESTA PARTE
                        movimientosLegales.add(new MovimientoAtaque(tablero, this,pieceEnLaCasillaDeDestino,coordenadaDeDestino));
                    }
                }
            }
        }
        return Collections.unmodifiableList(movimientosLegales);
    }
    @Override
    public String toString(){
        return tipoDePieza.PEÓN.toString();
    }
}
