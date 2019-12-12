package Jugador;
//revisar línea 56 a 59 (falta arreglar algo)
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

public class JugadorBlanco extends Jugador{
    public JugadorBlanco(final Tablero tablero, 
                          final Collection<Movimiento> movimientosLegalesStandardBlanco, 
                          final Collection<Movimiento> movimientosLegalesStandardNegro){   
        super(tablero, movimientosLegalesStandardBlanco, movimientosLegalesStandardNegro);
    }
    
    @Override
    public Collection<Pieza> getPiezasActivas(){
        return this.tablero.getPiezasBlancas();
    }
    
    @Override
    public Color getColor(){
        return Color.BLANCO;
    }
    
    @Override
    public Jugador getOponente(){
        return this.tablero.jugadorNegro();
    }

    @Override
    protected Collection<Movimiento> cacularEnroqueRey(final Collection<Movimiento> jugadorLegales,final Collection<Movimiento> oponenteLegales) {
        
        final List<Movimiento> enroquesRey = new ArrayList<>();
        if(this.jugadorRey.esPrimerMovimiento() && !this.estáEnJaque()){
            //Enroque por lado del rey para las blancas
            if(!this.tablero.getCasilla(61).estáOcupadoPorPieza() && !this.tablero.getCasilla(62).estáOcupadoPorPieza()){
                final Casilla casillaEnroque = this.tablero.getCasilla(63);
                if(casillaEnroque.estáOcupadoPorPieza() && casillaEnroque.getPieza().esPrimerMovimiento()){
                    if(Jugador.calcularAtaqueEnCasilla(61, oponenteLegales).isEmpty() &&
                       Jugador.calcularAtaqueEnCasilla(62, oponenteLegales).isEmpty() &&
                       casillaEnroque.getPieza().getTipoDePieza().esTorre()){
                       enroquesRey.add(new Movimiento.EnroqueRey(this.tablero,this.jugadorRey, 62, (Torre) casillaEnroque.getPieza(), casillaEnroque.getCoordenadaDeCasilla(), 61));
                    }
                }
            }
            if(!this.tablero.getCasilla(59).estáOcupadoPorPieza() && 
               !this.tablero.getCasilla(58).estáOcupadoPorPieza() && 
               !this.tablero.getCasilla(57).estáOcupadoPorPieza()){
                final Casilla casillaEnroque = this.tablero.getCasilla(56);
                if(casillaEnroque.estáOcupadoPorPieza() && casillaEnroque.getPieza().esPrimerMovimiento()&& Jugador.calcularAtaqueEnCasilla(58, movimientosLegales).isEmpty() &&
                        Jugador.calcularAtaqueEnCasilla(59, movimientosLegales).isEmpty() && 
                        casillaEnroque.getPieza().getTipoDePieza().esTorre()){
                      enroquesRey.add(new Movimiento.EnroqueDama(this.tablero, this.jugadorRey, 58, (Torre)casillaEnroque.getPieza(), casillaEnroque.getCoordenadaDeCasilla(), 59));
                }
            }
        }
        return ImmutableList.copyOf(enroquesRey);
    }
}