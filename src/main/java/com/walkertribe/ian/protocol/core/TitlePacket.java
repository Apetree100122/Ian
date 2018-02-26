package com.walkertribe.ian.protocol.core;

import com.walkertribe.ian.enums.Origin;
import com.walkertribe.ian.iface.PacketReader;
import com.walkertribe.ian.iface.PacketWriter;
import com.walkertribe.ian.protocol.BaseArtemisPacket;
import com.walkertribe.ian.protocol.Packet;

/**
 * Displays a title message on the main screen. This is transmitted in response
 * to a <code>&lt;big_message&gt;</code> tag in a scripted mission.
 * @author rjwut
 */
@Packet(origin = Origin.SERVER, type = CorePacketType.BIG_MESS)
public class TitlePacket extends BaseArtemisPacket {
	private CharSequence mTitle;
	private CharSequence mSubtitle1;
	private CharSequence mSubtitle2;

	public TitlePacket(CharSequence title, CharSequence subtitle1, CharSequence subtitle2) {
		mTitle = title;
		mSubtitle1 = subtitle1;
		mSubtitle2 = subtitle2;
	}

	public TitlePacket(PacketReader reader) {
		mTitle = reader.readString();
		mSubtitle1 = reader.readString();
		mSubtitle2 = reader.readString();
	}

	public CharSequence getTitle() {
		return mTitle;
	}

	public CharSequence getSubtitle1() {
		return mSubtitle1;
	}

	public CharSequence getSubtitle2() {
		return mSubtitle2;
	}

	@Override
	protected void writePayload(PacketWriter writer) {
		writer.writeString(mTitle);
		writer.writeString(mSubtitle1);
		writer.writeString(mSubtitle2);
	}

	@Override
	protected void appendPacketDetail(StringBuilder b) {
		b.append(mTitle).append('\n').append(mSubtitle1).append('\n').append(mSubtitle2);
	}

}
