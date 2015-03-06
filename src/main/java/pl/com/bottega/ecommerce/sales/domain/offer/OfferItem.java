/*
 * Copyright 2011-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package pl.com.bottega.ecommerce.sales.domain.offer;

import java.math.BigDecimal;

public class OfferItem {

	private ProductSnaphot productSnapshot;

	private int quantity;

	private BigDecimal totalCost;

	private String currency;

	private Discount discount;

	public OfferItem(ProductSnaphot productSnapshot, int quantity) {
		this(productSnapshot, quantity, null);
	}

	public OfferItem(ProductSnaphot productSnapshot, int quantity,
			Discount discount) {

		this.productSnapshot = productSnapshot;
		this.quantity = quantity;
		this.discount = discount;

		BigDecimal discountValue = new BigDecimal(0);
		if (discount != null)
			discountValue = discountValue.subtract(discount.getDiscount());

		this.totalCost = productSnapshot.getProductPrice()
				.multiply(new BigDecimal(quantity)).subtract(discountValue);
	}

	public ProductSnaphot getProductSnapshot() {
		return productSnapshot;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public String getTotalCostCurrency() {
		return currency;
	}

	public BigDecimal getDiscount() {
		return discount.getDiscount();
	}

	public String getDiscountCause() {
		return discount.getDiscountCause();
	}

	public int getQuantity() {
		return quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime
				* result
				+ ((discount.getDiscount() == null) ? 0 : discount
						.getDiscount().hashCode());
		result = prime * result
				+ ((productSnapshot == null) ? 0 : productSnapshot.hashCode());
		result = prime * result + quantity;
		result = prime * result
				+ ((totalCost == null) ? 0 : totalCost.hashCode());
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
		OfferItem other = (OfferItem) obj;
		if (discount.getDiscount() == null) {
			if (other.discount.getDiscount() != null)
				return false;
		} else if (!discount.getDiscount().equals(other.discount.getDiscount()))
			return false;
		if (productSnapshot == null) {
			if (other.productSnapshot != null)
				return false;
		} else if (!productSnapshot.equals(other.productSnapshot))
			return false;
		if (quantity != other.quantity)
			return false;
		if (totalCost == null) {
			if (other.totalCost != null)
				return false;
		} else if (!totalCost.equals(other.totalCost))
			return false;
		return true;
	}

	/**
	 * 
	 * @param item
	 * @param delta
	 *            acceptable percentage difference
	 * @return
	 */
	public boolean sameAs(OfferItem other, double delta) {
		if (productSnapshot == null) {
			if (other.productSnapshot != null)
				return false;
		} else if (!productSnapshot.equals(other.productSnapshot))
			return false;

		if (quantity != other.quantity)
			return false;

		BigDecimal max, min;
		if (totalCost.compareTo(other.totalCost) > 0) {
			max = totalCost;
			min = other.totalCost;
		} else {
			max = other.totalCost;
			min = totalCost;
		}

		BigDecimal difference = max.subtract(min);
		BigDecimal acceptableDelta = max.multiply(new BigDecimal(delta / 100));

		return acceptableDelta.compareTo(difference) > 0;
	}

}
