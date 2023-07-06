/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conwaysgameoflife;

import apputilities.AppModel;
import java.util.ArrayList;

/**
 *
 * @author rhill
 */
public class ConwaysGameOfLifeModel extends AppModel {
    private final ArrayList<Step> steps;
    private int rows, cols, numSteps;
    private boolean needsSeed = true;
    
    public ConwaysGameOfLifeModel(int rows, int cols, int numSteps) {
        super();
        this.setRows(rows);
        this.setCols(cols);
        this.setNumSteps(numSteps);
        this.steps = new ArrayList<>();
    }
    
    public boolean needsSeed() {
        return this.needsSeed;
    }
    
    public final void setRows(int r) {
        this.rows = r;
    }
    
    public final void setCols(int c) {
        this.cols = c;
    }
    
    public final void setNumSteps(int n) {
        this.numSteps = n;
    }
    
    public  final int getRows() {
        return this.rows;
    }

    public final int getCols() {
        return this.cols;
    }
    
    public final int getNumSteps() {
        return this.numSteps;
    }
    
    public Step setSeed(int[][] seed) {
        this.needsSeed = false;
        if (seed.length != this.rows || seed[0].length != this.cols) {
            throw new RuntimeException("ERROR: invalid grid dimensions");
        }
        int alive = 0, dead = 0, dalive, ddead;
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                if (seed[i][j] == -1) {
                    dead++;
                } else {
                    alive++;
                }
            }
        }
        dalive = alive;
        ddead = -alive;
        Step step = new Step(this.rows, this.cols, alive, dead, dalive, ddead);
        step.board = seed;
        do {
            this.steps.add(step);
            step = this.getNextStep(step);
        }
        while (step != null);
        return this.getStep(1);
    }
    
    public Step getNextStep(Step step) {
        if (this.steps.size() > this.numSteps) {
            return null;
        }
        
        int[][] next = new int[this.rows][this.cols];
        int alive = 0, dead = 0;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int count = 0;
                for (int a = i-1; a <= i+1; a++) {
                    for (int b = j-1; b <= j+1; b++) {
                        if (a != i || b != j) {
                            if (step.board[a<0?rows-1:(a>rows-1?0:a)][b<0?cols-1:(b>cols-1?0:b)] > -1) {
                                count++;
                            }
                        }
                    }
                }
                if (step.board[i][j] > -1 && (count == 2 || count == 3) 
                    ||  step.board[i][j] == -1 && count == 3) {
                    next[i][j] = step.board[i][j]+1;
                    alive++;
                } else {
                    next[i][j] = -1;
                    dead++;
                }
            }
        }
        Step nextStep = new Step(this.rows, this.cols, alive, dead, alive-step.alive, dead-step.dead);
        nextStep.board = next;
        return nextStep;
    }
    
    public Step getStep(int i) {
        return this.steps.get(i<0?0:i>this.steps.size()-1?this.steps.size()-1:i);
    }
    
    public static class Step {
        public int rows, cols, alive, dead, dalive, ddead;
        public int[][] board; //int represents age of cell in steps 
        
        public Step(int rows, int cols, int alive, int dead, int dalive, int ddead) {
            this.rows = rows;
            this.cols = cols;
            this.alive = alive;
            this.dead = dead;
            this.dalive = dalive;
            this.ddead = ddead;
            this.board = new int[rows][cols];
            this.resetBoard();
        }
        
        private final void resetBoard() {
            for (int i = 0; i < this.rows; i++) {
                for (int j = 0; j < this.cols; j++) {
                    this.board[i][j] = -1;
                }
            }
        }
    }
}
