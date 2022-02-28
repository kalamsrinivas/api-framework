package utils;

import cases.ATFBaseTest;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class ATFUtils {

    public static String ReadAndParseFile(String filePath, Map<String, String> inputDataMap) {
        String fileContent = readFileContent(filePath);
        fileContent = findAndReplace(fileContent, inputDataMap);
        ATFBaseTest.ReportInfo(fileContent);
        return fileContent;
    }

    public static String findAndReplace(String fileContent, Map<String, String> inputDataMap) {
        String[] variables = fileContent.split("\\{\\{");
        int i = 1;
        while (i < variables.length) {
            variables[i] = variables[i].substring(0, variables[i].indexOf("}}"));
            String value = findSystemValue(inputDataMap, variables[i]);
            fileContent = fileContent.replaceAll("\\{\\{" + variables[i] + "}}", value);
            i++;
        }
        return fileContent;
    }

    public static String readFileContent(String filePath) {
        String line, fileContent = "";
        FileReader fileReader;
        try {
            fileReader = new FileReader(filePath);

            BufferedReader bufferedReader =
                    new BufferedReader(fileReader);
            while ((line = bufferedReader.readLine()) != null) {
                fileContent += line;
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileContent;
    }

    public static String findSystemValue(Map<String, String> inputDataMap, String key) {
        String returnStr = "{{" + key + "}}";
        if (inputDataMap.get(key) != null) {
            returnStr = inputDataMap.get(key);
        }
        return returnStr;
    }
}