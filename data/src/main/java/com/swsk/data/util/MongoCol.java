package com.swsk.data.util;

/**
 * @author zzy
 * @Date 2020-01-09 10:21
 * @desc Mongodb集合定义
 */
public class MongoCol {


    //#########################集合#########################
    /**
     * 数据平均值时统计
     */
    public static final String DATA_AVG_MINUTE = "dataAvgMinute";

    /**
     * 数据平均值时统计
     */
    public static final String DATA_AVG_HOUR = "dataAvgHour";

    /**
     * 数据平均值日统计
     */
    public static final String DATA_AVG_DAY = "dataAvgDay";

    /**
     * 数据平均值月统计
     */
    public static final String DATA_AVG_MONTH = "dataAvgMonth";

    /**
     * 数据平均值年统计
     */
    public static final String DATA_AVG_YEAR = "dataAvgYear";


    //#########################风向属性#########################

    /**
     * 两分钟平均风向
     */
    public static final String DIRECTION_AVG_2_MIN = "winDirectionAvg2mi";

    /**
     * 十分钟平均风速
     */
    public static final String DIRECTION_AVG_10_MIN = "winDirectionAvg10mi";

    /**
     * 极大风向
     */
    public static final String DIRECTION_INST = "winDirectionInst";

    /**
     * 最大风向
     */
    public static final String DIRECTION_MAX = "winDirectionMax";

}
