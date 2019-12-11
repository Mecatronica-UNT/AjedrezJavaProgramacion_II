package Jugador;

public enum EstatusDeMovimiento {
    HECHO {
        @Override
        boolean estáHecho() {
            return true;
        }
    },
    
    MOVIMIENTO_ILEGAL {
        @Override
        boolean estáHecho() {
            return false;
        }
        

    },
    
    DEJAR_AL_JUGADOR_EN_JAQUE{
        @Override
        boolean estáHecho() {
            return false;
        }
    };
    abstract boolean estáHecho();
   
}
