package com.gymreservation.model;

/**
 * 預約紀錄狀態。
 */
public enum ReservationStatus {
    RESERVED,      // 正常保留
    CANCELLED,     // 使用者/管理員取消
    COMPLETED      // 課程結束
}