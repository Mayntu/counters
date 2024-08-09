package test.group.counters.CustomExceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ExcelFileNotFoundException extends NotFoundException {
    public ExcelFileNotFoundException() { super("excel file not found exception"); }
    public ExcelFileNotFoundException(String message) { super(message); }
}