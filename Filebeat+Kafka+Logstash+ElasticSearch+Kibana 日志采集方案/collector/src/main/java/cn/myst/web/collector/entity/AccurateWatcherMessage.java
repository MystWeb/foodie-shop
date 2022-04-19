package cn.myst.web.collector.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ziming.xing
 * Create Date：2022/4/18
 */
@Data
public class AccurateWatcherMessage implements Serializable {

    private String title;

    private String executionTime;

    private String applicationName;

    private String level;

    private String body;

    private static final long serialVersionUID = 3121485721266678741L;
}
