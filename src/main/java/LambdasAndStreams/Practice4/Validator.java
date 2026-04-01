package LambdasAndStreams.Practice4;

@FunctionalInterface
public interface Validator<T> {
    boolean validate(T t);
}
