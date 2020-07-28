package Trie;

public class Person {
    String name, phone;

    public Person(String name, String phone_number) {
        this.name = name;
        this.phone = phone_number;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return "[Name: " + name + ", Phone=" + phone + "]";
    }
}
