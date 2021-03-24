package it.matlice.matlichess.controller.net;

public class RematchChoice implements ComPacket{

    public final boolean rematch;

    public RematchChoice(boolean rematch) {
        this.rematch = rematch;
    }

    @Override
    public String getPacketType() {
        return "REMATCH";
    }
}
