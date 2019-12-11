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
    };
    
    public abstract int getDirección();
    public abstract boolean esBlanco();
    public abstract boolean esNegro();
    public abstract Jugador elegirJugador(JugadorBlanco jugadorBlanco, JugadorNegro jugadorNegro); 
    }
    

