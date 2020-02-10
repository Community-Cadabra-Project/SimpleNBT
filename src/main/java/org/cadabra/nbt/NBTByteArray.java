package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static org.cadabra.nbt.TagType.*;

public class NBTByteArray extends NBTObject<List<Byte>> {

    private List<Byte> array = new ArrayList<>();

    private NBTByteArray() {
        super(TAG_BYTE_ARRAY.getID());
    }

    private NBTByteArray(String name) {
        super(TAG_BYTE_ARRAY.getID(), name);
    }

    public static NBTByteArray unnamed() {
        return new NBTByteArray();
    }

    public static NBTByteArray named(String name) {
        return new NBTByteArray(name);
    }

    @Override
    List<Byte> getValue() {
        return array;
    }

    @Override
    List<Byte> setValue(List<Byte> e) {
        Objects.requireNonNull(e);
        List<Byte> oldArr = array;
        array = e;
        return oldArr;
    }

    @Override
    protected void writeBody(NBTOutputStream out) throws IOException {
        out.writeInt(array.size());
        for (byte val :
                array) {
            out.write(val);
        }
    }

    @Override
    public void read(NBTInputStream in) throws IOException {
        int size = in.readInt();
        if (size == 0)
            return;
        ArrayList<Byte> array = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            array.add(in.readByte());
        }
        this.array = array;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTByteArray.class.getSimpleName() + "[", "]")
                .add("array=" + array)
                .add("tagID=" + tagID)
                .add("name='" + name + "'")
                .toString();
    }
}
