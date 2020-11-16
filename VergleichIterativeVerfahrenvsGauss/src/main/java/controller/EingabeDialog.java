package controller;
import algorithmen.GaußElimination;
import algorithmen.GaußSeidel;
import algorithmen.Jacobi;
import algorithmen.SOR;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.StringTokenizer;

public class EingabeDialog {
    public void startEingabe() throws IOException {
        String strInput = null;

        // Ausgabe eines Texts zur Begruessung
        System.out.println("c/o Thanh Ha Dinh in 2020\n");

        // Initialisierung des Eingabe-View
        BufferedReader input = new BufferedReader( new InputStreamReader(System.in ) );
        while ( true ) {
            try {
                System.out.print("> ");
                strInput = input.readLine();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            String[] strings = strInput.split(" ");

            // 	Falls 'help' eingegeben wurde, werden alle Befehle ausgedruckt
            if ( strings[0].equals("help") ) {
                System.out.println("Folgende Befehle stehen zur Verfuegung: help, jacobi, gauß-seidel, sor");
            }
            else {
                int n;
                double[][] M;

                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                PrintWriter writer = new PrintWriter(System.out, true);

                System.out.println("Geben Sie die Anzahl der Variablen von linearen Gleichungssystemen ein:");
                n = Integer.parseInt(reader.readLine());
                M = new double[n][n+1];
                System.out.println("Geben Sie die erweiterte Matrix(A|b) ein:");

                for (int i = 0; i < n; i++) {
                    StringTokenizer strtk = new StringTokenizer(reader.readLine());

                    while (strtk.hasMoreTokens())
                        for (int j = 0; j < n + 1 && strtk.hasMoreTokens(); j++)
                            M[i][j] = Double.parseDouble(strtk.nextToken());
                }

                if(strings[0].equals("gauß-seidel")) {
                    GaußSeidel gausSeidel = new GaußSeidel(M);

                    if (gausSeidel.checkDominant()) {
                        writer.println("Gauß-Seidel-Verfahren konvergieren weil A diagonaldominant ist");
                    }
                    else {
                        writer.println("Gauß-Seidel-Verfahren kann keine Konvergenz garantieren weil A nicht diagonaldominant ist");

                    }

                    writer.println();
                    gausSeidel.print();
                    gausSeidel.solve();
                }
                else if(strings[0].equals("sor")) {
                    SOR sor = new SOR(M);
                    sor.solveopti();
                }
                else if(strings[0].equals("gauß")) {
                    GaußElimination gauß = new GaußElimination(M);
                    gauß.lsolve();
                }
                else if(strings[0].equals("sor-unterrelaxation")) {
                    SOR sor = new SOR(M);
                    sor.solveunter();
                }
                else if(strings[0].equals("sor-überrelaxation")) {
                    SOR sor = new SOR(M);
                    sor.solveuber();
                }
                else if(strings[0].equals("jacobi")) {
                    Jacobi jacobi = new Jacobi(M);
                    jacobi.solve();
                }
            }

        }

    }
}
