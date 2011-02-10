package de.core23.javapicross.model;

import java.util.ArrayList;

public class Level {
    private int _number;

    private boolean[][] _block;

    private FieldStatus[][] _status;

    private int _size;

    public Level(int size) {
        this(new boolean[size][size]);
    }

    public Level(boolean[][] block) {
        _size = block.length;
        _block = block;

        _status = new FieldStatus[_size][_size];
        for (int x = 0; x < _size; x++)
            for (int y = 0; y < _size; y++)
                _status[x][y] = FieldStatus.HIDDEN;
    }

    public Level(int number, boolean[][] map) {
        this(map);

        _number = number;
    }

    public int getSize() {
        return _size;
    }

    public int getNumber() {
        return _number;
    }

    public FieldStatus getStatus(int x, int y) {
        if (x < 0 || y < 0 || x >= _size || y >= _size)
            return null;
        return _status[x][y];
    }

    public boolean isBlock(int x, int y) {
        return _block[x][y];
    }

    public boolean setMarkedStatus(int x, int y) {
        if (x < 0 || y < 0 || x >= _size || y >= _size)
            return false;
        if (_status[x][y] == FieldStatus.MARKED)
            _status[x][y] = FieldStatus.HIDDEN;
        else if (_status[x][y] == FieldStatus.HIDDEN)
            _status[x][y] = FieldStatus.MARKED;
        else
            return false;
        return true;
    }

    public boolean setVisibleStatus(int x, int y) {
        if (x < 0 || y < 0 || x >= _size || y >= _size)
            return false;
        if (_status[x][y] == FieldStatus.HIDDEN || _status[x][y] == FieldStatus.MARKED) {
            if (_block[x][y])
                _status[x][y] = FieldStatus.BLOCK;
            else
                _status[x][y] = FieldStatus.FAILURE;
            return true;
        }
        return false;
    }

    public FieldStatus show(int x, int y) {
        if (x < 0 || y < 0 || x >= _size || y >= _size)
            return null;
        if (_status[x][y] != FieldStatus.FAILURE) {
            if (_block[x][y])
                _status[x][y] = FieldStatus.BLOCK;
            else
                _status[x][y] = FieldStatus.MARKED;
        }
        return _status[x][y];
    }

    public boolean isSolved() {
        for (int x = 0; x < _size; x++) {
            for (int y = 0; y < _size; y++) {
                if (_block[x][y] && _status[x][y] != FieldStatus.BLOCK)
                    return false;
            }
        }
        return true;
    }

    public int[][] getColumns() {
        int[][] columns = new int[_size][];

        for (int x = 0; x < _size; x++) {
            ArrayList<Integer> list = new ArrayList<Integer>();

            int count = 0;
            for (int y = 0; y < _size; y++) {
                if (_block[x][y])
                    count++;
                else {
                    if (count > 0)
                        list.add(count);
                    count = 0;
                }
            }
            if (count > 0)
                list.add(count);

            columns[x] = new int[list.size()];
            for (int i = 0; i < list.size(); i++)
                columns[x][i] = list.get(i);
        }

        return columns;
    }

    public int[][] getRows() {
        int[][] rows = new int[_size][];

        for (int y = 0; y < _size; y++) {
            ArrayList<Integer> list = new ArrayList<Integer>();

            int count = 0;
            for (int x = 0; x < _size; x++) {
                if (_block[x][y])
                    count++;
                else {
                    if (count > 0)
                        list.add(count);
                    count = 0;
                }
            }
            if (count > 0)
                list.add(count);

            rows[y] = new int[list.size()];
            for (int i = 0; i < list.size(); i++)
                rows[y][i] = list.get(i);
        }

        return rows;
    }

    public boolean[][] getBlock() {
        return _block;
    }

    public boolean getBlock(int x, int y) {
        return _block[x][y];
    }

    public boolean isValidBlock(int x, int y) {
        return !(x < 0 || y < 0 || x >= _size || y >= _size);
    }

    public void setBlock(int x, int y, boolean block) {
        _block[x][y] = block;
    }

    @Override
    public String toString() {
        return String.valueOf(getNumber() + 1);
    }
}
