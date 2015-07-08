package com.practice;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import com.google.common.base.Function;
import com.google.common.base.Objects;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import com.google.common.collect.Ordering;
import com.google.common.collect.Sets;

public class CustomNetworkApi {

	private static final Logger log = LoggerFactory.getLogger(CustomNetwork.class);
	
	public List<Network> getNetworks() {
		List<Network> nets = new ArrayList<Network>();
		List<String> subnet = new ArrayList<String>();
		nets.add(new Network("1", "netC", Network.Status.ACTIVE, subnet, true));
		nets.add(new Network("2", "netA", Network.Status.ACTIVE, subnet, false));
		nets.add(new Network("3", "netT", Network.Status.ACTIVE, subnet, true));
		return nets;
	}
	
	public List<Network> getNetworksSortedByName() {
		List<Network> networks = getNetworks();
		if (networks != null && !networks.isEmpty()) {
			networks = Ordering.natural().nullsFirst().onResultOf(new Function<Network, String>() {
				public String apply(Network net) {
					return net.getName();
				}
			}).immutableSortedCopy(networks);
		}
		return networks;
	}
	
	public List<Network> getCustomNetworks() {
		List<Network> networks = getNetworks();
		customNetworks(networks);
		return networks;
	}
	
	public List<Network> getNetworks(final boolean available) {
		List<Network> networks = getNetworks();
		
		final boolean isAvailable = available;
		Predicate<Network> predicate = new Predicate<Network>() {
	        @Override
	        public boolean apply(Network net) {
	        	if (net.isAvailable) {
	        		return isAvailable;
	        	} else {
	        		return !isAvailable;
	        	}
	        }
	    };
	    return FluentIterable.from(networks).filter(predicate).toList();
	}
	
	public Network getNetwork(String networkId) {
		Network network = getNetwork(networkId);
		customNetworks(network);
		return network;
	}
	
	protected void customNetworks(Object networks) {
		List<Computer> pms = getComputers();
		if (pms == null || pms.isEmpty()) return;
		Map<String, Computer> netPmMap = Maps.uniqueIndex(pms, new Function<Computer, String>() {
	          public String apply(Computer pm) {
	            return pm.getNetworkId();
	          }
	    });
		if (networks instanceof Network) {
			customNetwork((Network) networks, netPmMap);
		} else {
			List<Network> nets = (ArrayList<Network>) networks;
			for (Network net : nets) {
				customNetwork(net, netPmMap);
			}
		}
	}
	
	protected List<Computer> getComputers() {
		List<Computer> cptr = new ArrayList<Computer>();
		cptr.add(new Computer("Apple1", "Mac", "1", "192.168.1.100", 2, 2));
		cptr.add(new Computer("Ninja1", "Windows", "2", "192.168.1.101", 2, 2));
		cptr.add(new Computer("Pinki1", "Linux", "3", "192.168.1.102", 2, 5));
		return cptr;
	}
	
	protected void customNetwork(Network network, Map<String, Computer> netInstMap) {
		NetworkTo networkTo = (NetworkTo) network;
		Computer pm = netInstMap.get(network.getId());
		if (pm != null) {
			networkTo.getComputers().put(pm.getName(), pm.getIpAddress());
		}
	}

	class NetworkTo extends Network {
		
		private Map<String, String> computers;
		
		public String toString() {
			return Objects.toStringHelper(this).omitNullValues()
					.add("computers", computers)
					.addValue(super.toString()).addValue("\n").toString();
		}

		public Map<String, String> getComputers() {
			return computers;
		}

		public void setComputers(Map<String, String> computers) {
			this.computers = computers;
		}
	}
	
	class Computer {

		private String name;

		private String osType;

		private String networkId;
		
		private String ipAddress;
		
		private Integer memory;   // GB
		
		private Integer diskSize; // TB
		
		public Computer() {};
		
		public Computer(String name, String osType, String networkId, String ip, Integer memory, Integer diskSize) {
			this.name = name;
			this.osType = osType;
			this.networkId = networkId;
			this.ipAddress = ip;
			this.memory = memory;
			this.diskSize = diskSize;
		}
		
		public String toString() {
			return Objects.toStringHelper(this).omitNullValues()
					.add("name", name).add("osType", osType)
					.add("networkId", networkId).add("ipAddress", ipAddress)
					.add("memory", memory).add("diskSize", diskSize)
					.addValue("\n").toString();
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getOsType() {
			return osType;
		}

		public void setOsType(String osType) {
			this.osType = osType;
		}

		public String getNetworkId() {
			return networkId;
		}

		public void setNetworkId(String networkId) {
			this.networkId = networkId;
		}

		public String getIpAddress() {
			return ipAddress;
		}

		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}

		public Integer getMemory() {
			return memory;
		}

		public void setMemory(Integer memory) {
			this.memory = memory;
		}

		public Integer getDiskSize() {
			return diskSize;
		}

		public void setDiskSize(Integer diskSize) {
			this.diskSize = diskSize;
		}
	}
}
