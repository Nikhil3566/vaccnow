package org.nagarro.vaccine.enums;

public enum PaymentMethod {
	CASH("cash"), CREDIT("credit"), FAWRY("fawry");

	private String paymentType;

	PaymentMethod(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentType() {
		return paymentType;
	}
}
