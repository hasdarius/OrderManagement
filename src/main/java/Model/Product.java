package Model;

/**
 * A class that is equivalent to an SQL table of a product whose unique identifier is an ID, having other fields such as name, price and quantity.
 *
 * @author Darius Has
 */
public class Product {
    private Integer id;
    private String name;
    private float price;
    private int quantity;

    /**
     * Instantiates the Product with the following parameters.
     *
     * @param id       the id
     * @param name     the name
     * @param price    the price
     * @param quantity the quantity
     */
    public Product(Integer id, String name, float price, int quantity) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    /**
     * Empty constructor. Used for creating Product objects from a ResultSet.
     */
    public Product() {

    }

    /**
     * Method for accessing the ID field.
     *
     * @return the ID of the product upon which this method is called
     */
    public Integer getId() {
        return id;
    }

    /**
     * Mutator for the ID field of a product
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Method for accessing the name field.
     *
     * @return a string representing the name of the product upon which this method is called
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator for the name field of a product
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method for accessing the price field.
     *
     * @return a float representing the price of the product upon which this method is called
     */
    public float getPrice() {
        return price;
    }

    /**
     * Mutator for the price field of a product
     *
     * @param price the price
     */
    public void setPrice(float price) {
        this.price = price;
    }

    /**
     * Method for accessing the quantity field.
     *
     * @return an int representing the quantity of the product upon which this method is called
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Mutator for the quantity field of a product
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


}
