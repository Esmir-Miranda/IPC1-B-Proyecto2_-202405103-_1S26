package util;

import java.io.*;

public class Serializador {

    public static void guardar(Object obj, String ruta) throws IOException {
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(ruta));
        out.writeObject(obj);
        out.close();
    }

    public static Object cargar(String ruta) throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(ruta));
        Object obj = in.readObject();
        in.close();
        return obj;
    }
}