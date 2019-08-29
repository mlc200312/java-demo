package org.xmin.demo.core.entity;

import com.xmin.lab.base.standard.DoBean;
import com.xmin.lab.common.lang.BaseBean;
import lombok.Data;

import java.util.Date;

/**
 * @Description: 功能描述
 * @Author: Mr.Min
 * @Date: 2019-07-30
 **/
@Data
public class Order extends BaseBean implements DoBean {

    private Long id;
    private Long buyerId;
    private Date orderDate;
    private String remark;

}
