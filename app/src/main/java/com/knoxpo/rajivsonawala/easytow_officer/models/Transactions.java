package com.knoxpo.rajivsonawala.easytow_officer.models;

public class Transactions {

        public final static  String COLLECTION_NANE="transactions";
        public final static  String DATE="date";
        public final static  String STATUS="status";
        public final static  String TAXAMOUNT="taxamount";
        public final static  String VNUMBER="vehiclenumber";

        private String orderId;
        private String date;
        private String status;
        private String taxAmount;
        private String vehiclenumber;

        public Transactions(String orderId, String date, String status, String taxAmount, String vehiclenumber) {
                this.orderId = orderId;
                this.date = date;
                this.status = status;
                this.taxAmount = taxAmount;
                this.vehiclenumber = vehiclenumber;
        }

        public String getOrderId() {
                return orderId;
        }

        public String getDate() {
                return date;
        }

        public String getStatus() {
                return status;
        }

        public String getTaxAmount() {
                return taxAmount;
        }

        public String getVehiclenumber() {
                return vehiclenumber;
        }

}
