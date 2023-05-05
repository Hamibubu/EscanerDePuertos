package iteso.scanner;

import java.net.InetSocketAddress;
import java.net.Socket;
// Importamos las librerías de socket de java

public class ScannerTCP extends ScannerHilo {
    // Constructor
    public ScannerTCP(String direccionip, int puertoinicio, int puertofinal){
        super(direccionip,puertoinicio,puertofinal);
    }
    // Método de escaneo para puertos TCP
    @Override
    protected boolean puertoAbierto(int puerto) {
        try{
            // Creamos el socket
            Socket socket = new Socket();
            // Intentamos conectarnos
            socket.connect(new InetSocketAddress(direccionip,puerto),50);
            // Cerramos el socket
            socket.close();
            return true;
        }catch (Exception ex){
            // Si hay una excepción de conexión regresamos falso por que no está abierto
            return false;
        }
    }
}
