package example.spring.Qualifer;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--24 16:02
 **/

public class BaseCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        /*try {
            DNoUser bean = context.getBeanFactory().getBean(DNoUser.class);
        } catch (Exception e) {
            System.out.println(e);
            return true;
        }*/
        return true;
    }
}
