package jxl.demo;

import common.Logger;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import jxl.Cell;
import jxl.Range;
import jxl.Workbook;

public class Demo {
    private static final int CSVFormat = 13;
    private static final int XMLFormat = 14;
    static Class class$jxl$demo$Demo;
    private static Logger logger;

    static {
        Class cls;
        if (class$jxl$demo$Demo == null) {
            cls = class$("jxl.demo.Demo");
            class$jxl$demo$Demo = cls;
        } else {
            cls = class$jxl$demo$Demo;
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

    private static void displayHelp() {
        System.err.println("Command format:  Demo [-unicode] [-csv] [-hide] excelfile");
        System.err.println("                 Demo -xml [-format]  excelfile");
        System.err.println("                 Demo -readwrite|-rw excelfile output");
        System.err.println("                 Demo -biffdump | -bd | -wa | -write | -formulas | -features excelfile");
        System.err.println("                 Demo -ps excelfile [property] [output]");
        System.err.println("                 Demo -version | -logtest | -h | -help");
    }

    public static void main(String[] args) {
        String file;
        if (args.length == 0) {
            displayHelp();
            System.exit(1);
        }
        if (args[0].equals("-help") || args[0].equals("-h")) {
            displayHelp();
            System.exit(1);
        }
        if (args[0].equals("-version")) {
            System.out.println(new StringBuffer().append("v").append(Workbook.getVersion()).toString());
            System.exit(0);
        }
        if (args[0].equals("-logtest")) {
            logger.debug("A sample \"debug\" message");
            logger.info("A sample \"info\" message");
            logger.warn("A sample \"warning\" message");
            logger.error("A sample \"error\" message");
            logger.fatal("A sample \"fatal\" message");
            System.exit(0);
        }
        boolean write = false;
        boolean readwrite = false;
        boolean formulas = false;
        boolean biffdump = false;
        boolean jxlversion = false;
        boolean propertysets = false;
        boolean features = false;
        String str = args[0];
        String outputFile = null;
        String propertySet = null;
        if (args[0].equals("-write")) {
            write = true;
            file = args[1];
        } else if (args[0].equals("-formulas")) {
            formulas = true;
            file = args[1];
        } else if (args[0].equals("-features")) {
            features = true;
            file = args[1];
        } else if (args[0].equals("-biffdump") || args[0].equals("-bd")) {
            biffdump = true;
            file = args[1];
        } else if (args[0].equals("-wa")) {
            jxlversion = true;
            file = args[1];
        } else if (args[0].equals("-ps")) {
            propertysets = true;
            file = args[1];
            if (args.length > 2) {
                propertySet = args[2];
            }
            if (args.length == 4) {
                outputFile = args[3];
            }
        } else if (args[0].equals("-readwrite") || args[0].equals("-rw")) {
            readwrite = true;
            file = args[1];
            outputFile = args[2];
        } else {
            file = args[args.length - 1];
        }
        String encoding = "UTF8";
        int format = 13;
        boolean formatInfo = false;
        boolean hideCells = false;
        if (!write && !readwrite && !formulas && !biffdump && !jxlversion && !propertysets && !features) {
            for (int i = 0; i < args.length - 1; i++) {
                if (args[i].equals("-unicode")) {
                    encoding = "UnicodeBig";
                } else if (args[i].equals("-xml")) {
                    format = 14;
                } else if (args[i].equals("-csv")) {
                    format = 13;
                } else if (args[i].equals("-format")) {
                    formatInfo = true;
                } else if (args[i].equals("-hide")) {
                    hideCells = true;
                } else {
                    System.err.println("Command format:  CSV [-unicode] [-xml|-csv] excelfile");
                    System.exit(1);
                }
            }
        }
        if (write) {
            try {
                new Write(file).write();
            } catch (Throwable t) {
                System.out.println(t.toString());
                t.printStackTrace();
            }
        } else if (readwrite) {
            new ReadWrite(file, outputFile).readWrite();
        } else if (formulas) {
            Workbook w = Workbook.getWorkbook(new File(file));
            new Formulas(w, System.out, encoding);
            w.close();
        } else if (features) {
            Workbook w2 = Workbook.getWorkbook(new File(file));
            new Features(w2, System.out, encoding);
            w2.close();
        } else if (biffdump) {
            new BiffDump(new File(file), System.out);
        } else if (jxlversion) {
            new WriteAccess(new File(file));
        } else if (propertysets) {
            OutputStream os = System.out;
            if (outputFile != null) {
                os = new FileOutputStream(outputFile);
            }
            new PropertySetsReader(new File(file), propertySet, os);
        } else {
            Workbook w3 = Workbook.getWorkbook(new File(file));
            if (format == 13) {
                new CSV(w3, System.out, encoding, hideCells);
            } else if (format == 14) {
                new XML(w3, System.out, encoding, formatInfo);
            }
            w3.close();
        }
    }

    private static void findTest(Workbook w) {
        logger.info("Find test");
        Cell c = w.findCellByName("named1");
        if (c != null) {
            logger.info(new StringBuffer().append("named1 contents:  ").append(c.getContents()).toString());
        }
        Cell c2 = w.findCellByName("named2");
        if (c2 != null) {
            logger.info(new StringBuffer().append("named2 contents:  ").append(c2.getContents()).toString());
        }
        Cell c3 = w.findCellByName("namedrange");
        if (c3 != null) {
            logger.info(new StringBuffer().append("named2 contents:  ").append(c3.getContents()).toString());
        }
        Range[] range = w.findByName("namedrange");
        if (range != null) {
            logger.info(new StringBuffer().append("namedrange top left contents:  ").append(range[0].getTopLeft().getContents()).toString());
            logger.info(new StringBuffer().append("namedrange bottom right contents:  ").append(range[0].getBottomRight().getContents()).toString());
        }
        Range[] range2 = w.findByName("nonadjacentrange");
        if (range2 != null) {
            for (int i = 0; i < range2.length; i++) {
                logger.info(new StringBuffer().append("nonadjacent top left contents:  ").append(range2[i].getTopLeft().getContents()).toString());
                logger.info(new StringBuffer().append("nonadjacent bottom right contents:  ").append(range2[i].getBottomRight().getContents()).toString());
            }
        }
        Range[] range3 = w.findByName("horizontalnonadjacentrange");
        if (range3 != null) {
            for (int i2 = 0; i2 < range3.length; i2++) {
                logger.info(new StringBuffer().append("horizontalnonadjacent top left contents:  ").append(range3[i2].getTopLeft().getContents()).toString());
                logger.info(new StringBuffer().append("horizontalnonadjacent bottom right contents:  ").append(range3[i2].getBottomRight().getContents()).toString());
            }
        }
    }
}
