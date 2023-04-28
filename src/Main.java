import com.opencsv.exceptions.CsvValidationException;
import iteso.scanner.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Main {
    // Agregar opciones si el escaneo será UDP o TCP
    // Si quiere escanear servicio
    // Si quiere exportar y el nombre del archivo
    // IP y rango de puertos
    public static void main(String[] args) throws InterruptedException, IOException, CsvValidationException {
        boolean opcion_escanea_servicios = true;
        boolean exportar = true;
        HilosTCP TCP = new HilosTCP("127.0.0.1",1,1000);
        ArrayList<Integer> puertosabiertos = TCP.scan();
        if(!opcion_escanea_servicios) {
            for (int puerto : puertosabiertos) {
                System.out.println("El puerto " + puerto + " está abierto");
            }
            if(exportar){
                Export.exportaJSONSolo(puertosabiertos,"prueba.json");
            }
        }
        /*
        HilosUDP UDP = new HilosUDP("127.0.0.1",1,200);
        ArrayList<Integer> puertosUDP = UDP.escanea();
        if(!opcion_escanea_servicios) {
            for (int puerto : puertosUDP) {
                System.out.println("El puerto " + puerto + " está abierto");
            }
            if(exportar){
                Export.exportaJSONSolo(puertosabiertos,"prueba.json");
            }
        }
        */
        if(opcion_escanea_servicios) {
            IanaDatabase a = new IanaDatabase();
            HashMap<Integer,String> info = new HashMap<>();
            info = a.leeTCP(puertosabiertos);
            for (Map.Entry<Integer,String> entry : info.entrySet()){
                System.out.println("[*] Puerto : "+entry.getKey()+"\t"+entry.getValue());
            }
            if(exportar){
                Export.exportaJSONDescripcion(info,"prueba.json");
            }
        }
        /*
        if(opcion_escanea_servicios) {
            IanaDatabase a = new IanaDatabase();
            HashMap<Integer,String> info = new HashMap<>();
            info = a.leeUDP(puertosUDP);
            for (Map.Entry<Integer,String> entry : info.entrySet()){
                System.out.println("[*] Puerto : "+entry.getKey()+"\t"+entry.getValue());
            }
            if(exportar){
                Export.exportaJSONDescripcion(info,"prueba.json");
            }
        }
         */
    }
}