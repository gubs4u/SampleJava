/**
 * 
 */
package com.gubs.springTutorial;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * @author gubs
 * 
 */
public class SpringCollection {

	private List<String> addressList;
	private Set<String> addressSet;
	private Map<String, String> addressMap;
	private List<String> csvHeaders;
	private Properties addressProp;

	public List<String> getAddressList() {
		return addressList;
	}

	public void setAddressList(List<String> addressList) {
		this.addressList = addressList;
	}

	public Set<String> getAddressSet() {
		return addressSet;
	}

	public void setAddressSet(Set<String> addressSet) {
		this.addressSet = addressSet;
	}

	public Map<String, String> getAddressMap() {
		return addressMap;
	}

	public void setAddressMap(Map<String, String> addressMap) {
		this.addressMap = addressMap;
	}

	public Properties getAddressProp() {
		return addressProp;
	}

	public void setAddressProp(Properties addressProp) {
		this.addressProp = addressProp;
	}

	public List<String> getCsvHeaders() {
		return csvHeaders;
	}

	public void setCsvHeaders(List<String> csvHeaders) {
		this.csvHeaders = csvHeaders;
	}
}
