package com.suneee.eas.oa.controller.car;import com.suneee.eas.common.component.Pager;import com.suneee.eas.common.component.QueryFilter;import com.suneee.eas.common.component.ResponseMessage;import com.suneee.eas.common.constant.FunctionConstant;import com.suneee.eas.common.constant.ModuleConstant;import com.suneee.eas.common.utils.*;import com.suneee.eas.oa.model.car.CarApply;import com.suneee.eas.oa.model.car.CarArrange;import com.suneee.eas.oa.model.car.CarDriver;import com.suneee.eas.oa.service.car.CarArrangeService;import com.suneee.eas.oa.service.car.CarDriverService;import org.apache.logging.log4j.LogManager;import org.apache.logging.log4j.Logger;import org.springframework.beans.factory.annotation.Autowired;import org.springframework.stereotype.Controller;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.ResponseBody;import javax.servlet.http.HttpServletRequest;import java.util.List;/** * @program: eas-parent * @description: 车俩安排controller * @author: liuhai * @create: 2018-08-21 17:00 **/@Controller@RequestMapping(ModuleConstant.CAR_MODULE+ FunctionConstant.CAR_ARRANGE)public class CarArrangeApiController {    private static final Logger logger = LogManager.getLogger(CarArrangeApiController.class);    @Autowired    private CarArrangeService carArrangeService;    @Autowired    private CarDriverService carDriverService;    /**     * 新增车辆安排     * @param request     * @param carArrange     * @return     */    @RequestMapping("save")    @ResponseBody    public ResponseMessage save(HttpServletRequest request, CarArrange carArrange){        ResponseMessage mess = new ResponseMessage();        mess.setStatus(ResponseMessage.STATUS_SUCCESS);        Long arrId = carArrange.getArrId();        if(null == arrId){            arrId = 0l;        }        try {            if (0 == arrId) {                carArrangeService.save(carArrange);                mess.setMessage("直接派车成功。");            } else {                carArrangeService.update(carArrange);                mess.setMessage("直接派车成功。");            }        } catch (Exception e) {            mess.setStatus(ResponseMessage.STATUS_FAIL);            mess.setData(e);            if (0 == arrId) {                mess.setMessage("直接派车失败:"+e.getMessage());            } else {                mess.setMessage("直接派车失败:"+e.getMessage());            }            logger.error(mess.getMessage() + e.getMessage(), e);        }        return mess;    }    /**     * 获取车辆安排分页列表     * @param request     * @return     */    @RequestMapping("listPage")    @ResponseBody    public ResponseMessage listPage(HttpServletRequest request) {        try {            String startTime = RequestUtil.getString(request, "startTime");            String endTime = RequestUtil.getString(request, "endTime");            QueryFilter queryFilter = new QueryFilter("list", request);            if(StringUtil.isNotEmpty(startTime)){                queryFilter.addFilter("startTime", DateUtil.getDate(startTime));            }            if(StringUtil.isNotEmpty(endTime)){                queryFilter.addFilter("endTime" , DateUtil.getDate(endTime));            }            String enterpriseCode = ContextSupportUtil.getCurrentEnterpriseCode();            queryFilter.addFilter("enterpriseCode", enterpriseCode);            queryFilter.addFilter("userId", ContextSupportUtil.getCurrentUserId());            Pager<CarArrange> carArrangeList = carArrangeService.getPageBySqlKey(queryFilter);            return ResponseMessage.success("获取车辆安排分页列表成功。", carArrangeList);        } catch (Exception e) {            logger.error("获取车辆安排分页列表失败:" + e.getMessage(), e);            return ResponseMessage.fail("获取车辆安排分页列表失败！"+e.getMessage(), e);        }    }    /**     * 获取车辆安排列表     * @param request     * @return     */    @RequestMapping("list")    @ResponseBody    public ResponseMessage lsit(HttpServletRequest request){        try {            String startTime = RequestUtil.getString(request, "startTime");            String endTime = RequestUtil.getString(request, "endTime");            QueryFilter queryFilter = new QueryFilter("list", request);            if(StringUtil.isNotEmpty(startTime)){                queryFilter.addFilter("startTime", DateUtil.getDate(startTime));            }            if(StringUtil.isNotEmpty(endTime)){                queryFilter.addFilter("endTime" , DateUtil.getDate(endTime));            }            String enterpriseCode = ContextSupportUtil.getCurrentEnterpriseCode();            queryFilter.addFilter("enterpriseCode", enterpriseCode);            List<CarArrange> carArrangeList = carArrangeService. getListBySqlKey(queryFilter);            return ResponseMessage.success("获取车辆安排列表成功。", carArrangeList);        } catch (Exception e) {            logger.error("获取车辆安排列表失败:" + e.getMessage(), e);            return ResponseMessage.fail("获取车辆安排列表失败！"+e.getMessage(), e);        }    }    /**     * 获取车辆安排详情     * @param request     * @return     */    @RequestMapping("findById")    @ResponseBody    public ResponseMessage findById(HttpServletRequest request){        Long arrId = RequestUtil.getLong(request, "arrId");        if(arrId == 0){            logger.error("获取车辆安排失败，参数不能为空！");            return ResponseMessage.fail("获取车辆安排失败，参数不能为空！");        }        try {            CarArrange carArrange = carArrangeService.findById(arrId);            return ResponseMessage.success("查询详情成功。", carArrange);        } catch (Exception e) {            logger.error("查询详情失败："+e.getMessage(), e);            return ResponseMessage.fail("查询详情失败！"+ e.getMessage(), e);        }    }    /**     * 根据ids删除关联表     * @param request     */    @RequestMapping("deleteByIds")    @ResponseBody    public ResponseMessage deleteByIds(HttpServletRequest request){        Long[] ids = RequestUtil.getLongAry(request, "ids");        if(ids.length <= 0){            logger.error("删除车辆安排列表失败, 车辆安排id为空！");            return ResponseMessage.fail("删除车辆安排列表失败, 车辆安排id为空！");        }        try {            carArrangeService.deleteByIds(ids);            return ResponseMessage.success("删除车辆安排列表成功。");        } catch (Exception e) {            logger.error("删除车辆安排列表失败：" + e.getMessage(), e);            return ResponseMessage.fail("删除车辆安排列表失败！"+ e.getMessage(), e);        }    }    /**     * 派车/不派     * @return     */    @RequestMapping("sendCar")    @ResponseBody    public ResponseMessage SendCar(HttpServletRequest request, CarArrange carArrange){        if(BeanUtils.isEmpty(carArrange.getArrId())){            logger.error("操作失败，缺少参数！");            return ResponseMessage.fail("操作失败，缺少参数！");        }        CarArrange arrange = carArrangeService.findById(carArrange.getArrId());        if(null == arrange){            logger.error("操作失败，找不到该派车记录！");            return ResponseMessage.fail("操作失败，找不到该派车记录！");        }        try {            if(CarApply.STATUS_CAR_ARRANGE_FAIL == carArrange.getStatus()){                carArrangeService.sendRefuse(arrange, CarApply.STATUS_CAR_ARRANGE_FAIL);            }else if(CarApply.STATUS_CAR_DRIVE_PENDDING == carArrange.getStatus()){                arrange.setDriverId(carArrange.getDriverId());                arrange.setCarId(carArrange.getCarId());                CarDriver carDriver = carDriverService.findById(arrange.getDriverId());                arrange.setMobile(carDriver.getMobile());                carArrangeService.update(arrange);                carArrangeService.sendCar(arrange, carDriver.getDriverId().toString());            }            return ResponseMessage.success("操作成功。");        } catch (Exception e) {            logger.error("操作失败：", e);            return ResponseMessage.fail("操作失败："+e.getMessage(),e);        }    }}