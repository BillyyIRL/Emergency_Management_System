package com.EMS.EMS.enums;

public enum RequestStatus {
    PENDING,       // request created, not yet assigned
    ASSIGNED,      // assigned to a hospital
    ACCEPTED,      // hospital accepted
    REJECTED,      // hospital rejected
    COMPLETED      // patient arrived
}