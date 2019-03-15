package com.knoxpo.rajivsonawala.easytow_officer.models;

public class Transactions {

        public final static  String COLLECTION_NANE="transactions";
        public final static  String DATE="date";
        public final static  String STATUS="status";
        public final static  String TAXAMOUNT="taxamount";
        public final static  String UID="uid";

        private String orderId;
        private String date;
        private String status;
        private String taxAmount;
        private String uid;

        public Transactions(String orderId, String date, String status, String taxAmount, String uid) {
                this.orderId = orderId;
                this.date = date;
                this.status = status;
                this.taxAmount = taxAmount;
                this.uid = uid;
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

        public String getUid() {
                return uid;
        }

}
