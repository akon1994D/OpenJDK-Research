package my.JCTreeTest;

import com.sun.tools.javac.comp.AttrContext;
import com.sun.tools.javac.comp.Env;
import com.sun.tools.javac.comp.Todo;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.main.JavaCompiler;
import com.sun.tools.javac.nio.JavacPathFileManager;
import com.sun.tools.javac.parser.*;
import com.sun.tools.javac.tree.JCTree;
import com.sun.tools.javac.util.Context;
import static org.junit.Assert.*;

import com.sun.tools.javac.util.List;
import com.sun.tools.javac.util.ListBuffer;
import com.sun.tools.javac.util.Pair;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import java.io.*;
import java.nio.file.FileStore;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Queue;

/**
 * @author: whp
 * @description:
 * @date: 2019-7-25
 */

public class JCTreeDemo1 {


    @Test
    public void javacPathFileManagerTest() throws Exception{
        Context context = new Context();

        JavacFileManager fileManager = new JavacFileManager(context, true, null);
        JavaFileObject javaFileObject = fileManager.getFileForInput("TestClass\\Test.java");

        CharSequence s = javaFileObject.getCharContent(false);
        ParserFactory parserFactory = ParserFactory.instance(context);
        Parser parser = parserFactory.newParser(s, false, false, true);
        JCTree.JCCompilationUnit roots = parser.parseCompilationUnit();

        roots.sourcefile = javaFileObject;  // ??????  package-info 和 类名是否和文件名相同判断
        JavaCompiler com = JavaCompiler.instance(context);
        com.enterTrees(List.of(roots));
        Todo todos = com.todo;
        while (!todos.isEmpty()){
            Env<AttrContext> todo = todos.remove();
            Env<AttrContext> env = com.attribute(todo);
//            JCTree tree = env.tree;
////            System.out.println(tree);
////
////            Queue<Env<AttrContext>> flow = com.flow(env);
////            Queue<Pair<Env<AttrContext>, JCTree.JCClassDecl>> desugar = com.desugar(flow);
////            com.generate(desugar);
        }
    }


    @Test
    public void complic() throws Exception{
        Context context = new Context();
        JavacFileManager.preRegister(context);
        String s = FileUtils.readFileToString(new File("TestClass\\Test.java"), "UTF-8");
        ParserFactory parserFactory = ParserFactory.instance(context);
        Parser parser = parserFactory.newParser(s, false, false, true);
        JCTree.JCCompilationUnit roots = parser.parseCompilationUnit();
        System.out.println(roots.sourcefile);
        JavaCompiler com = JavaCompiler.instance(context);
//        com.enterTrees(List.of(roots));
//        com.compile();
    }

    @Test
    public void parseTest() throws IOException{
        Context context = new Context();
        JavacFileManager.preRegister(context);
        String s = FileUtils.readFileToString(new File("TestClass\\Test.java"), "UTF-8");
        ParserFactory parserFactory = ParserFactory.instance(context);
        Parser parser = parserFactory.newParser(s, false, false, true);
        JCTree.JCCompilationUnit jcCompilationUnit = parser.parseCompilationUnit();
        List<JCTree> defs = jcCompilationUnit.defs;
        for (JCTree def : defs) {
            System.out.println("======");
            System.out.println(def);
        }
    }

    @Test
    public void TokensTest() throws IOException {
        Context context = new Context();
        ScannerFactory factory = ScannerFactory.instance(context);
        String s = FileUtils.readFileToString(new File("TestClass\\Test.java"), "UTF-8");
        Scanner scanner = factory.newScanner(s, false);
        do{
            scanner.nextToken();
            Tokens.Token token = scanner.token();
            System.out.print("kind="+token.kind+"   ");
            System.out.print("tag="+token.kind.tag+"  ");
            System.out.println("class="+token.getClass().getSimpleName());
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
