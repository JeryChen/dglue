package com.ars.dglue.admin.utils;

import javax.tools.*;
import java.util.*;

/**
 * 〈一句话介绍功能〉<br>
 * java 语法解析器
 *
 * @author jierui on 2019-10-24.
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class JavaSyntaxChecker {

    public static void main(String[] args) {
        System.out.println(JavaSyntaxChecker.check("/Users/jierui/Downloads/GlueInfoController.java"));
    }

    public static List<String> check(String file) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();

        StandardJavaFileManager fileManager = compiler.getStandardFileManager(null, null, null);

        Iterable<? extends JavaFileObject> compilationUnits =
                fileManager.getJavaFileObjectsFromStrings(Arrays.asList(file));

        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        compiler.getTask(null, fileManager, diagnostics, null, null, compilationUnits).call();

        List<String> messages = new ArrayList<String>();
        for (Diagnostic diagnostic : diagnostics.getDiagnostics()) {
            messages.add(diagnostic.getKind() + ":\t Line [" + diagnostic.getLineNumber() + "] \t Position ["
                    + diagnostic.getPosition() + "]\t" + diagnostic.getMessage(Locale.ROOT) + "\n");
        }

        return messages;
    }
}


