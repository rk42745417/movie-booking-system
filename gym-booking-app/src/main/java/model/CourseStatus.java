package com.gymreservation.model;

/**
 * 課程 (Booking) 目前狀態。
 */
public enum CourseStatus {
    OPEN,          // 可預約
    FULL,          // 額滿
    CANCELLED,     // 已取消
    INACTIVE       // 尚未上架 / 已過期
}