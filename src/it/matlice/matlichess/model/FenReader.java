package it.matlice.matlichess.model;

import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.model.pieces.*;

public enum FenReader {
    PIECE_POSITION,
    TURN,
    CASTLING,
    EN_PASSANT,
    SEMIMOVES,
    MOVES,
    FINISHED;

    int cur_rank = 7;
    int cur_col = 0;
    String location_partial = "";

    FenReader action(Chessboard c, char character) {
        switch (this) {
            case PIECE_POSITION:
                switch (character) {
                    case 'r':
                    case 'R':
                        c.setPiece(new Rook((character & 0x20) != 0 ? PieceColor.BLACK : PieceColor.WHITE), new Location(cur_col++, cur_rank));
                        break;
                    case 'q':
                    case 'Q':
                        c.setPiece(new Queen((character & 0x20) != 0 ? PieceColor.BLACK : PieceColor.WHITE), new Location(cur_col++, cur_rank));
                        break;
                    case 'n':
                    case 'N':
                        c.setPiece(new Knight((character & 0x20) != 0 ? PieceColor.BLACK : PieceColor.WHITE), new Location(cur_col++, cur_rank));
                        break;
                    case 'b':
                    case 'B':
                        c.setPiece(new Bishop((character & 0x20) != 0 ? PieceColor.BLACK : PieceColor.WHITE), new Location(cur_col++, cur_rank));
                        break;
                    case 'p':
                    case 'P':
                        c.setPiece(new Pawn((character & 0x20) != 0 ? PieceColor.BLACK : PieceColor.WHITE), new Location(cur_col++, cur_rank));
                        break;
                    case 'k':
                    case 'K':
                        c.setKing(new King((character & 0x20) != 0 ? PieceColor.BLACK : PieceColor.WHITE), new Location(cur_col++, cur_rank));
                        break;
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                        this.cur_col += Integer.parseInt(String.valueOf(character));
                        break;
                    case '/':
                        this.cur_col = 0;
                        this.cur_rank -= 1;
                        break;
                    case ' ':
                        return FenReader.TURN;
                }
                break;
            case TURN:
                if (character == 'b') c.setTurn(PieceColor.BLACK);
                else if (character == 'w') c.setTurn(PieceColor.WHITE);
                else if (character == ' ') return FenReader.CASTLING;
                break;
            case CASTLING:
                //todo
                if (character == ' ') return FenReader.EN_PASSANT;
                break;
            case EN_PASSANT:
                if (character == ' ') {
                    if (!this.location_partial.equals("-"))
                        c.setEnPassantTargetSquare(new Location(this.location_partial));
                    return FenReader.SEMIMOVES;
                } else {
                    this.location_partial += character;
                }
                break;
            case SEMIMOVES:
                //todo
                return FenReader.MOVES;
            case MOVES:
                //todo
                return FenReader.FINISHED;
            case FINISHED:
            default:
                break;
        }
        return this;
    }
}
