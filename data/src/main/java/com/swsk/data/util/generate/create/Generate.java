package com.swsk.data.util.generate.create;

/**
 * @author zzy
 * @Date 2018/12/26 3:35 PM
 */
public class Generate {

    /**
     * 生成bean/dao/service
     * @param config
     */
    public void CreateBean(Config config){
        CreateModel createModel = new CreateModel(config);
        createModel.CreateEntity();

    }

    /**
     * 生成dao
     * @param beanUrl===bean所在的包
     * @param beanNames===bean名称，
     */
    public void CreateDao(String beanUrl,String beanNames){
        CreateDao createDao = new CreateDao(beanUrl,beanNames);
        createDao.generation();
    }

    /**
     * 生成service
     * @param beanUrl===bean所在的包
     * @param beanNames===bean名称，
     */
    public void CreateService(String beanUrl,String beanNames){
        CreateService createService = new CreateService(beanUrl,beanNames);
        createService.generation();
    }
}
