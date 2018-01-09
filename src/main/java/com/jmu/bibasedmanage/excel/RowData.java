package com.jmu.bibasedmanage.excel;

import jxl.Sheet;

import java.util.Map;

public class RowData {

	private Sheet sheet;

	private int rowIndex;

	private Map<String, Integer> columnNameIndexs;

	public String getContentByColumnName(String columnName) {
		Integer columnIndex = columnNameIndexs.get(columnName);
		if (columnIndex == null)
			return null;
		return sheet.getCell(columnIndex, rowIndex).getContents();
	}

	public String getContentByColunmIndex(int columnIndex) {
		return sheet.getCell(columnIndex, rowIndex).getContents();
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Map<String, Integer> getColumnNameIndexs() {
		return columnNameIndexs;
	}

	public void setColumnNameIndexs(Map<String, Integer> columnNameIndexs) {
		this.columnNameIndexs = columnNameIndexs;
	}
}
