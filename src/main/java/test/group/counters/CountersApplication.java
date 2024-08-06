package test.group.counters;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.FileOutputStream;
import java.io.OutputStream;

@SpringBootApplication
public class CountersApplication {

	public static void main(String[] args)
	{
//		Workbook workbook = new HSSFWorkbook();
//		try(OutputStream fileOut = new FileOutputStream("test.xls"))
//		{
//			Sheet firstSheet = workbook.createSheet("firstSheet");
//			Row row = firstSheet.createRow(0);
//			Cell cell  = row.createCell(0);
//			cell.setCellValue("test");
//			workbook.write(fileOut);
//		}
//		catch (Exception exception)
//		{
//			exception.printStackTrace();
//		}
		SpringApplication.run(CountersApplication.class, args);
	}

}
