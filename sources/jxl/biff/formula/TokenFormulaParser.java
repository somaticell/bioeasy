package jxl.biff.formula;

import common.Assert;
import common.Logger;
import java.util.Stack;
import jxl.Cell;
import jxl.WorkbookSettings;
import jxl.biff.WorkbookMethods;

class TokenFormulaParser implements Parser {
    static Class class$jxl$biff$formula$TokenFormulaParser;
    private static Logger logger;
    private WorkbookMethods nameTable;
    private int pos = 0;
    private Cell relativeTo;
    private ParseItem root;
    private WorkbookSettings settings;
    private byte[] tokenData;
    private Stack tokenStack;
    private ExternalSheet workbook;

    static {
        Class cls;
        if (class$jxl$biff$formula$TokenFormulaParser == null) {
            cls = class$("jxl.biff.formula.TokenFormulaParser");
            class$jxl$biff$formula$TokenFormulaParser = cls;
        } else {
            cls = class$jxl$biff$formula$TokenFormulaParser;
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

    public TokenFormulaParser(byte[] data, Cell c, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws) {
        this.tokenData = data;
        this.relativeTo = c;
        this.workbook = es;
        this.nameTable = nt;
        this.tokenStack = new Stack();
        this.settings = ws;
    }

    public void parse() throws FormulaException {
        parseSubExpression(this.tokenData.length);
        this.root = (ParseItem) this.tokenStack.pop();
        Assert.verify(this.tokenStack.empty());
    }

    private void parseSubExpression(int len) throws FormulaException {
        Attribute ifattr;
        Stack ifStack = new Stack();
        int endpos = this.pos + len;
        while (this.pos < endpos) {
            byte tokenVal = this.tokenData[this.pos];
            this.pos++;
            Token t = Token.getToken(tokenVal);
            if (t == Token.UNKNOWN) {
                throw new FormulaException(FormulaException.unrecognizedToken, (int) tokenVal);
            }
            Assert.verify(t != Token.UNKNOWN);
            if (t == Token.REF) {
                CellReference cr = new CellReference(this.relativeTo);
                this.pos += cr.read(this.tokenData, this.pos);
                this.tokenStack.push(cr);
            } else if (t == Token.REFV) {
                SharedFormulaCellReference cr2 = new SharedFormulaCellReference(this.relativeTo);
                this.pos += cr2.read(this.tokenData, this.pos);
                this.tokenStack.push(cr2);
            } else if (t == Token.REF3D) {
                CellReference3d cr3 = new CellReference3d(this.relativeTo, this.workbook);
                this.pos += cr3.read(this.tokenData, this.pos);
                this.tokenStack.push(cr3);
            } else if (t == Token.AREA) {
                Area a = new Area();
                this.pos += a.read(this.tokenData, this.pos);
                this.tokenStack.push(a);
            } else if (t == Token.AREAV) {
                SharedFormulaArea a2 = new SharedFormulaArea(this.relativeTo);
                this.pos += a2.read(this.tokenData, this.pos);
                this.tokenStack.push(a2);
            } else if (t == Token.AREA3D) {
                Area3d a3 = new Area3d(this.workbook);
                this.pos += a3.read(this.tokenData, this.pos);
                this.tokenStack.push(a3);
            } else if (t == Token.NAME) {
                Name n = new Name();
                this.pos += n.read(this.tokenData, this.pos);
                this.tokenStack.push(n);
            } else if (t == Token.NAMED_RANGE) {
                NameRange nameRange = new NameRange(this.nameTable);
                this.pos += nameRange.read(this.tokenData, this.pos);
                this.tokenStack.push(nameRange);
            } else if (t == Token.INTEGER) {
                IntegerValue i = new IntegerValue();
                this.pos += i.read(this.tokenData, this.pos);
                this.tokenStack.push(i);
            } else if (t == Token.DOUBLE) {
                DoubleValue d = new DoubleValue();
                this.pos += d.read(this.tokenData, this.pos);
                this.tokenStack.push(d);
            } else if (t == Token.BOOL) {
                BooleanValue bv = new BooleanValue();
                this.pos += bv.read(this.tokenData, this.pos);
                this.tokenStack.push(bv);
            } else if (t == Token.STRING) {
                StringValue stringValue = new StringValue(this.settings);
                this.pos += stringValue.read(this.tokenData, this.pos);
                this.tokenStack.push(stringValue);
            } else if (t == Token.MISSING_ARG) {
                MissingArg ma = new MissingArg();
                this.pos += ma.read(this.tokenData, this.pos);
                this.tokenStack.push(ma);
            } else if (t == Token.UNARY_PLUS) {
                UnaryPlus up = new UnaryPlus();
                this.pos += up.read(this.tokenData, this.pos);
                addOperator(up);
            } else if (t == Token.UNARY_MINUS) {
                UnaryMinus um = new UnaryMinus();
                this.pos += um.read(this.tokenData, this.pos);
                addOperator(um);
            } else if (t == Token.PERCENT) {
                Percent p = new Percent();
                this.pos += p.read(this.tokenData, this.pos);
                addOperator(p);
            } else if (t == Token.SUBTRACT) {
                Subtract s = new Subtract();
                this.pos += s.read(this.tokenData, this.pos);
                addOperator(s);
            } else if (t == Token.ADD) {
                Add s2 = new Add();
                this.pos += s2.read(this.tokenData, this.pos);
                addOperator(s2);
            } else if (t == Token.MULTIPLY) {
                Multiply s3 = new Multiply();
                this.pos += s3.read(this.tokenData, this.pos);
                addOperator(s3);
            } else if (t == Token.DIVIDE) {
                Divide s4 = new Divide();
                this.pos += s4.read(this.tokenData, this.pos);
                addOperator(s4);
            } else if (t == Token.CONCAT) {
                Concatenate c = new Concatenate();
                this.pos += c.read(this.tokenData, this.pos);
                addOperator(c);
            } else if (t == Token.POWER) {
                Power p2 = new Power();
                this.pos += p2.read(this.tokenData, this.pos);
                addOperator(p2);
            } else if (t == Token.LESS_THAN) {
                LessThan lt = new LessThan();
                this.pos += lt.read(this.tokenData, this.pos);
                addOperator(lt);
            } else if (t == Token.LESS_EQUAL) {
                LessEqual lte = new LessEqual();
                this.pos += lte.read(this.tokenData, this.pos);
                addOperator(lte);
            } else if (t == Token.GREATER_THAN) {
                GreaterThan gt = new GreaterThan();
                this.pos += gt.read(this.tokenData, this.pos);
                addOperator(gt);
            } else if (t == Token.GREATER_EQUAL) {
                GreaterEqual gte = new GreaterEqual();
                this.pos += gte.read(this.tokenData, this.pos);
                addOperator(gte);
            } else if (t == Token.NOT_EQUAL) {
                NotEqual ne = new NotEqual();
                this.pos += ne.read(this.tokenData, this.pos);
                addOperator(ne);
            } else if (t == Token.EQUAL) {
                Equal e = new Equal();
                this.pos += e.read(this.tokenData, this.pos);
                addOperator(e);
            } else if (t == Token.PARENTHESIS) {
                Parenthesis p3 = new Parenthesis();
                this.pos += p3.read(this.tokenData, this.pos);
                addOperator(p3);
            } else if (t == Token.ATTRIBUTE) {
                Attribute a4 = new Attribute(this.settings);
                this.pos += a4.read(this.tokenData, this.pos);
                if (a4.isSum()) {
                    addOperator(a4);
                } else if (a4.isIf()) {
                    ifStack.push(a4);
                }
            } else if (t == Token.FUNCTION) {
                BuiltInFunction bif = new BuiltInFunction(this.settings);
                this.pos += bif.read(this.tokenData, this.pos);
                addOperator(bif);
            } else if (t == Token.FUNCTIONVARARG) {
                VariableArgFunction vaf = new VariableArgFunction(this.settings);
                this.pos += vaf.read(this.tokenData, this.pos);
                if (vaf.getFunction() != Function.ATTRIBUTE) {
                    addOperator(vaf);
                } else {
                    vaf.getOperands(this.tokenStack);
                    if (ifStack.empty()) {
                        ifattr = new Attribute(this.settings);
                    } else {
                        ifattr = (Attribute) ifStack.pop();
                    }
                    ifattr.setIfConditions(vaf);
                    this.tokenStack.push(ifattr);
                }
            } else if (t == Token.MEM_FUNC) {
                MemFunc memFunc = new MemFunc();
                this.pos += memFunc.read(this.tokenData, this.pos);
                Stack oldStack = this.tokenStack;
                this.tokenStack = new Stack();
                parseSubExpression(memFunc.getLength());
                ParseItem[] subexpr = new ParseItem[this.tokenStack.size()];
                int i2 = 0;
                while (!this.tokenStack.isEmpty()) {
                    subexpr[i2] = (ParseItem) this.tokenStack.pop();
                    i2++;
                }
                memFunc.setSubExpression(subexpr);
                this.tokenStack = oldStack;
                this.tokenStack.push(memFunc);
            }
        }
    }

    private void addOperator(Operator o) {
        o.getOperands(this.tokenStack);
        this.tokenStack.push(o);
    }

    public String getFormula() {
        StringBuffer sb = new StringBuffer();
        this.root.getString(sb);
        return sb.toString();
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        this.root.adjustRelativeCellReferences(colAdjust, rowAdjust);
    }

    public byte[] getBytes() {
        return this.root.getBytes();
    }

    public void columnInserted(int sheetIndex, int col, boolean currentSheet) {
        this.root.columnInserted(sheetIndex, col, currentSheet);
    }

    public void columnRemoved(int sheetIndex, int col, boolean currentSheet) {
        this.root.columnRemoved(sheetIndex, col, currentSheet);
    }

    public void rowInserted(int sheetIndex, int row, boolean currentSheet) {
        this.root.rowInserted(sheetIndex, row, currentSheet);
    }

    public void rowRemoved(int sheetIndex, int row, boolean currentSheet) {
        this.root.rowRemoved(sheetIndex, row, currentSheet);
    }
}
