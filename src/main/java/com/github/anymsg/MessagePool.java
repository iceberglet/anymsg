package com.github.anymsg;

import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * A pool of message that recycles messages
 */
public class MessagePool implements Supplier<Message>, Consumer<Message> {

    private final Stack<Message> stack = new Stack<>();

    @Override
    public void accept(Message message) {
        message.clear();
        synchronized (stack) {
            stack.add(message);
        }
    }

    @Override
    public Message get() {
        synchronized (stack) {
            if(stack.isEmpty()) {
                return new MessageImpl();
            } else {
                return stack.pop();
            }
        }
    }
}
