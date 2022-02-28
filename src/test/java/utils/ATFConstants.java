package utils;

public class ATFConstants {
    /**
     * Project capabilities
     */
    public static final String PROJECT_NAME = "ATFServices";
    public static final String PROJECT_RELEASE = "-R2";
    public static final String AUTOMATION_OUTPUT_PATH = System.getProperty("user.home") + "/Desktop/Automation/" + PROJECT_NAME;
    public static final String AUTOMATION_RESOURCES_PATH = System.getProperty("user.dir") + "/src/test/resources/";
    public static final String AUTOMATION_PROPERTIES_PATH = AUTOMATION_RESOURCES_PATH + "app.properties";
    public static final String AUTOMATION_LOG4J_PATH = AUTOMATION_RESOURCES_PATH + "log4j.properties";
    public static final String EXCELFILE_PATH = AUTOMATION_RESOURCES_PATH + "data/excel/";
    public static final String JSONFILE_PATH = AUTOMATION_RESOURCES_PATH + "data/json/";
    public static final String ENVIRONMENT = "env";
    public static final String HOST_URL = "hostURL";
    public static final String SYSTEM = "system";
    public static final String AUTOMATION_EXCEL_OUTPUT = System.getProperty("user.dir") + "/Results/AutomationTestOutput.xls";
    public static final String OUTPUT_EXCEL_SHEET_NAME = "Output";
    /**
     * Project Data
     */
    public static final String GIVENPROJECT_NAME = "ATFServices, ADO, ApplePhoenix, Arbonne, Dinsey, ISAGENIX, ONEOPERATE, WBC";
}
