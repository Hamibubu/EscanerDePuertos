package iteso.scanner;

import java.util.ArrayList;

public class Scanner {
    // Variables que dará el usuario
    protected int puertoinicio = -1;
    protected int puertofinal = -1;
    protected String direccionip = null;
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
    public ArrayList<Integer> run() {
        ArrayList<Integer> puertosAbiertos = new ArrayList<>();
        for(int puerto = puertoinicio; puerto<= puertofinal; puerto++){
            if(puertoAbierto(puerto)){
                puertosAbiertos.add(puerto);
            }
        }
        return puertosAbiertos;
    }
    protected boolean puertoAbierto(int puerto){
        return false;
    }
}
