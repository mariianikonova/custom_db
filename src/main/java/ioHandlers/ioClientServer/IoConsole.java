package ioHandlers.ioClientServer;

import operations.operationDeterminators.CrudDeterminator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created on 30.01.15.
 */
public class IoConsole {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int inputDef = 0;
        while (inputDef == 0) {
            System.out.print("Select Operations (create/retrieve/update/delete):  ");
            String str = br.readLine();
            inputDef = CrudDeterminator.handleInput(str);
        }
    }


}