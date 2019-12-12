package Otros;

import Jugador.Jugador;
import Jugador.JugadorBlanco;
import Jugador.JugadorNegro;

public enum Color {
    BLANCO {
        @Override
        public int getDirección() {
            return -1;
        }

        @Override
        public boolean esBlanco() {
            return true;
        }

        @Override
        public boolean esNegro() {
            return false;
        }

        @Override
        public Jugador elegirJugador(final JugadorBlanco jugadorBlanco, final JugadorNegro jugadorNegro) {
            return jugadorBlanco;
        }

        @Override
        public int getDirecciónOpuesta() {
            return 1;
        }

        @Override
        public boolean esCasillaDePromociónDePeón(int posición) {
            return Herramientas.primeraFila[posición];
        }
    },
    NEGRO {
        @Override
        public int getDirección() {
            return 1;
        }

        @Override
        public boolean esBlanco() {
            return false;
        }

        @Override
        public boolean esNegro() {
            return true;
        }

        @Override
        public Jugador elegirJugador(final JugadorBlanco jugadorBlanco,final JugadorNegro jugadorNegro) {
            return jugadorNegro;
        }

        @Override
        public int getDirecciónOpuesta() {
            return -1;
        }

        @Override
        public boolean esCasillaDePromociónDePeón(int posición) {
            return Herramientas.octavaFila[posición];
        }
    };
    
    public abstract int getDirección();
    public abstract int getDirecciónOpuesta();
    public abstract boolean esBlanco();
    public abstract boolean esNegro();
    public abstract boolean esCasillaDePromociónDePeón(int posición);
    public abstract Jugador elegirJugador(JugadorBlanco jugadorBlanco, JugadorNegro jugadorNegro); 
    }
    

