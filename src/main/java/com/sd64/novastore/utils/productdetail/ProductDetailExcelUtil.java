package com.sd64.novastore.utils.productdetail;

import com.sd64.novastore.model.Product;
import com.sd64.novastore.model.ProductDetail;
import com.sd64.novastore.model.Size;
import com.sd64.novastore.repository.ColorRepository;
import com.sd64.novastore.repository.ProductDetailRepository;
import com.sd64.novastore.repository.SizeRepository;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

@Component
public class ProductDetailExcelUtil {

    @Autowired
    private ColorRepository colorRepository;

    @Autowired
    private SizeRepository sizeRepository;

    @Autowired
    private ProductDetailRepository productDetailRepository;

    public Boolean isValidExcel(MultipartFile file) {
        return Objects.equals(file.getContentType(), "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    public String getProductDetailFromExcel(String path, Integer productId) throws IOException {
        List<ProductDetail> listProductDetail = new ArrayList<>();
        FileInputStream fileInputStream = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
        XSSFSheet xssfSheet = workbook.getSheetAt(0);

        int rowIndex = 0;
        try {
            for (Row row : xssfSheet) {
                if (rowIndex == 0) {
                    rowIndex++;
                    continue;
                }
                Iterator<Cell> cellIterator = row.iterator();
                int cellIndex = 0;
                ProductDetail productDetail = new ProductDetail();
                while (cellIterator.hasNext()) {
                    Cell cell = cellIterator.next();
                    switch (cellIndex) {
//                        case 0:
//                            Optional<ProductDetail> optionalProductDetail = productDetailRepository.findAllByCode(cell.getStringCellValue());
//                            if (optionalProductDetail.isPresent()) {
//                                workbook.close();
//                                fileInputStream.close();
//                                File file = new File(path);
//                                file.delete();
//                                return "Trùng mã";
//                            }
//                            productDetail.setCode(cell.getStringCellValue());
//                            break;
                        case 0:
                            productDetail.setQuantity((int) cell.getNumericCellValue());
                            break;
                        case 1:
                            productDetail.setPrice(BigDecimal.valueOf(cell.getNumericCellValue()));
                            break;
                        case 2:
                            if (cell.getCellType() == CellType.NUMERIC) {
                                String sizeStr = String.valueOf(new Double(cell.getNumericCellValue()).intValue());
                                Size size = sizeRepository.findByName(sizeStr);
                                productDetail.setSize(size);
                            } else {
                                String sizeStr = cell.getStringCellValue();
                                Size size = sizeRepository.findByName(sizeStr);
                                productDetail.setSize(size);
                            }
                            break;
                        case 3:
                            productDetail.setColor(colorRepository.findByName(cell.getStringCellValue()));
                            break;
                        default:
                            break;
//                        case 3 -> price = BigDecimal.valueOf(cell.getNumericCellValue());
//                        case 4 -> brand = brandRepository.findByName(cell.getStringCellValue());
//                        case 5 -> material = materialRepository.findByName(cell.getStringCellValue());
//                        case 6 -> category = categoryRepository.findByName(cell.getStringCellValue());
//                        case 7 -> form = formRepository.findByName(cell.getStringCellValue());
//                        default -> {
//                        }
                    }
                    cellIndex++;
                }
                productDetail.setProduct(Product.builder().id(productId).build());
                productDetail.setStatus(1);
                productDetail.setCreateDate(new Date());
                productDetail.setUpdateDate(new Date());
                productDetailRepository.save(productDetail);
                productDetail.setCode("CT"+productDetail.getId());
                productDetailRepository.save(productDetail);
                listProductDetail.add(productDetail);
            }
        } catch (Exception e) {
            workbook.close();
            fileInputStream.close();
            File file = new File(path);
            file.delete();
            return "Sai dữ liệu";
        }
        workbook.close();
        fileInputStream.close();
        File file = new File(path);
        file.delete();
        productDetailRepository.saveAll(listProductDetail);
        return "okela";
    }
}
