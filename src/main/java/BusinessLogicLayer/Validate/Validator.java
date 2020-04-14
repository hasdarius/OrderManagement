package BusinessLogicLayer.Validate;

/**
 * A Generic Validator Interface.
 * Known Implementations: {@link OrderValidator},{@link ProductValidator}
 *
 * @param <T> -It is an Object which can be either of type Product or of type Order
 */
public interface Validator<T> {
    /**
     * The definition of the Validate method.
     *
     * @param t the t
     */
    public void validate(T t);
}
