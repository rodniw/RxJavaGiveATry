package dev.rodni.ru.rxjavamaster.pojo;

import java.util.Objects;

public class Human {

    private int age;
    private String name;
    private String profession;

    public Human(int age, String name, String profession) {
        this.age = age;
        this.name = name;
        this.profession = profession;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human human = (Human) o;
        return getAge() == human.getAge() &&
                getName().equals(human.getName()) &&
                Objects.equals(getProfession(), human.getProfession());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAge(), getName(), getProfession());
    }

    @Override
    public String toString() {
        return "Human{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", profession='" + profession + '\'' +
                '}';
    }
}
