DROP TABLE IF EXISTS db.shipment;

CREATE TABLE db.shipment(
	id bigserial PRIMARY KEY,
	user_id INT GENERATED ALWAYS AS IDENTITY,
	supplier_id INT GENERATED ALWAYS AS IDENTITY,
	carrier_id INT GENERATED ALWAYS AS IDENTITY,
	item_list text[],
	tracking_number varchar(255),
	notes varchar(255),
	trailer_number varchar(255),
	CONSTRAINT fk_user
		FOREIGN KEY(user_id)
			REFERENCES db.user(id),
	CONSTRAINT fk_supplier
		FOREIGN KEY(supplier_id)
			REFERENCES db.supplier(id),
	CONSTRAINT fk_carrier
		FOREIGN KEY(carrier_id)
			REFERENCES db.carrier(id)