package Jugador;

import Otros.Color;
import Piezas.Pieza;
import Piezas.Rey;
import Tablero.Movimiento;
import Tablero.Tablero;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;

public abstract class Jugador {

    protected final Tablero tablero;
    protected final Rey jugadorRey;
    protected final Collection<Movimiento> movimientosLegales;
    private final boolean estáEnJaque;
    
    Jugador(final Tablero tablero, final Collection<Movimiento> movimientosLegales, final Collection<Movimiento> movimientosDelOponente){
        this.tablero = tablero;
        this.jugadorRey = establecerRey();
        this.movimientosLegales = ImmutableList.copyOf(Iterables.concat(movimientosLegales,cacularEnroqueRey(movimientosLegales,movimientosDelOponente)));
        this.estáEnJaque = !Jugador.calcularAtaqueEnCasilla(this.jugadorRey.getPosiciónPieza(), movimientosDelOponente).isEmpty();
    
    }
    
    public Rey getJugadorRey(){
        return this.jugadorRey;
    }
    
    public Collection<Movimiento> getMovimientosLegales(){
        return this.movimientosLegales;
    }
    
    protected static Collection<Movimiento> calcularAtaqueEnCasilla(int posiciónDePieza, Collection<Movimiento> movimientos) {
        final List<Movimiento> movimientosDeAtaque = new ArrayList();
        for(final Movimiento movimiento : movimientos){
            if(posiciónDePieza == movimiento.getCoordenadaDeDestino()){
                movimientosDeAtaque.add(movimiento);
            }
        }
        return ImmutableList.copyOf(movimientosDeAtaque);
    }
    
    private Rey establecerRey(){
        for(final Pieza pieza : getPiezasActivas()){
            if(pieza.getTipoDePieza().esRey()){
                return (Rey) pieza;
            }
        }        
        throw new RuntimeException("No es un tablero válido.");
    }
    
    public boolean estáEnJaque(){
        return this.estáEnJaque;
    }
    
    public boolean estáEnJaqueMate(){
        return this.estáEnJaque && !tieneMovimientosDeEscape();
    }
    
    protected boolean tieneMovimientosDeEscape() {
        for(final Movimiento movimiento : this.movimientosLegales){
            final TransiciónDeMovimiento transición = hacerUnMovimiento(movimiento);
            if(transición.getEstatusDeMovimiento().estáHecho()){
                return true;
            }
        }
        return false;
    }
    
    public boolean estáEnAhogamiento(){
        return !this.estáEnJaque() && !this.tieneMovimientosDeEscape();
    }
    
    public boolean estáEnEnroque(){
        return false;
    }
    
    public boolean esMovimientoLegal(final Movimiento movimiento){
        return this.movimientosLegales.contains(movimiento);
    }
    
    public TransiciónDeMovimiento hacerUnMovimiento(final Movimiento movimiento){
        
        if(!esMovimientoLegal(movimiento)){
            return new TransiciónDeMovimiento(this.tablero, movimiento, EstatusDeMovimiento.MOVIMIENTO_ILEGAL);
        }
        
        final Tablero tableroDeTransición = movimiento.Ejecutar();
        
        final Collection<Movimiento> ataquesRey = Jugador.calcularAtaqueEnCasilla(tableroDeTransición.jugadorActual.getOponente().getJugadorRey().getPosiciónPieza(),
                tableroDeTransición.jugadorActual().getMovimientosLegales());
        
        if(!ataquesRey.isEmpty()){
            return new TransiciónDeMovimiento(this.tablero, movimiento, EstatusDeMovimiento.DEJAR_AL_JUGADOR_EN_JAQUE);
        }
        
        return new TransiciónDeMovimiento(tableroDeTransición, movimiento, EstatusDeMovimiento.HECHO); 
    }
 
    public abstract Collection<Pieza> getPiezasActivas();
    public abstract Color getColor();
    public abstract Jugador getOponente();
    protected abstract Collection<Movimiento> cacularEnroqueRey(Collection<Movimiento> jugadorLegales, Collection<Movimiento> oponenteLegales);
    
}
