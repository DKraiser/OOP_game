package sk.stuba.fiit.interfaces;

public interface Factory<T> {
    T create(Object ... args);
}
