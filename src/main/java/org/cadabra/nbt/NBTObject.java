package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

public abstract class NBTObject<T> implements NBT {

    protected final int tagID;
    protected final String name;

    protected NBTObject(int tagID) {
        this.tagID = tagID;
        name = null;
    }

    protected NBTObject(int tagID, String name) {
        Objects.requireNonNull(name);
        this.tagID = tagID;
        this.name = name;
    }

    abstract T getValue();

    abstract T setValue(T e);

    @Override
    public final int tagID() {
        return tagID;
    }

    @Override
    public final Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    @Override
    public boolean isUnnamed() {
        return Objects.isNull(name);
    }

    protected static String differentTags(int tagID, NBT o) {
        return String.format("Different tag: except %d, actually %d in case %s", tagID, o.tagID(), o.getClass().getSimpleName());
    }

    @Override
    public void write(NBTOutputStream out) throws IOException {
        writeHead(out, this);
        writeBody(out);
    }

    protected abstract void writeBody(NBTOutputStream out) throws IOException;

    public static void writeHead(NBTOutputStream out, NBT o) throws IOException {
        Optional<String> optName = o.getName();
        if (optName.isPresent()) {
            out.writeHeadTag(o.tagID(), optName.get());
        }
    }
}
