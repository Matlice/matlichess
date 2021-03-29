package it.matlice.matlichess.model;

import it.matlice.matlichess.Location;
import it.matlice.matlichess.PieceColor;
import it.matlice.matlichess.model.pieces.*;

/**
 * Utility class that reads a FEN
 */
public enum FenReader {
    PIECE_POSITION {
        public FenReader action(Chessboard c, char character) {
            // if it's reading a piece position, if a letter is given then place the piece;
            // if a number is given it should skip that number of cells
            // if a '/' is given it should skip to the next line
            // if a  ' ' is given it should skip to next reading state
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
                    cur_rank = 7;
                    cur_col = 0;
                    partial = "";
                    return FenReader.TURN;
            }
            return this;
        }
    },

    TURN {
        public FenReader action(Chessboard c, char character) {
            // reading a turn, if it's 'w' then it's white turns, same with black
            // ' ' skips to next state
            if (character == 'b') c.setTurn(PieceColor.BLACK);
            else if (character == 'w') c.setTurn(PieceColor.WHITE);
            else if (character == ' ') return FenReader.CASTLING;
            return this;
        }
    },

    CASTLING {
        public FenReader action(Chessboard c, char character) {
            // reading whether castling is available and for which pieces;
            // q stands for queen's side, k for king's side
            // upper case is for white king, lower case for black king
            if (character == ' ') return FenReader.EN_PASSANT;
            return this;
        }
    },

    EN_PASSANT {
        public FenReader action(Chessboard c, char character) {
            // reading the actual en passant cell;
            // if it's not given there should be a '-'
            if (character == ' ') {
                if (!this.partial.equals("-"))
                    c.setEnPassantTargetSquare(new Location(this.partial));
                this.partial = "";
                return FenReader.SEMIMOVES;
            } else {
                this.partial += character;
            }
            return this;
        }
    },

    SEMIMOVES {
        public FenReader action(Chessboard c, char character) {
            // reading semimoves clock as an int
            // ' ' skips to next state
            if (character == ' ') {
                c.setHalfMoveClock(Integer.parseInt(partial));
                this.partial = "";
                return FenReader.MOVES;
            } else {
                this.partial += character;
            }
            return this;
        }
    },

    MOVES {
        public FenReader action(Chessboard c, char character) {
            // reading fullmoves clock as an int
            // ' ' skips to next state
            if (character == ' ') {
                c.setFullMoveNumber(Integer.parseInt(partial));
                this.partial = "";
                return FenReader.FINISHED;
            } else {
                this.partial += character;
            }
            return this;
        }
    },

    FINISHED {
        public FenReader action(Chessboard c, char character) {
            // the reading has finished, do nothing
            return this;
        }
    };

    int cur_rank = 7;
    int cur_col = 0;
    String partial = "";

    /**
     * Method that perform an action based on a given character and on the actual status given by the enum
     *
     * @param c the Chessboard to act on
     * @param character the char to evaluate
     * @return the next state of the fen reader
     */
    public abstract FenReader action(Chessboard c, char character);

}
