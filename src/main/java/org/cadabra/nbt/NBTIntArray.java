package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

import static org.cadabra.nbt.TagType.TAG_BYTE_ARRAY;

public class NBTIntArray extends NBTObject<List<Integer>> {

    private List<Integer> array = new ArrayList<>();

    private NBTIntArray() {
        super(TAG_BYTE_ARRAY.getID());
    }

    private NBTIntArray(String name) {
        super(TAG_BYTE_ARRAY.getID(), name);
    }

    public static NBTIntArray unnamed() {
        return new NBTIntArray();
    }

    public static NBTIntArray named(String name) {
        return new NBTIntArray(name);
    }

    @Override
    List<Integer> getValue() {
        return array;
    }

    @Override
    List<Integer> setValue(List<Integer> e) {
        Objects.requireNonNull(e);
        List<Integer> oldArr = array;
        array = e;
        return oldArr;
    }

    @Override
    protected void writeBody(NBTOutputStream out) throws IOException {
        out.writeInt(array.size());
        for (int val :
                array) {
            out.writeInt(val);
        }
    }

    @Override
    public void read(NBTInputStream in) throws IOException {
        int size = in.readInt();
        if (size == 0)
            return;
        ArrayList<Integer> array = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            array.add(in.readInt());
        }
        this.array = array;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTIntArray.class.getSimpleName() + "[", "]")
                .add("array=" + array)
                .add("tagID=" + tagID)
                .add("name='" + name + "'")
                .toString();
    }
}
