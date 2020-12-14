package org.linlinjava.litemall.wx;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.core.qcode.QRCodeService;
import org.linlinjava.litemall.db.domain.LitemallGoods;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class QRCodeTest {
    private final Log logger = LogFactory.getLog(QRCodeTest.class);
    @Autowired
    private QRCodeService qrCodeService;
    @Autowired
    LitemallGoodsService litemallGoodsService;

    @Test
    public void test() {
        try {
            LitemallGoods good = litemallGoodsService.findById(1006013);
            qrCodeService.createGoodShareImage(good.getId().toString(),"http://www.baidu.com", good.getPicUrl(), good.getName());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
}
