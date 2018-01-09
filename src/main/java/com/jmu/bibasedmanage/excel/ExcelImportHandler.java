/**
 * 
 */
package com.jmu.bibasedmanage.excel;

public interface ExcelImportHandler {

	/**
	 * 导入一行数据
	 * 
	 * @param rowIndex
	 * @param rowData
	 * @param context
	 * @return 返回错误信息
	 */
	String importRow(int rowIndex, RowData rowData, ExcelImportContext context);
}
