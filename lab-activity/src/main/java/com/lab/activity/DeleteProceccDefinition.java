package com.lab.activity;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;

import java.util.List;

/**
 * 流程定义查询
 *
 * @author zrj
 * @date 2020/12/29
 * @since V1.0
 **/
public class DeleteProceccDefinition {

    public static void main(String[] args) {
        deleteDeployment();
    }
    /**
     * 删除指定流程id的流程
     */
    public static void deleteDeployment() {
        // 流程部署id
        String deploymentId = "5001";
        //1.得到ProcessEngine对象
        ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

        // 通过流程引擎获取repositoryService
        RepositoryService repositoryService = processEngine.getRepositoryService();
        //删除流程定义， 如果该流程定义已有流程实例启动则删除时出错
        repositoryService.deleteDeployment( deploymentId );
        //设置true 级联删除流程定义，即使该流程有流程实例启动也可以删除，设
        //置为false非级别删除方式，如果流程
        repositoryService.deleteDeployment( deploymentId, true );
    }
}
