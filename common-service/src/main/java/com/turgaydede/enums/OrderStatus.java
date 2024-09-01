package com.turgaydede.enums;

public enum OrderStatus {
    CREATED("CREATED"),         // Sipariş oluşturuldu
    CONFIRMED("CONFIRMED"),       // Sipariş envanter kontrolü ve ödeme işlemlerinden geçti.
    CANCELLED("CANCELLED"),       // Sipariş iptal edildi.
    SHIPPED("SHIPPED"),         // Sipariş kargoya verildi.
    DELIVERED("DELIVERED"),       // Sipariş teslim edildi.
    RETURNED("RETURNED"),        // Sipariş iade edildi.
    PAYMENT_CONFIRMED("PAYMENT_CONFIRMED"),  // Ödeme işlemi başarılı oldu.
    FAILED_PAYMENT("FAILED_PAYMENT"),  // Ödeme işlemi başarısız oldu.
    OUT_OF_STOCK("OUT_OF_STOCK"),    // Ürün stokta mevcut değil, sipariş iptal edildi.
    PROCESSING("PROCESSING"),     // Sipariş işleniyor, henüz tamamlanmadı.
    COMPLETED("PROCESSING");     // Sipariş tamamlanmadı.

    private String value;

    OrderStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
