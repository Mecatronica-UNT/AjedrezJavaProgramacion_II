package Jugador;

public enum EstatusDeMovimiento {
    HECHO {
        @Override
        public boolean estáHecho() {
            return true;
        }
    },
    
    MOVIMIENTO_ILEGAL {
        @Override
        public boolean estáHecho() {
            return false;
        }
         
    },
    DEJAR_AL_JUGADOR_EN_JAQUE{
        @Override
        public boolean estáHecho() {
            return false;
        }
    };
    public abstract boolean estáHecho();
   
   
}
