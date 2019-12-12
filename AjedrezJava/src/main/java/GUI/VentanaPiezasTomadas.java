package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Piezas.Pieza;
import com.google.common.primitives.Ints;

public class VentanaPiezasTomadas extends JPanel{
    private final JPanel PanelNorte;
    private final JPanel PanelSur;
    private static final Color ColorPanel = Color.decode("0xFD5E6");
    private static final Dimension DimensionPiezaTomada = new Dimension(40,80);
    private static final EtchedBorder BordePanel = new EtchedBorder(EtchedBorder.RAISED);
    private static String defaultPiezaImgCamino = "C:\\Users\\ASUS\\Documents\\NetBeansProjects\\AjedrezJava\\src\\main\\java\\art\\Piezas\\";
    
    public VentanaPiezasTomadas(){
        super(new BorderLayout());
        this.setBackground(ColorPanel);
        this.setBorder(BordePanel);
        this.PanelNorte = new JPanel(new GridLayout(8,2));
        this.PanelSur = new JPanel(new GridLayout(8,2));
        this.PanelNorte.setBackground(ColorPanel);
        this.PanelSur.setBackground(ColorPanel);
        this.add(this.PanelNorte,BorderLayout.NORTH);
        this.add(this.PanelSur, BorderLayout.SOUTH);
        setPreferredSize(DimensionPiezaTomada);
    }
    public void rehacer(final VentanaTablero.MovRegistro movRegistro){
        this.PanelSur.removeAll();
        this.PanelNorte.removeAll();
        final List<Pieza> PiezasBlancasTomadas = new ArrayList<>();
        final List<Pieza> PiezasNegrasTomadas = new ArrayList<>();  
        movRegistro.getMovimientos().stream().filter((movimiento) -> (movimiento.esAtaque())).map((movimiento) -> movimiento.getPiezaAtacada()).forEachOrdered((PiezaTomada) -> {
            if(PiezaTomada.getColorPieza().esBlanco()){
                PiezasBlancasTomadas.add(PiezaTomada);
            }else if(PiezaTomada.getColorPieza().esNegro()){
                PiezasNegrasTomadas.add(PiezaTomada);
            }else{
                throw new RuntimeException("No debería alcanzar aquí");
            }
        });       
        Collections.sort(PiezasBlancasTomadas, (Pieza o1, Pieza o2) -> Ints.compare(o1.getValorPieza(), o2.getValorPieza()));
        Collections.sort(PiezasNegrasTomadas, (Pieza o1, Pieza o2) -> Ints.compare(o1.getValorPieza(), o2.getValorPieza()));
        
        PiezasBlancasTomadas.forEach((PiezaTomada) -> {
            try{
                final BufferedImage imagen = ImageIO.read(new File(defaultPiezaImgCamino
                        + PiezaTomada.getColorPieza().toString().substring(0,1) + PiezaTomada.toString()+".gif"));
                final ImageIcon ic= new ImageIcon(imagen);
                final JLabel imagenLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.PanelNorte.add(imagenLabel);
            } catch(final IOException e){
                e.printStackTrace();
            }
        });
        
        PiezasNegrasTomadas.forEach((PiezaTomada) -> {
            try{
                final BufferedImage imagen = ImageIO.read(new File(defaultPiezaImgCamino
                        + PiezaTomada.getColorPieza().toString().substring(0,1) + PiezaTomada.toString()+".gif"));
                final ImageIcon ic = new ImageIcon(imagen);
                final JLabel imagenLabel = new JLabel(new ImageIcon(ic.getImage().getScaledInstance(
                        ic.getIconWidth() - 15, ic.getIconWidth() - 15, Image.SCALE_SMOOTH)));
                this.PanelSur.add(imagenLabel);
            } catch(final IOException e){
                e.printStackTrace();
            }
        });
        validate();
    }
}


