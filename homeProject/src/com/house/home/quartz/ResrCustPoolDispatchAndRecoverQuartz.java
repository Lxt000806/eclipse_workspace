package com.house.home.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.house.home.service.design.ResrCustPoolService;

public class ResrCustPoolDispatchAndRecoverQuartz {
    
    private static Logger logger = LoggerFactory
            .getLogger(ResrCustPoolDispatchAndRecoverQuartz.class);
    
    @Autowired
    private ResrCustPoolService resrCustPoolService;

    public void execute() {
        
        try {
            
            logger.debug("线索池回收定时任务开始");
            long start = System.currentTimeMillis();
            
            // 捕获回收中可能产生的异常，以便不影响派单
            try {
                resrCustPoolService.recover();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            long end = System.currentTimeMillis();
            logger.debug("线索池回收定时任务结束，用时：" + (end - start) + "ms");
            
            //---------------------------------//
            
            logger.debug("线索池派单定时任务开始");
            start = System.currentTimeMillis();
            
            resrCustPoolService.dispatch();

            end = System.currentTimeMillis();
            logger.debug("线索池派单定时任务结束，用时：" + (end - start) + "ms");
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

}
