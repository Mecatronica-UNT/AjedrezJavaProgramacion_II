package Piezas;
import Otros.Color;
import Tablero.Movimiento;
import Tablero.Tablero;
import java.util.Collection;

public abstract class Pieza {
    
    protected final int posiciónPieza;
    protected final Color colorPieza;
    protected final boolean esPrimerMovimiento = false;
     
    Pieza(final int posiciónPieza, final Color colorPieza){
        this.posiciónPieza = posiciónPieza;
        this.colorPieza = colorPieza;
    }
    
    public int getPosiciónPieza(){
        return this.posiciónPieza;
    }
    
    public Color getColorPieza(){
        return this.colorPieza;
    }
    
    public boolean esPrimerMovimiento(){
        return this.esPrimerMovimiento;
    }
    
    public abstract Collection<Movimiento> calcularMovimientosLegales(final Tablero tablero);
     
    public enum tipoDePieza{
        ALFIL("A"){
            @Override
            public boolean esRey(){
                return false;
            }
        },
        CABALLO("C"){
             @Override
             public boolean esRey(){
                return false;
            }
        },
        PEÓN("P"){
             @Override
             public boolean esRey(){
                return false;
            }
        },
        DAMA("D"){
             @Override
             public boolean esRey(){
                return false;
            }
        },
        REY("R"){
             @Override
             public boolean esRey(){
                return true;
            }
        },
        TORRE("T"){
             @Override
             public boolean esRey(){
                return false;
            }
        };
       
        private final String nombreDePieza;
        
        tipoDePieza(final String nombreDePieza){

            this.nombreDePieza = nombreDePieza;
        }
        
        @Override
        public String toString(){
            return this.nombreDePieza;
        }
        
        public abstract boolean esRey();

    }
}
    
 
    

