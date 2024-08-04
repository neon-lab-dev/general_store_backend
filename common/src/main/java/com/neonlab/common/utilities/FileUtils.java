package com.neonlab.common.utilities;

import com.neonlab.common.expectations.InvalidInputException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileUtils {


    public static String getFileContent(String path) throws InvalidInputException {
        try (var bufferedReader = new BufferedReader(new FileReader(path))) {
            String line;
            var retVal = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null){
                retVal.append(line).append("\n");
            }
            return retVal.toString();
        } catch (IOException e) {
            throw new InvalidInputException(e.getMessage());
        }
    }


}
