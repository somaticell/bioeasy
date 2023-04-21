package jxl.biff.formula;

import common.Logger;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;
import jxl.WorkbookSettings;
import jxl.biff.WorkbookMethods;

class StringFormulaParser implements Parser {
    static Class class$jxl$biff$formula$StringFormulaParser;
    private static Logger logger;
    private Stack arguments;
    private ExternalSheet externalSheet;
    private String formula;
    private WorkbookMethods nameTable;
    private String parsedFormula;
    private ParseItem root;
    private WorkbookSettings settings;

    static {
        Class cls;
        if (class$jxl$biff$formula$StringFormulaParser == null) {
            cls = class$("jxl.biff.formula.StringFormulaParser");
            class$jxl$biff$formula$StringFormulaParser = cls;
        } else {
            cls = class$jxl$biff$formula$StringFormulaParser;
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

    public StringFormulaParser(String f, ExternalSheet es, WorkbookMethods nt, WorkbookSettings ws) {
        this.formula = f;
        this.settings = ws;
        this.externalSheet = es;
        this.nameTable = nt;
    }

    public void parse() throws FormulaException {
        this.root = parseCurrent(getTokens().iterator());
    }

    private ParseItem parseCurrent(Iterator i) throws FormulaException {
        Stack stack = new Stack();
        Stack operators = new Stack();
        Stack args = null;
        boolean parenthesesClosed = false;
        ParseItem lastParseItem = null;
        while (i.hasNext() && !parenthesesClosed) {
            ParseItem pi = (ParseItem) i.next();
            if (pi instanceof Operand) {
                handleOperand((Operand) pi, stack);
            } else if (pi instanceof StringFunction) {
                handleFunction((StringFunction) pi, i, stack);
            } else if (pi instanceof Operator) {
                Operator op = (Operator) pi;
                if (op instanceof StringOperator) {
                    StringOperator sop = (StringOperator) op;
                    if (stack.isEmpty() || (lastParseItem instanceof Operator)) {
                        op = sop.getUnaryOperator();
                    } else {
                        op = sop.getBinaryOperator();
                    }
                }
                if (operators.empty()) {
                    operators.push(op);
                } else {
                    Operator operator = (Operator) operators.peek();
                    if (op.getPrecedence() < operator.getPrecedence()) {
                        operators.push(op);
                    } else {
                        operators.pop();
                        operator.getOperands(stack);
                        stack.push(operator);
                        operators.push(op);
                    }
                }
            } else if (pi instanceof ArgumentSeparator) {
                while (!operators.isEmpty()) {
                    Operator o = (Operator) operators.pop();
                    o.getOperands(stack);
                    stack.push(o);
                }
                if (args == null) {
                    args = new Stack();
                }
                args.push(stack.pop());
                stack.clear();
            } else if (pi instanceof OpenParentheses) {
                ParseItem pi2 = parseCurrent(i);
                Parenthesis p = new Parenthesis();
                pi2.setParent(p);
                p.add(pi2);
                stack.push(p);
            } else if (pi instanceof CloseParentheses) {
                parenthesesClosed = true;
            }
            lastParseItem = pi;
        }
        while (!operators.isEmpty()) {
            Operator o2 = (Operator) operators.pop();
            o2.getOperands(stack);
            stack.push(o2);
        }
        ParseItem rt = !stack.empty() ? (ParseItem) stack.pop() : null;
        if (!(args == null || rt == null)) {
            args.push(rt);
        }
        this.arguments = args;
        if (!stack.empty() || !operators.empty()) {
            logger.warn(new StringBuffer().append("Formula ").append(this.formula).append(" has a non-empty parse stack").toString());
        }
        return rt;
    }

    private ArrayList getTokens() throws FormulaException {
        ArrayList tokens = new ArrayList();
        Yylex lex = new Yylex((Reader) new StringReader(this.formula));
        lex.setExternalSheet(this.externalSheet);
        lex.setNameTable(this.nameTable);
        try {
            for (ParseItem pi = lex.yylex(); pi != null; pi = lex.yylex()) {
                tokens.add(pi);
            }
        } catch (IOException e) {
            logger.warn(e.toString());
        } catch (Error e2) {
            throw new FormulaException(FormulaException.lexicalError, new StringBuffer().append(this.formula).append(" at char  ").append(lex.getPos()).toString());
        }
        return tokens;
    }

    public String getFormula() {
        if (this.parsedFormula == null) {
            StringBuffer sb = new StringBuffer();
            this.root.getString(sb);
            this.parsedFormula = sb.toString();
        }
        return this.parsedFormula;
    }

    public byte[] getBytes() {
        byte[] bytes = this.root.getBytes();
        if (!this.root.isVolatile()) {
            return bytes;
        }
        byte[] newBytes = new byte[(bytes.length + 4)];
        System.arraycopy(bytes, 0, newBytes, 4, bytes.length);
        newBytes[0] = Token.ATTRIBUTE.getCode();
        newBytes[1] = 1;
        return newBytes;
    }

    private void handleFunction(StringFunction sf, Iterator i, Stack stack) throws FormulaException {
        int numArgs = 1;
        ParseItem pi2 = parseCurrent(i);
        if (sf.getFunction(this.settings) == Function.UNKNOWN) {
            throw new FormulaException(FormulaException.unrecognizedFunction);
        } else if (sf.getFunction(this.settings) == Function.SUM && this.arguments == null) {
            Attribute a = new Attribute(sf, this.settings);
            a.add(pi2);
            stack.push(a);
        } else if (sf.getFunction(this.settings) == Function.IF) {
            Attribute a2 = new Attribute(sf, this.settings);
            VariableArgFunction vaf = new VariableArgFunction(this.settings);
            int numargs = this.arguments.size();
            for (int j = 0; j < numargs; j++) {
                vaf.add((ParseItem) this.arguments.get(j));
            }
            a2.setIfConditions(vaf);
            stack.push(a2);
        } else if (sf.getFunction(this.settings).getNumArgs() != 255) {
            BuiltInFunction bif = new BuiltInFunction(sf.getFunction(this.settings), this.settings);
            int numargs2 = sf.getFunction(this.settings).getNumArgs();
            if (numargs2 == 1) {
                bif.add(pi2);
            } else if ((this.arguments != null || numargs2 == 0) && (this.arguments == null || numargs2 == this.arguments.size())) {
                for (int j2 = 0; j2 < numargs2; j2++) {
                    bif.add((ParseItem) this.arguments.get(j2));
                }
            } else {
                throw new FormulaException(FormulaException.incorrectArguments);
            }
            stack.push(bif);
        } else if (this.arguments == null) {
            if (pi2 == null) {
                numArgs = 0;
            }
            VariableArgFunction vaf2 = new VariableArgFunction(sf.getFunction(this.settings), numArgs, this.settings);
            if (pi2 != null) {
                vaf2.add(pi2);
            }
            stack.push(vaf2);
        } else {
            int numargs3 = this.arguments.size();
            VariableArgFunction vaf3 = new VariableArgFunction(sf.getFunction(this.settings), numargs3, this.settings);
            ParseItem[] args = new ParseItem[numargs3];
            for (int j3 = 0; j3 < numargs3; j3++) {
                args[(numargs3 - j3) - 1] = (ParseItem) this.arguments.pop();
            }
            for (ParseItem add : args) {
                vaf3.add(add);
            }
            stack.push(vaf3);
            this.arguments.clear();
            this.arguments = null;
        }
    }

    public void adjustRelativeCellReferences(int colAdjust, int rowAdjust) {
        this.root.adjustRelativeCellReferences(colAdjust, rowAdjust);
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

    private void handleOperand(Operand o, Stack stack) {
        if (!(o instanceof IntegerValue)) {
            stack.push(o);
        } else if (o instanceof IntegerValue) {
            IntegerValue iv = (IntegerValue) o;
            if (!iv.isOutOfRange()) {
                stack.push(iv);
            } else {
                stack.push(new DoubleValue(iv.getValue()));
            }
        }
    }
}
