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
import Model.OrderSum;
import Model.Person;

/**
 * A Class to implement specific behaviour for an OrderSum's CRUD operations in relation to the database.
 * Other operations for an OrderSum object are inherited from {@link AbstractDAO} class.
 *
 * @author Has Darius
 */
public class OrderSumDAO extends AbstractDAO<OrderSum> {
    /**
     * A Method that finds an OrderSum object with the PersonID corresponding to the given parameter from the OrderSum table in the database.
     *
     * @param id the id
     * @return an OrderSum object wih the personID equal to the parameter.
     */
    public OrderSum findByPersonId(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("personId");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<OrderSum> orderSumList = createObjects(resultSet);
            if (orderSumList.isEmpty())
                return null;
            else
                return orderSumList.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findByPersonId " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * A Method that deletes an OrderSum object with the PersonID corresponding to the given parameter from the OrderSum table in the database.
     *
     * @param id the id
     * @return number of rows affected by the operation.
     */
    public Integer deleteByPersonID(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("personId");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:delete " + e.getMessage());
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    /**
     * A Method that updates an OrderSum object with the PersonID corresponding to the given id parameter from the OrderSum table in the database.
     * The update is done the following way: the whatToUpdate parameter determines which field will be updated and the value parameter represents the new value the field will take.
     *
     * @param whatToUpdate the what to update
     * @param id           the id
     * @param value        the value
     * @return number of rows affected by the operation.
     */
    public Integer updateByPersonID(String whatToUpdate, int id, Object value) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery(whatToUpdate, "personId");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setObject(1, value);
            statement.setObject(2, id);
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
