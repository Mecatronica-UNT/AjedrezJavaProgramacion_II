
package Piezas;

import Otros.Color;
import Otros.Herramientas;
import com.google.common.collect.ImmutableTable;
import com.google.common.collect.Table;

enum UtilidadesPieza {

    INSTANCIAS;

    private final Table<Color, Integer, Dama> TODODS_LAS_DAMAS = UtilidadesPieza.crearTodasLasDamas();
    private final Table<Color, Integer, Torre> TODAS_LAS_TORRES = UtilidadesPieza.crearTodasLasTorres();
    private final Table<Color, Integer, Caballo> TODOS_LOS_CABALLOS = UtilidadesPieza.crearTodosLosCaballos();
    private final Table<Color, Integer, Alfil> TODOS_LOS_ALFILES = UtilidadesPieza.crearTodosLosAlfiles();
    private final Table<Color, Integer, Peón> TODOS_LOS_PEONES = UtilidadesPieza.crearTodosLosPosiblesPeones();

    Peón getPeonMovido(final Color color,
                      final int coordenadaDeDestino) {
        return TODOS_LOS_PEONES.get(color, coordenadaDeDestino);
    }

    Caballo getCaballoMovido(final Color color,
                          final int coordenadaDeDestino) {
        return TODOS_LOS_CABALLOS.get(color, coordenadaDeDestino);
    }

    Alfil getAlfilMovido(final Color color,
                          final int coordenadaDeDestino) {
        return TODOS_LOS_ALFILES.get(color, coordenadaDeDestino);
    }

    Torre getTorreMovida(final Color color,
                      final int coordenadaDeDestino) {
        return TODAS_LAS_TORRES.get(color, coordenadaDeDestino);
    }

    Dama getDamaMovida(final Color color,
                        final int coordenadaDeDestino) {
        return TODODS_LAS_DAMAS.get(color, coordenadaDeDestino);
    }

    private static Table<Color, Integer, Peón> crearTodosLosPosiblesPeones() {
        final ImmutableTable.Builder<Color, Integer, Peón> piezas = ImmutableTable.builder();
        for(final Color color : Color.values()) {
            for(int i = 0; i < Herramientas.NUM_CASILLAS; i++) {
                piezas.put(color, i, new Peón(color, i, false));
            }
        }
        return piezas.build();
    }

    private static Table<Color, Integer, Caballo> crearTodosLosCaballos() {
        final ImmutableTable.Builder<Color, Integer, Caballo> piezas = ImmutableTable.builder();
        for(final Color color : Color.values()) {
            for(int i = 0; i < Herramientas.NUM_CASILLAS; i++) {
                piezas.put(color, i, new Caballo(color, i, false));
            }
        }
        return piezas.build();
    }

    private static Table<Color, Integer, Alfil> crearTodosLosAlfiles() {
        final ImmutableTable.Builder<Color, Integer, Alfil> piezas = ImmutableTable.builder();
        for(final Color color : Color.values()) {
            for(int i = 0; i < Herramientas.NUM_CASILLAS; i++) {
                piezas.put(color, i, new Alfil(color, i, false));
            }
        }
        return piezas.build();
    }

    private static Table<Color, Integer, Torre> crearTodasLasTorres() {
        final ImmutableTable.Builder<Color, Integer, Torre> piezas = ImmutableTable.builder();
        for(final Color color : Color.values()) {
            for(int i = 0; i < Herramientas.NUM_CASILLAS; i++) {
                piezas.put(color, i, new Torre(color, i, false));
            }
        }
        return piezas.build();
    }

    private static Table<Color, Integer, Dama> crearTodasLasDamas() {
        final ImmutableTable.Builder<Color, Integer, Dama> piezas = ImmutableTable.builder();
        for(final Color color : Color.values()) {
            for(int i = 0; i < Herramientas.NUM_CASILLAS; i++) {
                piezas.put(color, i, new Dama(color, i, false));
            }
        }
        return piezas.build();
    }

}