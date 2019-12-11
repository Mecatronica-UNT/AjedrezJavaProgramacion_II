/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jugador;

import Otros.Color;
import Piezas.Pieza;
import Piezas.Rey;
import Tablero.Movimiento;
import Tablero.Tablero;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
/**
 *
 * @author JOSE MONTALVO
 */
public abstract class Jugador {

    
    
    protected final Tablero tablero;
    protected final Rey jugadorRey;
    protected final Collection<Movimiento> movimientosLegales;
    private final boolean estáEnJaque;
    
    Jugador(final Tablero tablero, final Rey rey, final Collection<Movimiento> movimientosDelOponente){
        this.tablero = tablero;
        this.jugadorRey = establecerRey();
        this.movimientosLegales = movimientosLegales;
        this.estáEnJaque = !Jugador.calcularAtaqueEnCasilla(this.jugadorRey.getPosiciónPieza(), movimientosDelOponente).isEmpty();
    
    }
    
    public Rey getJugadorRey(){
        return this.jugadorRey;
    }
    
    public Collection<Movimiento> getMovimientosLegales(){
        return this.movimientosLegales;
    }
    private static Collection<Movimiento> calcularAtaqueEnCasilla(int posiciónDePieza, Collection<Movimiento> movimientos) {
        final List<Movimiento> movimientosDeAtaque = new ArrayList();
        for(final Movimiento movimiento : movimientos){
            if(posiciónDePieza == movimiento.getCoordenadaDeDestino()){
                movimientosDeAtaque.add(movimiento);
            }
        }
        
    }
    
    private Rey establecerRey(){
        for(final Pieza pieza : getPiezasActivas()){
            if(pieza.getTipoDePieza().esRey()){
                return (Rey) pieza;
            }
        }        
        throw new RuntimeException("Should not reach here! No es una casilla válida");
    }
    
    public boolean estáEnJaque(){
        return this.estáEnJaque;
    }
    
    public boolean estáEnJaqueMate(){
        return this.estáEnJaque && !tieneMovimientosDeEscape();
    }
    
    protected boolean tieneMovimientosDeEscape() {
        
        for(final Movimiento movimiento : this.movimientosLegales){
            final TransiciónDeMovimiento transición = makeMove(movimiento);
            if(transición.getEstatusDeMovimiento().estáHecho()){
                return true;
            }
        }
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
    
    public TransiciónDeMovimiento makeMove(final Movimiento movimiento){
        
        if(!esMovimientoLegal(movimiento)){
            return new TransiciónDeMovimiento(this.tablero, movimiento, EstatusDeMovimiento.MOVIMIENTO_ILEGAL);
        }
        
        final Tablero transiciónTablero = movimiento.ejecución();
        
        final Collection<Movimiento> ataquesRey = Jugador.calcularAtaqueEnCasilla(transiciónTablero.jugadorActual.getOponente().getReyJugador().getPosiciónPieza(),
                transiciónTablero.jugadorActual().getMovimientosLegales());
        
        if(!ataquesRey.isEmpty()){
            return new TransiciónDeMovimiento(this.tablero, movimiento, EstatusDeMovimiento.LEAVES_PLAYER_IN_CHECK);
        }
        
        return new TransiciónDeMovimiento(tableroDeTransición, movimiento, EstatusDeMovimiento.HECHO);
        
        
    }
    
    public abstract Collection<Pieza> getPiezasActivas();
    public abstract Color getColor();
    public abstract Jugador getOponente();

    
}
