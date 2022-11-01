package org.lab.collect.list;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListDemo {
    public static void main(String[] args) {
        List<Person> list = new ArrayList(Arrays.asList(new Person("项目21", 21, "住址21"), new Person("项目2", 2, "住址2"),
                new Person("项目3", 3, "住址3"), new Person("项目4", 4, "住址4"),
                new Person("项目5", 5, "住址5"), new Person("项目6", 6, "住址6"),
                new Person("项目7", 7, "住址7"), new Person("项目8", 8, "住址8"),
                new Person("项目9", 9, "住址9"), new Person("项目10", 10, "住址10")));

        list.add(new Person("项目11", 11, "住址11"));
        System.out.println(list);
    }

}

@Data
@NoArgsConstructor
@AllArgsConstructor
class Person implements Comparable<Person> {
    private String name;
    private int age;
    private String address;

    @Override
    public int compareTo(Person o) {
        return 0;
    }
}
