package com.wolfman.travel;

import com.wolfman.travel.util.UuidComponent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
class SpringbootTravelApplicationTests {

    @Autowired
    UuidComponent myUuidUtil;

    @Test
    void test01()
    {
        System.out.println(myUuidUtil.getUuid());
    }

}
