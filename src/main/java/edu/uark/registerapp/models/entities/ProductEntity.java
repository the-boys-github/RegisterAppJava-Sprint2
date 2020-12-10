package edu.uark.registerapp.models.entities;

import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.persistence.*;

import org.apache.commons.lang3.StringUtils;


import edu.uark.registerapp.models.api.Product;

@Entity
@Table(name="product")
public class ProductEntity {
    @Id
    @Column(name="id", updatable = false)
    @GeneratedValue(strategy=GenerationType.AUTO)
    private final UUID id;

	public UUID getId() {
		return this.id;
	}

	@Column(name = "lookupcode")
	private String lookupCode;

	public String getLookupCode() {
		return this.lookupCode;
	}

	public ProductEntity setLookupCode(final String lookupCode) {
		this.lookupCode = lookupCode;
		return this;
	}

	@Column(name = "count")
	private int count;

	public int getCount() {
		return this.count;
	}

	public ProductEntity setCount(final int count) {
		this.count = count;
		return this;
	}

	@Column(name = "createdon")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	public Date getCreatedOn() {
		return this.createdOn;
	}

	public Product synchronize(final Product apiProduct) {
		this.setCount(apiProduct.getCount());
		this.setLookupCode(apiProduct.getLookupCode());

		apiProduct.setId(this.getId());
		apiProduct.setCreatedOn(this.getCreatedOn().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

		return apiProduct;
	}

	public ProductEntity() {
		this.count = -1;
		this.id = new UUID(0, 0);
		this.lookupCode = StringUtils.EMPTY;
	}

	public ProductEntity(final String lookupCode, final int count) {
		this.count = count;
		this.id = new UUID(0, 0);
		this.lookupCode = lookupCode;
	}

	public ProductEntity(final Product apiProduct) {
    	this.id = new UUID(0, 0);
		this.count = apiProduct.getCount();
		this.lookupCode = apiProduct.getLookupCode();
	}

	@PrePersist
	void createdAt(){
		this.createdOn = Calendar.getInstance().getTime();
	}
}
