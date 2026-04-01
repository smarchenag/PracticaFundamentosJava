package LambdasAndStreams.Practice4;

@FunctionalInterface
public interface Combiner <A,B,R>{
    R combine(A a,B b);
}
