package futebol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/*
 import org.apache.commons.lang.builder.ToStringBuilder;
 import org.apache.commons.lang.builder.ToStringStyle;
 */

public class FazTime {

	static final int JOGADORES_POR_TIME = 2;

	static List<Jogador> jogadores = new ArrayList<Jogador>();
	static List<Time> times = new ArrayList<Time>();

	private static int forcaMedia;

	private static void inicializaJogadores() {
		jogadores.add(new Jogador("Tiago Carpanese", 2));
		jogadores.add(new Jogador("Bruno Borges", 5));
		jogadores.add(new Jogador("Felipe Magela", 5));
		jogadores.add(new Jogador("Augusto Nasser", 3));
		jogadores.add(new Jogador("Juan Garay", 3));
		jogadores.add(new Jogador("Diogo M�ximo", 5));
		jogadores.add(new Jogador("Andr� Guedes", 3));
		jogadores.add(new Jogador("Fabiano Rodrigues", 3));
		jogadores.add(new Jogador("Leonardo Pinto", 3));
		jogadores.add(new Jogador("L�o Soares", 4));
		jogadores.add(new Jogador("Diego Berardino", 4));
		jogadores.add(new Jogador("Jean Pereira", 3));

		calculaMedia();

		int totalTimes = jogadores.size() / JOGADORES_POR_TIME;
		for (int i = 0; i < totalTimes; i++) {
			times.add(new Time("Time " + i, JOGADORES_POR_TIME));
		}
	}

	private static void calculaMedia() {
		int forcaTotal = 0;

		for (Jogador j : jogadores) {
			forcaTotal += j.getForca();
		}

		forcaMedia = forcaTotal / jogadores.size();
	}

	static boolean isEquilibrado(Time a, Time b) {
		int diff = Math.abs(a.getForca() - b.getForca());
		return (diff == 0 || diff < 3);
	}

	public static void main(String[] args) {
		inicializaJogadores();
		montaTimes();

		// double percentual = (double) times.size() / (double) total;
		// System.out.println(percentual * 100);
		for (Time time : times) {
			System.out.println(time);
			System.out.println("-----");
		}

		calculaEquilibrio();
	}

	private static void calculaEquilibrio() {
		int forcaMinima = Integer.MAX_VALUE;
		int forcaMaxima = Integer.MIN_VALUE;

		for (Time time : times) {
			forcaMinima = Math.min(time.getForca(), forcaMinima);
			forcaMaxima = Math.max(time.getForca(), forcaMaxima);
		}

		if (forcaMinima < 0.5 * forcaMaxima) {
			System.out.println("Sorteio ** NAO ** equilibrado!!");
		} else {
			System.out.println("Sorteio equilibrado!!");
		}
	}

	private static void montaTimes() {
		// Randomiza a lista de jogadores
		Collections.shuffle(jogadores, new Random(System.currentTimeMillis()));
		Collections.shuffle(times, new Random(System.currentTimeMillis()));

		Iterator<Time> itTime = times.iterator();
		while (jogadores.size() > 0) {
			Time timeDaVez = itTime.next();
			if (timeDaVez.isCompleto()) {
				continue;
			}

			Jogador j = getJogador(timeDaVez);
			timeDaVez.addJogador(j);

			if (itTime.hasNext() == false) {
				itTime = times.iterator();
			}
		}
	}

	private static Jogador getJogador(int forca) {
		Jogador jj = null;
		if (jogadores.size() == 0)
			return null;
		else if (jogadores.size() == 1)
			return jogadores.remove(0);

		Iterator<Jogador> it = jogadores.iterator();
		while (it.hasNext()) {
			Jogador j = it.next();
			if (j.getForca() == forca) {
				jj = j;
				break;
			}
		}

		if (jj != null)
			jogadores.remove(jj);

		return jj;
	}

	private static Jogador getJogador(Time timeDaVez) {
		double peso = Math.random();

		if (timeDaVez.getForca() < forcaMedia) {
			peso += Math.random();
		} else {
			peso -= Math.random();
		}

		int forca = 0;
		if (peso >= 0 && peso < 0.10) {
			// 10%
			forca = 1;
		} else if (peso >= 0.10 && peso < 0.25) {
			// 15%
			forca = 2;
		} else if (peso >= 0.25 && peso < 0.65) {
			// 40%
			forca = 3;
		} else if (peso >= 0.65 && peso < 0.95) {
			// 30%
			forca = 4;
		} else if (peso >= 0.95) {
			// 5%
			forca = 5;
		}

		Jogador jj = getJogador(forca);
		while (jj == null && jogadores.size() > 0)
			jj = getJogador(timeDaVez);

		return jj;
	}

}