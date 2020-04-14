package PresentationLayer;

import BusinessLogicLayer.OrderLogic;
import BusinessLogicLayer.PersonLogic;
import BusinessLogicLayer.ProductLogic;
import DataAccessLayer.OrderDAO;
import DataAccessLayer.PersonDAO;
import DataAccessLayer.ProductDAO;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * A Class that reads from a file and parses through each of the commands given.
 */
public class Parser {

    private OrderLogic orderLogic;
    private PersonLogic personLogic;
    private ProductLogic productLogic;
    private Printer printer;
    private Integer nrOfPeople, nrOfProducts, nrOfOrders;

    /**
     * Instantiates a new Parser.
     *
     * @param fileName the file name
     */
    public Parser(String fileName) {
        orderLogic = new OrderLogic();
        productLogic = new ProductLogic();
        personLogic = new PersonLogic();
        printer = new Printer();
        nrOfOrders = nrOfPeople = nrOfProducts = 0;
        readFromFile(fileName);
    }

    /**
     * Reads from the file, line by line and for each line makes a call to the parseLine() method.
     *
     * @param fileName the file name
     */
    public void readFromFile(String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                String name;
                if (line.contains(": ")) {
                    if (line.contains(",")) {
                        name = line.substring(line.indexOf(":") + 1, line.indexOf(","));
                    } else {
                        name = line.substring(line.indexOf(":") + 1);
                    }
                    line = line.replace(name, "");
                    name = name.trim().replaceAll("\\s+", " ");

                } else {
                    name = null;
                }
                line = line.trim().replaceAll("\\s+", " ");
                line = line.replaceAll(":", "");
                line = line.replaceAll(",", "");
                List<String> words = Arrays.asList(line.split(" "));
                parseLine(words, name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * It parses through a line, decoding an operation that is going to be executed.
     * The commands with their corresponding operations are the following:
     * INSERT OBJECT: PARAMETERS  -inserts a new object (the object can be a Person or a Product and the parameters are the Object's fields),
     * ORDER nameOfPerson, nameOfProduct, Quantity  - inserts a new Order with the nameOfPerson, nameOfProduct, quantity parameters and generates a pdf file with the orders corresponding to the person given by the
     * name parameter,
     * REPORT OBJECT (Object can be Person, Product, or Order) -generates a pdf file with all the Objects from the database table corresponding to them.
     *
     * @param words the words
     * @param name  the name
     */
    public void parseLine(List<String> words, String name) {
        if (words.get(0).equalsIgnoreCase("insert")) {
            if (words.get(1).equalsIgnoreCase("client")) {
                personLogic.addPerson(++nrOfPeople, name, words.get(2));
            }
            if (words.get(1).equalsIgnoreCase("product")) {
                productLogic.addProduct(++nrOfProducts, name, Integer.parseInt(words.get(2)), Float.parseFloat(words.get(3)));
            }
        }
        if (words.get(0).equalsIgnoreCase("Order")) {
            orderLogic.addOrder(++nrOfOrders, name, words.get(1), Integer.parseInt(words.get(2)));
        }
        if (words.get(0).equalsIgnoreCase("delete")) {
            if (words.get(1).equalsIgnoreCase("client")) {
                personLogic.deletePerson(name);
            }
            if (words.get(1).equalsIgnoreCase("product")) {
                productLogic.deleteProduct(name);
            }
        }
        if (words.get(0).equalsIgnoreCase("report")) {
            if (words.get(1).equalsIgnoreCase("client"))
                printer.printAll(new PersonDAO().findAll());
            if (words.get(1).equalsIgnoreCase("order"))
                printer.printAllOrders(new OrderDAO().findAll());
            if (words.get(1).equalsIgnoreCase("product"))
                printer.printAll(new ProductDAO().findAll());
        }
    }

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        if (args.length == 1) {
            new Parser(args[0]);
        } else {
            System.out.println("Please introduce one arguments. The input file from which to read the commands.");
        }
    }
}
