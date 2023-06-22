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
    static String alf;
    static List<String> termi;
    static ArrayList<String> NoTerminales;
    static int noTerm = 65;
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
                    AFD(path);
                    break;
                case "-gld":
                    System.out.println("Realizando Gramatica");
                    gramatica(path);
                    break;
                case "-min":
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
        //int estadosT = 2;
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

    public static void gramatica(String path) {
        File file = new File(path);
        try {
            Scanner scanner = new Scanner(file);
            String nombre = path.replace("./regexps/","./afd/");
            nombre = nombre.replace(".rgx", ".gld");
            alf = scanner.nextLine();
            termi = Arrays.asList(alf.split(","));
            System.out.println(alf);
            System.out.println(termi);
            String inst = scanner.nextLine();
            inst = inst.replace("+","|");
            List<Gram> grams = GLD(inst);
            for (Gram gramatica : grams) {
                System.out.println(gramatica.Variable + " -> " + gramatica.Term);
            }
            //List<Gram> gramatica = GLD(inst); 
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("El archivo no existe.");
        }
    }

    public static class Gram {
        public String Variable;
        public String Term;

        public Gram (String Variable, String Term) {
            this.Variable = Variable;
            this.Term = Term;
        }
    }

    public static List<Gram> GLD(String regex) {
        List<Gram> prod = new ArrayList<>();
        Stack<String> stack = new Stack<>();
        boolean posi;

        for (int i = 0; i < regex.length(); i++) {
            String symbol = regex.substring(i,i+1);
            System.out.println(symbol);
            posi = termi.contains(symbol);
            if (posi) {
                String vari = String.valueOf((char)(noTerm + 1));
                prod.add(new Gram(vari,symbol));
                stack.push(vari);
            } else if (symbol == "*") {
                String vari = stack.pop();
                String newVari = String.valueOf((char)(noTerm + 1));
                prod.add(new Gram(newVari, vari + " " + newVari + " |λ"));
                stack.push(newVari);
            } else if (symbol == "|") {
                String vari2 = stack.pop();
                String vari = stack.pop();
                String newVari = String.valueOf((char)(noTerm + 1));
                prod.add(new Gram(newVari,vari + " | " + vari2));
                stack.push(newVari);
            } else if (symbol == "(") {
                stack.push("(");
            } else if (symbol == ")") {
                List<String> noTerminales = new ArrayList<>();
                String top = stack.peek();

                while (!top.equals("(")) {
                    noTerminales.add(stack.pop());
                    top = stack.peek();
                }

                stack.pop();
                String newVari = String.valueOf((char)(noTerm + 1));
                prod.add(new Gram(newVari, String.join(" ", noTerminales)));
                stack.push(newVari);
            }
        }

        prod.add(new Gram("S", stack.pop()));
        return prod;
    }
    
    public static void minimo(String path) {
        System.out.println("Archivo: " + path);
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
