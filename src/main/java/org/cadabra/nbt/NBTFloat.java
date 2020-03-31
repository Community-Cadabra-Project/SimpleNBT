package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.StringJoiner;

public final class NBTFloat extends NBTObject {

    private final static TagType TAG = TagType.TAG_FLOAT;

    private float val = 0;

    NBTFloat(String name) {
        super(TAG.getID(), name);
    }

    public float get() {
        return val;
    }

    public float set(float newVal) {
        float oldVal = val;
        val = newVal;
        return oldVal;
    }

    @Override
    protected void writeBody(NBTOutputStream out) throws IOException {
        out.writeFloat(val);
    }

    @Override
    protected void readBody(NBTInputStream in) throws IOException {
        val = in.readFloat();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTFloat.class.getSimpleName() + "[", "]")
                .add("val=" + val)
                .add("tagID=" + tag)
                .add("name='" + name + "'")
                .toString();
    }
}
