package org.linlinjava.litemall.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.linlinjava.litemall.db.domain.LitemallOrderGoods;
import org.linlinjava.litemall.db.service.LitemallOrderGoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@WebAppConfiguration
@RunWith(SpringRunner.class)
@SpringBootTest
public class DbTest {
    @Autowired
    private LitemallOrderGoodsService orderGoodsService;

    @Test
    public void test() {
        LitemallOrderGoods orderGoods = orderGoodsService.findByCommentId(241);
        System.out.println(orderGoods == null ? "null" : orderGoods.getGoodsName());
    }

}
