package grafos;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Grafo {

	private int m[][];
	private boolean[] marcado;

	public Grafo(int nodos) {
		if (nodos <= 0)
			throw new IllegalArgumentException("N�mero de nodos inv�lido: "
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
		// no caso de n�o-dirigido
	}

	private void verificar(int a) {
		if (a <= 0 || a > m.length)
			throw new IllegalArgumentException("Nodo inv�lido: " + a);
	}

	// Caminhamento em largura
	public List<Integer> largura(int s) {
		List<Integer> r = new ArrayList<>();
		limpar();
		Queue<Integer> q = new LinkedList<>();

		// visitar(s); // 1. Visite um nodo arbitr�rio
		r.add(s);
		marcar(s); // 2. [Marque o nodo] e [coloque-o em uma fila Q]
		q.add(s);
		// 3. Enquanto a fila Q n�o estiver vazia
		while (!q.isEmpty()) {
			int n = q.remove(); // 4. Retire um elemento N de Q
			// 5. Para cada nodo M (n�o marcado) adjacente a N
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
	// 1. Visite um nodo arbitr�rio
	// 2. Marque o nodo e coloque-o em uma pilha S
	// 3. Enquanto a pilha S n�o estiver vazia
	// 4. Retire um elemento N de S
	// 5. Para cada nodo M (n�o marcado) adjacente a N
	// 6. Visite M
	// 7. Coloque N na pilha S
	// 8. Marque M
	// 9. Fa�a N = M
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
		s += "   ";
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

	public void dijkstra(int v) {
		int d[] = new int[m.length];
		int p[] = new int[m.length];

		// INITIALIZE
		for (int i = 1; i < d.length; i++) {
			d[i] = Integer.MAX_VALUE / 3;
			p[i] = -1;
		}
		d[v] = 0;

		Queue<Integer> q = new LinkedList<>();
		limpar();
		marcar(v); // 2. [Marque o nodo] e [coloque-o em uma fila Q]
		q.add(v);
		while (!q.isEmpty()) {
			int n = removeMin(q, d); // 4. Retire um elemento N de Q
			for (int m : adjacentes(n)) {
				if (!isMarcado(m)) {
					// RELAX
					if (d[n] + w(n, m) < d[m]) {
						d[m] = d[n] + w(n, m);
						p[m] = n;
					}
					q.add(m);
					marcar(m);
				}
			}
		}

		System.out.println("Distancias (3) Dijkstra:");
		System.out.println(Arrays.toString(d));
		System.out.println("Predecessores(3) Dijkstra:");
		System.out.println(Arrays.toString(p));
	}

	private int w(int u, int v) {
		return m[u][v] == 0 ? Integer.MAX_VALUE / 3 : m[u][v];
	}

	private int removeMin(Queue<Integer> q, int[] d) {
		List<Integer> lista = new ArrayList<>(q);
		int min = lista.get(0);
		for (int i = 0; i < lista.size(); i++)
			if (d[min] > d[lista.get(i)])
				min = lista.get(i);

		// remove o elemento com menor d da fila
		// q.removeObject(min);
		// q.removeAt(i);
		q.remove(new Integer(min));

		return min;
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
	public List<Integer> topologica() {
		List<Integer> L = new ArrayList<>();
		List<Integer> S = new ArrayList<>();
		int m2[][] = new int[m.length][m.length];
		for (int i = 0; i < m2.length; i++) { // copiar matriz m
			for (int j = 0; j < m2.length; j++) {
				m2[i][j] = m[i][j];
			}
		}
		atualizarS(S, m2); // iniciar S com nodos de grau de entrada zero
		while (!S.isEmpty()) {
			int n = S.remove(0);
			L.add(n);
			for (int j = 1; j < m2.length; j++) {
				if (m2[n][j] != 0) {
					m2[n][j] = 0;
					verificarNodoSemEntradas(S, m2, j);
				}
			}
		}
		return L;
	}

	private void verificarNodoSemEntradas(List<Integer> S, int[][] m2, int j) {
		int c = 0;
		for (int i = 1; i < m2.length; i++) {
			if (m2[i][j] != 0) {
				c++;
			}
		}
		if (c == 0) {
			S.add(j);
		}
	}

	private void atualizarS(List<Integer> S, int[][] m2) {
		S.clear();
		for (int j = 1; j < m2.length; j++) {
			verificarNodoSemEntradas(S, m2, j);
		}
	}

	public int fordFulkerson(int f, int s) {
		// copiar m
		int d[][] = new int[m.length][m.length];
		for (int i = 0; i < d.length; i++) { // copiar matriz m
			for (int j = 0; j < d.length; j++) {
				d[i][j] = m[i][j];
			}
		}
		
		//int f = 0;
		//int s = 0;
		// encontrar fonte e sumidouro (rede)
		
		// enquanto existe caminho entre f e s
			// remove uma unidade de capacidade do caminho
		List<Integer> c = caminho(f, s, d);
		while ( !c.isEmpty()) {
			System.out.println(c);
			for (int b = 1; b < c.size(); b++) {
				d[c.get(b - 1)][c.get(b)]--;
			}
			c = caminho(f, s, d);
		}
		
		// calcular vazao
		int capacidade = 0;
		int residual = 0;

		for (int j = 0; j < d.length; j++) {
			residual += d[f][j];
		}
		for (int j = 0; j < d.length; j++) {
			capacidade += m[f][j];
		}
		
		int vazao = capacidade - residual;
		
		
		return vazao;
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
	
	public List<Integer> caminho(int a, int b) {
		return caminho(a, b, m);
	}
	
	private List<Integer> caminho(int a, int b, int mat[][]) {
		List<Integer> c = new ArrayList<>();
		
		boolean r = caminho0(a, b, c, mat);
		Collections.reverse(c);
		return c;
	}

	private boolean caminho0(int a, int b, List<Integer> c, int mat[][]) {
		if (a == b) {
			c.add(b);
			return true;
		}
		
		for (int j = 0; j < mat.length; j++) {
			if (mat[a][j] != 0) {
				if (caminho0(j, b, c, mat)) {
					c.add(a);
					return true;
				}
			}
		}
		
		return false;
		
	}

	// Retornar a lista de nodos que se encontra até uma distância
	// dois, a partir do nodo passado por parâmetro
	public List<Integer> getAllToTwo(int s) {
		List<Integer> r = new LinkedList<>();
		limpar();
		List<Integer> n1 = new LinkedList<>();
		List<Integer> n2 = new LinkedList<>();
		marcar(s);
		r.add(s);
		for (int m : adjacentes(s)) {
			n1.add(m);
			marcar(m);
		}
		r.addAll(n1);
		while (!n1.isEmpty()) {
			int n = n1.remove(0);
			for (int m : adjacentes(n))
				if (!isMarcado(m)) {
					n2.add(m);
					marcar(m);
				}
		}
		r.addAll(n2);
		return r;
	}

	public List<Integer> getAllToTwoR(int s) {
		List<Integer> r = new ArrayList<>();
		limpar();
		getAllToTwoR0(s, r, 0, 2);
		return r;
	}

	private void getAllToTwoR0(int s, List<Integer> r, int c, int n) {
		if (c > n)
			return;
		r.add(s);
		marcar(s);
		for (int m : adjacentes(s)) {
			if (!isMarcado(m))
				getAllToTwoR0(m, r, c + 1, n);
		}

	}
	
	
	
	
	
}