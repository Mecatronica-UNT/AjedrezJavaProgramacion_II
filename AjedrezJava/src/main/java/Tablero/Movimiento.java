package Tablero;

import Piezas.Peón;
import Piezas.Pieza;
import Piezas.Torre;
import Tablero.Tablero.Constructor;

public abstract class Movimiento {

    final Tablero tablero;
    final Pieza piezaEnMovimiento;
    final int coordenadaDeDestino;
    
    public static final Movimiento MovimientoNulo = new MovimientoNulo();
    
    private Movimiento(final Tablero tablero,final Pieza piezaEnMovimiento,final int coordenadaDeDestino){
        this.tablero = tablero;
        this.piezaEnMovimiento = piezaEnMovimiento;
        this.coordenadaDeDestino = coordenadaDeDestino;
    }
    
    @Override
    public int hashCode(){
        final int primo = 31;
        int resultado = 1;
        resultado = primo * resultado + this.coordenadaDeDestino;
        resultado = primo * resultado + this.piezaEnMovimiento.hashCode();
        return resultado;
    }
    
    @Override
    public boolean equals(final Object otro){
        if(this == otro){
            return true;
        }
        if(!(otro instanceof Movimiento)){
            return false;
        }
        final Movimiento otroMovimiento = (Movimiento) otro;
        return getCoordenadaDeDestino() == otroMovimiento.getCoordenadaDeDestino() &&
               getPiezaEnMovimiento().equals(otroMovimiento.getPiezaEnMovimiento());
    }
    
    public int getCoordenadaActual(){
        return this.piezaEnMovimiento.getPosiciónPieza();
    }
    public int getCoordenadaDeDestino(){
        return this.coordenadaDeDestino;
    }
    
    public Pieza getPiezaEnMovimiento(){
        return this.piezaEnMovimiento;
    }
    
    public boolean esAtaque(){
        return false;
    }
    
    public boolean esEnroque(){
        return false;
    }
    
    public Pieza getPiezaAtacada(){
        return null;
    }
    
    public Tablero Ejecutar() {
        final Constructor constructor = new Constructor();
        for (final Pieza pieza : this.tablero.jugadorActual.getPiezasActivas()) {
            if(!this.piezaEnMovimiento.equals(pieza)){
                constructor.setPieza(pieza);
            }
        }
        for (final Pieza pieza : this.tablero.jugadorActual.getOponente().getPiezasActivas()) {
            constructor.setPieza(pieza);
        }
        constructor.setPieza(this.piezaEnMovimiento.moverPieza(this));
        constructor.setJugadorDeTurno(this.tablero.jugadorActual.getOponente().getColor());
        return constructor.Construir();
    }
    

    
    public static final class MovimientoPacífico extends Movimiento{
        public MovimientoPacífico(final Tablero tablero, final Pieza piezaEnMovimiento, final int coordenadaDeDestino) {
            super(tablero, piezaEnMovimiento, coordenadaDeDestino);
        }
    }
    
    Pieza piezaAtacada;
    
    public static class MovimientoAtaque extends Movimiento{
        public MovimientoAtaque(final Tablero tablero, final Pieza piezaEnMovimiento,final Pieza piezaAtacada, final int coordenadaDeDestino) {
            super(tablero, piezaEnMovimiento, coordenadaDeDestino);
            this.piezaAtacada = piezaAtacada;
        }
        
        @Override
        public int hashCode(){
            return this.piezaAtacada.hashCode() + super.hashCode();
        }
        
        @Override
        public boolean equals(final Object otro){
            if(this == otro){
                return true;
            }
            if(!(otro instanceof MovimientoAtaque)){
                return false;
            }
            final MovimientoAtaque otroMovimientoAtaque = (MovimientoAtaque) otro;
            return super.equals(otroMovimientoAtaque) && getPiezaAtacada().equals(otroMovimientoAtaque.getPiezaAtacada());
        }
        @Override
        public Tablero Ejecutar() {
            return null;
           
        }
        
        @Override
        public boolean esAtaque(){
            return true;
        }
    }
    
    public static final class MovimientoDePeón extends Movimiento{
        public MovimientoDePeón(final Tablero tablero, final Pieza piezaEnMovimiento, final int coordenadaDeDestino) {
            super(tablero, piezaEnMovimiento, coordenadaDeDestino);
        }
    }
    
    public static  class MovimientoAtaquePeón extends MovimientoAtaque{
        public MovimientoAtaquePeón(Tablero tablero, Pieza piezaEnMovimiento, Pieza piezaAtacada, int coordenadaDeDestino) {
            super(tablero, piezaEnMovimiento, piezaAtacada, coordenadaDeDestino);
        }  
    }
    
    public static final class CapturaAlPaso extends MovimientoAtaquePeón{
        public CapturaAlPaso(Tablero tablero, Pieza piezaEnMovimiento, Pieza piezaAtacada, int coordenadaDeDestino) {
            super(tablero, piezaEnMovimiento, piezaAtacada, coordenadaDeDestino);
        }  
    }
    
    public static final class SaltoPeón extends Movimiento {
        public SaltoPeón(final Tablero tablero,final Pieza piezaEnMovimiento,final int coordenadaDeDestino) {
            super(tablero, piezaEnMovimiento, coordenadaDeDestino);
        }
        
        @Override
        public Tablero Ejecutar(){
            final Constructor constructor = new Constructor();
            for(final Pieza pieza : this.tablero.jugadorActual.getPiezasActivas()){
                if(!this.piezaEnMovimiento.equals(pieza)){
                    constructor.setPieza(pieza);
                }
            }
            for(final Pieza pieza : this.tablero.jugadorActual().getOponente().getPiezasActivas()){
                constructor.setPieza(pieza);
            }
            final Peón peónMovido = (Peón)this.piezaEnMovimiento.moverPieza(this);
            constructor.setPieza(peónMovido);
            constructor.setCapturaAlPaso(peónMovido);
            constructor.setJugadorDeTurno(this.tablero.jugadorActual().getOponente().getColor());
            return constructor.Construir();
        }
    }
    
    static abstract class Enroque extends Movimiento{
        protected final Torre torreEnroque;
        protected final int torreEnroqueInicio;
        protected final int torreEnroqueDestino;
        
        public Enroque(final Tablero tablero, final Pieza piezaEnMovimiento, final int coordenadaDeDestino,
                       final Torre torreEnroque, final int torreEnroqueInicio,final int torreEnroqueDestino) {
            super(tablero, piezaEnMovimiento, coordenadaDeDestino);
            this.torreEnroque = torreEnroque;
            this.torreEnroqueDestino = torreEnroqueDestino;
            this.torreEnroqueInicio = torreEnroqueInicio;
        }
        
        public Torre getTorreEnroque(){
            return this.torreEnroque;
        }
        
        @Override
        public boolean esEnroque(){
            return true;
        }
        
        @Override
        public Tablero Ejecutar(){
            final Constructor constructor = new Constructor();
            for(final Pieza pieza : this.tablero.jugadorActual.getPiezasActivas()){
                if(!this.piezaEnMovimiento.equals(pieza)&&!this.torreEnroque.equals(pieza)){
                    constructor.setPieza(pieza);
                }
            }
            for(final Pieza pieza : this.tablero.jugadorActual().getOponente().getPiezasActivas()){
                constructor.setPieza(pieza);
            }
            constructor.setPieza(this.piezaEnMovimiento.moverPieza(this));
            //POR HACERRRRRRRRR
            constructor.setPieza(new Torre(this.torreEnroque.getColorPieza(), this.torreEnroqueDestino));
            constructor.setJugadorDeTurno(this.tablero.jugadorActual.getOponente().getColor());
            return constructor.Construir();
        }
    }
    
    public static final class EnroqueRey extends Enroque{
        public EnroqueRey(final Tablero tablero,final Pieza piezaEnMovimiento,final int coordenadaDeDestino,
                          final Torre torreEnroque, final int torreEnroqueInicio,final int torreEnroqueDestino) {
            super(tablero, piezaEnMovimiento, coordenadaDeDestino, torreEnroque,torreEnroqueInicio, torreEnroqueDestino);
        }
        
        @Override 
        public String toString(){
            return "0-0";
        }
    }
    public static final class EnroqueDama extends Enroque{
        public EnroqueDama(final Tablero tablero, final Pieza piezaEnMovimiento,final  int coordenadaDeDestino,
                           final Torre torreEnroque, final int torreEnroqueInicio,final int torreEnroqueDestino) {
            super(tablero, piezaEnMovimiento, coordenadaDeDestino, torreEnroque,torreEnroqueInicio, torreEnroqueDestino);
        }
        
        @Override 
        public String toString(){
            return "0-0-0";
        }
    }
    
    public static final class MovimientoNulo extends Movimiento{
        
        public MovimientoNulo() {
            super(null,null,-1);
        }
        
        @Override
        public Tablero Ejecutar(){
            throw new RuntimeException("No se puede ejecutar este Movimiento.");
        }
    }
    
    public static class FábricaDeMovimiento{
        private FábricaDeMovimiento(){
            throw new RuntimeException("No instanciable.");
        }
        public static Movimiento crearMovimiento(final Tablero tablero, final int coordenadaActual, final int coordenadaDeLlegada){
            for(final Movimiento movimiento : tablero.getTodosLosMovimientosLegales()){
                if(movimiento.getCoordenadaActual() == coordenadaActual &&
                   movimiento.getCoordenadaDeDestino() == coordenadaDeLlegada){
                    return movimiento;
                }
            }
            return MovimientoNulo;
        }
    }
}