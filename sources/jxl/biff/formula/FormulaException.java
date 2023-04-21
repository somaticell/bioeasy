package jxl.biff.formula;

import jxl.JXLException;

public class FormulaException extends JXLException {
    public static FormulaMessage biff8Supported = new FormulaMessage("Only biff8 formulas are supported");
    static FormulaMessage cellNameNotFound = new FormulaMessage("Could not find named cell");
    static FormulaMessage incorrectArguments = new FormulaMessage("Incorrect arguments supplied to function");
    static FormulaMessage lexicalError = new FormulaMessage("Lexical error:  ");
    static FormulaMessage sheetRefNotFound = new FormulaMessage("Could not find sheet");
    static FormulaMessage unrecognizedFunction = new FormulaMessage("Unrecognized function");
    static FormulaMessage unrecognizedToken = new FormulaMessage("Unrecognized token");

    private static class FormulaMessage {
        public String message;

        FormulaMessage(String m) {
            this.message = m;
        }
    }

    public FormulaException(FormulaMessage m) {
        super(m.message);
    }

    public FormulaException(FormulaMessage m, int val) {
        super(new StringBuffer().append(m.message).append(" ").append(val).toString());
    }

    public FormulaException(FormulaMessage m, String val) {
        super(new StringBuffer().append(m.message).append(" ").append(val).toString());
    }
}
