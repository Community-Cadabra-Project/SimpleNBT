package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.StringJoiner;

public final class NBTByte extends NBTObject {

    private final static TagType TAG = TagType.TAG_BYTE;

    private byte val = 0;

    NBTByte(String name) {
        super(TAG.getID(), name);
    }

    public byte get() {
        return val;
    }

    public byte set(byte newVal) {
        byte oldVal = val;
        val = newVal;
        return oldVal;
    }

    @Override
    protected void writeBody(NBTOutputStream out) throws IOException {
        out.writeByte(val);
    }

    @Override
    protected void readBody(NBTInputStream in) throws IOException {
        val = in.readByte();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTByte.class.getSimpleName() + "[", "]")
                .add("val=" + val)
                .add("tagID=" + tag)
                .add("name='" + name + "'")
                .toString();
    }
}
