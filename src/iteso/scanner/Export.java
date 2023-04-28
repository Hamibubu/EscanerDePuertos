package iteso.scanner;

import org.json.simple.*;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Export {
    // Método para exportar con descripción
    public static void exportaJSONDescripcion(HashMap<Integer,String> mapa, String nombre_arch) throws IOException {
        // Objeto de json
        JSONObject json = new JSONObject();
        // Agregamos el mapa
        json.putAll(mapa);
        // Escribimos
        FileWriter arch = new FileWriter(nombre_arch);
        arch.write(JSONValue.toJSONString(json));
        // Cerramos el archivo
        arch.close();
    }
    // Método para escribir sin descripción
    public static void exportaJSONSolo(ArrayList<Integer> arr,String nombre_arch) throws IOException {
        // Creamos los objetos de json
        JSONObject json = new JSONObject();
        JSONArray jsonarr = new JSONArray();
        // Agregamos al array
        for (int item : arr) {
            jsonarr.add(item);
        }
        // Guardamos
        json.put("puertos", jsonarr);
        // Escribir el objeto JSON en un archivo
        FileWriter file = new FileWriter(nombre_arch);
        file.write(json.toJSONString());
        // Cerramos
        file.close();
    }
}
