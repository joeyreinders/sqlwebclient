package net.sqlwebclient.util;

public final class Holder<T> {
    private final boolean immutable;
    private T instance;

    private Holder(final boolean immutable, final T object) {
        this.immutable = immutable;
        this.instance = object;
    }

    public T get() {
        return this.instance;
    }

    public Holder<T> set(final T instance) {
        if(immutable) {
            throw new RuntimeException("You cannot change the content of an immutable holder");
        }
        this.instance = instance;
        return this;
    }

    public boolean isNull() {
        return this.instance == null;
    }

    public static <T> Holder<T> of(final T instance) {
        return new Holder<T>(false, instance);
    }

    public static <T> Holder<T> newInstance() {
        return new Holder<T>(false, null);
    }

    @Deprecated
    public static <T> Holder<T> of() {
        return of(null);
    }

    public static <T> Holder<T> newImmutable(final T instance) {
        return new Holder<T>(true, instance);
    }
}
