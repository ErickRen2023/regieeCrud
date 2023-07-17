package me.erickren.reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import me.erickren.reggie.mapper.EmployeeMapper;
import me.erickren.reggie.pojo.Employee;
import me.erickren.reggie.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
