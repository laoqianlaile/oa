package com.suneee.eas.oa.service.car;import com.suneee.eas.common.service.BaseService;import com.suneee.eas.oa.model.car.CarInfo;/** * @program: eas-parent * @description: 车辆信息Service * @author: liuhai * @create: 2018-08-20 13:53 **/public interface CarInfoService extends BaseService<CarInfo>{    boolean isPlateNumExist(String plateNum, Long carId);    void removeById(Long carId);}