package cases;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.*;

import java.lang.reflect.Method;
import java.util.Map;


public class RegressionTest extends ATFBaseTest {
    private String postAddUpdate, endPoint;

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod(Method method) {
        endPoint = HOST_URL + getProperty("requestURL");
        intSuite(method);
    }

    @DataProvider()
    public Object[] addOrUpdateData() {
        return ATFExcelManager.GetEnabledExcelTests(EXCEL_FILE_PATH + "addUpdateItem.xlsx", "TestData");
    }

    @ATFTestDetails(user = "ABHI", date = "16/03/2021", displayName = "Login Into Application")
    @Test(groups = {REGG}, dataProvider = "addOrUpdateData")
    public void test(Map<String, String> inputData) {
        startTest(inputData.get("S NO"));
        String method = inputData.get("Method");
        String body = inputData.get("Json_Inputfile_Path");
        //postAddUpdate = ATFUtils.ReadAndParseFile(JSON_FILE_PATH + "AddUpdateRequest.json", inputData);
        RequestBuilder requestBuilder = new RequestBuilder(method,"http://restapi.demoqa.com/customer/register","",body);
        RestResponse res = RestResponse.getRestResponse(requestBuilder);
        ReportInfo(String.valueOf(res.getStatusCode()));
        AssertEquals((String.valueOf(res.getStatusCode())), "200", "Pass");
    }
}