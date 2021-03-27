package it.matlice.matlichess.controller.net;

/**
 * nop
 */
public class Nop implements ComPacket{

    @Override
    public String getPacketType() {
        return "NOP";
    }
}
