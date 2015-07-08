package com.practice;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonValue;

class Network {
		
	private String id;
		
	private String name;
		
	private Status status;
		
	private List<String> subnets;
		
	private Boolean isAvailable;
		
	public Network() {
	}
		
	public Network(String id, String name, Status status, List<String> subnets, Boolean isAvailable) {
		this.id = id;
		this.name = name;
		this.status = status;
		this.subnets = subnets;
		this.isAvailable = isAvailable;
	}
		
	public String toString() {
		return Objects.toStringHelper(this).omitNullValues()
				.add("id",  id).add("name", name)
				.add("status", status).add("subnets", subnets)
				.add("isAvailable", isAvailable)
				.addValue(super.toString()).addValue("\n").toString();
	}
		
	@Override
	public int hashCode() {
		return Objects.hashCode(this.id);
	}
		
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Network other = (Network) obj;
		if (!Objects.equal(this.id, other.id)) {
			return false;
		}
		return true;
	}
		
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public List<String> getSubnets() {
		return subnets;
	}

	public void setSubnets(List<String> subnets) {
		this.subnets = subnets;
	}
		
	public static enum Status {
		ACTIVE("active"), 
		BUILD("build"), 
		DOWN("down"), 
		ERROR("error"), 
		UNRECOGNIZED("unrecognized");

		private final String value;

		private Status(String value) {
			this.value = value;
		}

		@JsonValue
		public String getValue() {
			return value;
		}

		@Override
		public String toString() {
			return value;
		}
	}
}