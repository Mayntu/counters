package test.group.counters.services;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;
import test.group.counters.dto.CounterJoinDataDTO;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

@Service
public class ExcelEditorService
{
    public static void writeToExcel (List<CounterJoinDataDTO> counterJoinDataModelList, String fileName) {
        try (Workbook workbook = new HSSFWorkbook(); OutputStream fileOut = new FileOutputStream(fileName)) {
            Sheet mainSheet = workbook.createSheet("counters");


            Row row = mainSheet.createRow(0);
            Cell cell = row.createCell(0);
            cell.setCellValue("Группа");
            cell = row.createCell(1);
            cell.setCellValue("Имя счётчи");
            cell = row.createCell(2);
            cell.setCellValue("МПОКАЗАНИЕ");
            cell = row.createCell(3);
            cell.setCellValue("МПОКАЗАНИЕ");
            cell = row.createCell(4);
            cell.setCellValue("РАСХОДНОЕП");
            cell = row.createCell(5);
            cell.setCellValue("ОБЩАЯСУММА");

            int index = 1;
            for (CounterJoinDataDTO counterJoinDataModel : counterJoinDataModelList)
            {
                Row newRow = mainSheet.createRow(index);
                Cell newCell = newRow.createCell(0);
                newCell.setCellValue(counterJoinDataModel.getGroupName());
                newCell = newRow.createCell(1);
                newCell.setCellValue(counterJoinDataModel.getCounterData().getName());
                newCell = newRow.createCell(2);
                newCell.setCellValue(counterJoinDataModel.getCounterData().getMin());
                newCell = newRow.createCell(3);
                newCell.setCellValue(counterJoinDataModel.getCounterData().getMax());
                newCell = newRow.createCell(4);
                newCell.setCellValue(counterJoinDataModel.getCounterData().getAvg());
                newCell = newRow.createCell(5);
                newCell.setCellValue(counterJoinDataModel.getSum());
                index++;
            }
            workbook.write(fileOut);
        } catch (Exception e) {
            throw new ServerErrorException("internal server error", e);
        }
    }
}
