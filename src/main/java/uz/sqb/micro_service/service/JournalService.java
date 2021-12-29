package uz.sqb.micro_service.service;

import com.google.gson.Gson;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import uz.sqb.micro_service.model.ApiResponse;
import uz.sqb.micro_service.model.JournalByStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class JournalService {



    public List<JournalByStatus> getExcelReportByDate(String object) throws IOException {
        JournalByStatus[] array = new Gson().fromJson(object, JournalByStatus[].class);
        List<JournalByStatus> list = List.of(array);
        Date date = new Date();
        String path = "C:/home/telegram_report_excel/";
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        File excel = new File(path + "journal_backup_" + (date.getMonth()) + ".xlsx");
        FileOutputStream fileOutputStream = new FileOutputStream(excel);
        XSSFWorkbook workbook = new XSSFWorkbook();

        XSSFSheet sheet = workbook.createSheet("journal_backup_month_" + (date.getMonth() + 1));
        XSSFRow row = sheet.createRow(0);

        row.createCell(0).setCellValue("journal_id");
        row.createCell(1).setCellValue("buyer_id");
        row.createCell(2).setCellValue("buyer_name");
        row.createCell(3).setCellValue("buyer_phone");
        row.createCell(4).setCellValue("status");
        row.createCell(5).setCellValue("quantity_of_product");
        row.createCell(6).setCellValue("total_price");
        row.createCell(7).setCellValue("register_date");

        int j = 0;
        for (JournalByStatus journal : list) {

            XSSFRow other_rows = sheet.createRow(j + 1);
            other_rows.createCell(0).setCellValue(journal.getId());
            other_rows.createCell(1).setCellValue(journal.getBuyer_id());
            other_rows.createCell(2).setCellValue(journal.getFull_name());
            other_rows.createCell(3).setCellValue(journal.getPhone());
            other_rows.createCell(4).setCellValue(journal.getStatus());
            other_rows.createCell(5).setCellValue(journal.getQuantity());
            other_rows.createCell(6).setCellValue(journal.getTotal_price());
            other_rows.createCell(7).setCellValue(journal.getRegister_date());
            j++;
        }
        workbook.write(fileOutputStream);
        workbook.close();
        log.info("|BEKTEXNO| Жунралы для телеграм были экспортированы в excel за прошлый месяц " + date.getMonth() + " | " + date);
        return list;
    }
}
