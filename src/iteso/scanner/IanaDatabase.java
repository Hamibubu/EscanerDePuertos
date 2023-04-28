package iteso.scanner;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IanaDatabase {
    // Ubicación de la base de datos de la IANA con los servicios
    private static final String DATABASE_FILE = "D:\\iana.csv";
    FileReader filereader = new FileReader(DATABASE_FILE);
    // Seteamos el lector de archivos
    CSVReader csvReader = new CSVReader(filereader);
    // Lector para csv
    String[] next;
    // Seteamos donde guardaremos las celdas
    // Constructor que avienta la excepción
    public IanaDatabase() throws FileNotFoundException {
    }
    // Lectura de TCP
    public HashMap<Integer, String> leeTCP(ArrayList<Integer> puertos) throws CsvValidationException, IOException {
        HashMap<Integer, String> valorespuerto = new HashMap<>();
        for (int puerto : puertos) {
            valorespuerto.put(puerto, "Desconocido");
        }
        // Seteamos el mapa a que por default los puertos sean desconocidos
        while ((next = csvReader.readNext()) != null) {
            try {
                if (puertos.contains(Integer.parseInt(next[1]))) {
                    if (next[2].equals("tcp") || next[2].equals("sctp")) {
                        // Si es tcp y el número de puerto coincide agregamos el nombre al mapa
                        valorespuerto.put(Integer.parseInt(next[1]), next[3]);
                    }
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return valorespuerto;
    }
    // Lectura para UDP
    public HashMap<Integer, String> leeUDP(ArrayList<Integer> puertos) throws CsvValidationException, IOException {
        HashMap<Integer, String> valorespuerto = new HashMap<>();
        for (int puerto : puertos) {
            valorespuerto.put(puerto, "Desconocido");
        }
        // Seteamos el mapa a que por default los puertos sean desconocidos
        while ((next = csvReader.readNext()) != null) {
            try {
                if (puertos.contains(Integer.parseInt(next[1]))) {
                    if (next[2].equals("udp")) {
                        // Si es udp y el número de puerto coincide agregamos el nombre al mapa
                        valorespuerto.put(Integer.parseInt(next[1]), next[3]);
                    }
                }
            } catch (NumberFormatException ignored) {
            }
        }
        return valorespuerto;
    }
}