/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Jugador;

/**
 *
 * @author JOSE MONTALVO
 */
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
