package GUI;

import Otros.Herramientas;
import Piezas.Pieza;
import Tablero.Casilla;
import Tablero.Movimiento;
import Jugador.TransiciónDeMovimiento;
import Tablero.Tablero;
import com.google.common.collect.Lists;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Collections.list;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import static javax.swing.SwingUtilities.*;

public class Ventana1 {
    
    private final JFrame FrameJuego;
    private final PanelTablero panelTablero;
    private Tablero AjedrezTablero;
    private DirecciónTablero direccionTablero;
    
    private Casilla casillaFuente;
    private Casilla destinoCasilla;
    private Pieza PiezaMovHumano;
    
    private final static Dimension DimensionExteriorVentana = new Dimension(600,600);
    private final static Dimension DimensionTablero = new Dimension(400,350);
    private final static Dimension DimensionCasilla = new Dimension(10,10);
    
    private final Color CasillaColorClaro = Color.decode("#FFFACD");
    private final Color CasillaColorOscuro = Color.decode("#593E1A");
    private static String defaultPiezaImgCamino = "C:\\Users\\ASUS\\Documents\\NetBeansProjects\\AjedrezJava\\src\\main\\java\\art\\";
    
    public Ventana1(){
        this.FrameJuego = new JFrame("JAjedrez");
        this.FrameJuego.setLayout(new BorderLayout());
        final JMenuBar BarraMenuVentana = crearBarraMenuVentana();
        this.FrameJuego.setJMenuBar(BarraMenuVentana);
        this.FrameJuego.setSize(DimensionExteriorVentana);
        this.AjedrezTablero = Tablero.crearTableroEstándar();
        this.panelTablero = new PanelTablero();
        this.direccionTablero = DirecciónTablero.NORMAL;
        this.FrameJuego.add(this.panelTablero,BorderLayout.CENTER);
        this.FrameJuego.setVisible(true);
    }

    private JMenuBar crearBarraMenuVentana() {
        final JMenuBar BarraMenuVentana = new JMenuBar();
        BarraMenuVentana.add(crearArchivoMenu());
        BarraMenuVentana.add(crearMenuPreferencias());
        return BarraMenuVentana;
    }

    private JMenu crearArchivoMenu() {
        final JMenu menuArchivo = new JMenu("Archivo");
        final JMenuItem AbrirPGN = new JMenuItem("Leer archivo PGN");
        AbrirPGN.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(final ActionEvent e) {
               System.out.println("Abre ese archivo pgn");}
        });
        menuArchivo.add(AbrirPGN);
        
        final JMenuItem salirMenuItem = new JMenuItem("Salir");
        salirMenuItem.addActionListener(new ActionListener(){
           @Override
           public void actionPerformed(final ActionEvent e) {
               System.exit(0);
           }
        });
        
        menuArchivo.add(salirMenuItem);
        return menuArchivo;  
    }
    
    private JMenu crearMenuPreferencias(){
        final JMenu menuPreferencia = new JMenu("Preferencias");
        final JMenuItem itemVoltearTablero = new JMenuItem("Flip Board");
        itemVoltearTablero.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(final ActionEvent e){
                direccionTablero = direccionTablero.opuesto();
                panelTablero.dibujarTablero(AjedrezTablero);
            }
        });
        menuPreferencia.add(itemVoltearTablero);
        return menuPreferencia;
    }
    public enum DirecciónTablero{
        
        NORMAL {
            @Override
            List<PanelCasilla> transverso(List<PanelCasilla> casillasTablero) {
                return casillasTablero;
            }

            @Override
            DirecciónTablero opuesto() {
                return VOLTEADO;
            }
        },
        VOLTEADO {
            @Override
            List<PanelCasilla> transverso(List<PanelCasilla> casillasTablero) {
                return Lists.reverse(casillasTablero);
            }

            @Override
            DirecciónTablero opuesto() {
                return NORMAL;
            }
        };
        
        abstract List<PanelCasilla> transverso(final List<PanelCasilla> casillasTablero);
        abstract DirecciónTablero opuesto();
        
    }
    
    private class PanelTablero extends JPanel {
        final java.util.List<PanelCasilla> Casillastablero;
        
        PanelTablero(){
            super(new GridLayout(8,8));
            this.Casillastablero = new ArrayList<>();
            for(int i=0; i< Herramientas.numeroCasillas; i++){
                final PanelCasilla casillaPanel = new PanelCasilla(this, i);
                this.Casillastablero.add(casillaPanel);
                add(casillaPanel);
            }
            setPreferredSize(DimensionTablero);
            validate();
            
        }
        
        public void dibujarTablero(final Tablero tablero){
            removeAll();
            for(final PanelCasilla casillaPanel : direccionTablero.transverso(Casillastablero)){
                casillaPanel.dibujarCasilla(tablero);
                add(casillaPanel);
            }
            validate();
            repaint();
        }
    }
    private class PanelCasilla extends JPanel {
        private final int casillaID;
        PanelCasilla(final PanelTablero panelTablero, final int casillaID){
            
            super(new GridBagLayout());
            this.casillaID= casillaID;
            setPreferredSize(DimensionCasilla);
            asignCasillaColor();
            asignIconoPiezaCasilla(AjedrezTablero);
            addMouseListener(new MouseListener() {             
                
                @Override
                public void mouseClicked(final MouseEvent e) {
                    if(isRightMouseButton(e)){
                         casillaFuente = null;
                         PiezaMovHumano = null;
                         destinoCasilla = null;
                    }else if(isLeftMouseButton(e)){
                        if(casillaFuente == null){
                            casillaFuente = AjedrezTablero.getCasilla(casillaID);
                            PiezaMovHumano = casillaFuente.getPieza();
                            if(PiezaMovHumano == null){
                            casillaFuente = null;
                            }
                        }else {  
                            destinoCasilla = AjedrezTablero.getCasilla(casillaID);
                            final Movimiento movimiento = Movimiento.FábricaDeMovimiento.crearMovimiento(AjedrezTablero,casillaFuente.getCoordenadaDeCasilla(),destinoCasilla.getCoordenadaDeCasilla());
                            final TransiciónDeMovimiento transición = AjedrezTablero.jugadorActual.hacerUnMovimiento(movimiento);
                            if(transición.getEstatusDeMovimiento().estáHecho()){
                                AjedrezTablero = transición.getTableroDeTransición();
                                //TODO add the move that was made to the move log
                            }
                             
                            casillaFuente = null;
                            destinoCasilla= null;
                            PiezaMovHumano = null;
                        }
                         SwingUtilities.invokeLater(new Runnable(){
                            @Override
                            public void run() {
                                panelTablero.dibujarTablero(AjedrezTablero);
                            }
                        });
                    }
                }
                @Override
                public void mousePressed(final MouseEvent e) {
                    
                }

                @Override
                public void mouseReleased(final MouseEvent e) {
                    
                }

                @Override
                public void mouseEntered(final MouseEvent e) {
                    
                }

                @Override
                public void mouseExited(final MouseEvent e) {
                    
                }
             });
            validate();
        }
        public void dibujarCasilla(final Tablero tablero){
            asignCasillaColor();
            asignIconoPiezaCasilla(tablero);
            validate();
            repaint();
        }
        private void asignIconoPiezaCasilla(final Tablero tablero){
            this.removeAll();
            if(tablero.getCasilla(this.casillaID).estáOcupadoPorPieza()){
                try {
                    final BufferedImage imagen=
                            ImageIO.read(new File(defaultPiezaImgCamino + tablero.getCasilla(this.casillaID).getPieza().getColorPieza().toString().substring(0,1)+
                                    tablero.getCasilla(this.casillaID).getPieza().toString() + ".gif"));
                    add(new JLabel(new ImageIcon(imagen)));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
                
        private void asignCasillaColor() {
           boolean esClaro = ((casillaID + casillaID/8)%2 == 0);
           setBackground(esClaro? CasillaColorClaro : CasillaColorOscuro);
        }

    }
}
