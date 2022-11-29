package org.lab.base.util;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Reflect {
  public static void main(String[] args) throws Exception {

    Class<?> aClass = Class.forName("User");
    String name = aClass.getName();
    String simpleName = aClass.getSimpleName();
    ClassLoader classLoader = aClass.getClassLoader();
    System.out.println(name);
    System.out.println(simpleName);
    System.out.println(classLoader);

    System.out.println(ClassLoader.getSystemClassLoader());
    System.out.println(ClassLoader.getSystemClassLoader().getParent());
    System.out.println(ClassLoader.getSystemClassLoader().getParent().getParent());

//    System.out.println(System.getProperties());
//    System.out.println("_________");
//    System.out.println(System.getProperty("java.class.path"));
    System.out.println("============Fields================");
    Field[] fields = aClass.getFields();
    for (Field field : fields) {
      System.out.println("getFields:   " + field);
    }
    Field[] declaredFields = aClass.getDeclaredFields();
//    Stream.of(declaredFields).forEach(System.out::println);
    for (Field field : declaredFields) {
      System.out.println("getDeclaredFields:   " + field);
    }
    Field name1 = aClass.getField("hobby"); // 仅能获取public修饰的字段
    System.out.println("getField public:  " + name1);

//    Field name2 = aClass.getField("birth"); // Exception in thread "main" java.lang.NoSuchFieldException: birth
//    System.out.println("getField protected:  " + name2);

    Field birth = aClass.getDeclaredField("birth");
    System.out.println("getDeclaredField birth:  " + birth);

    System.out.println("============Method================");
    Method[] methods = aClass.getMethods(); // 本类和父类的public方法
    for (Method m : methods) {
      System.out.println("getMethods: " + m);
    }

    Method[] declaredMethods = aClass.getDeclaredMethods();
    for (Method m : declaredMethods) {
      System.out.println("getDeclaredMethods:" + m);
    }
    final Method getName = aClass.getMethod("getName", null);
    System.out.println("getName: " + getName);

    final Method getNewName = aClass.getDeclaredMethod("getNewName", null);
    System.out.println("getNewName: " + getNewName);

    Method enclosingMethod = aClass.getEnclosingMethod();
    System.out.println("getEnclosingMethod: " + enclosingMethod);

    System.out.println("============Constructor================");
    Constructor<?>[] constructors = aClass.getConstructors();
    for (Constructor c : constructors) {
      System.out.println("getConstructors:   " + c);
    }
    Constructor<?>[] declaredConstructors = aClass.getDeclaredConstructors();
    for (Constructor c : declaredConstructors) {
      System.out.println("getDeclaredConstructors:  " + c);
    }

    Constructor<?> declaredConstructor = aClass.getDeclaredConstructor(String.class, String.class, String.class, String.class, short.class); // Short.class会报错
    System.out.println("getDeclaredConstructor with name:   " + declaredConstructor);

    Object o = aClass.newInstance();
    User u = (User) o;
    System.out.println(u);

    Constructor<?> declaredConstructor2 = aClass.getDeclaredConstructor(String.class, String.class, String.class, String.class, short.class); // Short.class会报错
    short i = 6;
    Object o1 = declaredConstructor2.newInstance("羽毛球", "20210405", "中国山东", "法外狂徒张三", i);
    User u1 = (User) o1;
    System.out.println(u1);

    Method setName = aClass.getDeclaredMethod("setName", String.class);
    setName.setAccessible(true);
    setName.invoke(u1, "合法公民");
    System.out.println(u1);

    Field name2 = aClass.getDeclaredField("name");
    name2.setAccessible(true);
    name2.set(u1, "合法名字");

    System.out.println(u1);

    List<String> test = Collections.nCopies(8, "test");
    System.out.println(String.join(",  ", test));

  }
}
class User {

  public String hobby;
  protected String birth;
  String address;
  private String name;
  private short age;

  public User() {
  }

  private User(String hobby) {
  }

  public User(String hobby, String birth, String address, String name, short age) {
    this.hobby = hobby;
    this.birth = birth;
    this.address = address;
    this.name = name;
    this.age = age;
  }

  private String getNewName() {
    return "";
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return age == user.age && Objects.equals(hobby, user.hobby) && Objects.equals(birth, user.birth) && Objects.equals(address, user.address) && Objects.equals(name, user.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(hobby, birth, address, name, age);
  }

  @Override
  public String toString() {
    return "User{" +
            "hobby='" + hobby + '\'' +
            ", birth='" + birth + '\'' +
            ", address='" + address + '\'' +
            ", name='" + name + '\'' +
            ", age=" + age +
            '}';
  }

  public String getHobby() {
    return hobby;
  }

  public void setHobby(String hobby) {
    this.hobby = hobby;
  }

  public String getBirth() {
    return birth;
  }

  public void setBirth(String birth) {
    this.birth = birth;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public short getAge() {
    return age;
  }

  public void setAge(short age) {
    this.age = age;
  }
}
