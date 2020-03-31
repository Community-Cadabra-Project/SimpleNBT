package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.StringJoiner;

public final class NBTLong extends NBTObject {

    private final static TagType TAG = TagType.TAG_LONG;

    private long val = 0;

    NBTLong(String name) {
        super(TAG.getID(), name);
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
    protected void writeBody(NBTOutputStream out) throws IOException {
        out.writeLong(val);
    }

    @Override
    protected void readBody(NBTInputStream in) throws IOException {
        val = in.readLong();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTLong.class.getSimpleName() + "[", "]")
                .add("val=" + val)
                .add("tagID=" + tag)
                .add("name='" + name + "'")
                .toString();
    }
}
