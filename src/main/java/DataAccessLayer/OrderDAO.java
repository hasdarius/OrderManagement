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
import java.util.List;
import java.util.logging.Level;

import Connection.ConnectionFactory;
import Model.Orders;

/**
 * A Class to implement specific behaviour for an Order's CRUD operations in relation to the database.
 * Other operations for an Order object are inherited from {@link AbstractDAO} class.
 *
 * @author Has Darius
 */
public class OrderDAO extends AbstractDAO<Orders> {
    /**
     * A Method that deletes all Order objects with the PersonID corresponding to the given parameter from the Order table in the database.
     *
     * @param id the id
     * @return number of rows affected by the operation.
     */
    public Integer deleteAllByPersonID(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("personId");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteAllByPersonID from order " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    /**
     * A Method that deletes all Order objects with the ProductID corresponding to the given parameter from the Order table in the database.
     *
     * @param id the id
     * @return number of rows affected by the operation.
     */
    public Integer deleteAllByProductID(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("productID");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:deleteAllByProductID from order " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    /**
     * A Method that finds all Order objects with the PersonID corresponding to the given parameter from the Order table in the database.
     *
     * @param id the id
     * @return a list of Order Objects  wih the personID equal to the parameter.
     */
    public List<Orders> findAllByPersonID(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("personId");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll from order " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

}
