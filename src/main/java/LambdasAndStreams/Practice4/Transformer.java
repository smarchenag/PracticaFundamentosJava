package LambdasAndStreams.Practice4;

@FunctionalInterface
public interface Transformer <T,R>{
    R transform(T t);
}
