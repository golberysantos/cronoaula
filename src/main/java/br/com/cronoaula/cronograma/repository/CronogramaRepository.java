package br.com.cronoaula.cronograma.repository;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
	
public class CronogramaRepository {

	public Set<LocalDate> lerDatasNaoLetivas(String caminho) {

		Set<LocalDate> datas = new HashSet<>();

		try (FileInputStream fis = new FileInputStream(caminho); Workbook workbook = new XSSFWorkbook(fis)) {

			Sheet sheet = workbook.getSheetAt(0);

			for (Row row : sheet) {

				if (row.getRowNum() == 0)
					continue;

				Cell cell = row.getCell(0);

				if (cell != null && cell.getCellType() == CellType.NUMERIC) {

					LocalDate data = cell.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

					datas.add(data);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return datas;
	}
}