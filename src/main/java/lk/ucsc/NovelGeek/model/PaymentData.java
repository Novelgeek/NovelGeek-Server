package lk.ucsc.NovelGeek.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "PaymentData")
public class PaymentData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long payid;

    private String merchant_id;
    private String order_id;
    private String payment_id;
    private String payhere_amount;
    private String payhere_currency;
    private int status_code;
    private String method;
    private String status_message;

    public long getPayid() {
        return payid;
    }

    public void setPayid(long payid) {
        this.payid = payid;
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
}
