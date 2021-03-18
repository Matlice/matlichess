package it.matlice.matlichess.controller.net;

import java.io.Serializable;

public interface ComPacket extends Serializable {
    String getPacketType();
}
