package my.test;

//在VM arguments中加上-Xbootclasspath/p:target\classes
//这样优先使用javac1.8这个项目中自带的javax包，否则会出现如下错误:
/////////////////////////////////////////////////////////////////
//编译器 ((版本信息不可用)) 中出现异常错误。 如果在 Bug Parade 中没有找到该错误, 
//请在 Java Developer Connection (http://java.sun.com/webapps/bugreport) 中建立 Bug。请在报告中附上您的程序和以下诊断信息。谢谢。
//java.lang.NoSuchFieldError: NATIVE_HEADER_OUTPUT
//
public class JavacStarter {
    public static void main(String[] args) throws Exception {
        //在args.txt中加入要编译的Java文件
        com.sun.tools.javac.Main.main(new String[] { "TestClass\\Test.java" });
    }
}
