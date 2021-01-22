package example.algorithm.interview.construct.queueandstack;

import example.spring.Qualifer.D;
import example.spring.test.Person;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @description:
 * @author: weiliuyi
 * @create: 2021--22 14:40
 **/
public class DogCatQueue {

}


class PetQueue {
    /**
     * 狗队列
     */
    private Queue<PetEntity> dogQ ;
    /**
     * 猫队列
     */
    private Queue<PetEntity> catQ;
    /**
     * 时间戳
     */
    private int count;

    public PetQueue() {
        this.dogQ = new LinkedList<>();
        this.catQ = new LinkedList<>();
        this.count = 0;
    }

    public boolean isCatQEmpty () {
        return this.catQ.isEmpty();
    }

    public boolean isDogQEmpty () {
        return this.dogQ.isEmpty();
    }

    /**
     * 队列中的所有的Dog
     */
    public List<Dog> pollAllDog () {
        List<Dog> result = new ArrayList<>();
        while (!this.dogQ.isEmpty()) {
            PetEntity pet = dogQ.poll();
            result.add((Dog) pet.pet);
        }
        return result;
    }

    /**
     * 队列中所有的
     * @return
     */
    public List<Cat> pollAllCat () {
        List<Cat> result = new ArrayList<>();
        while (!this.catQ.isEmpty()) {
            PetEntity pet = catQ.poll();
            result.add((Cat) pet.pet);
        }
        return result;
    }

    /**
     * 所有的pet
     */
    public List<Pet> pollAllPet() {
        List<Pet> result = new ArrayList<>();
        while (!this.catQ.isEmpty() || !this.dogQ.isEmpty()) {
            PetEntity catP = catQ.peek();
            PetEntity dogP = dogQ.peek();
            if (catP != null && dogP != null) {
                result.add(catP.timestamp <= dogP.timestamp ? catQ.poll().pet : dogQ.poll().pet);
                continue;
            }
            if (catP != null) {
                result.add(catQ.poll().pet);
                continue;
            }
            if (dogP != null) {
                result.add(dogQ.poll().pet);
            }
        }
        return result;
    }


}

class Pet {
    /**
     * 名字
     */
    String name;
    /**
     * 宠物的类型
     */
    private String type;

    Pet(String type) {
        this.type = type;
    }
}




class PetEntity {

    /**
     * 宠物
     */
    Pet pet;
    /**
     * 时间戳
     */
    int timestamp;

}

class Dog extends Pet {

    Dog() {
        super("Dog");
    }
}


class Cat extends Pet {
     Cat() {
        super("Cat");
    }
}