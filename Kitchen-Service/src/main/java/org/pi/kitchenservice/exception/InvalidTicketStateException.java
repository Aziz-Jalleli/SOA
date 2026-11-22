package org.pi.kitchenservice.exception;

import org.pi.kitchenservice.model.TicketState;

public class InvalidTicketStateException extends RuntimeException {

    public InvalidTicketStateException(Long id, TicketState current, String action) {
        super("Cannot " + action + " ticket " + id + " while it is in state " + current);
    }
}
