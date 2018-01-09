/**
 * 
 */
package com.jmu.bibasedmanage.excel;

import jxl.Sheet;
import jxl.Workbook;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class ExcelImportContext {

	/**
	 * 本次导入的批次ID，如果导入过程是异步进行，可以通过批次id查询到对应的导入进度等信息
	 */
	private String batchId;

	private Workbook workbook;

	private Sheet sheet;

	/**
	 * 总行数，包括首行
	 */
	private int totalRows;

	/**
	 * 总列数
	 */
	private int totalColumns;

	/**
	 * 临时错误信息文件路径
	 */
	private String excelOutputFileName;

	/**
	 * 是否有错误存在
	 */
	private Boolean isError = false;


	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public Workbook getWorkbook() {
		return workbook;
	}

	public void setWorkbook(Workbook workbook) {
		this.workbook = workbook;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public int getTotalColumns() {
		return totalColumns;
	}

	public void setTotalColumns(int totalColumns) {
		this.totalColumns = totalColumns;
	}

	public Sheet getSheet() {
		return sheet;
	}

	public void setSheet(Sheet sheet) {
		this.sheet = sheet;
	}

	public String getExcelOutputFileName() {
		return excelOutputFileName;
	}

	public void setExcelOutputFileName(String excelOutputFileName) {
		this.excelOutputFileName = excelOutputFileName;
	}

	public Boolean getError() {
		return isError;
	}

	public void setError(Boolean error) {
		isError = error;
	}
}
