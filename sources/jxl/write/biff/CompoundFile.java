package jxl.write.biff;

import android.support.v4.media.TransportMediator;
import common.Logger;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import jxl.biff.BaseCompoundFile;
import jxl.biff.IntegerHelper;
import jxl.read.biff.BiffException;

final class CompoundFile extends BaseCompoundFile {
    static Class class$jxl$write$biff$CompoundFile;
    private static Logger logger;
    private int additionalPropertyBlocks;
    private ArrayList additionalPropertySets;
    private int bbdPos;
    private int bbdStartBlock;
    private byte[] bigBlockDepot;
    private byte[] excelData;
    private int excelDataBlocks;
    private int excelDataStartBlock;
    private int extensionBlock;
    private int numBigBlockDepotBlocks;
    private int numExtensionBlocks;
    private int numPropertySets;
    private int numRootEntryBlocks = 1;
    private int numSmallBlockDepotBlocks;
    private int numSmallBlockDepotChainBlocks;
    private int numSmallBlocks;
    private OutputStream out;
    private HashMap readPropertySets;
    private int requiredSize;
    private ReadPropertyStorage rootEntryPropertySet;
    private int rootStartBlock;
    private int sbdStartBlock;
    private int sbdStartBlockChain;
    private int size;
    private int[] standardPropertySetMappings;

    static {
        Class cls;
        if (class$jxl$write$biff$CompoundFile == null) {
            cls = class$("jxl.write.biff.CompoundFile");
            class$jxl$write$biff$CompoundFile = cls;
        } else {
            cls = class$jxl$write$biff$CompoundFile;
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

    private static final class ReadPropertyStorage {
        byte[] data;
        int number;
        BaseCompoundFile.PropertyStorage propertyStorage;

        ReadPropertyStorage(BaseCompoundFile.PropertyStorage ps, byte[] d, int n) {
            this.propertyStorage = ps;
            this.data = d;
            this.number = n;
        }
    }

    public CompoundFile(byte[] data, int l, OutputStream os, jxl.read.biff.CompoundFile rcf) throws CopyAdditionalPropertySetsException, IOException {
        this.size = l;
        this.excelData = data;
        readAdditionalPropertySets(rcf);
        this.numPropertySets = (this.additionalPropertySets != null ? this.additionalPropertySets.size() : 0) + 4;
        if (this.additionalPropertySets != null) {
            this.numSmallBlockDepotChainBlocks = getBigBlocksRequired(this.numSmallBlocks * 4);
            this.numSmallBlockDepotBlocks = getBigBlocksRequired(this.numSmallBlocks * 64);
            this.numRootEntryBlocks += getBigBlocksRequired(this.additionalPropertySets.size() * 128);
        }
        int blocks = getBigBlocksRequired(l);
        if (l < 4096) {
            this.requiredSize = 4096;
        } else {
            this.requiredSize = blocks * 512;
        }
        this.out = os;
        this.excelDataBlocks = this.requiredSize / 512;
        this.numBigBlockDepotBlocks = 1;
        int startTotalBlocks = this.excelDataBlocks + 8 + 8 + this.additionalPropertyBlocks + this.numSmallBlockDepotBlocks + this.numSmallBlockDepotChainBlocks + this.numRootEntryBlocks;
        this.numBigBlockDepotBlocks = (int) Math.ceil(((double) (startTotalBlocks + this.numBigBlockDepotBlocks)) / 128.0d);
        this.numBigBlockDepotBlocks = (int) Math.ceil(((double) (startTotalBlocks + this.numBigBlockDepotBlocks)) / 128.0d);
        int totalBlocks = startTotalBlocks + this.numBigBlockDepotBlocks;
        if (this.numBigBlockDepotBlocks > 108) {
            this.extensionBlock = 0;
            this.numExtensionBlocks = (int) Math.ceil(((double) ((this.numBigBlockDepotBlocks - 109) + 1)) / 127.0d);
            this.numBigBlockDepotBlocks = (int) Math.ceil(((double) ((this.numExtensionBlocks + startTotalBlocks) + this.numBigBlockDepotBlocks)) / 128.0d);
            totalBlocks = this.numExtensionBlocks + startTotalBlocks + this.numBigBlockDepotBlocks;
        } else {
            this.extensionBlock = -2;
            this.numExtensionBlocks = 0;
        }
        this.excelDataStartBlock = this.numExtensionBlocks;
        this.sbdStartBlock = -2;
        if (this.additionalPropertySets != null) {
            this.sbdStartBlock = this.excelDataStartBlock + this.excelDataBlocks + this.additionalPropertyBlocks + 16;
        }
        this.sbdStartBlockChain = -2;
        if (this.sbdStartBlock != -2) {
            this.sbdStartBlockChain = this.sbdStartBlock + this.numSmallBlockDepotBlocks;
        }
        if (this.sbdStartBlockChain != -2) {
            this.bbdStartBlock = this.sbdStartBlockChain + this.numSmallBlockDepotChainBlocks;
        } else {
            this.bbdStartBlock = this.excelDataStartBlock + this.excelDataBlocks + this.additionalPropertyBlocks + 16;
        }
        this.rootStartBlock = this.bbdStartBlock + this.numBigBlockDepotBlocks;
        if (totalBlocks != this.rootStartBlock + this.numRootEntryBlocks) {
            logger.warn("Root start block and total blocks are inconsistent  generated file may be corrupt");
            logger.warn(new StringBuffer().append("RootStartBlock ").append(this.rootStartBlock).append(" totalBlocks ").append(totalBlocks).toString());
        }
    }

    private void readAdditionalPropertySets(jxl.read.biff.CompoundFile readCompoundFile) throws CopyAdditionalPropertySetsException, IOException {
        byte[] data;
        if (readCompoundFile != null) {
            this.additionalPropertySets = new ArrayList();
            this.readPropertySets = new HashMap();
            String[] psnames = readCompoundFile.getPropertySetNames();
            int blocksRequired = 0;
            this.standardPropertySetMappings = new int[STANDARD_PROPERTY_SETS.length];
            for (int i = 0; i < psnames.length; i++) {
                BaseCompoundFile.PropertyStorage ps = readCompoundFile.getPropertySet(psnames[i]);
                boolean standard = false;
                for (int j = 0; j < STANDARD_PROPERTY_SETS.length && !standard; j++) {
                    if (psnames[i].equalsIgnoreCase(STANDARD_PROPERTY_SETS[j])) {
                        standard = true;
                        this.readPropertySets.put(psnames[i], new ReadPropertyStorage(ps, (byte[]) null, i));
                    }
                }
                if (!standard) {
                    try {
                        if (ps.size > 0) {
                            data = readCompoundFile.getStream(ps.name);
                        } else {
                            data = new byte[0];
                        }
                        ReadPropertyStorage rps = new ReadPropertyStorage(ps, data, i);
                        this.readPropertySets.put(psnames[i], rps);
                        this.additionalPropertySets.add(rps);
                        if (data.length > 4096) {
                            blocksRequired += getBigBlocksRequired(data.length);
                        } else {
                            this.numSmallBlocks += getSmallBlocksRequired(data.length);
                        }
                    } catch (BiffException e) {
                        logger.error(e);
                        throw new CopyAdditionalPropertySetsException();
                    }
                }
            }
            this.additionalPropertyBlocks = blocksRequired;
        }
    }

    public void write() throws IOException {
        writeHeader();
        writeExcelData();
        writeDocumentSummaryData();
        writeSummaryData();
        writeAdditionalPropertySets();
        writeSmallBlockDepot();
        writeSmallBlockDepotChain();
        writeBigBlockDepot();
        writePropertySets();
    }

    private void writeAdditionalPropertySets() throws IOException {
        if (this.additionalPropertySets != null) {
            Iterator i = this.additionalPropertySets.iterator();
            while (i.hasNext()) {
                byte[] data = ((ReadPropertyStorage) i.next()).data;
                if (data.length > 4096) {
                    this.out.write(data, 0, data.length);
                    byte[] padding = new byte[((getBigBlocksRequired(data.length) * 512) - data.length)];
                    this.out.write(padding, 0, padding.length);
                }
            }
        }
    }

    private void writeExcelData() throws IOException {
        this.out.write(this.excelData, 0, this.size);
        this.out.write(new byte[(this.requiredSize - this.size)]);
    }

    private void writeDocumentSummaryData() throws IOException {
        this.out.write(new byte[4096]);
    }

    private void writeSummaryData() throws IOException {
        this.out.write(new byte[4096]);
    }

    private void writeHeader() throws IOException {
        byte[] headerBlock = new byte[512];
        byte[] extensionBlockData = new byte[(this.numExtensionBlocks * 512)];
        System.arraycopy(IDENTIFIER, 0, headerBlock, 0, IDENTIFIER.length);
        headerBlock[24] = 62;
        headerBlock[26] = 3;
        headerBlock[28] = -2;
        headerBlock[29] = -1;
        headerBlock[30] = 9;
        headerBlock[32] = 6;
        headerBlock[57] = 16;
        IntegerHelper.getFourBytes(this.numBigBlockDepotBlocks, headerBlock, 44);
        IntegerHelper.getFourBytes(this.sbdStartBlockChain, headerBlock, 60);
        IntegerHelper.getFourBytes(this.numSmallBlockDepotChainBlocks, headerBlock, 64);
        IntegerHelper.getFourBytes(this.extensionBlock, headerBlock, 68);
        IntegerHelper.getFourBytes(this.numExtensionBlocks, headerBlock, 72);
        IntegerHelper.getFourBytes(this.rootStartBlock, headerBlock, 48);
        int pos = 76;
        int blocksToWrite = Math.min(this.numBigBlockDepotBlocks, 109);
        int blocksWritten = 0;
        for (int i = 0; i < blocksToWrite; i++) {
            IntegerHelper.getFourBytes(this.bbdStartBlock + i, headerBlock, pos);
            pos += 4;
            blocksWritten++;
        }
        for (int i2 = pos; i2 < 512; i2++) {
            headerBlock[i2] = -1;
        }
        this.out.write(headerBlock);
        int pos2 = 0;
        for (int extBlock = 0; extBlock < this.numExtensionBlocks; extBlock++) {
            int blocksToWrite2 = Math.min(this.numBigBlockDepotBlocks - blocksWritten, TransportMediator.KEYCODE_MEDIA_PAUSE);
            for (int j = 0; j < blocksToWrite2; j++) {
                IntegerHelper.getFourBytes(this.bbdStartBlock + blocksWritten + j, extensionBlockData, pos2);
                pos2 += 4;
            }
            blocksWritten += blocksToWrite2;
            IntegerHelper.getFourBytes(blocksWritten == this.numBigBlockDepotBlocks ? -2 : extBlock + 1, extensionBlockData, pos2);
            pos2 += 4;
        }
        if (this.numExtensionBlocks > 0) {
            for (int i3 = pos2; i3 < extensionBlockData.length; i3++) {
                extensionBlockData[i3] = -1;
            }
            this.out.write(extensionBlockData);
        }
    }

    private void checkBbdPos() throws IOException {
        if (this.bbdPos >= 512) {
            this.out.write(this.bigBlockDepot);
            this.bigBlockDepot = new byte[512];
            this.bbdPos = 0;
        }
    }

    private void writeBlockChain(int startBlock, int numBlocks) throws IOException {
        int blocksToWrite = numBlocks - 1;
        int blockNumber = startBlock + 1;
        while (blocksToWrite > 0) {
            int bbdBlocks = Math.min(blocksToWrite, (512 - this.bbdPos) / 4);
            for (int i = 0; i < bbdBlocks; i++) {
                IntegerHelper.getFourBytes(blockNumber, this.bigBlockDepot, this.bbdPos);
                this.bbdPos += 4;
                blockNumber++;
            }
            blocksToWrite -= bbdBlocks;
            checkBbdPos();
        }
        IntegerHelper.getFourBytes(-2, this.bigBlockDepot, this.bbdPos);
        this.bbdPos += 4;
        checkBbdPos();
    }

    private void writeAdditionalPropertySetBlockChains() throws IOException {
        if (this.additionalPropertySets != null) {
            int blockNumber = this.excelDataStartBlock + this.excelDataBlocks + 16;
            Iterator i = this.additionalPropertySets.iterator();
            while (i.hasNext()) {
                ReadPropertyStorage rps = (ReadPropertyStorage) i.next();
                if (rps.data.length > 4096) {
                    String str = rps.propertyStorage.name;
                    int numBlocks = getBigBlocksRequired(rps.data.length);
                    writeBlockChain(blockNumber, numBlocks);
                    blockNumber += numBlocks;
                }
            }
        }
    }

    private void writeSmallBlockDepotChain() throws IOException {
        if (this.sbdStartBlockChain != -2) {
            byte[] smallBlockDepotChain = new byte[(this.numSmallBlockDepotChainBlocks * 512)];
            int pos = 0;
            int sbdBlockNumber = 1;
            Iterator i = this.additionalPropertySets.iterator();
            while (i.hasNext()) {
                ReadPropertyStorage rps = (ReadPropertyStorage) i.next();
                if (rps.data.length <= 4096 && rps.data.length != 0) {
                    int numSmallBlocks2 = getSmallBlocksRequired(rps.data.length);
                    for (int j = 0; j < numSmallBlocks2 - 1; j++) {
                        IntegerHelper.getFourBytes(sbdBlockNumber, smallBlockDepotChain, pos);
                        pos += 4;
                        sbdBlockNumber++;
                    }
                    IntegerHelper.getFourBytes(-2, smallBlockDepotChain, pos);
                    pos += 4;
                    sbdBlockNumber++;
                }
            }
            this.out.write(smallBlockDepotChain);
        }
    }

    private void writeSmallBlockDepot() throws IOException {
        if (this.additionalPropertySets != null) {
            byte[] smallBlockDepot = new byte[(this.numSmallBlockDepotBlocks * 512)];
            int pos = 0;
            Iterator i = this.additionalPropertySets.iterator();
            while (i.hasNext()) {
                ReadPropertyStorage rps = (ReadPropertyStorage) i.next();
                if (rps.data.length <= 4096) {
                    System.arraycopy(rps.data, 0, smallBlockDepot, pos, rps.data.length);
                    pos += getSmallBlocksRequired(rps.data.length) * 64;
                }
            }
            this.out.write(smallBlockDepot);
        }
    }

    private void writeBigBlockDepot() throws IOException {
        this.bigBlockDepot = new byte[512];
        this.bbdPos = 0;
        for (int i = 0; i < this.numExtensionBlocks; i++) {
            IntegerHelper.getFourBytes(-3, this.bigBlockDepot, this.bbdPos);
            this.bbdPos += 4;
            checkBbdPos();
        }
        writeBlockChain(this.excelDataStartBlock, this.excelDataBlocks);
        int summaryInfoBlock = this.excelDataStartBlock + this.excelDataBlocks + this.additionalPropertyBlocks;
        for (int i2 = summaryInfoBlock; i2 < summaryInfoBlock + 7; i2++) {
            IntegerHelper.getFourBytes(i2 + 1, this.bigBlockDepot, this.bbdPos);
            this.bbdPos += 4;
            checkBbdPos();
        }
        IntegerHelper.getFourBytes(-2, this.bigBlockDepot, this.bbdPos);
        this.bbdPos += 4;
        checkBbdPos();
        for (int i3 = summaryInfoBlock + 8; i3 < summaryInfoBlock + 15; i3++) {
            IntegerHelper.getFourBytes(i3 + 1, this.bigBlockDepot, this.bbdPos);
            this.bbdPos += 4;
            checkBbdPos();
        }
        IntegerHelper.getFourBytes(-2, this.bigBlockDepot, this.bbdPos);
        this.bbdPos += 4;
        checkBbdPos();
        writeAdditionalPropertySetBlockChains();
        if (this.sbdStartBlock != -2) {
            writeBlockChain(this.sbdStartBlock, this.numSmallBlockDepotBlocks);
            writeBlockChain(this.sbdStartBlockChain, this.numSmallBlockDepotChainBlocks);
        }
        for (int i4 = 0; i4 < this.numBigBlockDepotBlocks; i4++) {
            IntegerHelper.getFourBytes(-3, this.bigBlockDepot, this.bbdPos);
            this.bbdPos += 4;
            checkBbdPos();
        }
        writeBlockChain(this.rootStartBlock, this.numRootEntryBlocks);
        if (this.bbdPos != 0) {
            for (int i5 = this.bbdPos; i5 < 512; i5++) {
                this.bigBlockDepot[i5] = -1;
            }
            this.out.write(this.bigBlockDepot);
        }
    }

    private int getBigBlocksRequired(int length) {
        int blocks = length / 512;
        return length % 512 > 0 ? blocks + 1 : blocks;
    }

    private int getSmallBlocksRequired(int length) {
        int blocks = length / 64;
        return length % 64 > 0 ? blocks + 1 : blocks;
    }

    private void writePropertySets() throws IOException {
        int block;
        ReadPropertyStorage rps;
        byte[] propertySetStorage = new byte[(this.numRootEntryBlocks * 512)];
        int[] mappings = null;
        if (this.additionalPropertySets != null) {
            mappings = new int[this.numPropertySets];
            for (int i = 0; i < STANDARD_PROPERTY_SETS.length; i++) {
                ReadPropertyStorage rps2 = (ReadPropertyStorage) this.readPropertySets.get(STANDARD_PROPERTY_SETS[i]);
                if (rps2 != null) {
                    mappings[rps2.number] = i;
                } else {
                    logger.warn(new StringBuffer().append("Standard property set ").append(STANDARD_PROPERTY_SETS[i]).append(" not present in source file").toString());
                }
            }
            int newMapping = STANDARD_PROPERTY_SETS.length;
            Iterator i2 = this.additionalPropertySets.iterator();
            while (i2.hasNext()) {
                mappings[((ReadPropertyStorage) i2.next()).number] = newMapping;
                newMapping++;
            }
        }
        int size2 = 0;
        if (this.additionalPropertySets != null) {
            size2 = 0 + (getBigBlocksRequired(this.requiredSize) * 512) + (getBigBlocksRequired(4096) * 512) + (getBigBlocksRequired(4096) * 512);
            Iterator i3 = this.additionalPropertySets.iterator();
            while (i3.hasNext()) {
                ReadPropertyStorage rps3 = (ReadPropertyStorage) i3.next();
                if (rps3.propertyStorage.type != 1) {
                    if (rps3.propertyStorage.size >= 4096) {
                        size2 += getBigBlocksRequired(rps3.propertyStorage.size) * 512;
                    } else {
                        size2 += getSmallBlocksRequired(rps3.propertyStorage.size) * 64;
                    }
                }
            }
        }
        BaseCompoundFile.PropertyStorage ps = new BaseCompoundFile.PropertyStorage((BaseCompoundFile) this, BaseCompoundFile.ROOT_ENTRY_NAME);
        ps.setType(5);
        ps.setStartBlock(this.sbdStartBlock);
        ps.setSize(size2);
        ps.setPrevious(-1);
        ps.setNext(-1);
        ps.setColour(0);
        int child = 1;
        if (this.additionalPropertySets != null) {
            child = mappings[((ReadPropertyStorage) this.readPropertySets.get(BaseCompoundFile.ROOT_ENTRY_NAME)).propertyStorage.child];
        }
        ps.setChild(child);
        System.arraycopy(ps.data, 0, propertySetStorage, 0, 128);
        int pos = 0 + 128;
        BaseCompoundFile.PropertyStorage ps2 = new BaseCompoundFile.PropertyStorage((BaseCompoundFile) this, BaseCompoundFile.WORKBOOK_NAME);
        ps2.setType(2);
        ps2.setStartBlock(this.excelDataStartBlock);
        ps2.setSize(this.requiredSize);
        int previous = 3;
        int next = -1;
        if (this.additionalPropertySets != null) {
            ReadPropertyStorage rps4 = (ReadPropertyStorage) this.readPropertySets.get(BaseCompoundFile.WORKBOOK_NAME);
            previous = rps4.propertyStorage.previous != -1 ? mappings[rps4.propertyStorage.previous] : -1;
            next = rps4.propertyStorage.next != -1 ? mappings[rps4.propertyStorage.next] : -1;
        }
        ps2.setPrevious(previous);
        ps2.setNext(next);
        ps2.setChild(-1);
        System.arraycopy(ps2.data, 0, propertySetStorage, pos, 128);
        int pos2 = pos + 128;
        BaseCompoundFile.PropertyStorage ps3 = new BaseCompoundFile.PropertyStorage((BaseCompoundFile) this, BaseCompoundFile.SUMMARY_INFORMATION_NAME);
        ps3.setType(2);
        ps3.setStartBlock(this.excelDataStartBlock + this.excelDataBlocks);
        ps3.setSize(4096);
        int previous2 = 1;
        int next2 = 3;
        if (!(this.additionalPropertySets == null || (rps = (ReadPropertyStorage) this.readPropertySets.get(BaseCompoundFile.SUMMARY_INFORMATION_NAME)) == null)) {
            previous2 = rps.propertyStorage.previous != -1 ? mappings[rps.propertyStorage.previous] : -1;
            next2 = rps.propertyStorage.next != -1 ? mappings[rps.propertyStorage.next] : -1;
        }
        ps3.setPrevious(previous2);
        ps3.setNext(next2);
        ps3.setChild(-1);
        System.arraycopy(ps3.data, 0, propertySetStorage, pos2, 128);
        int pos3 = pos2 + 128;
        BaseCompoundFile.PropertyStorage ps4 = new BaseCompoundFile.PropertyStorage((BaseCompoundFile) this, BaseCompoundFile.DOCUMENT_SUMMARY_INFORMATION_NAME);
        ps4.setType(2);
        ps4.setStartBlock(this.excelDataStartBlock + this.excelDataBlocks + 8);
        ps4.setSize(4096);
        ps4.setPrevious(-1);
        ps4.setNext(-1);
        ps4.setChild(-1);
        System.arraycopy(ps4.data, 0, propertySetStorage, pos3, 128);
        int pos4 = pos3 + 128;
        if (this.additionalPropertySets == null) {
            this.out.write(propertySetStorage);
            return;
        }
        int bigBlock = this.excelDataStartBlock + this.excelDataBlocks + 16;
        int smallBlock = 0;
        Iterator i4 = this.additionalPropertySets.iterator();
        while (i4.hasNext()) {
            ReadPropertyStorage rps5 = (ReadPropertyStorage) i4.next();
            if (rps5.data.length > 4096) {
                block = bigBlock;
            } else {
                block = smallBlock;
            }
            BaseCompoundFile.PropertyStorage ps5 = new BaseCompoundFile.PropertyStorage((BaseCompoundFile) this, rps5.propertyStorage.name);
            ps5.setType(rps5.propertyStorage.type);
            ps5.setStartBlock(block);
            ps5.setSize(rps5.propertyStorage.size);
            int previous3 = rps5.propertyStorage.previous != -1 ? mappings[rps5.propertyStorage.previous] : -1;
            int next3 = rps5.propertyStorage.next != -1 ? mappings[rps5.propertyStorage.next] : -1;
            int child2 = rps5.propertyStorage.child != -1 ? mappings[rps5.propertyStorage.child] : -1;
            ps5.setPrevious(previous3);
            ps5.setNext(next3);
            ps5.setChild(child2);
            System.arraycopy(ps5.data, 0, propertySetStorage, pos4, 128);
            pos4 += 128;
            if (rps5.data.length > 4096) {
                bigBlock += getBigBlocksRequired(rps5.data.length);
            } else {
                smallBlock += getSmallBlocksRequired(rps5.data.length);
            }
        }
        this.out.write(propertySetStorage);
    }
}
