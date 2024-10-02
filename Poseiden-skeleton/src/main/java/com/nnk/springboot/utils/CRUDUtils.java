package com.nnk.springboot.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.util.ArrayList;
import java.util.List;

public abstract class CRUDUtils {

    /**
     * This method retrieves the names of all properties of an object that have a null value.
     * It uses the Spring {@link BeanWrapper} to introspect the given object's properties and
     * check if their values are null. The names of the properties that are null are returned
     * as an array of strings.
     *
     * @param source the object to inspect for null properties
     * @return an array of property names that have null values. If no properties are null, an empty array is returned.
     * @throws IllegalArgumentException if the source object is null
     */
    public static String[] getNullPropertyNames(Object source) {
        BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        List<String> emptyNames = new ArrayList<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) {
                emptyNames.add(pd.getName());
            }
        }
        return emptyNames.toArray(new String[0]);
    }
}
