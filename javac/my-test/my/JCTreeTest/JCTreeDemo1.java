package my.JCTreeTest;

import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.parser.Scanner;
import com.sun.tools.javac.parser.ScannerFactory;
import com.sun.tools.javac.parser.Tokens;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;
import static org.junit.Assert.*;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.*;
import java.nio.file.Files;

/**
 * @author: whp
 * @description:
 * @date: 2019-7-25
 */

public class JCTreeDemo1 {

    @Test
    public void TokensTest() throws IOException {
        Context context = new Context();
        ScannerFactory factory = ScannerFactory.instance(context);
        String s = FileUtils.readFileToString(new File("TestClass\\Test.java"), "UTF-8");
        Scanner scanner = factory.newScanner(s, false);
        do{
            scanner.nextToken();
            Tokens.Token token = scanner.token();
            System.out.print(token.kind+"   ");
            System.out.println(token.kind.tag);
        }while (scanner.token().kind != Tokens.TokenKind.EOF);

    }




    @Test
    public void javaCompilerParse(){
        Context context = new Context();
        JavacFileManager.preRegister(context);
        JavacFileManager javaFileManager = (JavacFileManager)context.get(JavaFileManager.class);
        JavaFileObject javaFileObject = javaFileManager.getFileForInput("TestClass\\Test.java");
        JavaCompiler com = JavaCompiler.instance(context);
        JCTree.JCCompilationUnit parse = com.parse(javaFileObject);
        System.out.println(parse.pid);  // 报名
        System.out.println(parse.defs.size());
    }



}
