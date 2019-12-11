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
    LEAVES_PLAYER_IN_CHECK{
        @Override
        boolean estáHecho() {
            return false;
        }
    };
    abstract boolean estáHecho();
   
}
