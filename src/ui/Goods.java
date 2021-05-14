package ui;

public class Goods {
    private int code;
    private String name;
    private int amt;
    private float sellingprice;
    private float purchasingprice;
    private float discount;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmt() {
        return amt;
    }

    public void setAmt(int amt) {
        this.amt = amt;
    }

    public float getSellingprice() {
        return sellingprice;
    }

    public void setSellingprice(float sellingprice) {
        this.sellingprice = sellingprice;
    }

    public float getPurchasingprice() {
        return purchasingprice;
    }

    public void setPurchasingprice(float purchasingprice) {
        this.purchasingprice = purchasingprice;
    }

    public float getDiscount() {
        return discount;
    }

    public void setDiscount(float discount) {
        this.discount = discount;
    }
}
