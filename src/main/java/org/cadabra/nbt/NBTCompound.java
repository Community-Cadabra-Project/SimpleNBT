package org.cadabra.nbt;

import org.cadabra.nbt.io.NBTInputStream;
import org.cadabra.nbt.io.NBTOutputStream;

import java.io.IOException;
import java.util.*;

import static org.cadabra.nbt.TagType.*;

public class NBTCompound extends NBTObject<Map<String, NBT>> {

    private final static TagType TAG = TAG_COMPOUND;

    private Map<String, NBT> map = new LinkedHashMap<>();

    private NBTCompound() {
        super(TAG.getID());
    }

    private NBTCompound(String name) {
        super(TAG.getID(), name);
    }

    public static NBTCompound unnamed() {
        return new NBTCompound();
    }

    public static NBTCompound named(String name) {
        return new NBTCompound(name);
    }

    @Override
    Map<String, NBT> getValue() {
        return null;
    }

    @Override
    Map<String, NBT> setValue(Map<String, NBT> newMap) {
        Objects.requireNonNull(newMap);
        Map<String, NBT> oldMap = map;
        map = newMap;
        return oldMap;
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
        return ((NBTByteArray) getNBTObject(name, TAG_BYTE_ARRAY)).getValue();
    }

    public List<Integer> getIntArray(String name) {
        return ((NBTIntArray) getNBTObject(name, TAG_BYTE_ARRAY)).getValue();
    }

    public List<Long> getLongArray(String name) {
        return ((NBTLongArray) getNBTObject(name, TAG_BYTE_ARRAY)).getValue();
    }

    public String getString(String name) {
        return ((NBTString) getNBTObject(name, TAG_STRING)).getValue();
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
        e.setValue(array);
    }

    public void putIntArray(String name, ArrayList<Integer> array) {
        NBTIntArray e = (NBTIntArray) getOrCreate(name, TAG_INT_ARRAY);
        e.setValue(array);
    }

    public void putLongArray(String name, ArrayList<Long> array) {
        NBTLongArray e = (NBTLongArray) getOrCreate(name, TAG_LONG_ARRAY);
        e.setValue(array);
    }

    public void putString(String name, String val) {
        NBTString e = (NBTString) getOrCreate(name, TAG_STRING);
        e.setValue(val);
    }

    public void put(String name, NBT o) {
        putNotNull(map, name, o);
    }

    public NBT remove(String name) {
        return map.remove(name);
    }

    public NBT remove(String name, TagType t) {
        return remove(name, t.getID());
    }

    public NBT remove(String name, int tagID) {
        NBT e = remove(name);
        if (Objects.nonNull(e)) {
            if (e.tagID() == tagID)
                return e;
            else {
                map.put(name, e);
                return null;
            }
        } else
            return null;
    }

    private NBT getOrCreate(String name, TagType tag) {
        return getOrCreate(name, tag.getID());
    }

    private NBT getOrCreate(String name, int tagID) {
        NBT o = map.get(name);
        if (Objects.nonNull(o) && o.tagID() == tagID)
            return o;
        else {
            o = NBTFactory.named(tagID, name);
            if (o.tagID() != tagID)
                throw new IllegalArgumentException(differentTags(tagID, o));
            putNotNullKey(map, name, o);
            return o;
        }
    }

    private void putNotNull(Map<String, NBT> map, String name, NBT o) {
        Objects.requireNonNull(o, notNullValue());
        putNotNullKey(map, name, o);
    }

    private void putNotNullKey(Map<String, NBT> map, String name, NBT o) {
        Objects.requireNonNull(name, notNullKey());
        map.put(name, o);
    }

    private NBT getNBTObject(String name, TagType tag) {
        NBT o;
        Objects.requireNonNull(o = map.get(name), notFound(name));
        if (o.tagID() == tag.getID()) {
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
        for (NBT e :
                map.values()) {
            if (e.isUnnamed())
                throw new IOException("Objects in compound must have name: " + e);
            e.write(out);
        }
        out.write(TAG_END.getID());
    }

    @Override
    public void read(NBTInputStream in) throws IOException {
        Map<String, NBT> map = new LinkedHashMap<>();
        Map.Entry<Byte, String> e = in.readHeadTag();
        do {
            NBT o = NBTFactory.named(e.getKey(), e.getValue());
            o.read(in);
            map.put(e.getValue(), o);
            e = in.readHeadTag();
        } while (e.getKey() > TAG_END.getID());
        this.map = map;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", NBTCompound.class.getSimpleName() + "[", "]")
                .add("map=" + map)
                .add("tagID=" + tagID)
                .add("name='" + name + "'")
                .toString();
    }
}
