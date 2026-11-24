package com.pi.deliveryservice.dto;

/** courierCin is optional: when absent, an available courier is picked automatically. */
public record AssignCourierRequest(String courierCin) {
}
