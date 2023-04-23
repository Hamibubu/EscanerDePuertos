package iteso.scanner;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;

public class Scanner {
    // Variables que dará el usuario
    private int puertoinicio = -1;
    private int puertofinal = -1;
    private String direccionip = null;
    // Creamos el scanner
    public Scanner(String direccionip, int puertoinicio, int puertofinal){
        setDireccionip(direccionip);
        setPuertoinicio(puertoinicio);
        setPuertofinal(puertofinal);
    }
    // Setters
    public void setPuertoinicio(int puertoinicio){
        this.puertoinicio = puertoinicio;
    }
    public void setDireccionip(String direccionip) {
        this.direccionip = direccionip;
    }
    public void setPuertofinal(int puertofinal) {
        this.puertofinal = puertofinal;
    }
    // Getters
    public String getDireccionip() {
        return direccionip;
    }
    public int getPuertofinal() {
        return puertofinal;
    }
    public int getPuertoinicio() {
        return puertoinicio;
    }
    // Creamos seteamos la lógica para el escaneo de puertos
    public ArrayList<Integer> run(){
        ArrayList<Integer> puertos = new ArrayList<>();
        System.out.println("[*] Empezando el escaneo...");
        for(int puerto = puertoinicio; puerto <= puertofinal; puerto++){
            try{
                Socket socket = new Socket();
                socket.connect(new InetSocketAddress(direccionip,puerto),100);
                socket.close();
                puertos.add(puerto);
            }catch (Exception ex){
            }
        }
        System.out.println("[*] Escaneo finalizado a continación se muestran los resultados...");
        return puertos;
    }
}
