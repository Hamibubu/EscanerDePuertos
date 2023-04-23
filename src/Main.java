import iteso.scanner.Scanner;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner escaneo = new Scanner("127.0.0.1",1,500);
        ArrayList<Integer> puertosabiertos = escaneo.run();
        System.out.println("El resultado del escaneo a "+escaneo.getDireccionip()
                +" de los puertos "+escaneo.getPuertoinicio()+" a "+ escaneo.getPuertofinal()+" es: ");
        for(int puerto:puertosabiertos){
            System.out.println("El puerto "+puerto+" está abierto");
        }
    }
}