package lk.ucsc.NovelGeek.model.request;

import java.lang.reflect.Constructor;

public class NewPayment {

    private String merchant_id;
    private String order_id;
    private String payment_id;
    private String payhere_amount;
    private String payhere_currency;
    private int status_code;
    private String method;
    private String status_message;
//    private String custom_1; //firstname
//    private String custom_2; //lastname
//    private String custom_3; //email
//    private String custom_4; //phone
//    private String custom_5; //address
//    private String custom_6; //city
//    private String custom_7; //country
    private String card_holder_name;
    private String card_no;
    private String card_expiry;

    public NewPayment() {
        this.merchant_id = "";
        this.order_id = "";
        this.payment_id = "";
        this.payhere_amount = "";
        this.payhere_currency = "";
        this.status_code = 0;
        this.method = "";
        this.status_message = "";
//        this.custom_1 = "";
//        this.custom_2 = "";
//        this.custom_3 = "";
//        this.custom_4 = "";
//        this.custom_5 = "";
//        this.custom_6 = "";
//        this.custom_7 = "";
        this.card_holder_name = "";
        this.card_no = "";
        this.card_expiry = "";
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getPayhere_amount() {
        return payhere_amount;
    }

    public void setPayhere_amount(String payhere_amount) {
        this.payhere_amount = payhere_amount;
    }

    public String getPayhere_currency() {
        return payhere_currency;
    }

    public void setPayhere_currency(String payhere_currency) {
        this.payhere_currency = payhere_currency;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getStatus_message() {
        return status_message;
    }

    public void setStatus_message(String status_message) {
        this.status_message = status_message;
    }

//    public String getCustom_1() {
//        return custom_1;
//    }
//
//    public void setCustom_1(String custom_1) {
//        this.custom_1 = custom_1;
//    }
//
//    public String getCustom_2() {
//        return custom_2;
//    }
//
//    public void setCustom_2(String custom_2) {
//        this.custom_2 = custom_2;
//    }
//
//    public String getCustom_3() {
//        return custom_3;
//    }
//
//    public void setCustom_3(String custom_3) {
//        this.custom_3 = custom_3;
//    }
//
//    public String getCustom_4() {
//        return custom_4;
//    }
//
//    public void setCustom_4(String custom_4) {
//        this.custom_4 = custom_4;
//    }
//
//    public String getCustom_5() {
//        return custom_5;
//    }
//
//    public void setCustom_5(String custom_5) {
//        this.custom_5 = custom_5;
//    }
//
//    public String getCustom_6() {
//        return custom_6;
//    }
//
//    public void setCustom_6(String custom_6) {
//        this.custom_6 = custom_6;
//    }
//
//    public String getCustom_7() {
//        return custom_7;
//    }
//
//    public void setCustom_7(String custom_7) {
//        this.custom_7 = custom_7;
//    }

    public String getCard_holder_name() {
        return card_holder_name;
    }

    public void setCard_holder_name(String card_holder_name) {
        this.card_holder_name = card_holder_name;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCard_expiry() {
        return card_expiry;
    }

    public void setCard_expiry(String card_expiry) {
        this.card_expiry = card_expiry;
    }
}
