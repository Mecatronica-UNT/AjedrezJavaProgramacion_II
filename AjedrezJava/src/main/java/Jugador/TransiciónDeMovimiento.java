package Jugador;

import Tablero.Movimiento;
import Tablero.Tablero;

public class TransiciónDeMovimiento {
    
    private final Tablero tableroDeTransición;
    private final Movimiento movimiento;
    private final EstatusDeMovimiento estatusDeMovimiento;
    
    
    public TransiciónDeMovimiento(final Tablero tableroDeTransición, final Movimiento movimiento, final EstatusDeMovimiento estatusDeMovimiento){
        this.tableroDeTransición = tableroDeTransición;
        this.movimiento = movimiento;
        this.estatusDeMovimiento = estatusDeMovimiento;
    }
    
    public EstatusDeMovimiento getEstatusDeMovimiento(){
        return this.estatusDeMovimiento;
    }
    
    public Tablero getTableroDeTransición() {
        return this.tableroDeTransición;
    }
}
