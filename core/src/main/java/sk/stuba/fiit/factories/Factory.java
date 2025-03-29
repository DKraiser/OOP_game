package sk.stuba.fiit.factories;

public interface Factory <T>{
    T create(Object ... args);
}
