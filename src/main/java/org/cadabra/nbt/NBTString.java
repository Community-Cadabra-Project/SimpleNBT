package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.Objects;
import java.util.StringJoiner;

public final class NBTString extends NBTObject {

    private final static TagType TAG = TagType.TAG_STRING;

    private String val = "";

    NBTString(String name) {
        super(TAG.getID(), name);
    }

    public String getString() {
        return val;
    }

    public String setString(String newVal) {
        Objects.requireNonNull(newVal);
        String oldVal = val;
        val = newVal;
        return oldVal;
    }

    @Override
    protected void writeBody(NBTOutputStream out) throws IOException {
        out.writeUTF(val);
    }

    @Override
    protected void readBody(NBTInputStream in) throws IOException {
        val = in.readUTF();
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTString.class.getSimpleName() + "[", "]")
                .add("val='" + val + "'")
                .add("tagID=" + tag)
                .add("name='" + name + "'")
                .toString();
    }
}
