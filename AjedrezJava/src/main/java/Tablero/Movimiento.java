package Tablero;

import Otros.Herramientas;
import Piezas.Peón;
import Piezas.Pieza;
import Piezas.Torre;
import Tablero.Tablero.Constructor;
import com.google.common.collect.Iterables;

public abstract class Movimiento {

    protected final Tablero tablero;
    protected final Pieza piezaEnMovimiento;
    protected final int coordenadaDeDestino;
    protected final boolean esPrimerMovimiento;
    
    public static final Movimiento MovimientoNulo = new MovimientoNulo();
    
    private Movimiento(final Tablero tablero,final Pieza piezaEnMovimiento,final int coordenadaDeDestino){
        this.tablero = tablero;
        this.piezaEnMovimiento = piezaEnMovimiento;
        this.coordenadaDeDestino = coordenadaDeDestino;
        this.esPrimerMovimiento = piezaEnMovimiento.esPrimerMovimiento();
    }
    
    private Movimiento(final Tablero tablero, final int coordenadaDeDestino){
        this.tablero = tablero;
        this.coordenadaDeDestino = coordenadaDeDestino;
        this.piezaEnMovimiento = null;
        this.esPrimerMovimiento = false;
    }
    
    @Override
    public int hashCode(){
        final int primo = 31;
        int resultado = 1;
        resultado = primo * resultado + this.coordenadaDeDestino;
        resultado = primo * resultado + this.piezaEnMovimiento.hashCode();
        resultado = primo * resultado + this.piezaEnMovimiento.getPosiciónPieza();
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
        return getCoordenadaActual() == otroMovimiento.getCoordenadaActual() &&
               getCoordenadaDeDestino() == otroMovimiento.getCoordenadaDeDestino() &&
               getPiezaEnMovimiento().equals(otroMovimiento.getPiezaEnMovimiento());
    }
    
    public Tablero getTablero(){
        return this.tablero;
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
        this.tablero.jugadorActual().getPiezasActivas().stream().filter((pieza) -> (!this.piezaEnMovimiento.equals(pieza))).forEachOrdered((pieza) -> {
            constructor.setPieza(pieza);
        });
        this.tablero.jugadorActual().getOponente().getPiezasActivas().forEach((pieza) -> {
            constructor.setPieza(pieza);
        });
        constructor.setPieza(this.piezaEnMovimiento.moverPieza(this));
        constructor.setJugadorDeTurno(this.tablero.jugadorActual().getOponente().getColor());
        
        return constructor.Construir();
    }
    public static class MovimientoOrdinarioAtaque extends MovimientoAtaque{
        
        public MovimientoOrdinarioAtaque(final Tablero tablero,final Pieza piezaEnMovimiento,final Pieza piezaAtacada,final int coordenadaDeDestino) {
            super(tablero, piezaEnMovimiento, piezaAtacada, coordenadaDeDestino);
        }
        
        @Override
        public boolean equals(final Object otro){
            return this == otro || otro instanceof MovimientoOrdinarioAtaque && super.equals(otro);
        }
        
        @Override
        public String toString(){
            return this.piezaEnMovimiento.getTipoDePieza().toString() + Herramientas.getPosiciónEnLaCoordenada(this.coordenadaDeDestino);
        }
        
        @Override
        public boolean esAtaque(){
            return true;
        }
    }
    public static final class MovimientoOrdinario extends Movimiento{
        public MovimientoOrdinario(final Tablero tablero, final Pieza piezaEnMovimiento, final int coordenadaDeDestino) {
            super(tablero, piezaEnMovimiento, coordenadaDeDestino);
        }
        
        @Override
        public boolean equals(final Object otro){
            return this == otro || otro instanceof MovimientoOrdinario && super.equals(otro);
        }
        @Override
        public String toString(){
            return piezaEnMovimiento.getTipoDePieza().toString() + Herramientas.getPosiciónEnLaCoordenada(this.coordenadaDeDestino);
        }
        
    }
    
    public static class MovimientoAtaque extends Movimiento{
        final Pieza piezaAtacada;
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
        public boolean esAtaque(){
            return true;
        }
        
        @Override
        public Pieza getPiezaAtacada(){
            return this.piezaAtacada;
        }
    }
    
    public static final class MovimientoDePeón extends Movimiento{
        public MovimientoDePeón(final Tablero tablero, final Pieza piezaEnMovimiento, final int coordenadaDeDestino) {
            super(tablero, piezaEnMovimiento, coordenadaDeDestino);
        }
        
        @Override
        public boolean equals(final Object otro){
            return this == otro || otro instanceof MovimientoDePeón && super.equals(otro);
        }
        
        @Override
        public String toString(){
            return piezaEnMovimiento.getTipoDePieza().toString() + Herramientas.getPosiciónEnLaCoordenada(this.coordenadaDeDestino);
        }
    }
    
    public static  class MovimientoAtaquePeón extends Movimiento{
        final Pieza piezaAtacada;
        public MovimientoAtaquePeón(Tablero tablero, Pieza piezaEnMovimiento, Pieza piezaAtacada, int coordenadaDeDestino) {
            super(tablero, piezaEnMovimiento, coordenadaDeDestino);
            this.piezaAtacada = piezaAtacada;
        }
        
        @Override
        public boolean equals(final Object otro){
            return this == otro || otro instanceof MovimientoAtaquePeón && super.equals(otro);
        }
        
        @Override
        public String toString(){
            return piezaEnMovimiento.getTipoDePieza().toString() +
                   Herramientas.getPosiciónEnLaCoordenada(this.getCoordenadaDeDestino());
        }
        
        @Override
        public boolean esAtaque(){
            return true;
        }
        
        @Override
        public Pieza getPiezaAtacada(){
            return this.piezaAtacada;
        }
    }
    
    public static final class CapturaAlPaso extends MovimientoAtaquePeón{
        public CapturaAlPaso(Tablero tablero, Pieza piezaEnMovimiento, Pieza piezaAtacada, int coordenadaDeDestino) {
            super(tablero, piezaEnMovimiento, piezaAtacada, coordenadaDeDestino);
        }
        
        @Override
        public boolean equals(final Object otro){
            return this == otro || otro instanceof CapturaAlPaso && super.equals(otro);
        }
        
        @Override
        public Tablero Ejecutar(){
            final Constructor constructor = new Constructor();
            this.tablero.jugadorActual().getPiezasActivas().stream().filter((pieza) -> (!this.piezaEnMovimiento.equals(pieza))).forEachOrdered((pieza) -> {
                constructor.setPieza(pieza);
            });
            this.tablero.jugadorActual().getOponente().getPiezasActivas().stream().filter((pieza) -> (!pieza.equals(this.getPiezaAtacada()))).forEachOrdered((pieza) -> {
                constructor.setPieza(pieza);
            });
            constructor.setPieza(this.piezaEnMovimiento.moverPieza(this));
            constructor.setJugadorDeTurno(this.tablero.jugadorActual().getOponente().getColor());
            constructor.setTransiciónDeMovimiento(this);
            return constructor.Construir();
        }
        
        @Override
        public boolean esAtaque(){
            return true;
        }
        
        @Override
        public Pieza getPiezaAtacada(){
            return this.piezaAtacada;
        }
    }
    
    public static final class SaltoPeón extends Movimiento {
        public SaltoPeón(final Tablero tablero,final Pieza piezaEnMovimiento,final int coordenadaDeDestino) {
            super(tablero, piezaEnMovimiento, coordenadaDeDestino);
        }
        
        @Override
        public Tablero Ejecutar(){
            final Constructor constructor = new Constructor();
            this.tablero.jugadorActual().getPiezasActivas().stream().filter((pieza) -> (!this.piezaEnMovimiento.equals(pieza))).forEachOrdered((pieza) -> {
                constructor.setPieza(pieza);
            });
            this.tablero.jugadorActual().getOponente().getPiezasActivas().forEach((pieza) -> {
                constructor.setPieza(pieza);
            });
            final Peón peónMovido = (Peón)this.piezaEnMovimiento.moverPieza(this);
            constructor.setPieza(peónMovido);
            constructor.setCapturaAlPaso(peónMovido);
            constructor.setJugadorDeTurno(this.tablero.jugadorActual().getOponente().getColor());
            return constructor.Construir();
        }
        
        @Override
        public String toString(){
            return piezaEnMovimiento.getTipoDePieza().toString() + Herramientas.getPosiciónEnLaCoordenada(this.coordenadaDeDestino);
        }
    }
    public static class PromociónDePeón extends Movimiento{
        final Movimiento movimientoDecorado;
        final Peón peónPromovido;
        public PromociónDePeón(final Movimiento movimientoDecorado) {
            super(movimientoDecorado.getTablero(), movimientoDecorado.getPiezaEnMovimiento(), movimientoDecorado.getCoordenadaDeDestino());
            this.movimientoDecorado = movimientoDecorado;
            this.peónPromovido = (Peón) movimientoDecorado.getPiezaEnMovimiento();
        }
        @Override 
        public int hashCode(){
            return movimientoDecorado.hashCode() + (31* peónPromovido.hashCode());
        }
        
        @Override
        public boolean equals(final Object otro){
            return this == otro || otro instanceof PromociónDePeón && super.equals(otro);
        }
        
        @Override 
        public Tablero Ejecutar(){
            
            final Tablero tableroDePeónMovido = this.movimientoDecorado.Ejecutar();
            final Tablero.Constructor constructor= new Constructor();
            tableroDePeónMovido.jugadorActual().getPiezasActivas().stream().filter((pieza) -> (!this.peónPromovido.equals(pieza))).forEachOrdered((pieza) -> {
                constructor.setPieza(pieza);
            });
            tableroDePeónMovido.jugadorActual().getOponente().getPiezasActivas().forEach((pieza) -> {
                constructor.setPieza(pieza);
            });
            constructor.setPieza(this.peónPromovido.getPromociónDePeones().moverPieza(this));
            constructor.setJugadorDeTurno(tableroDePeónMovido.jugadorActual().getColor());
            return constructor.Construir();
        }
        
        @Override
        public boolean esAtaque(){
            return this.movimientoDecorado.esAtaque();
        }
        
        @Override
        public Pieza getPiezaAtacada(){
            return movimientoDecorado.getPiezaEnMovimiento();
        }
        
        @Override
        public String toString(){
            return "";
        }
    }
    
    public static abstract class Enroque extends Movimiento{
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
            this.tablero.jugadorActual().getPiezasActivas().stream().filter((pieza) -> (!this.piezaEnMovimiento.equals(pieza)&&!this.torreEnroque.equals(pieza))).forEachOrdered((pieza) -> {
                constructor.setPieza(pieza);
            });
            this.tablero.jugadorActual().getOponente().getPiezasActivas().forEach((pieza) -> {
                constructor.setPieza(pieza);
            });
            constructor.setPieza(this.piezaEnMovimiento.moverPieza(this));
            constructor.setPieza(new Torre(this.torreEnroque.getColorPieza(), this.torreEnroqueDestino));
            constructor.setJugadorDeTurno(this.tablero.jugadorActual().getOponente().getColor());
            return constructor.Construir();
        }
        
        @Override
        public int hashCode(){
            final int primo = 31;
            int resultado = super.hashCode();
            resultado = primo * resultado + this.torreEnroque.hashCode();
            resultado = primo * resultado + this.torreEnroqueDestino;
            return resultado;
        }
        public boolean equals(final Object otro){
            if(this == otro){
                return true;
            }
            if(!(otro instanceof Enroque)){
                return false;
            }
            final Enroque otroEnroque = (Enroque) otro;
            return super.equals(otroEnroque) && this.torreEnroque.equals(otroEnroque.getPiezaAtacada());
        }
    }
    
    public static final class EnroqueRey extends Enroque{
        public EnroqueRey(final Tablero tablero,final Pieza piezaEnMovimiento,final int coordenadaDeDestino,
                          final Torre torreEnroque, final int torreEnroqueInicio,final int torreEnroqueDestino) {
            super(tablero, piezaEnMovimiento, coordenadaDeDestino, torreEnroque,torreEnroqueInicio, torreEnroqueDestino);
        }
        
        @Override
        public boolean equals(final Object otro){
            return this == otro || otro instanceof EnroqueRey && super.equals(otro);
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
        public boolean equals(final Object otro){
            return this == otro || otro instanceof EnroqueDama && super.equals(otro);
        }
        
        @Override 
        public String toString(){
            return "0-0-0";
        }
    }
    
    public static final class MovimientoNulo extends Movimiento{
        
        public MovimientoNulo() {
            super(null, 65);
        }
        
        @Override
        public Tablero Ejecutar(){
            throw new RuntimeException("No se puede ejecutar este Movimiento.");
        }
        
        @Override
        public int getCoordenadaActual(){
            return -1;
        }
    }
    
    public static class FábricaDeMovimiento{
        private FábricaDeMovimiento(){
            throw new RuntimeException("No instanciable.");
        }
        public static Movimiento crearMovimiento(final Tablero tablero, final int coordenadaActual, final int coordenadaDeLlegada){
            for(Movimiento movimiento : Iterables.unmodifiableIterable(Iterables.concat(tablero.jugadorBlanco().getMovimientosLegales(), tablero.jugadorNegro().getMovimientosLegales()))){
                if(movimiento.getCoordenadaActual() == coordenadaActual &&
                   movimiento.getCoordenadaDeDestino() == coordenadaDeLlegada){
                    return movimiento;
                }
            }
            return MovimientoNulo;
        }
    }
}