/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jugador;
 
import Otros.Color;
import Piezas.Pieza;
import Tablero.Movimiento;
import Tablero.Tablero;

import java.util.Collection;
/**
 *
 * @author JOSE MONTALVO
 */
public abstract class JugadorNegro extends Jugador{
    public JugadorNegro(Tablero tablero, Collection<Movimiento> movimientosLegalesStandardBlanco, Collection<Movimiento> movimientosLegalesStandardNegro){
        
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
        return this.tablero.jugadorNegro;
    }
}
