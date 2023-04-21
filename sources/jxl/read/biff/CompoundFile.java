package jxl.read.biff;

import android.support.v4.media.TransportMediator;
import common.Logger;
import java.util.ArrayList;
import java.util.Iterator;
import jxl.WorkbookSettings;
import jxl.biff.BaseCompoundFile;
import jxl.biff.IntegerHelper;

public final class CompoundFile extends BaseCompoundFile {
    static Class class$jxl$read$biff$CompoundFile;
    private static Logger logger;
    private int[] bigBlockChain;
    private int[] bigBlockDepotBlocks;
    private byte[] data;
    private int extensionBlock;
    private int numBigBlockDepotBlocks;
    private int numExtensionBlocks;
    private ArrayList propertySets;
    private byte[] rootEntry;
    private int rootStartBlock;
    private int sbdStartBlock;
    private WorkbookSettings settings;
    private int[] smallBlockChain;

    static {
        Class cls;
        if (class$jxl$read$biff$CompoundFile == null) {
            cls = class$("jxl.read.biff.CompoundFile");
            class$jxl$read$biff$CompoundFile = cls;
        } else {
            cls = class$jxl$read$biff$CompoundFile;
        }
        logger = Logger.getLogger(cls);
    }

    static Class class$(String x0) {
        try {
            return Class.forName(x0);
        } catch (ClassNotFoundException x1) {
            throw new NoClassDefFoundError(x1.getMessage());
        }
    }

    public CompoundFile(byte[] d, WorkbookSettings ws) throws BiffException {
        this.data = d;
        this.settings = ws;
        for (int i = 0; i < IDENTIFIER.length; i++) {
            if (this.data[i] != IDENTIFIER[i]) {
                throw new BiffException(BiffException.unrecognizedOLEFile);
            }
        }
        this.propertySets = new ArrayList();
        this.numBigBlockDepotBlocks = IntegerHelper.getInt(this.data[44], this.data[45], this.data[46], this.data[47]);
        this.sbdStartBlock = IntegerHelper.getInt(this.data[60], this.data[61], this.data[62], this.data[63]);
        this.rootStartBlock = IntegerHelper.getInt(this.data[48], this.data[49], this.data[50], this.data[51]);
        this.extensionBlock = IntegerHelper.getInt(this.data[68], this.data[69], this.data[70], this.data[71]);
        this.numExtensionBlocks = IntegerHelper.getInt(this.data[72], this.data[73], this.data[74], this.data[75]);
        this.bigBlockDepotBlocks = new int[this.numBigBlockDepotBlocks];
        int pos = 76;
        int bbdBlocks = this.numExtensionBlocks != 0 ? 109 : this.numBigBlockDepotBlocks;
        for (int i2 = 0; i2 < bbdBlocks; i2++) {
            this.bigBlockDepotBlocks[i2] = IntegerHelper.getInt(d[pos], d[pos + 1], d[pos + 2], d[pos + 3]);
            pos += 4;
        }
        for (int j = 0; j < this.numExtensionBlocks; j++) {
            int pos2 = (this.extensionBlock + 1) * 512;
            int blocksToRead = Math.min(this.numBigBlockDepotBlocks - bbdBlocks, TransportMediator.KEYCODE_MEDIA_PAUSE);
            for (int i3 = bbdBlocks; i3 < bbdBlocks + blocksToRead; i3++) {
                this.bigBlockDepotBlocks[i3] = IntegerHelper.getInt(d[pos2], d[pos2 + 1], d[pos2 + 2], d[pos2 + 3]);
                pos2 += 4;
            }
            bbdBlocks += blocksToRead;
            if (bbdBlocks < this.numBigBlockDepotBlocks) {
                this.extensionBlock = IntegerHelper.getInt(d[pos2], d[pos2 + 1], d[pos2 + 2], d[pos2 + 3]);
            }
        }
        readBigBlockDepot();
        readSmallBlockDepot();
        this.rootEntry = readData(this.rootStartBlock);
        readPropertySets();
    }

    private void readBigBlockDepot() {
        int index = 0;
        this.bigBlockChain = new int[((this.numBigBlockDepotBlocks * 512) / 4)];
        for (int i = 0; i < this.numBigBlockDepotBlocks; i++) {
            int pos = (this.bigBlockDepotBlocks[i] + 1) * 512;
            for (int j = 0; j < 128; j++) {
                this.bigBlockChain[index] = IntegerHelper.getInt(this.data[pos], this.data[pos + 1], this.data[pos + 2], this.data[pos + 3]);
                pos += 4;
                index++;
            }
        }
    }

    private void readSmallBlockDepot() {
        int index = 0;
        int sbdBlock = this.sbdStartBlock;
        this.smallBlockChain = new int[0];
        while (sbdBlock != -2) {
            int[] oldChain = this.smallBlockChain;
            this.smallBlockChain = new int[(this.smallBlockChain.length + 128)];
            System.arraycopy(oldChain, 0, this.smallBlockChain, 0, oldChain.length);
            int pos = (sbdBlock + 1) * 512;
            for (int j = 0; j < 128; j++) {
                this.smallBlockChain[index] = IntegerHelper.getInt(this.data[pos], this.data[pos + 1], this.data[pos + 2], this.data[pos + 3]);
                pos += 4;
                index++;
            }
            sbdBlock = this.bigBlockChain[sbdBlock];
        }
    }

    private void readPropertySets() {
        for (int offset = 0; offset < this.rootEntry.length; offset += 128) {
            byte[] d = new byte[128];
            System.arraycopy(this.rootEntry, offset, d, 0, d.length);
            BaseCompoundFile.PropertyStorage ps = new BaseCompoundFile.PropertyStorage((BaseCompoundFile) this, d);
            if (ps.name == null || ps.name.length() == 0) {
                if (ps.type == 5) {
                    ps.name = BaseCompoundFile.ROOT_ENTRY_NAME;
                    logger.warn(new StringBuffer().append("Property storage name for ").append(ps.type).append(" is empty - setting to ").append(BaseCompoundFile.ROOT_ENTRY_NAME).toString());
                } else if (ps.size != 0) {
                    logger.warn(new StringBuffer().append("Property storage type ").append(ps.type).append(" is non-empty and has no associated name").toString());
                }
            }
            this.propertySets.add(ps);
        }
    }

    public byte[] getStream(String streamName) throws BiffException {
        BaseCompoundFile.PropertyStorage ps = getPropertyStorage(streamName);
        if (ps.size >= 4096 || streamName.equalsIgnoreCase(BaseCompoundFile.ROOT_ENTRY_NAME)) {
            return getBigBlockStream(ps);
        }
        return getSmallBlockStream(ps);
    }

    private BaseCompoundFile.PropertyStorage getPropertyStorage(String name) throws BiffException {
        Iterator i = this.propertySets.iterator();
        boolean found = false;
        BaseCompoundFile.PropertyStorage ps = null;
        while (!found && i.hasNext()) {
            ps = (BaseCompoundFile.PropertyStorage) i.next();
            if (ps.name.equalsIgnoreCase(name)) {
                found = true;
            }
        }
        if (found) {
            return ps;
        }
        throw new BiffException(BiffException.streamNotFound);
    }

    private byte[] getBigBlockStream(BaseCompoundFile.PropertyStorage ps) {
        int numBlocks = ps.size / 512;
        if (ps.size % 512 != 0) {
            numBlocks++;
        }
        byte[] streamData = new byte[(numBlocks * 512)];
        int block = ps.startBlock;
        int count = 0;
        while (block != -2 && count < numBlocks) {
            System.arraycopy(this.data, (block + 1) * 512, streamData, count * 512, 512);
            count++;
            block = this.bigBlockChain[block];
        }
        if (block != -2 && count == numBlocks) {
            logger.warn("Property storage size inconsistent with block chain.");
        }
        return streamData;
    }

    private byte[] getSmallBlockStream(BaseCompoundFile.PropertyStorage ps) throws BiffException {
        BaseCompoundFile.PropertyStorage rootps;
        try {
            rootps = getPropertyStorage(BaseCompoundFile.ROOT_ENTRY_NAME);
        } catch (BiffException e) {
            rootps = (BaseCompoundFile.PropertyStorage) this.propertySets.get(0);
        }
        byte[] rootdata = readData(rootps.startBlock);
        byte[] sbdata = new byte[0];
        int block = ps.startBlock;
        while (block != -2) {
            byte[] olddata = sbdata;
            sbdata = new byte[(olddata.length + 64)];
            System.arraycopy(olddata, 0, sbdata, 0, olddata.length);
            System.arraycopy(rootdata, block * 64, sbdata, olddata.length, 64);
            block = this.smallBlockChain[block];
            if (block == -1) {
                logger.warn(new StringBuffer().append("Incorrect terminator for small block stream ").append(ps.name).toString());
                block = -2;
            }
        }
        return sbdata;
    }

    private byte[] readData(int bl) throws BiffException {
        int block = bl;
        byte[] entry = new byte[0];
        while (block != -2) {
            byte[] oldEntry = entry;
            entry = new byte[(oldEntry.length + 512)];
            System.arraycopy(oldEntry, 0, entry, 0, oldEntry.length);
            System.arraycopy(this.data, (block + 1) * 512, entry, oldEntry.length, 512);
            if (this.bigBlockChain[block] == block) {
                throw new BiffException(BiffException.corruptFileFormat);
            }
            block = this.bigBlockChain[block];
        }
        return entry;
    }

    public String[] getPropertySetNames() {
        String[] sets = new String[this.propertySets.size()];
        for (int i = 0; i < sets.length; i++) {
            sets[i] = ((BaseCompoundFile.PropertyStorage) this.propertySets.get(i)).name;
        }
        return sets;
    }

    public BaseCompoundFile.PropertyStorage getPropertySet(String ps) {
        boolean found = false;
        BaseCompoundFile.PropertyStorage propertySet = null;
        Iterator i = this.propertySets.iterator();
        while (i.hasNext() && !found) {
            propertySet = (BaseCompoundFile.PropertyStorage) i.next();
            if (propertySet.name.equalsIgnoreCase(ps)) {
                found = true;
            }
        }
        if (found) {
            return propertySet;
        }
        return null;
    }
}
