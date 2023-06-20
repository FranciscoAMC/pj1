import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.ReadOnlyFileSystemException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Esta es la clase principal donde debe tener su metodo main. 
// Para este proyecto tiene la libertad de ejecutar su programa como desee
// siempre que se cumplan los lineamientos de las instrucciones.
public class Regexp {
    static String salida;
    // Implemente aqui su metodo main. Recuerde que debe recibir los argumentos en este orden
    // 1. en path con el archivo .rgx donde esta la informacion de la expresion regular
    // 2. El modo de ejecucion [-afd|-gld|-min|-eval]
    // 3. El nombre del archivo de salida donde escribiran el resultado (para la bandera -eval, este argumento no se toma en cuenta)
    public static void main(String[] args) throws Exception {
        String path = args[0];
        try {
            String bandera = args[1];
            switch (bandera) {
                case "-afd":
                    //salida = args[2];
                    AFD(path);
                    break;
                case "-gld":
                    //salida = args[2];
                    System.out.println("Realizando Gramatica");
                    //gramatica(path,salida);
                    break;
                case "-min":
                    //salida = args[2];
                    System.out.println("Realizando el minimo");
                    //minimo(path,salida);
                    break;
                case "-eval":
                    evaluar(path);
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

    public static void AFD(String path) {
        int estadosT = 2;
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            String nombre = path.replace("./regexps/","./afd/");
            nombre = nombre.replace(".rgx", ".afd");
            String alf = scanner.nextLine();
            //String[] parts = alf.split(",");
            //List<String> trans = Arrays.asList(parts);
            String inst = scanner.nextLine();
            inst = inst.replace("+","|");
            constAFN(inst);
            //HashMap<Integer,ArrayList<String>> tabla = new HashMap<Integer,ArrayList<String>>();
            /*File afdfile = new File(nombre);
            if (!afdfile.exists()) {
                afdfile.createNewFile();
            }
            FileWriter fw = new FileWriter(afdfile);
            fw.write(alf+"\n");
            fw.close();*/
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe.");
        } /*catch (IOException e) {
            e.fillInStackTrace();
        }*/
    }

    public static class State {
        public char label;
        public State next1;
        public State next2;
    }
    public static void /*State*/ constAFN(String regex) {
        Stack<State> stack = new Stack<>();

        for (int i = 0; i < regex.length(); i++) {
            char symbol = regex.charAt(i);

            if (symbol == '(') {
                State newState = new State();
                newState.label = symbol;
                stack.push(newState);
            } else if (symbol == ')') {
                State prev = null;
                
                while (!stack.isEmpty()) {
                    State top = stack.pop();

                    if (top.label == '(') {
                        prev = top;
                        break;
                    }

                    prev = merge(prevState, top);
                }
                stack.push(prev);
            } else if (symbol == '*') {
                State state = stack.pop();
                State newState = new State();
                newState.label = symbol;
                newState.next1 = state;
                stack.push(newState);
            } else if (symbol == '|') {
                State 
            }
        }
    }
    
    public static void gramatica(String path, String salida) {
        System.out.println("Archivo: " + path + "  Archivo salida: " + salida);
    }
    
    public static void minimo(String path, String salida) {
        System.out.println("Archivo: " + path + "  Archivo salida: " + salida);
    }
    
    public static void evaluar(String path) {
        File file = new File(path);
        try {
            System.out.println("---- Ingrese '**' para salir del programa ----");
            Scanner scanner = new Scanner(file);
            String[] lenguaje = scanner.nextLine().split(",");
            String Regex = scanner.nextLine();
            System.out.println("E.R. a utilizar: " + Regex + "\n");
            scanner.close();
            Regex = Regex.replace("+","|");
            Pattern pattern = Pattern.compile(Regex);
            Scanner sc = new Scanner(System.in);
            while(true) {
                System.out.print("Ingrese la cuerda a evaluar: ");
                String cuerda = sc.nextLine();
                if (cuerda.equals("**")) {
                    sc.close();
                    System.exit(-1);
                } else {
                    Matcher mat = pattern.matcher(cuerda);
                    if (mat.matches()) {
                        System.out.println("La cuerda es aceptada\n");
                    } else {
                        System.out.println("La cuerda es rechazada\n");
                    }
                }
            }
        } catch (FileNotFoundException s) {
            s.printStackTrace();
        }
    }
}
