package BusinessLogicLayer.Validate;

import Model.Orders;
import Model.Product;

/**
 * A Class that validates a Product Object.
 * Implements {@link Validator}
 */
public class ProductValidator implements Validator<Product> {
    /**
     * The method validates an object of type {@link Product}: it verifies that its quantity field or its price field are not less than  or equal than 0.
     * @param product - The Product object to be validated
     */
    public void validate(Product product) {
        if(product.getQuantity()<=0 ||product.getPrice()<=0){
            throw new IllegalArgumentException("Can not have a product with quantity or price smaller than or equal to 0!");
        }
    }
}
