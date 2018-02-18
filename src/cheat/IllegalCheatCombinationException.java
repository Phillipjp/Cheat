/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cheat;

/**
 *
 * @author phillipperks
 */
public class IllegalCheatCombinationException extends Exception {

    /**
     * Creates a new instance of <code>IllegalCheatCombinationException</code>
     * without detail message.
     */
    public IllegalCheatCombinationException() {
    }

    /**
     * Constructs an instance of <code>IllegalCheatCombinationException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public IllegalCheatCombinationException(String msg) {
        super(msg);
    }
}
