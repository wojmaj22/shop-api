package pl.majchrzw.shopapi.model;

import lombok.Data;


public enum OrderStatus {
	NEW,
	SHIPPED,
	CANCELED,
	CLOSED
}
