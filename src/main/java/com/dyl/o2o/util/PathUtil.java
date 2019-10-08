package com.dyl.o2o.util;

/** 路径处理工具类
 * @author ：dyl
 * @date ：Created in 2019/10/5 15:02
 */
public class PathUtil {

    //获取系统分隔文件的分隔符
    private static String separator = System.getProperty("file.separator");

    /**
     * 获取所有图片存放文件夹的根路径
     * @return
     */
    public static String getImgBasePath(){
        String imgBasePath = System.getProperty("user.dir") + "/img";
        //获取系统名称
//        String os = System.getProperty("os.name");
        //将分隔符设置为系统识别的分隔符
        imgBasePath = imgBasePath.replace("/",separator);
        return imgBasePath;
    }

    /**
     * 获取店铺图片存放的文件夹路径（每个店铺的图片单独存放在一个文件夹中）
     * @param shopId
     */
    public static String getShopImgFolderRelativePath(long shopId){
        String shopImgFolderPath = "/" + shopId + "/";
        return shopImgFolderPath.replace("/",separator);
    }

    public static void main(String[] args) {
        System.out.println(separator);
        System.out.println(getImgBasePath());
        System.out.println(getShopImgFolderRelativePath(1L));
    }
}
