package com.github.kahlkn.yui.test.entity;

/**
 * Test entity.
 * @author Kahle
 */
public class Superman extends Person implements FlyAbility {

    @Override
    public void fly() {
        System.out.println(this.getName() + " are fly now. ");
    }

}
