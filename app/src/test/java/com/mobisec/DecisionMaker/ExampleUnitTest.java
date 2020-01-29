package com.mobisec.DecisionMaker;

import com.mobisec.DecisionMaker.model.EventActivity;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void test_dist_algorithm() {
        List<EventActivity> activities = new ArrayList<>();
        List<String> registeredUsers = asList("u1", "u2", "u3", "u4");
        activities.add(new EventActivity("1", "1", 2, new ArrayList<>(registeredUsers)));
        activities.add(new EventActivity("2", "2", 5, new ArrayList<>()));
        activities.add(new EventActivity("3", "3", 5, new ArrayList<>()));

        List<String> users = new ArrayList<>();
        for (EventActivity ea : activities) {
            int over = ea.getRegistered() - ea.getAvailable();
            if (over > 0) {
                List<String> regUsers = ea.getregisteredUsers();
                Collections.shuffle(regUsers);
                List<String> sub = regUsers.subList(0, over);
                users.addAll(sub);
                regUsers.removeAll(sub);
            }
        }

        Stack<String> stack = new Stack<>();
        stack.addAll(users);

        boolean run = true;
        while (run) {
            for (EventActivity eventActivity : activities) {
                if (stack.isEmpty()) {
                    run = false;
                    break;
                }
                if (eventActivity.getRegistered() < eventActivity.getAvailable()) {
                    eventActivity.getregisteredUsers().add(stack.pop());
                }
            }
        }


        List<String> x = activities.get(0).getregisteredUsers();
        List<String> x2 = activities.get(1).getregisteredUsers();
        List<String> x3 = activities.get(1).getregisteredUsers();
        assertEquals(2, x.size());
        assertEquals(1, x2.size());
        assertEquals(1, x3.size());
    }
}