package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public interface NBT {

    int tagID();

    Optional<String> getName();

    void write(NBTOutputStream out) throws IOException;

    void read(NBTInputStream in) throws IOException;

    default boolean isUnnamed() {
        return getName().isEmpty();
    }
}
