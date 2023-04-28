package iteso.scanner;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;
// Para UDP hay mayor complejidad en la conexión ya que puede dar falsos positivos
// Importamos librerías que nos permiten trabajar con UDP

public class ScannerUDP extends ScannerHilo {
    // Constructor
    public ScannerUDP(String direccionip, int puertoinicio, int puertofinal){
        super(direccionip,puertoinicio,puertofinal);
    }
    // Método de escaneo de puertos
    @Override
    protected boolean puertoAbierto(int puerto) {
        // Genereamos un objeto de random
        Random random = new Random();
        // Bytes para el mensaje como no usaremos serialización
        // Podemos usar esto tal cual como bytes
        byte[] mensaje = new byte[1024];
        // Mandamos 3 mensajes por puerto para mayor fiabilidad
        for (int i = 0; i < 3; i++) {
            // Creamos un socket
            try (DatagramSocket socket = new DatagramSocket(null)) {
                // Generamos el mensaje
                random.nextBytes(mensaje);
                // Timeout del mensaje
                socket.setSoTimeout(500);
                // Reusamos la ip
                socket.setReuseAddress(true);
                // Ip por nombre
                InetAddress address = InetAddress.getByName(direccionip);
                // Nuevo datagrama
                DatagramPacket packet = new DatagramPacket(mensaje, mensaje.length, address, puerto);
                // Enviamos el datagrama
                socket.send(packet);
                // Limpiamos el datagrama
                packet.setData(new byte[1024]);
                // Recibidor del datagrama
                byte[] recibido = new byte[1024];
                // Creamos el datagrama
                DatagramPacket receive = new DatagramPacket(recibido, recibido.length);
                socket.receive(receive);
                // Recibimos el datagrama
                System.out.println(receive.getData());
                // Analizamos si el datagrama es icmp
                // Vemos si el icmp es de error de puertos
                if(receive.getData()[0] == (byte) 0x11 && receive.getData()[1] == (byte) 0x00){
                    // Vemos si es un error en puerto inexistente
                    if (packet.getData()[22] == (byte) 0x03 && packet.getData()[23] == (byte) 0x03) {
                        return false;
                    } else {
                        System.out.println("Puede que el puerto "+puerto+" este siendo bloquedo por el firewall");
                    }
                }
                return true;
            } catch (Exception e) {
            }
        }
        return false;

    }
}