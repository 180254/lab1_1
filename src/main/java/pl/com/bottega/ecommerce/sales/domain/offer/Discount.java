package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;

public class Discount {

	private String discountCause;
	private BigDecimal discount;

	public Discount(BigDecimal discount, String discountCause) {
		super();
		this.discount = discount;
		this.discountCause = discountCause;
	}

	public Discount(BigDecimal discount) {
		this(discount, null);
	}

	public String getDiscountCause() {
		return discountCause;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((discount == null) ? 0 : discount.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Discount other = (Discount) obj;
		if (discount == null) {
			if (other.discount != null)
				return false;
		} else if (!discount.equals(other.discount))
			return false;
		return true;
	}

}