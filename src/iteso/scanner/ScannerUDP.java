package iteso.scanner;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class ScannerUDP extends Scanner {
    private int puertoactual;

    public ScannerUDP(String direccionip, int puertoinicio, int puertofinal){
        super(direccionip,puertoinicio,puertofinal);
        puertoactual = puertoinicio;
    }

    @Override
    protected boolean puertoAbierto(int puerto) {
        Random random = new Random();
        byte[] mensaje = new byte[1024];
        for (int i = 0; i < 3; i++) {
            try (DatagramSocket socket = new DatagramSocket(null)) {
                random.nextBytes(mensaje);
                socket.setSoTimeout(20000);
                socket.setReuseAddress(true);
                InetAddress address = InetAddress.getByName(direccionip);
                DatagramPacket packet = new DatagramPacket(mensaje, mensaje.length, address, puerto);
                socket.send(packet);
                packet.setData(new byte[1024]);
                byte[] recibido = new byte[1024];
                DatagramPacket receive = new DatagramPacket(recibido, recibido.length);
                socket.receive(receive);
                System.out.println(receive);
                return true;
            } catch (Exception e) {
            }
        }
        return false;

    }
}