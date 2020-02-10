package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.StringJoiner;

public class NBTByte extends NBTObject<Byte> {

    private final static TagType TAG = TagType.TAG_BYTE;

    private byte val = 0;

    private NBTByte(String name) {
        super(TAG.getID(), name);
    }

    private NBTByte() {
        super(TAG.getID());
    }

    public static NBTByte unnamed() {
        return new NBTByte();
    }

    public static NBTByte named(String name) {
        return new NBTByte(name);
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
    public Byte getValue() {
        return get();
    }

    @Override
    public Byte setValue(Byte e) {
        return set(e);
    }

    @Override
    public void writeBody(NBTOutputStream out) throws IOException {
        out.writeByte(val);
    }

    @Override
    public void read(NBTInputStream in) throws IOException {
        val = in.readByte();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTByte.class.getSimpleName() + "[", "]")
                .add("val=" + val)
                .add("tagID=" + tagID)
                .add("name='" + name + "'")
                .toString();
    }
}
