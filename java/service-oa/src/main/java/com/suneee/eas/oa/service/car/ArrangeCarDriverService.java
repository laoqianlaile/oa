package com.suneee.eas.oa.service.car;import com.suneee.eas.common.service.BaseService;import com.suneee.eas.oa.model.car.ArrangeCarDriver;/** * @program: eas-parent * @description: 生成车辆安排、车辆信息、驾驶员关联Service * @author: liuhai * @create: 2018-08-24 14:07 **/public interface ArrangeCarDriverService extends BaseService<ArrangeCarDriver> {    void deleteByArrId(Long arrId);}