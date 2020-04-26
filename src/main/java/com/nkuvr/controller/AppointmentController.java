package com.nkuvr.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nkuvr.pojo.Appointment;
import com.nkuvr.pojo.Result;
import com.nkuvr.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Author: weizujie
 * @Date: 2020/4/25
 * @Version: 1.0
 * @Github: https://github.com/weizujie
 */

@Controller
@RequestMapping("/appointment")
public class AppointmentController {

    @Autowired
    private IAppointmentService appointmentService;

    /**
     * 跳转到 我的预约
     *
     * @return
     */
    @RequestMapping("/list/{id}")
    public String toMyAppointmentList(@PathVariable("id") Long id,
                                      @RequestParam(name = "page", defaultValue = "1") Integer pageNum,
                                      Model model) {
        PageHelper.startPage(pageNum, 5);
        List<Appointment> appointmentList = appointmentService.findAppointmentListByUserId(id);
        PageInfo<Appointment> pageInfo = new PageInfo<>(appointmentList, 5);
        model.addAttribute("pageInfo", pageInfo);
        return "appointment/my_appointment";
    }

    /**
     * 跳转到 预约列表
     *
     * @param pageNum
     * @param model
     * @return
     */
    @RequestMapping("/list")
    public String toAppointmentList(@RequestParam(name = "page", defaultValue = "1") Integer pageNum, Model model) {
        PageHelper.startPage(pageNum, 5);
        List<Appointment> appointmentList = appointmentService.findAll();
        PageInfo<Appointment> pageInfo = new PageInfo<>(appointmentList, 5);
        model.addAttribute("pageInfo", pageInfo);
        return "appointment/list";
    }

    /**
     * 跳转到 新增预约
     *
     * @return
     */
    @RequestMapping("/add")
    public String toAppointmentAdd() {
        return "appointment/add";
    }

    /**
     * 处理 新增预约
     *
     * @param appointment
     * @return
     */
    @RequestMapping("/doAppointmentAdd")
    public Result doAppointmentAdd(Appointment appointment) {
        Result result = new Result();
        try {
            appointmentService.appointmentAdd(appointment);
            result.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            result.setSuccess(false);
        }
        return result;
    }


}
