package org.linlinjava.litemall.admin;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.core.qcode.QCodeService;
import org.linlinjava.litemall.core.util.QRCodeUtil;
import org.linlinjava.litemall.db.service.LitemallGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CreateShareImageTest {
    @Autowired
    QCodeService qCodeService;
    @Autowired
    LitemallGoodsService litemallGoodsService;

    @Test
    public void test() {
//        Map<String, String> config = new HashMap<>();
//        config.put("litemall_wx_share","true");
//        SystemConfig.updateConfigs(config);
//        LitemallGoods good = litemallGoodsService.findById(1006013);
//        LitemallBrand brand = litemallBrandService.findById(good.getBrandId());
//        qCodeService.createQRCode(good.getId().toString(),"http://localhost:6255/#/items/detail/1006013", good.getPicUrl(), good.getName());
        QRCodeUtil.zxingCodeCreate("http://www.baidu.com", "D:/MyData/ex_yuhj/Documents/GitHub/litemall/storage/",500, "D:/MyData/ex_yuhj/Documents/GitHub/litemall/logo.jpg");
    }
}
