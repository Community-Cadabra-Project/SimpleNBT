package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.StringJoiner;

public class NBTLong extends NBTObject<Long> {

    private final static TagType TAG = TagType.TAG_LONG;

    private long val = 0;

    private NBTLong(String name) {
        super(TAG.getID(), name);
    }

    private NBTLong() {
        super(TAG.getID());
    }

    public static NBTLong unnamed() {
        return new NBTLong();
    }

    public static NBTLong named(String name) {
        return new NBTLong(name);
    }

    public long get() {
        return val;
    }

    public long set(long newVal) {
        long oldVal = val;
        val = newVal;
        return oldVal;
    }

    @Override
    public Long getValue() {
        return get();
    }

    @Override
    public Long setValue(Long e) {
        return set(e);
    }

    @Override
    public void writeBody(NBTOutputStream out) throws IOException {
        out.writeLong(val);
    }

    @Override
    public void read(NBTInputStream in) throws IOException {
        val = in.readLong();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTLong.class.getSimpleName() + "[", "]")
                .add("val=" + val)
                .add("tagID=" + tagID)
                .add("name='" + name + "'")
                .toString();
    }
}
