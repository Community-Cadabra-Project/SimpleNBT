package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.StringJoiner;

public class NBTInteger  extends NBTObject<Integer> {

    private final static TagType TAG = TagType.TAG_INT;

    private int val = 0;

    private NBTInteger(String name) {
        super(TAG.getID(), name);
    }

    private NBTInteger() {
        super(TAG.getID());
    }

    public static NBTInteger unnamed() {
        return new NBTInteger();
    }

    public static NBTInteger named(String name) {
        return new NBTInteger(name);
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
    public Integer getValue() {
        return get();
    }

    @Override
    public Integer setValue(Integer e) {
        return set(e);
    }

    @Override
    public void writeBody(NBTOutputStream out) throws IOException {
        out.writeInt(val);
    }

    @Override
    public void read(NBTInputStream in) throws IOException {
        val = in.readInt();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTInteger.class.getSimpleName() + "[", "]")
                .add("val=" + val)
                .add("tagID=" + tagID)
                .add("name='" + name + "'")
                .toString();
    }
}
