package DataAccessLayer;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import Connection.ConnectionFactory;
import Model.Person;
import Model.Product;

/**
 * A Class to implement specific behaviour for a Product's CRUD operations in relation to the database.
 * Other operations for a Product object are inherited from {@link AbstractDAO} class.
 *
 * @author Has Darius
 */
public class ProductDAO extends AbstractDAO<Product> {
    /**
     * Method to find a Product from a table using the name field. The Product with the name corresponding to the value of the parameter will be searched.
     *
     * @param name the name
     * @return the Product from the Product table in the database that has the name field corresponding to the value of the parameter.
     */
    public Product findByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("name");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            resultSet = statement.executeQuery();
            List<Product> productList = createObjects(resultSet);
            if (productList.isEmpty())
                return null;
            else
                return productList.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByName " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * A Method that deletes a Product with the name corresponding to the given parameter from the Product table in the database.
     *
     * @param name the name
     * @return number of rows affected by the operation.
     */
    public Integer deleteByName(String name) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("name");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setString(1, name);
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }
}
