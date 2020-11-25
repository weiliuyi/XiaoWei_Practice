package example.spring.Qualifer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;


/**
 * @description:
 * @author: weiliuyi
 * @create: 2020--24 15:43
 **/

@Service
public class CMerger {


    @Autowired
    @Qualifier("b")
    private TestImp testImp;

    public TestImp getTestImp() {
        return testImp;
    }

    public void setTestImp(TestImp testImp) {
        this.testImp = testImp;
    }

    @Override
    public String toString() {
        return "CMerger{" +
                "testImp=" + testImp +
                '}';
    }
}
