/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jugador;

import Tablero.Movimiento;
import Tablero.Tablero;

/**
 *
 * @author JOSE MONTALVO
 */
class TransiciónDeMovimiento {
    
    private final Tablero transiciónDeTablero;
    private final Movimiento movimiento;
    private final EstatusDeMovimiento estatusDeMovimiento;
    
    
    public TransiciónDeMovimiento(final Tablero transiciónDeTablero, final Movimiento movimiento, final EstatusDeMovimiento estatusDeMovimiento){
        this.transiciónDeTablero = transiciónDeTablero;
        this.movimiento = movimiento;
        this.estatusDeMovimiento = estatusDeMovimiento;
    }
    
    public EstatusDeMovimiento getEstatusDeMovimiento(){
        return this.estatusDeMovimiento;
    }
}
