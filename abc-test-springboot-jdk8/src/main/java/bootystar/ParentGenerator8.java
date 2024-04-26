package bootystar;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.config.rules.DateType;


/**
 * @author booty
 *
 */
public class ParentGenerator8 {

    private static String url ="jdbc:mysql://localhost:3306/test?useUnicode=true&characterEncoding=UTF-8";
    private static String username ="root";
    private static String password ="root";
    public static void main(String[] args) {
        String projectPath = System.getProperty("user.dir");
        io.github.bootystar.mybatisplus.generator.ParentGenerator generator = new io.github.bootystar.mybatisplus.generator.ParentGenerator(url, username, password);

        generator
                .globalConfigBuilder()
                .dateType(DateType.TIME_PACK)
//                .enableSwagger()
                .outputDir(projectPath+ "/abc-test-springboot-jdk8/src/main/java")
        ;
        generator
                .packageConfigBuilder()
                .parent("bootystar.test")
                .entity("entity.pojo")
                .xml("mapper")
//                .pathInfo(Collections.singletonMap(OutputFile.xml, projectPath + "/aa-test/src/main/resources/xml"))

        ;  // 指定输出目录
        generator
                .customConfigBuilder()
                .enableValidated(true)
                .orderColumn("age",true)
                .orderColumn("name", false)
                .orderColumn("id_card", true)
                .DTOPackage("entity.dto")
                .VOPackage("entity.vo")
//                .exportOnVO(true)
//                .importOnVO(true)
                .jakartaApi(false)
                .resultMapForVO(true)
                .allPost(false)
                .restStyle(true)
                .enableOrigins(true)
                .generateExport(true)
                .generateImport(true)
                .showServiceImplMethod(true)
                .showMapperMethod(true)


        ;
        generator.strategyConfigBuilder()
                .entityBuilder()
                .enableFileOverride()
                .enableActiveRecord()
                .idType(IdType.ASSIGN_ID)
                .enableTableFieldAnnotation()
                .logicDeleteColumnName("deleted")
        ;

        generator.strategyConfigBuilder()
                .serviceBuilder()
                .enableFileOverride()

            ;

        generator.strategyConfigBuilder()
                .mapperBuilder()
                .enableFileOverride()
        ;
        generator.strategyConfigBuilder()
                .entityBuilder()
                .enableLombok()
                .enableFileOverride()
        ;

        ;
        generator.globalConfigBuilder()
//                .enableSpringdoc()
//                .enableSwagger()
                ;

        generator.execute("user");
    }

}
