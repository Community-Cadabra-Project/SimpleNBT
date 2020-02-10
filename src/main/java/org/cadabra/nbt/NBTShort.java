package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.StringJoiner;

public class NBTShort extends NBTObject<Short> {

    private final static TagType TAG = TagType.TAG_SHORT;

    private short val = 0;

    private NBTShort(String name) {
        super(TAG.getID(), name);
    }

    private NBTShort() {
        super(TAG.getID());
    }

    public static NBTShort unnamed() {
        return new NBTShort();
    }

    public static NBTShort named(String name) {
        return new NBTShort(name);
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
    public Short getValue() {
        return get();
    }

    @Override
    public Short setValue(Short e) {
        return set(e);
    }

    @Override
    public void writeBody(NBTOutputStream out) throws IOException {
        out.writeShort(val);
    }

    @Override
    public void read(NBTInputStream in) throws IOException {
        val = in.readShort();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTShort.class.getSimpleName() + "[", "]")
                .add("val=" + val)
                .add("tagID=" + tagID)
                .add("name='" + name + "'")
                .toString();
    }
}
