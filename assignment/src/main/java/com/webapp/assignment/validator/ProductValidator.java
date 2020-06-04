package com.webapp.assignment.validator;

import com.webapp.assignment.Entity.Product;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ProductValidator  implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        return Product.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {

    }
}
