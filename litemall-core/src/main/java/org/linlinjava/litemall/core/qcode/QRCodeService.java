package org.linlinjava.litemall.core.qcode;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.linlinjava.litemall.core.storage.StorageService;
import org.linlinjava.litemall.core.util.QRCodeUtil;
import org.linlinjava.litemall.db.domain.LitemallStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;

/**
 * 二维码服务类（非微信）
 */
@Service
public class QRCodeService {
    private final Log logger = LogFactory.getLog(QRCodeService.class);

    @Autowired
    private StorageService storageService;

    /**
     * 创建商品分享图
     *
     * @param goodId     商品编码
     * @param goodPicUrl 商品链接
     * @param goodPicUrl 商品图片
     * @param goodName   商品名
     */
    public String createGoodShareImage(String goodId, String goodUrl, String goodPicUrl, String goodName) {
        try {
            //获取系统logo图片地址
            ClassPathResource redResource = new ClassPathResource("logo.png");
            String logoPath = redResource.getClassLoader().getResource("logo.png").getPath().substring(1);
            //构造商品分享图名字
            String fileName = goodName + ".jpg";
            fileName = storageService.generateKey(fileName);

            //创建该商品的二维码，获取二维码的字节输入流
            BufferedImage image = QRCodeUtil.getBufferedImage(goodUrl, 500, logoPath);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", os);
            InputStream is = new ByteArrayInputStream(os.toByteArray());
            //将商品图片，商品名字,商城名字画到模版图中
            byte[] imageData = drawPicture(is, goodPicUrl, goodName);
            ByteArrayInputStream inputStream2 = new ByteArrayInputStream(imageData);
            //存储分享图
            LitemallStorage litemallStorage = storageService.store(inputStream2, imageData.length, "image/jpeg",
                    getKeyName(goodId));

            return litemallStorage.getUrl();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        return "";
    }

    /**
     * 将商品图片，商品名字画到模版图中
     *
     * @param qrCodeImg  二维码图片
     * @param goodPicUrl 商品图片地址
     * @param goodName   商品名称
     * @return
     * @throws IOException
     */
    private byte[] drawPicture(InputStream qrCodeImg, String goodPicUrl, String goodName) throws IOException {
        //底图
        ClassPathResource redResource = new ClassPathResource("back.png");
        BufferedImage red = ImageIO.read(redResource.getInputStream());


        //商品图片
        URL goodPic = new URL(goodPicUrl);
        BufferedImage goodImage = ImageIO.read(goodPic);

        //小程序二维码
        BufferedImage qrCodeImage = ImageIO.read(qrCodeImg);

        // --- 画图 ---

        //底层空白 bufferedImage
        BufferedImage baseImage = new BufferedImage(red.getWidth(), red.getHeight(), BufferedImage.TYPE_4BYTE_ABGR_PRE);

        //画上图片
        drawImgInImg(baseImage, red, 0, 0, red.getWidth(), red.getHeight());

        //画上商品图片
        drawImgInImg(baseImage, goodImage, 71, 69, 660, 660);

        //画上二维码
        drawImgInImg(baseImage, qrCodeImage, 448, 767, 300, 300);

        //写上商品名称
        drawTextInImg(baseImage, goodName, 65, 867);

        //写上商城名称
        //        drawTextInImgCenter(baseImage, shopName, 98);


        //转jpg
        BufferedImage result = new BufferedImage(baseImage.getWidth(), baseImage
                .getHeight(), BufferedImage.TYPE_3BYTE_BGR);
        result.getGraphics().drawImage(baseImage, 0, 0, null);
        ByteArrayOutputStream bs = new ByteArrayOutputStream();
        ImageIO.write(result, "jpg", bs);

        //最终byte数组
        return bs.toByteArray();
    }

    private void drawTextInImg(BufferedImage baseImage, String textToWrite, int x, int y) {
        Graphics2D g2D = (Graphics2D) baseImage.getGraphics();
        g2D.setColor(new Color(167, 136, 69));

        //TODO 注意，这里的字体必须安装在服务器上
        g2D.setFont(new Font("Microsoft YaHei", Font.PLAIN, 28));
        g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2D.drawString(textToWrite, x, y);
        g2D.dispose();
    }

    private void drawImgInImg(BufferedImage baseImage, BufferedImage imageToWrite, int x, int y, int width,
                              int heigth) {
        Graphics2D g2D = (Graphics2D) baseImage.getGraphics();
        g2D.drawImage(imageToWrite, x, y, width, heigth, null);
        g2D.dispose();
    }

    private String getKeyName(String goodId) {
        return "GOOD_QCODE_" + goodId + ".jpg";
    }
}
