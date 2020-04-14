package Model;

/**
 * A class that is equivalent to an SQL table of an Order whose unique identifier is an ID, having other fields such as personId, productId and quantity
 *
 * @author Darius Has
 */
public class Orders {
    private Integer id;
    private Integer personId;
    private Integer productId;
    private Integer quantity;

    /**
     * Instantiates an Orders object with the following parameters.
     *
     * @param id        the id
     * @param personId  the person id
     * @param productId the product id
     * @param quantity  the quantity
     */
    public Orders(Integer id, Integer personId, Integer productId, Integer quantity) {
        this.id = id;
        this.personId = personId;
        this.productId = productId;
        this.quantity = quantity;
    }

    /**
     * Empty constructor. Used for creating Orders objects from a ResultSet.
     */
    public Orders() {
    }


    /**
     * Method for accessing the ID field.
     *
     * @return the ID of the order upon which this method is called
     */
    public Integer getId() {
        return id;
    }

    /**
     * Mutator for the ID field of an order
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Method for accessing the PersonId field.
     *
     * @return the ID of the person that corresponds to the order object upon which this method is called
     */
    public Integer getPersonId() {
        return personId;
    }

    /**
     * Mutator for the PersonId field of an order
     *
     * @param personId the person id
     */
    public void setPersonId(Integer personId) {
        this.personId = personId;
    }

    /**
     * Method for accessing the ProductId field.
     *
     * @return the ID of the product that corresponds to the order object upon which this method is called
     */
    public Integer getProductId() {
        return productId;
    }

    /**
     * Mutator for the ProductId field of an order
     *
     * @param productId the product id
     */
    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    /**
     * Method for accessing the quantity field.
     *
     * @return an int representing the quantity of the order upon which this method is called
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Mutator for the quantity field of an order
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
