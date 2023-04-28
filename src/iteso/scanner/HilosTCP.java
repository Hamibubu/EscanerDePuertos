package iteso.scanner;

import java.util.ArrayList;
import java.util.List;
import iteso.scanner.ScannerTCP;

public class HilosTCP{
    // Seteamos las variables
    private int numHilos;
    private ScannerTCP[] scanners;
    // Método para calcular el número de hilos por rango de puerto
    public HilosTCP(String direccionip, int puertoinicio, int puertofinal){
        // Obtenemos hilos diponibles
        numHilos = Runtime.getRuntime().availableProcessors();
        scanners = new ScannerTCP[numHilos];
        // Seteamos rango de puertos, inicio y fin
        int numPuertosPorHilo = (puertofinal - puertoinicio + 1) / numHilos;
        int inicio = puertoinicio;
        int fin = inicio + numPuertosPorHilo - 1;
        for (int i = 0; i < numHilos; i++) {
            // Asignamos los escáneres
            scanners[i] = new ScannerTCP(direccionip, inicio, fin);
            inicio = fin + 1;
            fin = inicio + numPuertosPorHilo - 1;
        }
    }
    // Método para realizar el escaneo
    public ArrayList<Integer> scan() throws InterruptedException {
        // Array para guardar los puertos
        ArrayList<Integer> puertosAbiertos = new ArrayList<>();
        Thread[] threads = new Thread[numHilos];
        // Creamos los hilos
        for (int i = 0; i < numHilos; i++) {
            // fi es para que sea estable
            int fi = i;
            // Creamos el hilo
            threads[i] = new Thread(new Scan(scanners[fi],puertosAbiertos));
            threads[i].start();
        }
        // Esperamos a que los hilos terminen
        for (Thread thread : threads) {
            // Esperamos a que acaben
            thread.join();
        }
        // Regresamos los valores
        return puertosAbiertos;
    }
    // Modificamos runnable para el hilo
    class Scan implements Runnable {
        private final ScannerTCP scanner;
        private final List<Integer> puertosAbiertos;
        // Modificamos lo que hará scan
        public Scan(ScannerTCP scanner, ArrayList<Integer> puertosAbiertos) {
            this.scanner = scanner;
            this.puertosAbiertos = puertosAbiertos;
        }
        // Le movemos a run para poner lo que hará el hilo
        public void run() {
            ArrayList<Integer> resultado = scanner.runScan();
            synchronized (puertosAbiertos) {
                puertosAbiertos.addAll(resultado);
            }
        }
    }
}
