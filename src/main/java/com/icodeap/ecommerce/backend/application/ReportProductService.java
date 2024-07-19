package com.icodeap.ecommerce.backend.application;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.icodeap.ecommerce.backend.domain.model.Category;
import com.icodeap.ecommerce.backend.domain.model.FileReport;
import com.icodeap.ecommerce.backend.domain.model.Product;
import com.icodeap.ecommerce.backend.domain.port.ICategoryRepository;
import com.icodeap.ecommerce.backend.domain.port.IProductRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReportProductService {

	private final IProductRepository iProductRepository;
	private final ICategoryRepository icategoryRepository;

	public ReportProductService(IProductRepository iProductRepository, ICategoryRepository icategoryRepository) {
		this.iProductRepository = iProductRepository;
		this.icategoryRepository = icategoryRepository;
	}

	public FileReport exportProductsToExcel() throws IOException {
		try (XSSFWorkbook workbook = new XSSFWorkbook()) {
			XSSFSheet sheet = workbook.createSheet("productos");
			createHeadReport(workbook, sheet);
			createBodyReport(sheet);
			return buildFileReport(workbook);
		}
	}

	private void createHeadReport(XSSFWorkbook workbook, XSSFSheet sheet) {
		XSSFCellStyle yellowCellStyle = workbook.createCellStyle();
		yellowCellStyle.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
		yellowCellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		// Crear encabezados
		XSSFRow headerRow = sheet.createRow(0);
		XSSFCell cell;
		cell = headerRow.createCell(0);
		cell.setCellValue("ID");
		cell.setCellStyle(yellowCellStyle);
		cell = headerRow.createCell(1);
		cell.setCellValue("Nombre");
		cell.setCellStyle(yellowCellStyle);

		cell = headerRow.createCell(2);
		cell.setCellValue("Descripción");
		cell.setCellStyle(yellowCellStyle);

		cell = headerRow.createCell(3);
		cell.setCellValue("Precio");
		cell.setCellStyle(yellowCellStyle);

		cell = headerRow.createCell(4);
		cell.setCellValue("Categoria");
		cell.setCellStyle(yellowCellStyle);

		cell = headerRow.createCell(5);
		cell.setCellValue("Imagen");
		cell.setCellStyle(yellowCellStyle);

		cell = headerRow.createCell(6);
		cell.setCellValue("Code");
		cell.setCellStyle(yellowCellStyle);

		cell = headerRow.createCell(7);
		cell.setCellValue("Fecha creación");
		cell.setCellStyle(yellowCellStyle);
	}

	private void createBodyReport(XSSFSheet sheet) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		List<Product> products = (List<Product>) iProductRepository.findAll();
		List<Category> categories = (List<Category>) icategoryRepository.findAll();
		int rowIndex = 1;
		for (Product product : products) {
			XSSFRow row = sheet.createRow(rowIndex++);
			row.createCell(0).setCellValue(product.getId());
			row.createCell(1).setCellValue(product.getName());
			row.createCell(2).setCellValue(product.getDescription());
			row.createCell(3).setCellValue(product.getPrice().toString());
			row.createCell(4).setCellValue(categories.stream().filter(c -> c.getId() == product.getCategory().getId())
					.findFirst().orElse(new Category(0, "Sin categoría", null, null, null)).getName());
			row.createCell(5).setCellValue(product.getUrlImage());
			row.createCell(6).setCellValue(product.getCode());
			row.createCell(7)
					.setCellValue(product.getDateCreated() == null ? "" : product.getDateCreated().format(formatter));

		}
	}

	private FileReport buildFileReport(XSSFWorkbook workbook) {
		FileReport file = new FileReport();
		try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			workbook.write(out);
			byte[] excelBytes = out.toByteArray();
			file.setName("productos.xlsx");
			file.setContent(Base64.getEncoder().encodeToString(excelBytes));
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return file;
	}

}
