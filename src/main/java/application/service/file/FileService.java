package application.service.file;

import application.entity.ExaminationEntity;
import application.entity.FileUpload;
import application.entity.UserEntity;
import application.repository.IExaminationRepository;
import application.repository.IUserRepository;
import application.service.UserService;
import application.utils.Const;
import application.entity.PhysicalExamEntity;
import application.repository.IPhysicalExamRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;


@Service
public class FileService {
    private final IPhysicalExamRepository iPhysicalExamRepository;
    private final UserService userService;

    @Value("${spring.folder_upload_files:}")
    private Path rootLocation;

    public FileService(IPhysicalExamRepository iPhysicalExamRepository, UserService userService) {
        this.iPhysicalExamRepository = iPhysicalExamRepository;
        this.userService = userService;
    }

    public List<PhysicalExamEntity> readFile(FileUpload fileUpload, UserEntity userEntity) throws IOException {
        List<PhysicalExamEntity> lstPersonEntities = new ArrayList<>();
        String excelFilePath = rootLocation + "/" + fileUpload.getExcelName();
        File file = new File(excelFilePath);
        // Get file
        InputStream inputStream = new FileInputStream(file);

        // Get workbook
        Workbook workbook = getWorkbook(inputStream, excelFilePath);

        // Get sheet
        Sheet sheet = workbook.getSheetAt(0);

        // Get all rows
        Iterator<Row> iterator = sheet.iterator();
        while (iterator.hasNext()) {
            Row nextRow = iterator.next();
            if (nextRow.getRowNum() < 2 || nextRow.getCell(Const.ReadFile.COLUMN_INDEX_2) == null || nextRow.getCell(Const.ReadFile.COLUMN_INDEX_2).toString().isEmpty()) {
                // Ignore header
                continue;
            }
            System.out.println(nextRow.getRowNum());
            //Create object
            PhysicalExamEntity physicalExamEntity = new PhysicalExamEntity();
            Date createTime = new Date();
            physicalExamEntity.setCreatedDate(createTime);
            physicalExamEntity.setIsActive(true);
            physicalExamEntity.setYear(fileUpload.getExamination().getYear());
            physicalExamEntity.setExamination(fileUpload.getExamination());
            physicalExamEntity.setDepartmentExam(fileUpload.getDepartment());
            physicalExamEntity.setUser(userEntity);
            // Get all cells
            Iterator<Cell> cellIterator = nextRow.cellIterator();
            // Read cells and set value for book object
            while (cellIterator.hasNext()) {
                //Read cell
                Cell cell = cellIterator.next();
                Object cellValue = getCellValue(cell);
                if (cellValue == null || cellValue.toString().isEmpty()) {
                    continue;
                }
                // Set value for book object
                int columnIndex = cell.getColumnIndex();
                switch (columnIndex) {
                    case Const.ReadFile.COLUMN_INDEX_0:
//                        person.setPersonId(Integer.parseInt(getCellValue(cell).toString()));
                        break;
                    case Const.ReadFile.COLUMN_INDEX_1:

                        break;
                    case Const.ReadFile.COLUMN_INDEX_2:
//                        person.setPersonName(getCellValue(cell).toString());
                        physicalExamEntity.setNameUser("Hoan");
                        break;
                    case Const.ReadFile.COLUMN_INDEX_3:
//                        person.setPersonName(getCellValue(cell).toString());
                        break;
                    case Const.ReadFile.COLUMN_INDEX_4:
//                        person.setPersonName(getCellValue(cell).toString());
                        break;
                    case Const.ReadFile.COLUMN_INDEX_5:
                        physicalExamEntity.setHeight((long) Double.parseDouble(getCellValue(cell).toString()));
                        break;
                    case Const.ReadFile.COLUMN_INDEX_6:
                        physicalExamEntity.setWeight((long) Double.parseDouble(getCellValue(cell).toString()));
                        break;
                    case Const.ReadFile.COLUMN_INDEX_7:
                        physicalExamEntity.setBloodPressure(getCellValue(cell).toString());
                        break;
                    case Const.ReadFile.COLUMN_INDEX_8:
                        physicalExamEntity.setEyes(getCellValue(cell).toString());
                        break;
                    case Const.ReadFile.COLUMN_INDEX_9:
                        physicalExamEntity.setInsideMedical(getCellValue(cell).toString());
                        break;
                    case Const.ReadFile.COLUMN_INDEX_10:
                        physicalExamEntity.setOutsideMedical(getCellValue(cell).toString());
                        break;
                    case Const.ReadFile.COLUMN_INDEX_11:
                        physicalExamEntity.setEarNoseThroat(getCellValue(cell).toString());
                        break;
                    case Const.ReadFile.COLUMN_INDEX_12:
                        physicalExamEntity.setDentomaxillofacial(getCellValue(cell).toString());
                        break;
                    case Const.ReadFile.COLUMN_INDEX_13:
                        physicalExamEntity.setDermatology(getCellValue(cell).toString());
                        break;
                    case Const.ReadFile.COLUMN_INDEX_14:
                        physicalExamEntity.setNerve(getCellValue(cell).toString());
                        break;
                    case Const.ReadFile.COLUMN_INDEX_15:
                        physicalExamEntity.setBloodAnalysis(getCellValue(cell).toString());
                        break;
                    case Const.ReadFile.COLUMN_INDEX_16:
                        physicalExamEntity.setWhiteBloodNumber(Double.valueOf(getCellValue(cell).toString()));
                        break;
                    case Const.ReadFile.COLUMN_INDEX_17:
                        physicalExamEntity.setRedBloodNumber(Double.valueOf(getCellValue(cell).toString()));
                        break;
                    case Const.ReadFile.COLUMN_INDEX_18:
                        physicalExamEntity.setHemoglobin(Double.valueOf(getCellValue(cell).toString()));
                        break;
                    case Const.ReadFile.COLUMN_INDEX_19:
                        physicalExamEntity.setPlateletNumber((long) Double.parseDouble(getCellValue(cell).toString()));
                        break;
                    case Const.ReadFile.COLUMN_INDEX_20:
                        physicalExamEntity.setBloodUrea((long) Double.parseDouble(getCellValue(cell).toString()));
                        break;
                    case Const.ReadFile.COLUMN_INDEX_21:
                        physicalExamEntity.setBloodCreatinine((long) Double.parseDouble(getCellValue(cell).toString()));
                        break;
                    case Const.ReadFile.COLUMN_INDEX_22:
                        physicalExamEntity.setHepatitisB(getCellValue(cell).toString());
                        break;
                    case Const.ReadFile.COLUMN_INDEX_23:
                        physicalExamEntity.setHealthType((long) Double.parseDouble(getCellValue(cell).toString()));
                        break;
                    case Const.ReadFile.COLUMN_INDEX_24:
                        physicalExamEntity.setAdvisory(getCellValue(cell).toString());
                        break;
                    default:
                        break;
                }
            }

            iPhysicalExamRepository.save(physicalExamEntity);
            lstPersonEntities.add(physicalExamEntity);
        }

        workbook.close();
        inputStream.close();

        return lstPersonEntities;
    }

    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook;
        if (excelFilePath.endsWith("xlsx")) {
            workbook = new XSSFWorkbook(inputStream);
        } else if (excelFilePath.endsWith("xls")) {
            workbook = new HSSFWorkbook(inputStream);
        } else {
            throw new IllegalArgumentException("The specified file is not Excel file");
        }

        return workbook;
    }

    // Get cell value
    private static Object getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        Object cellValue = null;
        switch (cellType) {
            case BOOLEAN:
                cellValue = cell.getBooleanCellValue();
                break;
            case FORMULA:
                Workbook workbook = cell.getSheet().getWorkbook();
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
                cellValue = evaluator.evaluate(cell).getNumberValue();
                break;
            case NUMERIC:
                cellValue = cell.getNumericCellValue();
                break;
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            default:
                break;
        }

        return cellValue;
    }
}
