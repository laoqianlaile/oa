/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: CarInsuranceService
 * Author:   lmr
 * Date:     2018/8/16 13:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.suneee.eas.oa.service.car;

import com.suneee.eas.common.service.BaseService;
import com.suneee.eas.oa.model.car.CarInsurance;

/**
 * 〈一句话功能简述〉<br> 
 * 〈〉
 *
 * @author lmr
 * @create 2018/8/16
 * @since 1.0.0
 */
public interface CarInsuranceService extends BaseService<CarInsurance> {

    boolean isInsurNumRepeatForAdd(String insurNum);

    boolean isInsurNumRepeatForUpdate(Long insurId, String insurNum);

    void deleteByIds(Long[] insurIds);
}
