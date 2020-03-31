package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.StringJoiner;

public final class NBTInteger extends NBTObject {

    private final static TagType TAG = TagType.TAG_INT;

    private int val = 0;

    NBTInteger(String name) {
        super(TAG.getID(), name);
    }

    public int get() {
        return val;
    }

    public int set(int newVal) {
        int oldVal = val;
        val = newVal;
        return oldVal;
    }

    @Override
    protected void writeBody(NBTOutputStream out) throws IOException {
        out.writeInt(val);
    }

    @Override
    protected void readBody(NBTInputStream in) throws IOException {
        val = in.readInt();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTInteger.class.getSimpleName() + "[", "]")
                .add("val=" + val)
                .add("tagID=" + tag)
                .add("name='" + name + "'")
                .toString();
    }
}
