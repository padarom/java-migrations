package io.padarom.migrations;

public interface Migration {
    public void up();

    public void down();
}
