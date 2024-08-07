package test.group.counters.CustomExceptions;

public class ExcelFileNotFoundException extends NotFoundException {
    public ExcelFileNotFoundException() { super("excel file not found exception"); }
    public ExcelFileNotFoundException(String message) { super(message); }
}
