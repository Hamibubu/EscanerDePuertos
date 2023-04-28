package iteso.scanner;

import iteso.scanner.ScannerUDP;
import java.util.ArrayList;
import java.util.List;

public class HilosUDP {
    // Seteamos las variables
    private int numHilos;
    private ScannerUDP[] escaneos;
    // Método para calcular el número de hilos por rango de puerto
    public HilosUDP(String direccionip, int puertoinicio, int puertofinal){
        // Obtenemos hilos diponibles
        numHilos = Runtime.getRuntime().availableProcessors();
        this.escaneos = new ScannerUDP[numHilos];
        // Seteamos rango de puertos, inicio y fin
        int inicio = puertoinicio;
        int puertosPHilo = (puertofinal - puertoinicio - 1)/numHilos;
        int fin = inicio + puertosPHilo - 1;
        for(int i=0;i<numHilos;i++){
            // Asignamos los escáneres
            escaneos[i] = new ScannerUDP(direccionip,inicio,fin);
            inicio = fin + 1;
            fin = inicio + puertosPHilo - 1;
        }
    }
    // Método para realizar el escaneo
    public ArrayList<Integer> escanea() throws InterruptedException {
        // Array para guardar los puertos
        ArrayList<Integer> puertosAbiertos = new ArrayList<>();
        // Creamos los hilos
        Thread[] threads = new Thread[numHilos];
        for (int i = 0; i < numHilos; i++) {
            // fi es para que sea estable
            int fi = i;
            // Creamos el hilo
            threads[i] = new Thread(new Scan(escaneos[fi], puertosAbiertos));
            threads[i].start();
        }
        for (Thread thread : threads) {
            // Esperamos a que acaben
            thread.join();
        }
        // Regresamos los valores
        return puertosAbiertos;
    }
    // Modificamos runnable para el hilo
    class Scan implements Runnable {
        private final ScannerUDP scanner;
        private final ArrayList<Integer> puertosAbiertos;
        // Modificamos lo que hará scan
        public Scan(ScannerUDP scanner, ArrayList<Integer> puertosAbiertos) {
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
