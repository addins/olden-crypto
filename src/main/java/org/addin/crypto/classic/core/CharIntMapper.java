package org.addin.crypto.classic.core;

/**
 *
 * @author addin <addins3009@gmail.com>
 */
public class CharIntMapper {

    public static int getIntRepresentative(char c) {
        if (c == 'a' || c == 'A') {
            return 0;
        }
        if (c == 'b' || c == 'B') {
            return 1;
        }
        if (c == 'c' || c == 'C') {
            return 2;
        }
        if (c == 'd' || c == 'D') {
            return 3;
        }
        if (c == 'e' || c == 'E') {
            return 4;
        }
        if (c == 'f' || c == 'F') {
            return 5;
        }
        if (c == 'g' || c == 'G') {
            return 6;
        }
        if (c == 'h' || c == 'H') {
            return 7;
        }
        if (c == 'i' || c == 'I') {
            return 8;
        }
        if (c == 'j' || c == 'J') {
            return 9;
        }
        if (c == 'k' || c == 'K') {
            return 10;
        }
        if (c == 'l' || c == 'L') {
            return 11;
        }
        if (c == 'm' || c == 'M') {
            return 12;
        }
        if (c == 'n' || c == 'N') {
            return 13;
        }
        if (c == 'o' || c == 'O') {
            return 14;
        }
        if (c == 'p' || c == 'P') {
            return 15;
        }
        if (c == 'q' || c == 'Q') {
            return 16;
        }
        if (c == 'r' || c == 'R') {
            return 17;
        }
        if (c == 's' || c == 'S') {
            return 18;
        }
        if (c == 't' || c == 'T') {
            return 19;
        }
        if (c == 'u' || c == 'U') {
            return 20;
        }
        if (c == 'v' || c == 'V') {
            return 21;
        }
        if (c == 'w' || c == 'W') {
            return 22;
        }
        if (c == 'x' || c == 'X') {
            return 23;
        }
        if (c == 'y' || c == 'Y') {
            return 24;
        }
        if (c == 'z' || c == 'Z') {
            return 25;
        }
        return -27;
    }

    public static char getCharRepresentative(int i, boolean ucase) {
        if (i == 0) {
            return ucase ? 'A' : 'a';
        }
        if (i == 1) {
            return ucase ? 'B' : 'b';
        }
        if (i == 2) {
            return ucase ? 'C' : 'c';
        }
        if (i == 3) {
            return ucase ? 'D' : 'd';
        }
        if (i == 4) {
            return ucase ? 'E' : 'e';
        }
        if (i == 5) {
            return ucase ? 'F' : 'f';
        }
        if (i == 6) {
            return ucase ? 'G' : 'g';
        }
        if (i == 7) {
            return ucase ? 'H' : 'h';
        }
        if (i == 8) {
            return ucase ? 'I' : 'i';
        }
        if (i == 9) {
            return ucase ? 'J' : 'j';
        }
        if (i == 10) {
            return ucase ? 'K' : 'k';
        }
        if (i == 11) {
            return ucase ? 'L' : 'l';
        }
        if (i == 12) {
            return ucase ? 'M' : 'm';
        }
        if (i == 13) {
            return ucase ? 'N' : 'n';
        }
        if (i == 14) {
            return ucase ? 'O' : 'o';
        }
        if (i == 15) {
            return ucase ? 'P' : 'p';
        }
        if (i == 16) {
            return ucase ? 'Q' : 'q';
        }
        if (i == 17) {
            return ucase ? 'R' : 'r';
        }
        if (i == 18) {
            return ucase ? 'S' : 's';
        }
        if (i == 19) {
            return ucase ? 'T' : 't';
        }
        if (i == 20) {
            return ucase ? 'U' : 'u';
        }
        if (i == 21) {
            return ucase ? 'V' : 'v';
        }
        if (i == 22) {
            return ucase ? 'W' : 'w';
        }
        if (i == 23) {
            return ucase ? 'X' : 'x';
        }
        if (i == 24) {
            return ucase ? 'Y' : 'y';
        }
        if (i == 25) {
            return ucase ? 'Z' : 'z';
        }
        return '*';
    }
}
