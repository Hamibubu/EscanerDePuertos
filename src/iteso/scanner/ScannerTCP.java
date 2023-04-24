package iteso.scanner;
import java.net.InetSocketAddress;
import java.net.Socket;

public class ScannerTCP extends Scanner {
    public ScannerTCP(String direccionip, int puertoinicio, int puertofinal){
        super(direccionip,puertoinicio,puertofinal);
    }
    @Override
    protected boolean puertoAbierto(int puerto) {
        try{
            Socket socket = new Socket();
            socket.connect(new InetSocketAddress(direccionip,puerto),100);
            socket.close();
            return true;
        }catch (Exception ex){
            return false;
        }
    }
}
