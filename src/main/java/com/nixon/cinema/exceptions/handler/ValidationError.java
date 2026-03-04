package com.nixon.cinema.exceptions.handler;

public record ValidationError(String field, String message) {
}
