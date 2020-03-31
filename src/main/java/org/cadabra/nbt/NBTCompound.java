package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.*;

import static org.cadabra.nbt.TagType.*;

public class NBTCompound extends NBTObject {

    private final static TagType TAG = TAG_COMPOUND;

    private Map<String, NBTObject> map = new LinkedHashMap<>();

    NBTCompound(String name) {
        super(TAG.getID(), name);
    }

    public byte getByte(String name) {
        return ((NBTByte) getNBTObject(name, TAG_BYTE)).get();
    }

    public short getShort(String name) {
        return ((NBTShort) getNBTObject(name, TAG_SHORT)).get();
    }

    public int getInt(String name) {
        return ((NBTInteger) getNBTObject(name, TAG_INT)).get();
    }

    public long getLong(String name) {
        return ((NBTLong) getNBTObject(name, TAG_LONG)).get();
    }

    public float getFloat(String name) {
        return ((NBTFloat) getNBTObject(name, TAG_FLOAT)).get();
    }

    public double getDouble(String name) {
        return ((NBTDouble) getNBTObject(name, TAG_DOUBLE)).get();
    }

    public List<Byte> getByteArray(String name) {
        return ((NBTByteArray) getNBTObject(name, TAG_BYTE_ARRAY)).getList();
    }

    public List<Integer> getIntArray(String name) {
        return ((NBTIntArray) getNBTObject(name, TAG_BYTE_ARRAY)).getList();
    }

    public List<Long> getLongArray(String name) {
        return ((NBTLongArray) getNBTObject(name, TAG_BYTE_ARRAY)).getList();
    }

    public String getString(String name) {
        return ((NBTString) getNBTObject(name, TAG_STRING)).getString();
    }

    public NBTList getList(String name) {
        return (NBTList) getNBTObject(name, TAG_LIST);
    }

    public NBTCompound getCompound(String name) {
        return (NBTCompound) getNBTObject(name, TAG_COMPOUND);
    }


    public void putByte(String name, byte val) {
        NBTByte e = (NBTByte) getOrCreate(name, TAG_BYTE);
        e.set(val);
    }

    public void putShort(String name, short val) {
        NBTShort e = (NBTShort) getOrCreate(name, TAG_SHORT);
        e.set(val);
    }

    public void putInt(String name, int val) {
        NBTInteger e = (NBTInteger) getOrCreate(name, TAG_INT);
        e.set(val);
    }

    public void putLong(String name, long val) {
        NBTLong e = (NBTLong) getOrCreate(name, TAG_LONG);
        e.set(val);
    }

    public void putFloat(String name, float val) {
        NBTFloat e = (NBTFloat) getOrCreate(name, TAG_FLOAT);
        e.set(val);
    }

    public void putDouble(String name, double val) {
        NBTDouble e = (NBTDouble) getOrCreate(name, TAG_DOUBLE);
        e.set(val);
    }

    public void putByteArray(String name, ArrayList<Byte> array) {
        NBTByteArray e = (NBTByteArray) getOrCreate(name, TAG_BYTE_ARRAY);
        e.setList(array);
    }

    public void putIntArray(String name, ArrayList<Integer> array) {
        NBTIntArray e = (NBTIntArray) getOrCreate(name, TAG_INT_ARRAY);
        e.setList(array);
    }

    public void putLongArray(String name, ArrayList<Long> array) {
        NBTLongArray e = (NBTLongArray) getOrCreate(name, TAG_LONG_ARRAY);
        e.setList(array);
    }

    public void putString(String name, String val) {
        NBTString e = (NBTString) getOrCreate(name, TAG_STRING);
        e.setString(val);
    }

    public void put(String name, NBTObject o) {
        putNotNull(map, name, o);
    }

    public NBTObject remove(String name) {
        return map.remove(name);
    }

    public NBTObject remove(String name, TagType t) {
        return remove(name, t.getID());
    }

    public NBTObject remove(String name, int tagID) {
        NBTObject e = remove(name);
        if (Objects.nonNull(e)) {
            if (e.getTag() == tagID)
                return e;
            else {
                map.put(name, e);
                return null;
            }
        } else
            return null;
    }

    private NBTObject getOrCreate(String name, TagType tag) {
        return getOrCreate(name, tag.getID());
    }

    private NBTObject getOrCreate(String name, int tagID) {
        NBTObject o = map.get(name);
        if (Objects.nonNull(o) && o.getTag() == tagID)
            return o;
        else {
            o = NBTFactory.named(tagID, name);
            if (o.getTag() != tagID)
                throw new IllegalArgumentException("Match name but not tag");
            putNotNullKey(map, name, o);
            return o;
        }
    }

    private void putNotNull(Map<String, NBTObject> map, String name, NBTObject o) {
        Objects.requireNonNull(o, notNullValue());
        putNotNullKey(map, name, o);
    }

    private void putNotNullKey(Map<String, NBTObject> map, String name, NBTObject o) {
        Objects.requireNonNull(name, notNullKey());
        map.put(name, o);
    }

    private NBTObject getNBTObject(String name, TagType tag) {
        NBTObject o;
        Objects.requireNonNull(o = map.get(name), notFound(name));
        if (o.getTag() == tag.getID()) {
            assert tag.getAttachedClass().isInstance(o);
            return o;
        } else
            throw new NullPointerException(notFound(name, tag.getAttachedClass()));
    }

    private static String notNullValue() {
        return "Value in compound required not null";
    }

    private static String notNullKey() {
        return "Key in compound required not null";
    }

    private static String notFound(String name) {
        return String.format("Object with key %s not found", name);
    }

    private static String notFound(String name, Class c) {
        return String.format("Object with key %s type of %s not found", name, c.getSimpleName());
    }

    @Override
    public void writeBody(NBTOutputStream out) throws IOException {
        for (NBTObject e :
                map.values()) {
            if (e.isUnnamed())
                throw new IOException("Objects in compound must have name: " + e);
            e.write(out);
        }
        out.write(TAG_END.getID());
    }

    @Override
    public void readBody(NBTInputStream in) throws IOException {
        Map<String, NBTObject> map = new LinkedHashMap<>();
        Map.Entry<Byte, String> e = in.readHeadTag();
        do {
            NBTObject o = NBTFactory.named(e.getKey(), e.getValue());
            o.readBody(in);
            map.put(e.getValue(), o);
            e = in.readHeadTag();
        } while (e.getKey() > TAG_END.getID());
        this.map = map;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTCompound.class.getSimpleName() + "[", "]")
                .add("map=" + map)
                .add("tagID=" + tag)
                .add("name='" + name + "'")
                .toString();
    }
}
