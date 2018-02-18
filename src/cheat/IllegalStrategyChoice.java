package cheat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author phillipperks
 */
public class IllegalStrategyChoice extends Exception {

    /**
     * Creates a new instance of <code>IllegalStrategyChoice</code> without
     * detail message.
     */
    public IllegalStrategyChoice() {
    }

    /**
     * Constructs an instance of <code>IllegalStrategyChoice</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public IllegalStrategyChoice(String msg) {
        super(msg);
    }
}
