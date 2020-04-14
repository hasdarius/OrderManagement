package Model;

/**
 * A class that is equivalent to an SQL table of a person whose unique identifier is an ID, having other fields such as name and address.
 *
 * @author Darius Has
 */
public class Person {
    private Integer id;
    private String name;
    private String address;

    /**
     * Instantiates a Person object with the following parameters.
     *
     * @param id      the id
     * @param name    the name
     * @param address the address
     */
    public Person(Integer id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    /**
     * Empty constructor. Used for creating Person objects from a ResultSet.
     */
    public Person() {

    }

    /**
     * Method for accessing the ID field from other functions
     *
     * @return the ID of the person upon which this method is called
     */
    public Integer getId() {
        return id;
    }

    /**
     * Mutator for the ID field of a person
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Method for accessing the name field.
     *
     * @return a string representing the name of the person upon which this method is called
     */
    public String getName() {
        return name;
    }

    /**
     * Mutator for the name field of a person
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Method for accessing the address field.
     *
     * @return a string representing Address of the person upon which this method is called
     */
    public String getAddress() {
        return address;
    }

    /**
     * Mutator for the address field of a person
     *
     * @param address the address
     */
    public void setAddress(String address) {
        this.address = address;
    }

   /* public String toString(){
        return name+", "+address;
    }*/

}
