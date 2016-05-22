package com.walkertribe.ian.protocol.core.comm;

import java.util.List;

import org.junit.Assert;

import org.junit.Test;

import com.walkertribe.ian.enums.ConnectionType;
import com.walkertribe.ian.protocol.AbstractPacketTester;

public class ToggleRedAlertPacketTest extends AbstractPacketTester<ToggleRedAlertPacket> {
	@Test
	public void test() {
		execute("core/comm/ToggleRedAlertPacket.txt", ConnectionType.CLIENT, 1);
	}

	@Override
	protected void testPackets(List<ToggleRedAlertPacket> packets) {
		Assert.assertNotNull(packets.get(0));
	}
}