package BusinessLogicLayer;

import DataAccessLayer.OrderDAO;
import DataAccessLayer.OrderSumDAO;
import DataAccessLayer.PersonDAO;
import DataAccessLayer.ProductDAO;
import Model.Person;

/**
 * Class that is responsible for the logical behaviour for Person objects.
 */
public class PersonLogic {
    /**
     * The Person dao.
     */
    PersonDAO personDAO;
    /**
     * The Order sum dao.
     */
    OrderSumDAO orderSumDAO;
    /**
     * The Order dao.
     */
    OrderDAO orderDAO;

    /**
     * Instantiates a new Person logic.
     */
    public PersonLogic() {
        personDAO = new PersonDAO();
        orderDAO = new OrderDAO();
        orderSumDAO = new OrderSumDAO();
    }

    /**
     * This method behaves the following way: it searches for a Person object with the name equal to the parameter name. If the Person was not found,
     * a new Person will be created with the parameters' values and will be added
     * to the database by using {@link PersonDAO}'s method for inserting a new object.
     *
     * @param id      the id
     * @param name    the name
     * @param address the address
     */
    public void addPerson(Integer id, String name, String address) {
        if (personDAO.findByName(name) == null) {
            Person newPerson = new Person(id, name, address);
            personDAO.insert(newPerson);
        }
    }

    /**
     * This method behaves the following way: it searches for the Person with the name given as parameter. If found, it will delete it by using {@link PersonDAO}'s method for deleting. Also, it deletes all Orders
     * from the Orders table that correspond to the Person using {@link OrderDAO}'s method deleteAllByPersonID() and the OrderSum object corresponding to the same Person
     * from the OrderSum table using the {@link OrderSumDAO}'s method deleteByPersonID().
     *
     * @param name the name
     */
    public void deletePerson(String name) {
        Person personToDelete = personDAO.findByName(name);
        if (personToDelete != null) {
            personDAO.deleteByName(name);
            orderDAO.deleteAllByPersonID(personToDelete.getId());
            orderSumDAO.deleteByPersonID(personToDelete.getId());
        }
    }
}
