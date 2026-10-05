package com.manukpo10.booking.common;

import java.util.List;

public record ApiError(
        int status,
        String error,
        String message,
        String path,
        List<String> details
) {}