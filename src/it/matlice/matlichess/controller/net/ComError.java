package it.matlice.matlichess.controller.net;

public class ComError extends Exception implements ComPacket{
    /**
     * Constructs a new exception with {@code null} as its detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     */
    public ComError(Exception e) {
        super(e.getMessage());
    }
    public ComError(String e) {
        super(e);
    }

    @Override
    public String getPacketType() {
        return "NET_ERROR";
    }
}
