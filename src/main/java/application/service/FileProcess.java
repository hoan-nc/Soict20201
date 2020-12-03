package application.service;

import application.data.entity.Person;
import application.data.repository.IPersonRepository;
import application.data.service.PersonService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static application.constant.ReadFile.*;

@Service
public class FileProcess {
    Logger log = LogManager.getLogger(FileStorageService.class);


    @Autowired
    IPersonRepository iPersonRepository;

    @Autowired
    PersonService personService;

    @Value("${spring.folder_upload_files:}")
    private Path rootLocation;

    public List<Person> readFile(String fileName) throws IOException {
        List<Person> lstPerson = new ArrayList<Person>();
//        Person person = new Person();
//        person.setPersonName("2");
//        person.setBloodAnalysis("2");
//        person.setBloodCreatinine("3");
//        person.setBloodCreatinine("4");
//        personService.save(person);

        String excelFilePath = rootLocation + "/" + fileName;
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
            if (nextRow.getRowNum() < 2 || nextRow.getCell(COLUMN_INDEX_2) == null || nextRow.getCell(COLUMN_INDEX_2).toString().isEmpty()) {
                // Ignore header
                continue;
            }
            System.out.println(nextRow.getRowNum());
            //Create object
            Person person = new Person();
//            person.setAdvisory("1");
//            person.setBloodAnalysis("2");
//            iPersonRepository.save(person);
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
                    case COLUMN_INDEX_0:
//                        person.setPersonId(Integer.parseInt(getCellValue(cell).toString()));
                        break;
                    case COLUMN_INDEX_1:

                        break;
                    case COLUMN_INDEX_2:
//                        person.setPersonName(getCellValue(cell).toString());
                        person.setPersonName("Hoan");
                        break;
                    case COLUMN_INDEX_3:
//                        person.setPersonName(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_4:
//                        person.setPersonName(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_5:
                        person.setHeight(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_6:
                        person.setWeight(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_7:
                        person.setBloodPressure(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_8:
                        person.setEyes(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_9:
                        person.setInsideMedical(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_10:
                        person.setOutsideMedical(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_11:
                        person.setEarNoseThroat(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_12:
                        person.setDentomaxillofacial(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_13:
                        person.setDermatology(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_14:
                        person.setNerve(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_15:
                        person.setBloodAnalysis(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_16:
                        person.setWhiteBloodNumber(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_17:
                        person.setRedBloodNumber(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_18:
                        person.setHemoglobin(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_19:
                        person.setPlateletNumber(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_20:
                        person.setBloodUrea(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_21:
                        person.setBloodCreatinine(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_22:
                        person.setHepatitisB(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_23:
                        person.setHealthType(getCellValue(cell).toString());
                        break;
                    case COLUMN_INDEX_24:
                        person.setAdvisory(getCellValue(cell).toString());
                        break;
//                    case COLUMN_INDEX_25:
//                        System.out.println(getCellValue(cell));
//                        break;
                    default:
                        break;
                }
            }

            iPersonRepository.save(person);
            lstPerson.add(person);
        }

        workbook.close();
        inputStream.close();

        return lstPerson;
    }

    // Get Workbook
    private static Workbook getWorkbook(InputStream inputStream, String excelFilePath) throws IOException {
        Workbook workbook = null;
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
            case _NONE:
            case BLANK:
            case ERROR:
                break;
            default:
                break;
        }

        return cellValue;
    }
}
