package com.dyl.o2o.common.util;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 图片工具类
 *
 * @author ：dyl
 * @date ：Created in 2019/10/4 20:34
 */
@Slf4j
public class ImageUtil {

    //classPath路径 test和java下运行获取到的目录不同
    private static String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    //设置时间格式
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    //设置随机数
    private static final Random random = new Random();

    /**
     * 根据传来的图片生成缩略图并替换原图片
     *
     * @param img
     */
    public static void img2Thumbnail(File img) {
        //图片完整路径
        String fullPath = img.getAbsolutePath();
        try {
            //获取水印图片
            System.out.println("水印图片地址" + classPath + "static/img/watermark.png");
            BufferedImage buffer = ImageIO.read(new File(classPath + "static/img/watermark.png"));
            //生成缩略图
            Thumbnails.of(img).size(500, 500).watermark(Positions.BOTTOM_RIGHT, buffer, 0.25f).outputQuality(0.8f).toFile(img.getAbsolutePath());
        } catch (IOException e) {
            log.error(e.toString());
            e.printStackTrace();
            throw new RuntimeException("生成缩略图发生错误！");
        }
    }

    /**
     * 获取文件的扩展名(原始文件名.之后的部分)
     *
     * @param file
     * @return
     */
    public static String getFileExtension(File file) {
        String originalFileName = file.getName();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    /**
     * 根据文件名获取该文件名的扩展
     * @param fileName
     * @return
     */
    public static String getFileNameExtension(String fileName){
        return fileName.substring(fileName.lastIndexOf("."));
    }

    /**
     * 获取随机文件名（当前的年月日时分秒+5位随机数）
     *
     * @return
     */
    public static String getRandomFileName() {
        //获取当前时间
        String nowTimeStr = sDateFormat.format(new Date());
        //获取5位的随机数
        int rannum = random.nextInt(89999) + 10000;//大于10000小于99999
        return nowTimeStr + rannum;
    }


    /**
     * 根据用户上传的文件生成缩略图
     * @param newImg
     * @return
     * @throws IOException
     */
    public static String generateThumbnail(MultipartFile newImg) throws IOException {
        //生成服务器上保存图片的地址
        String imgFileAbsolutePath = PathUtil.getImgBasePath() +"\\"+ ImageUtil.getRandomFileName() + ImageUtil.getFileNameExtension(newImg.getOriginalFilename());
        //根据地址创建文件
        File img = new File(imgFileAbsolutePath);
        //若文件地址不存在，生成地址
        if (!img.getParentFile().exists()){
            img.getParentFile().mkdirs();
        }
        //multipartFile转file，方便其他方法操作和单元测试
        newImg.transferTo(img);
        //用服务器上的图片生成带水印的缩略图，并将原图覆盖
        ImageUtil.img2Thumbnail(img);
        return imgFileAbsolutePath;
    }

    /**
     * 删除文件
     * @param shopImg
     */
    public static void deleteImg(String shopImg) {
        File file = new File(shopImg);
        if (file.exists()){
            file.delete();
        }
    }



    /**
     * 创建一个带有水印的缩略图
     *
     * @param args
     */
    public static void main(String[] args) throws IOException {
        //获取classPath路径
        String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println("classpath: " + classPath);
        //水印图片路径
        BufferedImage watermarkImg = ImageIO.read(new File(classPath + "/static/img/watermark.png"));
        /*
        of指定需要处理的文件
        size指定输出图片的大小（长和宽）
        watermark设置水印（位置，水印图片路径，设置透明度）
        outputQuality设置压缩比(0.8代表占用空间压缩到原先的80%）
        toFile设置输出路径
         */
        Thumbnails.of(new File(classPath + "/static/img/1.jpg"))
                .size(1000, 1000).watermark(Positions.BOTTOM_RIGHT, watermarkImg, 0.25f).outputQuality(0.8f).toFile(classPath + "/static/img/12.jpg");

    }
}
