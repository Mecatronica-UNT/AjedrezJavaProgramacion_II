package GUI;

import GUI.VentanaTablero.MovRegistro;
import Tablero.Movimiento;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Tablero.Tablero;
import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class PanelHistorialJuego extends JPanel{
     
    private final DataModel modelo;
    private final JScrollPane scrollPanel;
    private static final Dimension HISTORY_PANEL_DIMENSION = new Dimension(100,40);
    
    PanelHistorialJuego(){
        this.setLayout(new BorderLayout());
        this.modelo = new DataModel();
        final JTable tab = new JTable(modelo);
        tab.setRowHeight(15);
        this.scrollPanel = new JScrollPane(tab);
        scrollPanel.setColumnHeaderView(tab.getTableHeader());
        scrollPanel.setPreferredSize(HISTORY_PANEL_DIMENSION);
        this.add(scrollPanel, BorderLayout.CENTER);
        this.setVisible(true);
    }
    
    void rehacer(final Tablero tablero, final MovRegistro movRegistro){
        int filaActual = 0;
        this.modelo.limpiar();
        for(final Movimiento movimiento : movRegistro.getMovimientos()){
            final String moveText = movimiento.toString();
            if(movimiento.getPiezaEnMovimiento().getColorPieza().esBlanco()) {
                this.modelo.setValueAt(moveText, filaActual, 0);
            } else if(movimiento.getPiezaEnMovimiento().getColorPieza().esNegro()){
                this.modelo.setValueAt(moveText, filaActual, 1);
            filaActual++;
            }
        }
        
        if(movRegistro.getMovimientos().size() > 0){
            final Movimiento ultimoMovimiento = movRegistro.getMovimientos().get(movRegistro.tamaño() - 1);
            final String movTexto = ultimoMovimiento.toString();
        
        if(ultimoMovimiento.getPiezaEnMovimiento().getColorPieza().esBlanco()){
            this.modelo.setValueAt(movTexto + calcularJaques(tablero), filaActual, 0);
        } else if(ultimoMovimiento.getPiezaEnMovimiento().getColorPieza().esNegro()){
            this.modelo.setValueAt(movTexto + calcularJaques(tablero), filaActual -1,1);
        }
        
        final JScrollBar vertical = scrollPanel.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
        }        
    }

    private String calcularJaques(final Tablero tablero) {
        if(tablero.jugadorActual().estáEnJaqueMate()) {
            return "#";
        } else if(tablero.jugadorActual().estáEnJaque()){
            return "+";
        }
        return "";
    }

    private static class DataModel extends DefaultTableModel {
        
        private final List<Row> valores;
        private static final String[] NOMBRES ={"Blanco", "Negro"};
        
        
        DataModel(){
            this.valores = new ArrayList<>();
        }
        
        public void limpiar(){
            this.valores.clear();
            setRowCount(0);
        }
        @Override 
        public int getRowCount(){
           if(this.valores == null){
               return 0;
           }
           return this.valores.size();
        }
        @Override 
        public int getColumnCount(){
            return NOMBRES.length;
        }
        
        @Override 
        public Object getValueAt(final int fila, final int columna){
            final Row filaActual = this.valores.get(fila);
            if(columna == 0){
                return filaActual.getMovBlanco();
            } else if(columna == 1){
                return filaActual.getMovNegro();
            }
            return null;
        }
        
        @Override 
        public void setValueAt(final Object aValue, final int fila, final int columna){
            final Row filaActual;
            if(this.valores.size() <= fila){
                filaActual = new Row();
                this.valores.add(filaActual);
            } else {
                filaActual = this.valores.get(fila);
            }
            if(columna == 0){
                filaActual.setMovBlanco((String)aValue);
                fireTableRowsInserted(fila, fila);         
            } else if(columna == 1){
                filaActual.setMovNegro((String)aValue);
                fireTableCellUpdated(fila,columna);
            }
        }
        
        @Override
        public Class<?> getColumnClass(final int columna){
            return Movimiento.class;
        }
        
        @Override
        public String getColumnName(final int columna){
            return NOMBRES[columna];
        }
    }
    
    private static class Row {
        
        private String MovBlanco;
        private String MovNegro;
        
        Row(){
        }
        
        public String getMovBlanco(){
            return this.MovBlanco;
        }
        
        public String getMovNegro(){
            return this.MovNegro;
        }
        
        public void setMovBlanco(final String movimiento) {
            this.MovBlanco = movimiento;
        }

        public void setMovNegro(final String movimiento) {
            this.MovNegro = movimiento;
        }   
    }   
}