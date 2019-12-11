package Tablero;
import Jugador.Jugador;
import Jugador.JugadorBlanco;
import Jugador.JugadorNegro;
import Otros.Color;
import Otros.Herramientas;
import Piezas.Alfil;
import Piezas.Caballo;
import Piezas.Peón;
import Piezas.Pieza;
import Piezas.Dama;
import Piezas.Rey;
import Piezas.Torre;
import com.google.common.collect.ImmutableList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
        

public class Tablero {
    public final List<Casilla> tableroJuego;
    private final Collection<Pieza> piezasBlancas;
    private final Collection<Pieza> piezasNegras;
    
    public Tablero(Constructor builder){
        this.tableroJuego = crearJuegoTablero(builder);
        this.piezasBlancas = calcularPiezasActivas(this.tableroJuego, Color.BLANCO);
        this.piezasNegras = calcularPiezasActivas(this.tableroJuego, Color.NEGRO);
        
        final Collection<Movimiento> movimientosLegalesStandardBlanco = calcularMovimientosLegales(this.piezasBlancas);
        final Collection<Movimiento> movimientosLegalesStandardNegro = calcularMovimientosLegales(this.piezasNegras);
    }

    @Override
    public String toString(){
        final StringBuilder builder = new StringBuilder();
        for(int i=0; i<Herramientas.numeroCasillas; i++){
            final String textoCasilla  = this.tableroJuego.get(i).toString();
            builder.append(String.format("%3s", textoCasilla));
            if((i+1)% Herramientas.numeroCasillasPorColumna == 0){
                builder.append("\n");
            }
        }
        return builder.toString();
    }
    
    private Collection<Movimiento> calcularMovimientosLegales(Collection<Pieza> piezas){
        
        final  List<Movimiento> movimientosLegales = new ArrayList<>();
        
        for(final Pieza pieza : piezas){
            movimientosLegales.addAll(pieza.calcularMovimientosLegales(this));
        }
        return ImmutableList.copyOf(movimientosLegales);
    }
    
    public Collection<Pieza> getPiezasNegras(){
        return this.piezasNegras;
    }
    
    public Collection<Pieza> getPiezasBlancas(){
        return this.piezasBlancas;
    }
    
    
    private static Collection<Pieza> calcularPiezasActivas(final List<Casilla> tableroJuego, final Color color){
        final List<Pieza> piezasActivas = new ArrayList<>();
        
        for (final Casilla casilla: tableroJuego){
            if(casilla.estáOcupadoPorPieza()){
                final Pieza pieza = casilla.getPieza();
                if(pieza.getColorPieza() == color){
                    piezasActivas.add(pieza);
                }
            }
        }
        return ImmutableList.copyOf(piezasActivas);
    }

    public Casilla getCasilla(final int coordenadaDeDestino){
        return tableroJuego.get(coordenadaDeDestino);
    }
    
    private static List<Casilla> crearJuegoTablero(final Constructor builder){
        final Casilla[] casillas = new Casilla[Herramientas.numeroCasillas];
        for(int i=0; i<Herramientas.numeroCasillas; i++){
            casillas[i] = Casilla.crearEspacio(i, builder.ConfiguracionTablero.get(i));
        }
        return ImmutableList.copyOf(Arrays.asList(casillas));
    }
    
    public static Tablero crearTableroEstándar(){
        final  Constructor constructor = new Constructor();
        
        constructor.setPieza(new Torre(Color.NEGRO,0));
        constructor.setPieza(new Caballo(Color.NEGRO,1));
        constructor.setPieza(new Alfil(Color.NEGRO,2));
        constructor.setPieza(new Dama(Color.NEGRO,3));
        constructor.setPieza(new Rey(Color.NEGRO,4));
        constructor.setPieza(new Alfil(Color.NEGRO,5));
        constructor.setPieza(new Caballo(Color.NEGRO,6));
        constructor.setPieza(new Torre(Color.NEGRO,7));
        constructor.setPieza(new Peón(Color.NEGRO,8));
        constructor.setPieza(new Peón(Color.NEGRO,9));
        constructor.setPieza(new Peón(Color.NEGRO,10));
        constructor.setPieza(new Peón(Color.NEGRO,11));
        constructor.setPieza(new Peón(Color.NEGRO,12));
        constructor.setPieza(new Peón(Color.NEGRO,13));
        constructor.setPieza(new Peón(Color.NEGRO,14));
        constructor.setPieza(new Peón(Color.NEGRO,15));
        
        constructor.setPieza(new Peón(Color.BLANCO,48));
        constructor.setPieza(new Peón(Color.BLANCO,49));
        constructor.setPieza(new Peón(Color.BLANCO,50));
        constructor.setPieza(new Peón(Color.BLANCO,51));
        constructor.setPieza(new Peón(Color.BLANCO,52));
        constructor.setPieza(new Peón(Color.BLANCO,53));
        constructor.setPieza(new Peón(Color.BLANCO,54));
        constructor.setPieza(new Peón(Color.BLANCO,55));
        constructor.setPieza(new Torre(Color.BLANCO,56));
        constructor.setPieza(new Caballo(Color.BLANCO,57));
        constructor.setPieza(new Alfil(Color.BLANCO,58));
        constructor.setPieza(new Dama(Color.BLANCO,59));
        constructor.setPieza(new Rey(Color.BLANCO,60));
        constructor.setPieza(new Alfil(Color.BLANCO,61));
        constructor.setPieza(new Caballo(Color.BLANCO,62));
        constructor.setPieza(new Torre(Color.BLANCO,63));
        
        constructor.setJugadorDeTurno(Color.BLANCO);
        return constructor.Construir();
    }
    
    public static class Constructor{
        
        Map<Integer,Pieza> ConfiguracionTablero;
        Color siguienteJugador;
        
        public Constructor(){
            this.ConfiguracionTablero = new HashMap<>();
        }
        
        
        public Constructor setPieza(final Pieza pieza){
            this.ConfiguracionTablero.put(pieza.getPosiciónPieza(), pieza);
            return this;
        }
        
        public Constructor setJugadorDeTurno(final Color siguienteJugador){
            this.siguienteJugador = siguienteJugador;
            return this;
        }
        
        public Tablero Construir(){
            return new Tablero(this);
        
        }
    }
}
