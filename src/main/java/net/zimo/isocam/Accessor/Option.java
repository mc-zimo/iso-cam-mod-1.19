package net.zimo.isocam.Accessor;

public enum Option {
    DEFAULT(true),
    ISOMETRIC(false);

    private final boolean isDefault;
    private static final Option[] OPTIONS;

    Option(boolean b) {
        this.isDefault = b;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public Option next() {
        return OPTIONS[(this.ordinal() + 1) % OPTIONS.length];
    }

    static {
        OPTIONS = Option.values();
    }
}
