import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.WindowConstants;
import javax.swing.table.DefaultTableModel;

import entidades.Figura;
import entidades.Nota;
import entidades.NotaMusical;

public class FrmEditor extends JFrame {

    private JTable tabMelodia;
    JComboBox comboBoxNota, comboBoxFigura, comboBoxOctava;
    String nombArchivo = "";

    public FrmEditor() {
        setSize(700, 500);
        setTitle("Editor de Melodías");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JToolBar tBarEditor = new JToolBar();
        tBarEditor.setBackground(new java.awt.Color(135, 206, 250));
        

        JButton botonCargar = new JButton();
        botonCargar.setIcon(new ImageIcon(getClass().getResource("/iconos/AbrirArchivos.png")));
        botonCargar.setToolTipText("Agregar");
        botonCargar.setBackground(new java.awt.Color(135, 206, 250));
        botonCargar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                cargarMelodia();
            }
        });
        tBarEditor.add(botonCargar);

        JButton botonGuardar = new JButton();
        botonGuardar.setIcon(new ImageIcon(getClass().getResource("/iconos/Guardar.png")));
        botonGuardar.setToolTipText("Guardar");
        botonGuardar.setBackground(new java.awt.Color(135, 206, 250));
        botonGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                guardarMelodia();
            }
        });
        tBarEditor.add(botonGuardar);

        JButton botonAgregar = new JButton();
        botonAgregar.setIcon(new ImageIcon(getClass().getResource("/iconos/AgregarNota.png")));
        botonAgregar.setToolTipText("Agregar Nota");
        botonAgregar.setBackground(new java.awt.Color(135, 206, 250));
        botonAgregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                agregarNota();
            }
        });
        tBarEditor.add(botonAgregar);

        comboBoxNota = new JComboBox();
        comboBoxNota.setModel(new DefaultComboBoxModel(Arrays.stream(Nota.values()).map(Enum::name).toArray(String[]::new)));
        comboBoxNota.setToolTipText("Nota Musical");
        comboBoxNota.setBackground(new java.awt.Color(135, 206, 250));
        tBarEditor.add(comboBoxNota);

        comboBoxFigura = new JComboBox();
        comboBoxFigura.setToolTipText("Figura Musical");
        comboBoxFigura.setBackground(new java.awt.Color(135, 206, 250));
        comboBoxFigura.setModel(
                new DefaultComboBoxModel(Arrays.stream(Figura.values()).map(Enum::name).toArray(String[]::new)));
        tBarEditor.add(comboBoxFigura);

        comboBoxOctava = new JComboBox();
        comboBoxOctava.setToolTipText("Octava");
        comboBoxOctava.setBackground(new java.awt.Color(135, 206, 250));
        for (int i = 0; i <= 8; i++) {
            comboBoxOctava.addItem(i);
        }
        tBarEditor.add(comboBoxOctava);

        JButton botonModificar = new JButton();
        botonModificar.setIcon(new ImageIcon(getClass().getResource("/iconos/ModificarNota.png")));
        botonModificar.setToolTipText("Modificar Nota");
        botonModificar.setBackground(new java.awt.Color(135, 206, 250));
        botonModificar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                modificarNota();
            }
        });
        tBarEditor.add(botonModificar);

        JButton botonQuitar = new JButton();
        botonQuitar.setIcon(new ImageIcon(getClass().getResource("/iconos/quitarNota.png")));
        botonQuitar.setToolTipText("Quitar Nota");
        botonQuitar.setBackground(new java.awt.Color(135, 206, 250));
        botonQuitar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                quitarNota();
            }
        });
        tBarEditor.add(botonQuitar);

        JButton botonReproducir = new JButton();
       botonReproducir.setIcon(new ImageIcon(getClass().getResource("/iconos/Reproducir.png")));
       botonReproducir.setToolTipText("Reproducir Melodia");
       botonReproducir.setBackground(new java.awt.Color(135, 206, 250));
       botonReproducir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                reproducir();
            }
        });
        tBarEditor.add(botonReproducir);

        tabMelodia = new JTable();
        DefaultTableModel dtm = new DefaultTableModel(null, Melodia.encabezados);
        tabMelodia.setModel(dtm);
        tabMelodia.setBackground(new java.awt.Color(135, 206, 250));

        tabMelodia.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tabMelodia.getSelectedRow() >= 0) {
                Nodo nodoSelec = melodia.obtenerNodo(tabMelodia.getSelectedRow());
                if (nodoSelec != null) {
                    comboBoxNota.setSelectedItem(nodoSelec.getNotaMusical().getNota().name());
                    comboBoxFigura.setSelectedItem(nodoSelec.getNotaMusical().getFigura().name());
                    comboBoxOctava.setSelectedItem(nodoSelec.getNotaMusical().getOctava());
                }
            }
        });

        JScrollPane sPaneMelodia = new JScrollPane(tabMelodia);

        getContentPane().add(tBarEditor, BorderLayout.NORTH);
        getContentPane().add(sPaneMelodia, BorderLayout.CENTER);
    }

    private Melodia melodia = new Melodia();

    private void cargarMelodia() {
        nombArchivo = Archivo.elegirArchivo();
        melodia.desdeJSON(nombArchivo);
        melodia.mostrar(tabMelodia);
        ;
    }

    private void guardarMelodia() {
        if (nombArchivo.equals("")) {
            nombArchivo = Archivo.elegirArchivo();
        }
        melodia.guardarJSON(nombArchivo);
        JOptionPane.showMessageDialog(null, "El archivo fue guardado");
    }

    private void agregarNota() {
        NotaMusical nota = new NotaMusical(Nota.values()[comboBoxNota.getSelectedIndex()],
                Figura.values()[comboBoxFigura.getSelectedIndex()], comboBoxOctava.getSelectedIndex());
        melodia.agregar(new Nodo(nota));
        melodia.mostrar(tabMelodia);
    }

    private void reproducir() {
        melodia.reproducirMelodia();
    }

    private void quitarNota() {
        if (tabMelodia.getSelectedRow() >= 0)
            melodia.eliminar(melodia.obtenerNodo(tabMelodia.getSelectedRow()));
        melodia.mostrar(tabMelodia);
    }

    private void modificarNota() {
        if (tabMelodia.getSelectedRow() >= 0) {
            Nodo nodoSelec = melodia.obtenerNodo(tabMelodia.getSelectedRow());
            nodoSelec.getNotaMusical().setNota(Nota.values()[comboBoxNota.getSelectedIndex()]);
            nodoSelec.getNotaMusical().setFigura(Figura.values()[comboBoxFigura.getSelectedIndex()]);
            nodoSelec.getNotaMusical().setOctava(comboBoxOctava.getSelectedIndex());
            melodia.mostrar(tabMelodia);
        }
    }

}
