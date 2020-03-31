package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import static java.util.Objects.*;
import java.util.Optional;

public abstract class NBTObject {

    protected final String name;
    protected final int tag;

    protected NBTObject(int tag, String name) {
        this.tag = tag;
        this.name = name;
    }

    public final int getTag() {
        return tag;
    }

    public final Optional<String> getName() {
        return Optional.ofNullable(name);
    }

    public final boolean isUnnamed() {
        return isNull(name);
    }

    protected final void write(NBTOutputStream out) throws IOException {
        if (nonNull(name)) {
            out.writeHeadTag(tag, name);
        }
        writeBody(out);
    }

    protected abstract void writeBody(NBTOutputStream out) throws IOException;

    protected abstract void readBody(NBTInputStream in) throws IOException;
}
