package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.StringJoiner;

public final class NBTShort extends NBTObject {

    private final static TagType TAG = TagType.TAG_SHORT;

    private short val = 0;

    NBTShort(String name) {
        super(TAG.getID(), name);
    }

    public short get() {
        return val;
    }

    public short set(short newVal) {
        short oldVal = val;
        val = newVal;
        return oldVal;
    }

    @Override
    protected void writeBody(NBTOutputStream out) throws IOException {
        out.writeShort(val);
    }

    @Override
    protected void readBody(NBTInputStream in) throws IOException {
        val = in.readShort();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTShort.class.getSimpleName() + "[", "]")
                .add("val=" + val)
                .add("tagID=" + tag)
                .add("name='" + name + "'")
                .toString();
    }
}
