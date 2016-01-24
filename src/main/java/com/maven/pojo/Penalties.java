package com.maven.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class Penalties {
    private Integer paymentno;

    private Integer playerno;

    private Date paymentDate;

    private BigDecimal amount;

    public Integer getPaymentno() {
        return paymentno;
    }

    public void setPaymentno(Integer paymentno) {
        this.paymentno = paymentno;
    }

    public Integer getPlayerno() {
        return playerno;
    }

    public void setPlayerno(Integer playerno) {
        this.playerno = playerno;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}