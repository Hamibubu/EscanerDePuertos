package iteso.scanner;

import java.util.ArrayList;
import java.lang.Thread;
// Importamos la arraylist para almacenar los puertos en la lista

//Usamos una clase abstracta para partir de aquí para implementar TCP y UDP
public abstract class ScannerHilo extends Thread {
    // Primero declaramos donde guardaremos todo
    protected int puertoinicio = -1;
    protected int puertofinal = -1;
    protected String direccionip = null;
    // Constructor del scanner
    public ScannerHilo(String direccionip, int puertoinicio, int puertofinal){
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
    public ArrayList<Integer> runScan() {
        ArrayList<Integer> puertosAbiertos = new ArrayList<>();
        for(int puerto = puertoinicio; puerto<= puertofinal; puerto++){
            if(puertoAbierto(puerto)){
                puertosAbiertos.add(puerto);
            }
        }
        return puertosAbiertos;
    }
    // Clase booleana que regresa Verdadero si el puerto está abierto y falso
    // Si está cerrado
    protected abstract boolean puertoAbierto(int puerto);
}
