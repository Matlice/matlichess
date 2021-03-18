package it.matlice.matlichess.controller.net;

public class Nop implements ComPacket{

    @Override
    public String getPacketType() {
        return "NOP";
    }
}
