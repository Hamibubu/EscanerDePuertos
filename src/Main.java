import iteso.scanner.*;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ScannerTCP escaneoTCP = new ScannerTCP("127.0.0.1",1,500);
        ArrayList<Integer> puertosabiertos = escaneoTCP.run();
        System.out.println("El resultado del escaneo TCP a "+escaneoTCP.getDireccionip()
                +" de los puertos "+escaneoTCP.getPuertoinicio()+" a "+ escaneoTCP.getPuertofinal()+" es: ");
        for(int puerto:puertosabiertos){
            System.out.println("El puerto "+puerto+" está abierto");
        }
        ScannerUDP escaneoUDP = new ScannerUDP("127.0.0.1",12,13);
        System.out.println("[i] Como estás escaneando UDP va a tardar mucho tiempo...");
        System.out.println("[*] Comenzando el escaneo...");
        ArrayList<Integer> puertosUDP = escaneoUDP.run();
        System.out.println("El resultado del escaneo TCP a "+escaneoUDP.getDireccionip()
                +" de los puertos "+escaneoUDP.getPuertoinicio()+" a "+ escaneoUDP.getPuertofinal()+" es: ");
        for(int puerto:puertosUDP){
            System.out.println("El puerto "+puerto+" está abierto");
        }
    }
}