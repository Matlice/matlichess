package it.matlice.matlichess.controller.net;

public class ProtocolErrorException extends RuntimeException {
    public ProtocolErrorException() {
        System.err.println("Protocol error.");
        System.exit(-1);
    }
}
