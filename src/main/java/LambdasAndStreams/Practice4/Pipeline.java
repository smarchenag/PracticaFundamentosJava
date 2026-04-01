package LambdasAndStreams.Practice4;

public class Pipeline <T>{
    private T value;

    public Pipeline(T value){
        this.value = value;
    }

    public <R> Pipeline<R> map (Transformer<T,R> transformer){
        return new Pipeline<>(transformer.transform(value));
    }

    public Pipeline<T> validate (Validator<T> validator, String errorMessage){
        if (validator.validate(value)){
            return new Pipeline<>(value);
        }
        throw new IllegalArgumentException(errorMessage);
    }

    public T get() {
        return value;
    }
}
