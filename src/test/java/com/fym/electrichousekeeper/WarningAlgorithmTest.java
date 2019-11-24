package com.fym.electrichousekeeper;

import com.fym.electrichousekeeper.core.WarningAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class WarningAlgorithmTest {

    WarningAlgorithm algorithm = new WarningAlgorithm();

    @Test
    public void testCurrentOverLoad(){
        //capacity110 = 144.x ratedCurrent
        boolean overLoad = algorithm.currentOverLoad(145D, 100, 10);
        Assert.isTrue(!overLoad);


        //144.x * 10% = 14.x; 144.x + 14.x => 159
        overLoad = algorithm.currentOverLoad(159D,100,10);
        Assert.isTrue(overLoad);
    }

    @Test
    public void test2(){
        System.out.println((char)48);

    }
}
