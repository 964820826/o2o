package com.dyl.o2o.dto;

import com.dyl.o2o.domain.ShopDo;

import java.util.List;

//todo 暂时不用dto封装service结果，controller需要什么返回什么
/** 店铺service层执行结果包装类，包含执行结果码、含义和店铺对象
 * @author ：dyl
 * @date ：Created in 2019/10/7 22:58
 */
public class ShopExecution {
    //结果状态
    private int state;

    //状态含义
    private String stateInfo;

    //店铺数量（有时不止操作一个店铺）
    private int count;

    //操作的店铺(增删改店铺是使用)
    private ShopDo shopDo;

    //操作的shop列表（查询时使用）
    private List<ShopDo> shopDoList;

    //无参构造函数
    public ShopExecution() {
    }

    //
//    public ShopExecution(ShopStateEnum stateEnum){
//        this.state = stateEnum.getState();
//        this.stateInfo = stateEnum.getStateInfo();
//    }
}
