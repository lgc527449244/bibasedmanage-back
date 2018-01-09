/**
 * 
 */
package com.jmu.bibasedmanage.excel;


import com.jmu.bibasedmanage.consts.CommonConst;
import com.jmu.bibasedmanage.exception.BusinessException;
import com.jmu.bibasedmanage.util.FileUtils;
import com.jmu.bibasedmanage.util.UUIDUtils;
import jxl.Sheet;
import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.format.Colour;
import jxl.read.biff.BiffException;
import jxl.write.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class ExcelImportService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 *  对解析到的每一行调用
	 * {@link ExcelImportHandler#importRow(int, RowData, ExcelImportContext)}方法，
	 * 如果handler调用过程中有返回错误信息，会copy导入的excel内容并在行尾附上错误信息，生成导入结果excel文件并上传到云存储
	 * 
	 * @param excelPath
	 * @param handler
	 * @return 返回生成的导入结果excel的云存储路径，如果处理过程中没有错误信息，不会上传云存储，返回null
	 */
	public String importExcel(String excelPath, ExcelImportHandler handler) throws FileNotFoundException {
		File file = new File(excelPath);
		InputStream inputStream = new FileInputStream(file);
		ExcelImportContext context = generateContext(inputStream);
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss");
		String fileName = CommonConst.TEMP_FILE_UPLOAD_PATH + "导入结果-" + sdf.format(new Date()) + "-"
				+ excelPath.substring(excelPath.lastIndexOf("/") + 1);
		context.setExcelOutputFileName(fileName);
		importExcel(context, handler);
		if (context.getError()) {
			return fileName;
		} else {
			return null;
		}
	}

	/**
	 * 从excel文件输入流构造导入上下文
	 * 
	 * @param excelInputStream
	 * @return
	 */
	public ExcelImportContext generateContext(InputStream excelInputStream) {
		ExcelImportContext context = new ExcelImportContext();
		context.setBatchId(UUIDUtils.generator());
		Workbook workbook;
		try {
			workbook = Workbook.getWorkbook(excelInputStream);
			excelInputStream.close();
		} catch (IOException e) {
			throw new BusinessException("解析excel文件出错" + e);
		}catch (BiffException e){
			throw new BusinessException("解析excel文件出错" + e);
		}
		context.setWorkbook(workbook);
		Sheet sheet = workbook.getSheet(0);
		context.setSheet(sheet);
		context.setTotalColumns(sheet.getColumns());
		context.setTotalRows(sheet.getRows());
		return context;
	}

	// 通用的excel导入处理过程
	private void importExcel(ExcelImportContext context, ExcelImportHandler handler) {
		Map<String, Integer> columnNameIndexs = new HashMap();
		//空行数
		int rowNoDataCount = 0;
		//允许最大空行数
		int maxNoDataLimit = 40;
		for (int j = 0; j < context.getTotalColumns(); j++) {
			// 读取所有的列名
			String columnName = context.getSheet().getCell(j, 0).getContents();
			Integer oldIndex = columnNameIndexs.put(columnName.trim(), j);
			if (oldIndex != null)
				log.warn("读取导入的excel存在重复的列名：" + columnName);
		}
		WritableWorkbook writableWorkbook = null;
		WritableSheet writableSheet = null;
		int errMsgColumn = context.getTotalColumns();
		for (int i = 1; i < context.getTotalRows(); i++) { // 行
			//空列数
			int columnNoDataCount = 0;
			RowData rowData = new RowData();
			rowData.setColumnNameIndexs(columnNameIndexs);
			rowData.setRowIndex(i);
			rowData.setSheet(context.getSheet());
			String errMsg;
			try {
				//空行数达到允许最大值跳出循环
				if (rowNoDataCount == maxNoDataLimit){
					break;
				}
				for(int index:columnNameIndexs.values()){
					String con = context.getSheet().getCell(index,i).getContents();
					if (context.getSheet().getCell(index, i).getContents().equals("")){
						if (++columnNoDataCount == columnNameIndexs.size()){
							rowNoDataCount++;
						}
					}else{
						break;
					}
				}
				//该列的项都为空则continue
				if (columnNoDataCount == columnNameIndexs.size()){
					continue;
				}
				errMsg = handler.importRow(i, rowData, context);
			} catch (BusinessException e) {
				errMsg = e.getMessage();
			}
			if (StringUtils.isNotBlank(errMsg)) {
				context.setError(true);
				// 如果有错误信息，写入到要返回的workbook中
				try {
					if (writableWorkbook == null) {
						File tempFile = new File(context.getExcelOutputFileName());
						writableWorkbook = Workbook.createWorkbook(tempFile, context.getWorkbook(), new WorkbookSettings());
						writableSheet = writableWorkbook.getSheet(0);
						// 如果没有错误信息列，添加一列错误信息
						Integer errColumn = columnNameIndexs.get("错误信息");
						if (errColumn != null) {
							errMsgColumn = errColumn;
						} else {
							WritableCellFormat cellFormat = new WritableCellFormat();
							cellFormat.setBackground(Colour.YELLOW);
							writableSheet.addCell(new Label(errMsgColumn, 0, "错误信息", cellFormat));
							writableSheet.setColumnView(errMsgColumn, 60);
						}
					}
					writableSheet.addCell(new Label(errMsgColumn, i, errMsg));
				} catch (WriteException e) {
					throw new BusinessException(e);
				} catch (IOException e){
					throw new BusinessException(e);
				}
			}
		}
		if (writableWorkbook != null) {
			try {
				writableWorkbook.write();
				writableWorkbook.close();
			} catch (IOException e) {
				throw new BusinessException(e);
			} catch (WriteException e){
				throw new BusinessException(e);
			}
		}
	}

}
