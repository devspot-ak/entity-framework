
package biz.devspot.entity.framework.core.util;

import java.lang.reflect.Field;
import java.lang.reflect.Type;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class ReflectionUtilsTest {

    public ReflectionUtilsTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGetType() {
        Class clazz = FieldUtilsTestClass1.class;
        Field field = org.apache.commons.lang3.reflect.FieldUtils.getField(clazz, "field1", true);
        Type result = ReflectionUtils.getFieldType(clazz, field);
        assertEquals(String.class, result);
        field = org.apache.commons.lang3.reflect.FieldUtils.getField(clazz, "field2", true);
        result = ReflectionUtils.getFieldType(clazz, field);
        assertEquals(Object.class, result);
        clazz = FieldUtilsTestClass2.class;
        field = org.apache.commons.lang3.reflect.FieldUtils.getField(clazz, "field2", true);
        result = ReflectionUtils.getFieldType(clazz, field);
        assertEquals(String.class, result);
        clazz = FieldUtilsTestClass3.class;
        field = org.apache.commons.lang3.reflect.FieldUtils.getField(clazz, "field2", true);
        result = ReflectionUtils.getFieldType(clazz, field);
        assertEquals(String.class, result);
        System.out.println("==================================");
        clazz = FieldUtilsTestClass4.class;
        field = org.apache.commons.lang3.reflect.FieldUtils.getField(clazz, "field2", true);
        result = ReflectionUtils.getFieldType(clazz, field);
        assertEquals(Number.class, result);
        System.out.println("==================================");
        clazz = FieldUtilsTestClass5.class;
        field = org.apache.commons.lang3.reflect.FieldUtils.getField(clazz, "field2", true);
        result = ReflectionUtils.getFieldType(clazz, field);
        assertEquals(Integer.class, result);
    }
    
    public class FieldUtilsTestClass1<F extends Object>{
        
        private String field1;
        private F field2;
        
    }
    
    public class FieldUtilsTestClass2 extends FieldUtilsTestClass1<String>{
        
    }
    
    public class FieldUtilsTestClass3 extends FieldUtilsTestClass2{
        
    }
    
    public class FieldUtilsTestClass4<N extends Number> extends FieldUtilsTestClass1<N>{
        
    }
    
    public class FieldUtilsTestClass5 extends FieldUtilsTestClass4<Integer>{
        
    }

}