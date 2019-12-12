
//revisar línea 63 a 66 (falta arreglar algo)
package Jugador;
 
import Otros.Color;
import Piezas.Pieza;
import Piezas.Torre;
import Tablero.Casilla;
import Tablero.Movimiento;
import Tablero.Tablero;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;

import java.util.Collection;
import java.util.List;
/**
 *
 * @author JOSE MONTALVO
 */
public class JugadorNegro extends Jugador{
    public JugadorNegro(final Tablero tablero, 
                         final Collection<Movimiento> movimientosLegalesStandardBlanco, 
                         final Collection<Movimiento> movimientosLegalesStandardNegro){
        
        super(tablero, movimientosLegalesStandardNegro, movimientosLegalesStandardBlanco);
    }
    
    @Override
    public Collection<Pieza> getPiezasActivas(){
        return this.tablero.getPiezasNegras();
    }
    
    @Override
    public Color getColor(){
        return Color.NEGRO;
    }
    
    @Override
    public Jugador getOponente(){
        return this.tablero.jugadorBlanco();
    }

    @Override
    protected Collection<Movimiento> cacularEnroqueRey(final Collection<Movimiento> jugadorLegales,final Collection<Movimiento> oponenteLegales) {
        
        final List<Movimiento> enroquesRey = new ArrayList<>();
        if(this.jugadorRey.esPrimerMovimiento() && !this.estáEnJaque()){
            //Enroque por lado del rey para las negras
            if(!this.tablero.getCasilla(5).estáOcupadoPorPieza() && !this.tablero.getCasilla(6).estáOcupadoPorPieza()){
                final Casilla casillaEnroque = this.tablero.getCasilla(7);
                if(casillaEnroque.estáOcupadoPorPieza() && casillaEnroque.getPieza().esPrimerMovimiento()){
                    if(Jugador.calcularAtaqueEnCasilla(5, oponenteLegales).isEmpty() &&
                       Jugador.calcularAtaqueEnCasilla(6, oponenteLegales).isEmpty() &&
                       casillaEnroque.getPieza().getTipoDePieza().esTorre()){
                        enroquesRey.add(new Movimiento.EnroqueRey(this.tablero,this.jugadorRey, 6, (Torre) casillaEnroque.getPieza(), casillaEnroque.getCoordenadaDeCasilla(), 7));
                    }
                }
            }
            if(!this.tablero.getCasilla(1).estáOcupadoPorPieza() && 
               !this.tablero.getCasilla(2).estáOcupadoPorPieza() && 
               !this.tablero.getCasilla(3).estáOcupadoPorPieza()){
                final Casilla casillaEnroque = this.tablero.getCasilla(0);
                if(casillaEnroque.estáOcupadoPorPieza() && casillaEnroque.getPieza().esPrimerMovimiento() && Jugador.calcularAtaqueEnCasilla(2, movimientosLegales).isEmpty() &&
                        Jugador.calcularAtaqueEnCasilla(3, movimientosLegales).isEmpty() &&
                        casillaEnroque.getPieza().getTipoDePieza().esTorre()){
                    enroquesRey.add(new Movimiento.EnroqueDama(this.tablero, this.jugadorRey, 2, (Torre)casillaEnroque.getPieza(), casillaEnroque.getCoordenadaDeCasilla(), 1));
                }
            }
        }
        return ImmutableList.copyOf(enroquesRey);
    }
}
