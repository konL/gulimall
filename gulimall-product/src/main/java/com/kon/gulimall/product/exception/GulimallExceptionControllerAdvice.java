package com.kon.gulimall.product.exception;

import com.kon.common.exception.BizCodeEnum;
import com.kon.common.utils.R;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;


//@ControllerAdvice(basePackages = "com.example.gulimall.product.controller")
//@ResponseBody
// 这个注解=上面两个，类型RestController=ResponseBody+Controller
@Slf4j
@RestControllerAdvice(basePackages ="com.kon.gulimall.product.controller")
public class GulimallExceptionControllerAdvice {

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现问题{}，异常类型{}",e.getMessage(),e.getClass());
        BindingResult bindingResult=e.getBindingResult();
        Map<String,String> errorMap=new HashMap<>();
        bindingResult.getFieldErrors().forEach((fieldError)->{
            errorMap.put(fieldError.getField(),fieldError.getDefaultMessage());
        });
        return R.error(BizCodeEnum.VALID_EXCEPTION.getCode(), BizCodeEnum.VALID_EXCEPTION.getMsg()).put("data", errorMap);
    }
    @ExceptionHandler(value = {Throwable.class})
    public R handleException(Throwable e){
        log.error("错误：",e);
        return R.error(BizCodeEnum.UNKNOWN_EXCEPTION.getCode(),
                BizCodeEnum.UNKNOWN_EXCEPTION.getMsg());
    }
}
