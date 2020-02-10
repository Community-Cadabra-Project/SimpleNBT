package org.cadabra.nbt.io;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class NBTOutputStream extends DataOutputStream {

    public NBTOutputStream(OutputStream out) {
        super(out);
    }

    public static OutputStream wrapIfCanGZIPOut(OutputStream out) {
        try {
            if (out instanceof GZIPOutputStream)
                return out;
            return new GZIPOutputStream(out);
        } catch (Exception e) {
            return out;
        }
    }

    public void writeHeadTag(int tagID, String name) throws IOException {
        writeByte(tagID);
        writeUTF(name);
    }
}
