package com.turgaydede.enums;

public enum PaymentStatus {
    PENDING,    // Payment is initiated but not yet processed
    COMPLETED,  // Payment is successfully completed
    FAILED,     // Payment failed due to some error
    REFUNDED,   // Payment was refunded
    CANCELED    // Payment was canceled by the user or the system
}
