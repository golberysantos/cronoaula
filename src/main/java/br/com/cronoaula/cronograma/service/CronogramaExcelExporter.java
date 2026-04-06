package br.com.cronoaula.cronograma.service;

import br.com.cronoaula.cronograma.model.Cronograma;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.List;

public class CronogramaExcelExporter {

    public static void exportar(List<Cronograma> aulas, String caminho) {

        try (Workbook wb = new XSSFWorkbook()) {

            Sheet sheet = wb.createSheet("Cronograma");

            // ===== ESTILO CABEÇALHO =====
            CellStyle headerStyle = wb.createCellStyle();
            Font headerFont = wb.createFont();
            headerFont.setBold(true);
            headerStyle.setFont(headerFont);

            // ===== CABEÇALHO =====
            Row header = sheet.createRow(0);

            Cell c1 = header.createCell(0);
            c1.setCellValue("Data");
            c1.setCellStyle(headerStyle);

            Cell c2 = header.createCell(1);
            c2.setCellValue("Dia da Semana");
            c2.setCellStyle(headerStyle);

            // ===== ESTILO DATA =====
            CellStyle dateStyle = wb.createCellStyle();
            CreationHelper createHelper = wb.getCreationHelper();
            dateStyle.setDataFormat(
                    createHelper.createDataFormat().getFormat("dd/MM/yyyy")
            );

            // ===== DADOS =====
            int rowNum = 1;

            for (Cronograma aula : aulas) {

                Row row = sheet.createRow(rowNum++);

                // Data
                Cell cellData = row.createCell(0);
                LocalDate data = aula.getData();

                cellData.setCellValue(java.sql.Date.valueOf(data));
                cellData.setCellStyle(dateStyle);

                // Dia da semana
                row.createCell(1).setCellValue(data.getDayOfWeek().toString());
            }

            // ===== AJUSTAR COLUNAS =====
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);

            // ===== SALVAR =====
            try (FileOutputStream fos = new FileOutputStream(caminho)) {
                wb.write(fos);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}