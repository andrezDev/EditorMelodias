
import javax.swing.*;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;

public class Archivo {

    public static String elegirArchivo() {
        JFileChooser fc = new JFileChooser();

        if (fc.showOpenDialog(new JFrame()) == JFileChooser.APPROVE_OPTION) {
            File f = fc.getSelectedFile();
            return f.getAbsolutePath();
        } else {
            return "";
        }
    }

    public static BufferedReader abrirArchivo(String nombreArchivo) {
        File f = new File(nombreArchivo);
       
        if (f.exists()) {
            try {
                FileReader fr = new FileReader(f);
                return new BufferedReader(fr);
            } catch (IOException e) {
                
                return null;
            }
        } else {
            return null;
        }
    }

    
    public static boolean guardarArchivo(String nombreArchivo, String[] lineas) {
        if (lineas != null) {
           
            try {
                
                BufferedWriter bw = new BufferedWriter(new FileWriter(nombreArchivo));
                for (int i = 0; i < lineas.length; i++) {
                    
                    bw.write(lineas[i]);
                    bw.newLine();
                }
               
                bw.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return false;
        }
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    public static <T> T leerJson(String rutaArchivo, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(new File(rutaArchivo), typeReference);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo JSON: " + e.getMessage());
        }
        return null;
    }

    public static boolean guardarJson(String rutaArchivo, Object objeto) {
        try {
            objectMapper.writeValue(new File(rutaArchivo), objeto);
            System.out.println("Datos guardados correctamente en " + rutaArchivo);
            return true;
        } catch (IOException e) {
            System.err.println("Error al guardar el archivo JSON: " + e.getMessage());
        }
        return false;
    }
}
