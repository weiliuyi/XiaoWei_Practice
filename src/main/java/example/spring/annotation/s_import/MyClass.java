package example.spring.annotation.s_import;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--16 11:38
 **/
public class MyClass implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"example.spring.annotation.s_import.TestDemo3"};
    }
}
