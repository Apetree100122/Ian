package com.walkertribe.ian.protocol.core.singleseat;

import com.walkertribe.ian.enums.ConnectionType;
import com.walkertribe.ian.iface.PacketFactory;
import com.walkertribe.ian.iface.PacketFactoryRegistry;
import com.walkertribe.ian.iface.PacketReader;
import com.walkertribe.ian.iface.PacketWriter;
import com.walkertribe.ian.protocol.ArtemisPacket;
import com.walkertribe.ian.protocol.ArtemisPacketException;
import com.walkertribe.ian.protocol.BaseArtemisPacket;
import com.walkertribe.ian.protocol.PacketType;
import com.walkertribe.ian.protocol.core.CorePacketType;
import com.walkertribe.ian.util.TextUtil;

/**
 * Sent by a single-seat craft client to give updates on its position, heading,
 * etc. 
 * @author rjwalker
 */
public class SingleSeatPilotPacket extends BaseArtemisPacket {
    private static final PacketType TYPE = CorePacketType.VALUE_FLOAT;
    private static final byte SUBTYPE = 0x07;
    private static final byte[] DEFAULT_UNKNOWN = TextUtil.hexToByteArray("0000803f"); // 1.0f

	public static void register(PacketFactoryRegistry registry) {
		registry.register(ConnectionType.CLIENT, TYPE, SUBTYPE,
				new PacketFactory() {
			@Override
			public Class<? extends ArtemisPacket> getFactoryClass() {
				return SingleSeatPilotPacket.class;
			}

			@Override
			public ArtemisPacket build(PacketReader reader)
					throws ArtemisPacketException {
				return new SingleSeatPilotPacket(reader);
			}
		});
	}

    private int mObjectId;
	private float mRudder = 0.5f;
	private float mX;
	private float mY;
	private float mZ;
	private float mOrientX;
	private float mOrientY;
	private float mOrientZ;
	private float mOrientW;

	public SingleSeatPilotPacket(int objectId) {
		super(ConnectionType.CLIENT, TYPE);
		mObjectId = objectId;
	}

	private SingleSeatPilotPacket(PacketReader reader) {
		super(ConnectionType.CLIENT, TYPE);
		reader.skip(4); // subtype
		mRudder = reader.readFloat();
		mObjectId = reader.readInt();
		reader.readUnknown("UNKNOWN", 4);
		mX = reader.readFloat();
		mY = reader.readFloat();
		mZ = reader.readFloat();
		mOrientX = reader.readFloat();
		mOrientY = reader.readFloat();
		mOrientZ = reader.readFloat();
		mOrientW = reader.readFloat();
	}

	/**
	 * The ID of the single-seat craft, as showing in BayStatusPacket.
	 */
	public int getObjectId() {
		return mObjectId;
	}

	/**
	 * The position of the single-seat craft's rudder. This is a value from 0.0
	 * to 1.0 inclusive, where 0.0 is hard to port, 0.5 is rudder amidships,
	 * and 1.0 is hard to starboard.
	 */
	public float getRudder() {
		return mRudder;
	}

	public void setRudder(float rudder) {
		mRudder = rudder;
	}

	/**
	 * The craft's X-coordinate
	 */
	public float getX() {
		return mX;
	}

	public void setX(float x) {
		mX = x;
	}

	/**
	 * The craft's Y-coordinate
	 */
	public float getY() {
		return mY;
	}

	public void setY(float y) {
		mY = y;
	}

	/**
	 * The craft's Z-coordinate
	 */
	public float getZ() {
		return mZ;
	}

	public void setZ(float z) {
		mZ = z;
	}

	/**
	 * The X component of the quarternion representing the ship's orientation.
	 */
	public float getOrientX() {
		return mOrientX;
	}

	public void setOrientX(float orientX) {
		mOrientX = orientX;
	}

	/**
	 * The Y component of the quarternion representing the ship's orientation.
	 */
	public float getOrientY() {
		return mOrientY;
	}

	public void setOrientY(float orientY) {
		mOrientY = orientY;
	}

	/**
	 * The Z component of the quarternion representing the ship's orientation.
	 */
	public float getOrientZ() {
		return mOrientZ;
	}

	public void setOrientZ(float orientZ) {
		mOrientZ = orientZ;
	}

	/**
	 * The W component of the quarternion representing the ship's orientation.
	 */
	public float getOrientW() {
		return mOrientW;
	}

	public void setOrientW(float orientW) {
		mOrientW = orientW;
	}

	@Override
	protected void writePayload(PacketWriter writer) {
		writer
			.writeInt(SUBTYPE)
			.writeFloat(mRudder)
			.writeInt(mObjectId)
			.writeUnknown("UNKNOWN", DEFAULT_UNKNOWN)
			.writeFloat(mX)
			.writeFloat(mY)
			.writeFloat(mZ)
			.writeFloat(mOrientX)
			.writeFloat(mOrientY)
			.writeFloat(mOrientZ)
			.writeFloat(mOrientW);
	}

	@Override
	protected void appendPacketDetail(StringBuilder b) {
		b	.append('#').append(mObjectId)
			.append(" rudder=").append(mRudder)
			.append(" pos=(").append(mX).append(',').append(mY).append(',').append(mZ)
			.append(") orient=(").append(mOrientX).append(',').append(mOrientY).append(',')
			.append(mOrientZ).append(',').append(mOrientW).append(')');
	}
}
