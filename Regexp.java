import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

// Esta es la clase principal donde debe tener su metodo main. 
// Para este proyecto tiene la libertad de ejecutar su programa como desee
// siempre que se cumplan los lineamientos de las instrucciones.
public class Regexp {
    private String[] lenguaje;
    // Implemente aqui su metodo main. Recuerde que debe recibir los argumentos en este orden
    // 1. en path con el archivo .rgx donde esta la informacion de la expresion regular
    // 2. El modo de ejecucion [-afd|-gld|-min|-eval]
    // 3. El nombre del archivo de salida donde escribiran el resultado (para la bandera -eval, este argumento no se toma en cuenta)
    public static void main(String[] args) throws Exception {
        String path = args[0];
        try {
            String bandera = args[1];
            System.out.println(bandera);
            String salida = args[2];
            switch (bandera) {
                case "-afd":
                    System.out.println("Realizando AFD");
                    //AFD(path,salida);
                    break;
                case "-gld":
                    System.out.println("Realizando Gramatica");
                    //gramatica(path,salida);
                    break;
                case "-min":
                    System.out.println("Realizando el minimo");
                    //minimo(path,salida);
                    break;
                case "-eval":
                    System.out.println("Evaluando cuerda");
                    //evaluar(bandera);
                    break;
                default:
                    System.out.println("ERROR!! La bandera ingresada no es correcta.");
                    System.out.println("La bandera a ingresar debe ser [-afd|-gld|-min|-eval].");
                    break;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("ERROR!! Debe ingresar un bandera.");
            System.out.println("La bandera a ingresar debe ser [-afd|-gld|-min|-eval].");
        }
    }

    public static void AFD(String path, String salida) {
        System.out.println("Archivo: " + path + "  Archivo salida: " + salida);
    }
    
    public static void gramatica(String path, String salida) {
        System.out.println("Archivo: " + path + "  Archivo salida: " + salida);
    }
    
    public static void minimo(String path, String salida) {
        System.out.println("Archivo: " + path + "  Archivo salida: " + salida);
    }
    
    public static void evaluar(String path) {
        System.out.println("Archivo: " + path);
    }
}