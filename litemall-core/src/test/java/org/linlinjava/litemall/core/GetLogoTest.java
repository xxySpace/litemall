package org.linlinjava.litemall.core;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.core.qcode.QRCodeService;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class GetLogoTest {
    private final Log logger = LogFactory.getLog(ExpressTest.class);
    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    LitemallGoodsService litemallGoodsService;

    @Test
    public void test() {
        try {
            ClassPathResource redResource = new ClassPathResource("logo.png");
            System.out.println(redResource.getClassLoader().getResource("logo.png").getPath());
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            System.out.println(loader.getResource("").getPath());
            System.out.println(this.getClass().getResource("").getPath());
            System.out.println(this.getClass().getResource("/").getPath());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
