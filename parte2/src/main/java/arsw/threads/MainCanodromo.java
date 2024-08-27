package arsw.threads;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class MainCanodromo {

    private static Galgo[] galgos;
    private static Canodromo can;
    private static RegistroLlegada reg = new RegistroLlegada();

    public static void main(String[] args) {
        can = new Canodromo(17, 100);
        galgos = new Galgo[can.getNumCarriles()];
        can.setVisible(true);

        // Acción del botón Start
        can.setStartAction(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                //como acción, se crea un nuevo hilo que cree los hilos
                //'galgos', los pone a correr, y luego muestra los resultados.
                //La acción del botón se realiza en un hilo aparte para evitar
                //bloquear la interfaz gráfica.
                ((JButton) e.getSource()).setEnabled(false);
                new Thread() {
                    public void run() {
                        for (int i = 0; i < can.getNumCarriles(); i++) {
                            //crea los hilos 'galgos'
                            galgos[i] = new Galgo(can.getCarril(i), "" + i, reg);
                            //inicia los hilos
                            galgos[i].start();
                        }

                        try {
                            // Esperar a que todos los galgos terminen
                            for (Galgo galgo : galgos) {
                                galgo.join();
                            }
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        
                        can.winnerDialog(reg.getGanador(), reg.getUltimaPosicionAlcanzada() - 1);
                        System.out.println("El ganador fue: " + reg.getGanador());

                        // Volver a habilitar el botón Start
                        ((JButton) e.getSource()).setEnabled(true);
                    }
                }.start();
            }
        });

        can.setStopAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Carrera pausada!");
            }
        });

        can.setContinueAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Carrera reanudada!");
            }
        });
    }
}
