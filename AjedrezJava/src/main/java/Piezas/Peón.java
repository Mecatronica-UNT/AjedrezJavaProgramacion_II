package Piezas;

import Otros.Color;
import Otros.Herramientas;
import Tablero.Movimiento;
import Tablero.Movimiento.CapturaAlPaso;
import Tablero.Movimiento.MovimientoAtaquePeón;
import Tablero.Movimiento.MovimientoDePeón;
import Tablero.Movimiento.PromociónDePeón;
import Tablero.Movimiento.SaltoPeón;
import Tablero.Tablero;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Peón extends Pieza{
    
    private final static int[] CoordenadasMovimientosPosibles = {8,16,7,9};

    public Peón(final Color colorPieza, final int posiciónPieza) {
        super(Pieza.TipoDePieza.PEÓN, posiciónPieza, colorPieza,true);
    }
    
    public Peón(final Color colorPieza, final int posiciónPieza, final boolean esPrimerMovimiento){
        super(Pieza.TipoDePieza.TORRE, posiciónPieza, colorPieza, esPrimerMovimiento);
    }

    @Override
    public Collection<Movimiento> calcularMovimientosLegales(final Tablero tablero) {
        final List<Movimiento> movimientosLegales = new ArrayList<>();
        for(final int coordenadaSeleccionada : CoordenadasMovimientosPosibles){
            int coordenadaDeDestino = this.posiciónPieza + (coordenadaSeleccionada)*this.colorPieza.getDirección();
            if(!Herramientas.esCoordenadaVálida(coordenadaDeDestino)){
                continue;
            }
            if(coordenadaSeleccionada == 8 && !tablero.getCasilla(coordenadaDeDestino).estáOcupadoPorPieza()){
                if(this.colorPieza.esCasillaDePromociónDePeón(coordenadaDeDestino)){
                    movimientosLegales.add(new PromociónDePeón(new MovimientoDePeón(tablero, this,coordenadaDeDestino )));
                }else {
                    movimientosLegales.add(new MovimientoDePeón(tablero, this,coordenadaDeDestino));
                }
            }else if(coordenadaSeleccionada == 16 && this.esPrimerMovimiento()&&
                    ((Herramientas.segundaFila[this.posiciónPieza]&&this.getColorPieza().esNegro())||
                    (Herramientas.séptimaFila[this.posiciónPieza]&&this.getColorPieza().esBlanco()))){
                final int detrásDeCoordenadaDeDestino = this.posiciónPieza + (this.colorPieza.getDirección()*8);
                if(!tablero.getCasilla(detrásDeCoordenadaDeDestino).estáOcupadoPorPieza()&& 
                   !tablero.getCasilla(coordenadaDeDestino).estáOcupadoPorPieza()){
                    movimientosLegales.add(new SaltoPeón(tablero, this, coordenadaDeDestino));
                }
            }else if(coordenadaSeleccionada == 7 && !((Herramientas.octavaColumna[this.posiciónPieza] && this.colorPieza.esBlanco())||
                    (Herramientas.primeraColumna[this.posiciónPieza]&&this.colorPieza.esNegro()))){
                if(tablero.getCasilla(coordenadaDeDestino).estáOcupadoPorPieza()){
                    final Pieza piezaEnLaCasillaDeDestino = tablero.getCasilla(coordenadaDeDestino).getPieza();
                    if(this.colorPieza!=piezaEnLaCasillaDeDestino.getColorPieza()){
                        if(this.colorPieza.esCasillaDePromociónDePeón(coordenadaDeDestino)){
                            movimientosLegales.add(new PromociónDePeón(new MovimientoAtaquePeón(tablero, this, piezaEnLaCasillaDeDestino,coordenadaDeDestino )));
                        }else {
                            movimientosLegales.add(new MovimientoAtaquePeón(tablero, this,piezaEnLaCasillaDeDestino,coordenadaDeDestino));
                            }
                    }
                }else if(tablero.getEnPassantPeón()!=null){
                    if(tablero.getEnPassantPeón().getPosiciónPieza() == (this.posiciónPieza + (this.colorPieza.getDirecciónOpuesta()))){
                        final Pieza piezaEnLaCasillaDeDestino = tablero.getEnPassantPeón();
                        if(this.colorPieza != piezaEnLaCasillaDeDestino.getColorPieza()){
                            movimientosLegales.add(new CapturaAlPaso(tablero, this, piezaEnLaCasillaDeDestino, coordenadaDeDestino));                        
                        }
                    }
                }
            }else if(coordenadaSeleccionada == 9 && !((Herramientas.primeraColumna[this.posiciónPieza] && this.colorPieza.esBlanco())||
                    (Herramientas.octavaColumna[this.posiciónPieza]&&this.colorPieza.esNegro()))){
                if(tablero.getCasilla(coordenadaDeDestino).estáOcupadoPorPieza()){
                    final Pieza piezaEnLaCasillaDeDestino = tablero.getCasilla(coordenadaDeDestino).getPieza();
                    if(this.colorPieza!=piezaEnLaCasillaDeDestino.getColorPieza()){
                        if(this.colorPieza.esCasillaDePromociónDePeón(coordenadaDeDestino)){
                            movimientosLegales.add(new PromociónDePeón(new MovimientoAtaquePeón(tablero, this, piezaEnLaCasillaDeDestino,coordenadaDeDestino )));
                        }else {
                            movimientosLegales.add(new MovimientoDePeón(tablero, this,coordenadaDeDestino));
                            }
                        }
                }else if(tablero.getEnPassantPeón()!=null){
                    if(tablero.getEnPassantPeón().getPosiciónPieza() == (this.posiciónPieza - (this.colorPieza.getDirecciónOpuesta()))){
                        final Pieza piezaEnLaCasillaDeDestino = tablero.getEnPassantPeón();
                        if(this.colorPieza != piezaEnLaCasillaDeDestino.getColorPieza()){
                            movimientosLegales.add(new CapturaAlPaso(tablero, this, piezaEnLaCasillaDeDestino, coordenadaDeDestino));                        
                        }
                    }
                }
            }
        }
        return ImmutableList.copyOf(movimientosLegales);
    }
    
    @Override
    public Peón moverPieza(final Movimiento movimiento) {
        return new Peón(movimiento.getPiezaEnMovimiento().getColorPieza(), movimiento.getCoordenadaDeDestino());
    }
    
    @Override
    public String toString(){
        return TipoDePieza.PEÓN.toString();
    }
    
    public Pieza getPromociónDePeones(){
            return new Dama(this.colorPieza, this.posiciónPieza, false);
    }
}
