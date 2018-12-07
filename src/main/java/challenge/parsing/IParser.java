package challenge.parsing;

/**
 * This is an interface that defines behaviour for the class that will
 * handle the input to the program
 */
public interface IParser<T> {
    T parse();
}