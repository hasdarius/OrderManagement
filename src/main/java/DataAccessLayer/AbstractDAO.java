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

/**
 * Abstract Class to generate CRUD operations for working with database.
 *
 * @param <T> - the type on which the operations will be performed
 * @author Has Darius
 */
public class AbstractDAO<T> {
    /**
     * The constant LOGGER.
     */
    protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

    /**
     * The Type.
     */
    protected final Class<T> type;

    /**
     * Instantiates a new Abstract dao.
     */
    @SuppressWarnings("unchecked")
    public AbstractDAO() {
        this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    }

    /**
     * Creates a SELECT query for SQL using the field parameter.
     *
     * @param field the field
     * @return a SELECT query in the form of a String.
     */
    protected String createSelectQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT ");
        sb.append(" * ");
        sb.append(" FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Creates an INSERT query for SQL using the field parameter.
     *
     * @return an INSERT query in the form of a String.
     */
    private String createInsertQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append(type.getSimpleName());
        sb.append(" values ( ");
        for (Field field : type.getDeclaredFields()) {
            sb.append("? ,");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(" )");
        return sb.toString();
    }

    /**
     * Creates an UPDATE query for SQL using the field parameter.
     *
     * @param value the value
     * @param field the field
     * @return an UPDATE query in the form of a String.
     */
    protected String createUpdateQuery(String value, String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("UPDATE ");
        sb.append(type.getSimpleName());
        sb.append(" SET " + value + " =?");
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * Creates a DELETE query for SQL using the field parameter.
     *
     * @param field the field
     * @return a DELETE query in the form of a String.
     */
    protected String createDeleteQuery(String field) {
        StringBuilder sb = new StringBuilder();
        sb.append("DELETE FROM ");
        sb.append(type.getSimpleName());
        sb.append(" WHERE " + field + " =?");
        return sb.toString();
    }

    /**
     * It is a generic method that accesses the database and finds all objects from a table.
     *
     * @return a list of all objects from a table in the database.
     */
    public List<T> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement("Select * FROM " + type.getSimpleName());
            resultSet = statement.executeQuery();
            return createObjects(resultSet);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findAll " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Generic method to find an object from a table using the ID field. The Object with the ID corresponding to the value of the parameter will be searched.
     *
     * @param id the id
     * @return the object from a table in the database that has the id field corresponding to the value of the parameter.
     */
    public T findById(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = createSelectQuery("id");
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();
            List<T> listOfObjects = createObjects(resultSet);
            if (listOfObjects.isEmpty())
                return null;
            return listOfObjects.get(0);
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return null;
    }

    /**
     * Creates objects of a certain type from a ResultSet. The ResultSet is the result of a query that was executed.
     *
     * @param resultSet the result set
     * @return a list of objects that correspond to a certain type.
     * @throws SQLException - if there has been a database access error.
     */
    List<T> createObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<T>();

        try {
            while (resultSet.next()) {
                T instance = type.newInstance();
                for (Field field : type.getDeclaredFields()) {
                    Object value = resultSet.getObject(field.getName());
                    PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
                    Method method = propertyDescriptor.getWriteMethod();
                    method.invoke(instance, value);
                }
                list.add(instance);
            }
        } catch (InstantiationException | IllegalAccessException | SecurityException | IllegalArgumentException | InvocationTargetException | SQLException | IntrospectionException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * It is a generic method that inserts a new object into a table that it corresponds to from the database.
     *
     * @param t the t
     * @return number of rows affected by the operation.
     */
    public Integer insert(T t) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createInsertQuery();
        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(query);
            int k = 0;
            for (Field field : t.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(t);
                statement.setObject(++k, value);
            }
            return statement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, type.getName() + " DAO:insert " + e.getMessage());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(connection);
        }
        return 0;
    }

    /**
     * It is a generic method that deletes an object from a table that it corresponds to from the database.
     *
     * @param id the id
     * @return number of rows affected by the operation.
     */
    public Integer delete(int id) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createDeleteQuery("id");
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
     * It is a generic method that updates an object into that it corresponds to from the database. The whatToUpdate parameters indicates the field that will be updated and the value indicates the new value.
     *
     * @param whatToUpdate the what to update
     * @param id           the id
     * @param value        the value
     * @return number of rows affected by the operation.
     */
    public Integer update(String whatToUpdate, int id, Object value) {
        Connection connection = null;
        PreparedStatement statement = null;
        String query = createUpdateQuery(whatToUpdate, "id");
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
