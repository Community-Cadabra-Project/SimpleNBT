package org.cadabra.nbt;

public enum TagType {
    TAG_END(0, null),
    TAG_BYTE(1, NBTByte.class),
    TAG_SHORT(2, NBTShort.class),
    TAG_INT(3, NBTInteger.class),
    TAG_LONG(4, NBTLong.class),
    TAG_FLOAT(5, NBTFloat.class),
    TAG_DOUBLE(6, NBTDouble.class),
    TAG_BYTE_ARRAY(7, null),
    TAG_STRING(8, NBTString.class),
    TAG_LIST(9, null),
    TAG_COMPOUND(10, null),
    TAG_INT_ARRAY(11, null),
    TAG_LONG_ARRAY(12, null);

    private final int ID;
    private final Class<? extends NBT> clazz;
    private static TagType[] tags;

    static {
        tags = new TagType[values().length];
        for (TagType t :
                values()) {
            tags[t.getID()] = t;
        }
    }

    public static boolean checkID(int id) {
        return id > -1 && id < values().length;
    }

    public static TagType valueOf(int id) {
        return tags[id];
    }

    TagType(int id, Class<? extends NBT> clazz) {
        ID = id;
        this.clazz = clazz;
    }

    public int getID() {
        return ID;
    }

    public Class<? extends NBT> getAttachedClass() {
        return clazz;
    }
}

