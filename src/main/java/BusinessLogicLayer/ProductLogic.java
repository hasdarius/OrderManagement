package BusinessLogicLayer;

import BusinessLogicLayer.Validate.ProductValidator;
import BusinessLogicLayer.Validate.Validator;
import DataAccessLayer.OrderDAO;
import DataAccessLayer.ProductDAO;
import Model.Product;

/**
 * Class that is responsible for the logical behaviour for Product objects.
 */
public class ProductLogic {
    /**
     * The Product dao.
     */
    ProductDAO productDAO;
    /**
     * The Order dao.
     */
    OrderDAO orderDAO;
    /**
     * The Validator.
     */
    Validator<Product> validator;

    /**
     * Instantiates a new Product logic.
     */
    public ProductLogic() {
        productDAO = new ProductDAO();
        orderDAO = new OrderDAO();
        validator = new ProductValidator();
    }

    /**
     * This method behaves the following way: it searches for a Product object with the name equal to the parameter name. If found, it will update the product,increasing the quantity
     * with the quantity that is given as parameter. If the product was not found, a new Product will be created with the parameters' values and it will validated by the {@link ProductValidator} class and added
     * to the database by using {@link ProductDAO}'s method for inserting a new object.
     *
     * @param id       the id
     * @param name     the name
     * @param quantity the quantity
     * @param price    the price
     */
    public void addProduct(Integer id, String name, int quantity, float price) {
        if (productDAO.findByName(name) == null) {
            Product newProduct = new Product(id, name, price, quantity);
            validator.validate(newProduct);
            productDAO.insert(newProduct);
        } else {
            productDAO.update("quantity", productDAO.findByName(name).getId(), productDAO.findByName(name).getQuantity() + quantity);
        }
    }

    /**
     * This method behaves the following way: it searches for the product with the name given as parameter. If found, it will delete it by using {@link ProductDAO}'s method for deleting. Also, it deletes all Orders
     * from the Orders that correspond to the Product table using {@link OrderDAO}'s method deleteAllByProductID().
     *
     * @param name the name
     */
    public void deleteProduct(String name) {
        Product productToDelete = productDAO.findByName(name);
        if (productToDelete != null) {
            productDAO.deleteByName(name);
            orderDAO.deleteAllByProductID(productToDelete.getId());
        }
    }
}
