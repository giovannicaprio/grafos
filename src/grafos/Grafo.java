package grafos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Grafo {

	private int m[][];
	private boolean[] marcado;

	public Grafo(int nodos) {
		if (nodos <= 0)
			throw new IllegalArgumentException("Número de nodos inválido: "
					+ nodos);
		m = new int[nodos + 1][nodos + 1];
	}

	// TODO: ler XML ou JSON
	// TODO: ler graphviz
	// TODO: ver KML e GPX
	public Grafo(String arquivo) throws IOException {
		FileReader fr = new FileReader(arquivo);
		BufferedReader br = new BufferedReader(fr);
		String n = br.readLine();
		// System.out.println(n);
		int nodos = Integer.parseInt(n);
		m = new int[nodos + 1][nodos + 1];

		String par;
		while ((par = br.readLine()) != null) {
			// System.out.println(par);
			String[] campos = par.split(" ");
			int a = Integer.parseInt(campos[0]);
			int b = Integer.parseInt(campos[1]);
			adicionarArco(a, b);
		}
		br.close();
		fr.close();
	}

	public void adicionarArco(int a, int b) {
		adicionarArco(a, b, 1);
	}

	public void adicionarArco(int a, int b, int v) {
		verificar(a);
		verificar(b);
		m[a][b] = v;
		// m[b][a] = v;
		// acrescentar aresta de retorno
		// no caso de não-dirigido
	}

	private void verificar(int a) {
		if (a <= 0 || a > m.length)
			throw new IllegalArgumentException("Nodo inválido: " + a);
	}

	// Caminhamento em largura
	public List<Integer> largura(int s) {
		List<Integer> r = new ArrayList<>();
		limpar();
		Queue<Integer> q = new LinkedList<>();

		// visitar(s); // 1. Visite um nodo arbitrário
		r.add(s);
		marcar(s); // 2. [Marque o nodo] e [coloque-o em uma fila Q]
		q.add(s);
		// 3. Enquanto a fila Q não estiver vazia
		while (!q.isEmpty()) {
			int n = q.remove(); // 4. Retire um elemento N de Q
			// 5. Para cada nodo M (não marcado) adjacente a N
			for (int m : adjacentes(n)) {
				if (!isMarcado(m)) {
					// visitar(m); // 6. Visite M
					r.add(m);
					q.add(m); // 7. Coloque M na fila Q
					marcar(m); // 8. Marque M
				}
			}
		}
		return r;
	}

	private void limpar() {
		marcado = new boolean[m.length];
	}

	// Caminhamento em profundidade
	// 1. Visite um nodo arbitrário
	// 2. Marque o nodo e coloque-o em uma pilha S
	// 3. Enquanto a pilha S não estiver vazia
	// 4. Retire um elemento N de S
	// 5. Para cada nodo M (não marcado) adjacente a N
	// 6. Visite M
	// 7. Coloque N na pilha S
	// 8. Marque M
	// 9. Faça N = M
	public List<Integer> profundidade(int s) {
		List<Integer> r = new ArrayList<>();

		limpar();
		profundidade0(s, r);
		return r;
	}

	private void profundidade0(int s, List<Integer> r) {
		// visitar(s);
		r.add(s);
		marcar(s);
		for (int m : adjacentes(s)) {
			if (!isMarcado(m))
				profundidade0(m, r);
		}
	}

	private List<Integer> adjacentes(int n) {
		List<Integer> r = new ArrayList<>();
		for (int i = 0; i < m.length; i++) {
			if (m[n][i] != 0) {
				r.add(i);
			}
		}
		return r;
	}

	private boolean isMarcado(int s) {
		return marcado[s];
	}

//	private void visitar(int s) {
//		System.out.println(s);
//	}

	private void marcar(int s) {
		marcado[s] = true;
	}

	@Override
	public String toString() {
		String s = "";
		s += "    ";
		for (int i = 1; i < m.length; i++) {
			s += i + " ";
		}
		s += "\n";
		for (int i = 1; i < m.length; i++) {
			s += i + "  ";
			for (int j = 1; j < m.length; j++) {
				s += m[i][j] + " ";
			}
			s += "\n";
		}
		return s;
	}

	public void dijkstra() {
		// TODO:
	}
	public void floydWarshall() {
		// TODO:
	}	
	public void prim() {
		// TODO:
	}		
	public void kruskal() {
		// TODO:
	}	
	public void topologica() {
		// TODO:
	}
	public void fordFulkerson() {
		// TODO:
	}	
	public void ciclos() {
		// TODO:
	}
	public void componentes() {
		// TODO:
	}	
	public void caminho() {
		// TODO:
	}
}