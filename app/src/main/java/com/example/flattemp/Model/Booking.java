package com.example.flattemp.Model;

public class Booking {
    String booking_id,facility,mem_id, mem_name, mem_phone_num,booked_date, booking_reason,booked_status;

    public Booking() {
    }

    public Booking(String booking_id, String facility, String mem_id, String mem_name, String mem_phone_num, String booked_date, String booking_reason, String booked_status) {
        this.booking_id = booking_id;
        this.facility = facility;
        this.mem_id = mem_id;
        this.mem_name = mem_name;
        this.mem_phone_num = mem_phone_num;
        this.booked_date = booked_date;
        this.booking_reason = booking_reason;
        this.booked_status = booked_status;
    }

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
    }

    public String getFacility() {
        return facility;
    }

    public void setFacility(String facility) {
        this.facility = facility;
    }

    public String getMem_id() {
        return mem_id;
    }

    public void setMem_id(String mem_id) {
        this.mem_id = mem_id;
    }

    public String getMem_name() {
        return mem_name;
    }

    public void setMem_name(String mem_name) {
        this.mem_name = mem_name;
    }

    public String getMem_phone_num() {
        return mem_phone_num;
    }

    public void setMem_phone_num(String mem_phone_num) {
        this.mem_phone_num = mem_phone_num;
    }

    public String getBooked_date() {
        return booked_date;
    }

    public void setBooked_date(String booked_date) {
        this.booked_date = booked_date;
    }

    public String getBooking_reason() {
        return booking_reason;
    }

    public void setBooking_reason(String booking_reason) {
        this.booking_reason = booking_reason;
    }

    public String getBooked_status() {
        return booked_status;
    }

    public void setBooked_status(String booked_status) {
        this.booked_status = booked_status;
    }
}
