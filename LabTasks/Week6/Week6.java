class Animal{

    public void eat(){
        System.out.println("Animal eats");
    }
}

class Dog extends Animal{

    public void bark(){
        System.out.println("Woof Woof");
    }
}

class Week6 {
    public static void main(String[] arg){
        
        Animal animal = new Animal();
        Dog dog = new Dog();

        dog.bark();
        dog.eat();
    }
}