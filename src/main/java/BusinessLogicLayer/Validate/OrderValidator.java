package BusinessLogicLayer.Validate;

import Model.Orders;

/**
 * A Class that validates an Order Object.
 * Implements {@link Validator}
 */
public class OrderValidator implements Validator<Orders> {
    /**
     * The method validates an object of type {@link Orders}: it verifies that its quantity field is not less than or equal than 0.
     *
     * @param orders -the Orders object to be validated
     */
    public void validate(Orders orders) {
        if (orders.getQuantity() <= 0) {
            throw new IllegalArgumentException("Can not have an order with quantity smaller than or equal to 0!");
        }
    }
}
