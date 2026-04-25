package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class Serializador {
    public static void guardar(Object obj, String ruta) throws Exception {
        ObjectOutputStream salida = new ObjectOutputStream(new FileOutputStream(ruta));
        salida.writeObject(obj);
        salida.close();
    }

    public static Object cargar(String ruta) throws Exception {
        ObjectInputStream entrada = new ObjectInputStream(new FileInputStream(ruta));
        Object obj = entrada.readObject();
        entrada.close();
        return obj;
    }
}
