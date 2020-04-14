package Model;

/**
 * A class that is equivalent to an SQL table of a OrderSum whose unique identifier is an personID, having the other field the totalPrice that a person has to pay.This table is used to store the total amount
 * that a person has to pay for all of his/hers orders.
 *
 * @author Darius Has
 */
public class OrderSum {
    private Integer personID;
    private float totalPrice;

    /**
     * Instantiates a OrderSum object with the following parameters.
     *
     * @param id         the id
     * @param totalPrice the total price
     */
    public OrderSum(Integer id, float totalPrice) {
        this.personID = id;
        this.totalPrice = totalPrice;
    }

    /**
     * Empty Constructor.Used for creating OrderSum objects from a ResultSet.
     */
    public OrderSum() {

    }

    /**
     * Method for accessing the PersonID field.
     *
     * @return the ID of the person that corresponds to the OrderSum object upon which this method is called
     */
    public Integer getPersonID() {
        return personID;
    }

    /**
     * Mutator for the PersonId field of an OrderSum.
     *
     * @param personID the person id
     */
    public void setPersonID(Integer personID) {
        this.personID = personID;
    }

    /**
     * Method for accessing the PersonID field.
     *
     * @return the total price that the person that corresponds to the OrderSum object upon which this method is called has to pay
     */
    public float getTotalPrice() {
        return totalPrice;
    }

    /**
     * Mutator for the PersonId field of an OrderSum.
     *
     * @param totalPrice the total price
     */
    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }
}
