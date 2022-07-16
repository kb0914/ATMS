package com.ykb.ATMS.helper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ykb.ATMS.entity.Team;
import com.ykb.ATMS.entity.TeamStudent;

public class ExcelHelper {

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

	static String[] HEADERs = { "Team_ID", "Stundet_ID", "Mark" };

	static String SHEET = "Assignment_MarK";

	public static ByteArrayInputStream teamStudentToExcel(List<Team> team) {
		Workbook workbook = new XSSFWorkbook();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			Sheet sheet = workbook.createSheet(SHEET);
			// Header
			Row headerRow = sheet.createRow(0);
			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}
			int rowIdx = 1;
			for (Team i : team) {
				for (TeamStudent ts : i.getStudents()) {
					Row row = sheet.createRow(rowIdx++);
					row.createCell(0).setCellValue(ts.getTeam().getId());
					row.createCell(1).setCellValue(ts.getStudent().getUsername());
					row.createCell(2).setCellValue(ts.getMark());
				}
			}
			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}
}
