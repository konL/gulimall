package com.kon.common.group;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintViolation;
import java.util.HashSet;
import java.util.Set;

public class ListValueConstraintValidator implements ConstraintValidator<ListValue,Integer> {
    private Set<Integer> set=new HashSet<>();
    //初始化
    @Override
    public void initialize(ListValue constraintAnnotation) {
        int[] vals = constraintAnnotation.vals();
        //set是指定的数组范围
        for (int val:vals){
            set.add(val);
        }


    }


    //判断是否校验成功

    /**
     *
     * @param integer : 需要校验的值
     * @param context
     * @return
     */
    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext context) {
        return set.contains(integer);
    }
}
