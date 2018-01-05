package com.walkertribe.ian.protocol.core.helm;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.walkertribe.ian.enums.ConnectionType;
import com.walkertribe.ian.protocol.AbstractPacketTester;

public class HelmToggleReversePacketTest extends AbstractPacketTester<HelmToggleReversePacket> {
	@Test
	public void test() {
		execute("core/helm/HelmToggleReversePacket.txt", ConnectionType.CLIENT, 1);
	}

	@Test
	public void testConstruct() {
		new HelmToggleReversePacket();
	}

	@Override
	protected void testPackets(List<HelmToggleReversePacket> packets) {
		Assert.assertEquals(1, packets.size());
	}
}
