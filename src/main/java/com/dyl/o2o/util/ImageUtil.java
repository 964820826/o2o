package com.dyl.o2o.util;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.Buffer;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 图片工具类
 *
 * @author ：dyl
 * @date ：Created in 2019/10/4 20:34
 */
public class ImageUtil {
    private static final Logger LOG = LoggerFactory.getLogger(ImageUtil.class);

    //classPath路径 test和java下运行获取到的目录不同
    private static String classPath = Thread.currentThread().getContextClassLoader().getResource("").getPath();
    //设置时间格式
    private static final SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    //设置随机数
    private static final Random random = new Random();

    /**
     * 将传入的CommonsMultipartFile转化成File（CommonsMultipartFile测试时需要从前端传入后才能生成，不方便测试，故转为File类，测试时使用File类来测试）
     * @param cFile
     * @return
     */
    public static File transferCommonsMultipartFileToFile(CommonsMultipartFile cFile){
        File newFile = new File(cFile.getOriginalFilename());
        try {
            cFile.transferTo(newFile);
        } catch (IOException e) {
            LOG.error(e.toString());
            e.printStackTrace();
        }
        return newFile;
    }

    /**
     * 根据传来的图片生成缩略图并保存到目标地址中
     *
     * @param img
     * @param imgFolderRelativeAddr 图片所在目录的相对路径
     * @return 图片相对路径，用于保存到数据库中
     */
    public static String generateThumbnail(File img, String imgFolderRelativeAddr) {//todo targetAddr的用途，具体指代什么地址
        //生成的文件名由程序生成
        String fileName = getRandomFileName();
        //图片扩展类型
        String extension = getFileExtension(img);
        //创建缩略图保存的目标地址所涉及的目录
        makeDirPath(imgFolderRelativeAddr);
        //文件相对路径
        String imgRelativeAddr = imgFolderRelativeAddr + fileName + extension;
        LOG.debug("生成图片的相对路径为：" + imgRelativeAddr);
        //文件绝对路径（如：E:\IDEAWorkspace\git\o2o\src\main\resources\static\img\watermark.png）
        String imgFullPath = PathUtil.getImgBasePath() + imgFolderRelativeAddr + fileName + extension;
        LOG.debug("生成图片的绝对路径为：" + imgFullPath);
        System.out.println("imgFullPath= " + imgFullPath);
        //根据完整路径创建文件
        File dest = new File(imgFullPath);
        try {
            //获取水印图片
            System.out.println("水印图片地址" + classPath + "static/img/watermark.png");
            BufferedImage buffer = ImageIO.read(new File(classPath + "static/img/watermark.png"));
            //生成缩略图
            Thumbnails.of(img).size(500, 500).watermark(Positions.BOTTOM_RIGHT, buffer, 0.25f).outputQuality(0.8f).toFile(dest);
        } catch (IOException e) {
            LOG.error(e.toString());
            e.printStackTrace();
            throw new RuntimeException("生成缩略图发生错误！");
        }
        return imgRelativeAddr;
    }

    /**
     * 创建目标路径所涉及到的目录，如D:/upload/img/xxx.jpg，就需要创建upload和img文件夹
     *
     * @param targetAddr
     */
    private static void makeDirPath(String targetAddr) {
        String realFilePath = PathUtil.getImgBasePath() + targetAddr;
        File dirPath = new File(realFilePath);
        if (!dirPath.exists()) {
            dirPath.mkdirs();
        }
    }

    /**
     * 获取文件的扩展名(原始文件名.之后的部分)
     *
     * @param file
     * @return
     */
    private static String getFileExtension(File file) {
        String originalFileName = file.getName();
        return originalFileName.substring(originalFileName.lastIndexOf("."));
    }

    /**
     * 获取随机文件名（当前的年月日时分秒+5位随机数）
     *
     * @return
     */
    private static String getRandomFileName() {
        //获取当前时间
        String nowTimeStr = sDateFormat.format(new Date());
        //获取5位的随机数
        int rannum = random.nextInt(89999) + 10000;//大于10000小于99999
        return nowTimeStr + rannum;
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
