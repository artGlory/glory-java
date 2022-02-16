package com.glory.gloryUtils.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class BeanMergeUtilTest {

    @Test
    void cover() {
        Dog dog = Dog.builder()
                .name("dog-name")
                .name2("dog-name2")
                .name3("dog-name3")
                .build();
        Cat cat = Cat.builder()
                .name("cat-name")
                .sex(("cat-sex"))
                .age(1)
                .build();
        System.err.println(dog);
        System.err.println(cat);
        BeanMergeUtil.cover(cat, dog, 1);
        System.err.println(dog);
    }

    @Test
    void merge() {
        Dog dog = Dog.builder()
                .uk("123")
                .name("dog-name")
                .name2("dog-name2")
                .name3("dog-name3")
                .build();
        Dog dog2 = Dog.builder()
                .uk("123")
                .name("dog2-name")
                .name2("dog2-name2")
                .name3("dog2-name3")
                .build();
        System.err.println("1==" + dog);
        System.err.println("2==" + dog2);

        try {
            Field field = Dog.class.getDeclaredField("uk");
            Dog dog3 = BeanMergeUtil.merge(dog, dog2, field, 1);
            System.err.println("1==" + dog);
            System.err.println("2==" + dog2);
            System.err.println("3==" + dog3);
            Dog dog4 = BeanMergeUtil.merge(dog, dog2, field, 2);
            System.err.println("1==" + dog);
            System.err.println("2==" + dog2);
            System.err.println("4==" + dog4);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Test
    void mergeList() {
        List<Dog> list = new ArrayList<>();
        list.add(Dog.builder()
                .uk("123")
                .name("dog-name")
                .name2("dog-name2")
                .build());
        List<Dog> list2 = new ArrayList<>();
        list2.add(Dog.builder()
                .uk("123")
                .name3("dog-name3")
                .build());
        System.err.println(list);
        System.err.println(list2);
        try {
            Field field = Dog.class.getDeclaredField("uk");
            List<Dog> result = BeanMergeUtil.mergeList(list, list2, field);
            System.err.println();
            System.err.println(list);
            System.err.println(list2);
            System.err.println();
            System.err.println(result);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

}

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
class Dog {
    public String uk;
    public String name;
    protected String name2;
    private String name3;
}

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
class Cat {
    private String name;
    private String sex;
    private int age;
}