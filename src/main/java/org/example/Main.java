package org.example;

import org.mule.weave.v2.runtime.DataWeaveResult;
import org.mule.weave.v2.runtime.DataWeaveScript;
import org.mule.weave.v2.runtime.DataWeaveScriptingEngine;
import org.mule.weave.v2.runtime.ScriptingBindings;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    final static DataWeaveScriptingEngine dataWeaveScriptingEngine = new DataWeaveScriptingEngine();
    private static String readFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        return Files.readString(path);
    }

    public static void main(String[] args) {
        transform("src/resources/sample1.dwl",
                "src/resources/input1.json");
    }

    public static void transform(String dwFilePath, String dataModelPath) {
        try {
            String script = readFile(dwFilePath);
            String inputModel = readFile(dataModelPath);
            DataWeaveScript compiledExpression = dataWeaveScriptingEngine.compile(script);
            ScriptingBindings scriptingBindings = new ScriptingBindings();
            scriptingBindings.addBinding("payload", inputModel);
            DataWeaveResult result = compiledExpression.write(scriptingBindings);
            Object content = result.getContent();
            System.out.println(content);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}