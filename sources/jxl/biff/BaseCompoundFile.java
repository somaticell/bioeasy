package jxl.biff;

import common.Assert;

public abstract class BaseCompoundFile {
    protected static final int BIG_BLOCK_DEPOT_BLOCKS_POS = 76;
    protected static final int BIG_BLOCK_SIZE = 512;
    private static final int CHILD_POS = 76;
    private static final int COLOUR_POS = 67;
    public static final String COMP_OBJ_NAME = "\u0001CompObj";
    public static final int DIRECTORY_PS_TYPE = 1;
    public static final String DOCUMENT_SUMMARY_INFORMATION_NAME = "\u0005DocumentSummaryInformation";
    protected static final int EXTENSION_BLOCK_POS = 68;
    public static final int FILE_PS_TYPE = 2;
    protected static final byte[] IDENTIFIER = {-48, -49, 17, -32, -95, -79, 26, -31};
    private static final int NEXT_POS = 72;
    public static final int NONE_PS_TYPE = 0;
    protected static final int NUM_BIG_BLOCK_DEPOT_BLOCKS_POS = 44;
    protected static final int NUM_EXTENSION_BLOCK_POS = 72;
    protected static final int NUM_SMALL_BLOCK_DEPOT_BLOCKS_POS = 64;
    private static final int PREVIOUS_POS = 68;
    protected static final int PROPERTY_STORAGE_BLOCK_SIZE = 128;
    public static final String ROOT_ENTRY_NAME = "Root Entry";
    public static final int ROOT_ENTRY_PS_TYPE = 5;
    protected static final int ROOT_START_BLOCK_POS = 48;
    private static final int SIZE_OF_NAME_POS = 64;
    private static final int SIZE_POS = 120;
    protected static final int SMALL_BLOCK_DEPOT_BLOCK_POS = 60;
    protected static final int SMALL_BLOCK_SIZE = 64;
    protected static final int SMALL_BLOCK_THRESHOLD = 4096;
    public static final String[] STANDARD_PROPERTY_SETS = {ROOT_ENTRY_NAME, WORKBOOK_NAME, SUMMARY_INFORMATION_NAME, DOCUMENT_SUMMARY_INFORMATION_NAME};
    private static final int START_BLOCK_POS = 116;
    public static final String SUMMARY_INFORMATION_NAME = "\u0005SummaryInformation";
    private static final int TYPE_POS = 66;
    public static final String WORKBOOK_NAME = "Workbook";

    public class PropertyStorage {
        public int child;
        public int colour;
        public byte[] data;
        public String name;
        public int next;
        public int previous;
        public int size;
        public int startBlock;
        private final BaseCompoundFile this$0;
        public int type;

        public PropertyStorage(BaseCompoundFile this$02, byte[] d) {
            this.this$0 = this$02;
            this.data = d;
            int nameSize = IntegerHelper.getInt(this.data[64], this.data[65]);
            this.type = this.data[66];
            this.colour = this.data[67];
            this.startBlock = IntegerHelper.getInt(this.data[BaseCompoundFile.START_BLOCK_POS], this.data[117], this.data[118], this.data[119]);
            this.size = IntegerHelper.getInt(this.data[BaseCompoundFile.SIZE_POS], this.data[121], this.data[122], this.data[123]);
            this.previous = IntegerHelper.getInt(this.data[68], this.data[69], this.data[70], this.data[71]);
            this.next = IntegerHelper.getInt(this.data[72], this.data[73], this.data[74], this.data[75]);
            this.child = IntegerHelper.getInt(this.data[76], this.data[77], this.data[78], this.data[79]);
            int chars = nameSize > 2 ? (nameSize - 1) / 2 : 0;
            StringBuffer n = new StringBuffer("");
            for (int i = 0; i < chars; i++) {
                n.append((char) this.data[i * 2]);
            }
            this.name = n.toString();
        }

        public PropertyStorage(BaseCompoundFile this$02, String name2) {
            this.this$0 = this$02;
            this.data = new byte[128];
            Assert.verify(name2.length() < 32);
            IntegerHelper.getTwoBytes((name2.length() + 1) * 2, this.data, 64);
            for (int i = 0; i < name2.length(); i++) {
                this.data[i * 2] = (byte) name2.charAt(i);
            }
        }

        public void setType(int t) {
            this.type = t;
            this.data[66] = (byte) t;
        }

        public void setStartBlock(int sb) {
            this.startBlock = sb;
            IntegerHelper.getFourBytes(sb, this.data, BaseCompoundFile.START_BLOCK_POS);
        }

        public void setSize(int s) {
            this.size = s;
            IntegerHelper.getFourBytes(s, this.data, BaseCompoundFile.SIZE_POS);
        }

        public void setPrevious(int prev) {
            this.previous = prev;
            IntegerHelper.getFourBytes(prev, this.data, 68);
        }

        public void setNext(int nxt) {
            this.next = nxt;
            IntegerHelper.getFourBytes(this.next, this.data, 72);
        }

        public void setChild(int dir) {
            this.child = dir;
            IntegerHelper.getFourBytes(this.child, this.data, 76);
        }

        public void setColour(int col) {
            this.colour = col == 0 ? 0 : 1;
            this.data[67] = (byte) this.colour;
        }
    }

    protected BaseCompoundFile() {
    }
}
