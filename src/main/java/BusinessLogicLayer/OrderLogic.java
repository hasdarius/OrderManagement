package BusinessLogicLayer;

import BusinessLogicLayer.Validate.OrderValidator;
import BusinessLogicLayer.Validate.Validator;
import DataAccessLayer.OrderDAO;
import DataAccessLayer.OrderSumDAO;
import DataAccessLayer.PersonDAO;
import DataAccessLayer.ProductDAO;
import Model.OrderSum;
import Model.Orders;
import Model.Person;
import Model.Product;
import PresentationLayer.Printer;

/**
 * Class that is responsible for the logical behaviour for Order objects.
 */
public class OrderLogic {
    /**
     * The Printer.
     */
    Printer printer;
    /**
     * The Person dao.
     */
    PersonDAO personDAO;
    /**
     * The Product dao.
     */
    ProductDAO productDAO;
    /**
     * The Order dao.
     */
    OrderDAO orderDAO;
    /**
     * The Order sum dao.
     */
    OrderSumDAO orderSumDAO;
    /**
     * The Validator.
     */
    Validator<Orders> validator;

    /**
     * Instantiates a new Order logic.
     */
    public OrderLogic() {
        printer = new Printer();
        personDAO = new PersonDAO();
        productDAO = new ProductDAO();
        orderDAO = new OrderDAO();
        orderSumDAO = new OrderSumDAO();
        validator = new OrderValidator();
    }

    /**
     * This method behaves the following way: it searches for the Person and the Product that are given by the name and product parameters. If they are found, a new Order is created and inserted into the Order table
     * by using the {@link OrderDAO}'s method for inserting. Also, the product object corresponding to the product parameter is updated, more specifically, its quantity is decremented by the quantity parameter.
     * Also, the price of the order is computed and it is added to an OrderSum object corresponding to the person parameter. If the OrderSum object does not exist, i.e. this is the first order from a person, it will
     * be INSERTED into the table, otherwise, the existing OrderSUm object will be updated.
     *
     * @param id       the id
     * @param name     the name
     * @param product  the product
     * @param quantity the quantity
     */
    public void addOrder(Integer id, String name, String product, Integer quantity) {
        Person person = personDAO.findByName(name);
        Product product1 = productDAO.findByName(product);
        if (person != null && product1 != null) {
            if (product1.getQuantity() >= quantity) {
                productDAO.update("quantity", product1.getId(), product1.getQuantity() - quantity);
                Orders order = new Orders(id, person.getId(), product1.getId(), quantity);
                validator.validate(order);
                orderDAO.insert(order);
                Float sumToAddToTotal = order.getQuantity() * product1.getPrice();
                if (orderSumDAO.findByPersonId(person.getId()) == null) {
                    OrderSum orderSum = new OrderSum(person.getId(), sumToAddToTotal);
                    orderSumDAO.insert(orderSum);
                } else {
                    orderSumDAO.updateByPersonID("totalPrice", person.getId(), orderSumDAO.findByPersonId(person.getId()).getTotalPrice() + sumToAddToTotal);
                }
                printer.printOrders(person);
            } else {
                printer.printUnderStock(name, product, quantity);
            }
        }
    }
}
