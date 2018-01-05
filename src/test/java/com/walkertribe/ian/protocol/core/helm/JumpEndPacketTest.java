package com.walkertribe.ian.protocol.core.helm;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import com.walkertribe.ian.enums.ConnectionType;
import com.walkertribe.ian.protocol.AbstractPacketTester;

public class JumpEndPacketTest extends AbstractPacketTester<JumpEndPacket> {
	@Test
	public void test() {
		execute("core/helm/JumpEndPacket.txt", ConnectionType.SERVER, 1);
	}

	@Test
	public void testConstruct() {
		new JumpEndPacket();
	}

	@Override
	protected void testPackets(List<JumpEndPacket> packets) {
		Assert.assertEquals(1, packets.size());
	}
}
