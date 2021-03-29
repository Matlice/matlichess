package it.matlice.matlichess.controller.net;

/**
 * This packet signals an error has occured with a previous received packet.
 * This is fired if an invalid move is sent to the counterpart or any other exception in the engine.
 */
public class ComError extends Exception implements ComPacket {
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
