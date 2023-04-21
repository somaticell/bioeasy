package cn.com.bioeasy.app.excel;

import java.io.File;
import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

public class ExcelUtil {
    private static final String UTF8_ENCODING = "UTF-8";
    private static WritableFont arial10font = null;
    private static WritableCellFormat arial10format = null;
    private static WritableFont arial12font = null;
    private static WritableCellFormat arial12format = null;
    private static WritableFont arial14font = null;
    private static WritableCellFormat arial14format = null;

    private static void format() {
        try {
            arial14font = new WritableFont(WritableFont.ARIAL, 14, WritableFont.BOLD);
            arial14font.setColour(Colour.LIGHT_BLUE);
            arial14format = new WritableCellFormat(arial14font);
            arial14format.setAlignment(Alignment.CENTRE);
            arial14format.setBorder(Border.ALL, BorderLineStyle.THIN);
            arial14format.setBackground(Colour.VERY_LIGHT_YELLOW);
            arial10font = new WritableFont(WritableFont.ARIAL, 10, WritableFont.BOLD);
            arial10format = new WritableCellFormat(arial10font);
            arial10format.setAlignment(Alignment.CENTRE);
            arial10format.setBorder(Border.ALL, BorderLineStyle.THIN);
            arial10format.setBackground(Colour.GRAY_25);
            arial12font = new WritableFont(WritableFont.ARIAL, 10);
            arial12format = new WritableCellFormat(arial12font);
            arial10format.setAlignment(Alignment.CENTRE);
            arial12format.setBorder(Border.ALL, BorderLineStyle.THIN);
        } catch (WriteException e) {
            e.printStackTrace();
        }
    }

    public static void initExcel(String fileName, String[] colName) {
        format();
        WritableWorkbook workbook = null;
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            WritableWorkbook workbook2 = Workbook.createWorkbook(file);
            WritableSheet sheet = workbook2.createSheet("检测结果", 0);
            sheet.addCell(new Label(0, 0, fileName, arial14format));
            for (int col = 0; col < colName.length; col++) {
                sheet.addCell(new Label(col, 0, colName[col], arial10format));
            }
            sheet.setRowView(0, 340);
            workbook2.write();
            if (workbook2 != null) {
                try {
                    workbook2.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e3) {
                    e3.printStackTrace();
                }
            }
        } catch (Throwable th) {
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e4) {
                    e4.printStackTrace();
                }
            }
            throw th;
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:24:0x00a3 A[SYNTHETIC, Splitter:B:24:0x00a3] */
    /* JADX WARNING: Removed duplicated region for block: B:27:0x00a8 A[SYNTHETIC, Splitter:B:27:0x00a8] */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x00e8 A[SYNTHETIC, Splitter:B:49:0x00e8] */
    /* JADX WARNING: Removed duplicated region for block: B:52:0x00ed A[SYNTHETIC, Splitter:B:52:0x00ed] */
    /* JADX WARNING: Removed duplicated region for block: B:69:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T> void writeObjListToExcel(java.util.List<java.lang.String[]> r15, java.lang.String r16, android.content.Context r17) {
        /*
            java.lang.Class<cn.com.bioeasy.app.excel.ExcelUtil> r11 = cn.com.bioeasy.app.excel.ExcelUtil.class
            java.lang.String r11 = r11.getName()
            java.util.logging.Logger r11 = java.util.logging.Logger.getLogger(r11)
            java.lang.StringBuilder r12 = new java.lang.StringBuilder
            r12.<init>()
            java.lang.String r13 = "======export file path:"
            java.lang.StringBuilder r12 = r12.append(r13)
            r0 = r16
            java.lang.StringBuilder r12 = r12.append(r0)
            java.lang.String r12 = r12.toString()
            r11.info(r12)
            if (r15 == 0) goto L_0x00ab
            int r11 = r15.size()
            if (r11 <= 0) goto L_0x00ab
            r10 = 0
            r4 = 0
            jxl.WorkbookSettings r7 = new jxl.WorkbookSettings     // Catch:{ Exception -> 0x00fe }
            r7.<init>()     // Catch:{ Exception -> 0x00fe }
            java.lang.String r11 = "UTF-8"
            r7.setEncoding(r11)     // Catch:{ Exception -> 0x00fe }
            java.io.FileInputStream r5 = new java.io.FileInputStream     // Catch:{ Exception -> 0x00fe }
            java.io.File r11 = new java.io.File     // Catch:{ Exception -> 0x00fe }
            r0 = r16
            r11.<init>(r0)     // Catch:{ Exception -> 0x00fe }
            r5.<init>(r11)     // Catch:{ Exception -> 0x00fe }
            jxl.Workbook r9 = jxl.Workbook.getWorkbook((java.io.InputStream) r5)     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            java.io.File r11 = new java.io.File     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            r0 = r16
            r11.<init>(r0)     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            jxl.write.WritableWorkbook r10 = jxl.Workbook.createWorkbook((java.io.File) r11, (jxl.Workbook) r9)     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            r11 = 0
            jxl.write.WritableSheet r8 = r10.getSheet((int) r11)     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            r6 = 0
        L_0x0057:
            int r11 = r15.size()     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            if (r6 >= r11) goto L_0x00b6
            java.lang.Object r11 = r15.get(r6)     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            java.lang.String[] r11 = (java.lang.String[]) r11     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            r0 = r11
            java.lang.String[] r0 = (java.lang.String[]) r0     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            r1 = r0
            r3 = 0
        L_0x0068:
            int r11 = r1.length     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            if (r3 >= r11) goto L_0x00ac
            jxl.write.Label r11 = new jxl.write.Label     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            int r12 = r6 + 1
            r13 = r1[r3]     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            jxl.write.WritableCellFormat r14 = arial12format     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            r11.<init>(r3, r12, r13, r14)     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            r8.addCell(r11)     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            r11 = r1[r3]     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            int r11 = r11.length()     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            r12 = 4
            if (r11 > r12) goto L_0x0090
            r11 = r1[r3]     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            int r11 = r11.length()     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            int r11 = r11 + 8
            r8.setColumnView((int) r3, (int) r11)     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
        L_0x008d:
            int r3 = r3 + 1
            goto L_0x0068
        L_0x0090:
            r11 = r1[r3]     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            int r11 = r11.length()     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            int r11 = r11 + 5
            r8.setColumnView((int) r3, (int) r11)     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            goto L_0x008d
        L_0x009c:
            r2 = move-exception
            r4 = r5
        L_0x009e:
            r2.printStackTrace()     // Catch:{ all -> 0x00e5 }
            if (r10 == 0) goto L_0x00a6
            r10.close()     // Catch:{ Exception -> 0x00db }
        L_0x00a6:
            if (r4 == 0) goto L_0x00ab
            r4.close()     // Catch:{ IOException -> 0x00e0 }
        L_0x00ab:
            return
        L_0x00ac:
            int r11 = r6 + 1
            r12 = 350(0x15e, float:4.9E-43)
            r8.setRowView((int) r11, (int) r12)     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            int r6 = r6 + 1
            goto L_0x0057
        L_0x00b6:
            r10.write()     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            java.lang.String r11 = "导出Excel成功"
            r12 = 0
            r0 = r17
            android.widget.Toast r11 = android.widget.Toast.makeText(r0, r11, r12)     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            r11.show()     // Catch:{ Exception -> 0x009c, all -> 0x00fb }
            if (r10 == 0) goto L_0x00cb
            r10.close()     // Catch:{ Exception -> 0x00d6 }
        L_0x00cb:
            if (r5 == 0) goto L_0x00ab
            r5.close()     // Catch:{ IOException -> 0x00d1 }
            goto L_0x00ab
        L_0x00d1:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x00ab
        L_0x00d6:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x00cb
        L_0x00db:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x00a6
        L_0x00e0:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x00ab
        L_0x00e5:
            r11 = move-exception
        L_0x00e6:
            if (r10 == 0) goto L_0x00eb
            r10.close()     // Catch:{ Exception -> 0x00f1 }
        L_0x00eb:
            if (r4 == 0) goto L_0x00f0
            r4.close()     // Catch:{ IOException -> 0x00f6 }
        L_0x00f0:
            throw r11
        L_0x00f1:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x00eb
        L_0x00f6:
            r2 = move-exception
            r2.printStackTrace()
            goto L_0x00f0
        L_0x00fb:
            r11 = move-exception
            r4 = r5
            goto L_0x00e6
        L_0x00fe:
            r2 = move-exception
            goto L_0x009e
        */
        throw new UnsupportedOperationException("Method not decompiled: cn.com.bioeasy.app.excel.ExcelUtil.writeObjListToExcel(java.util.List, java.lang.String, android.content.Context):void");
    }
}
