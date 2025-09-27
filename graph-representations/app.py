# Nodos
# Creamos un grafo con 4 nodos (A, B, C, D) y estas aristas:
nodos = ["A", "B", "C", "D"]

# Aristas
# A — B
# A — C
# B — C
# C — D
aristas = [("A", "B"), ("A", "C"), ("B", "C"), ("C", "D")]


# 1. Matriz de adyacencia
# Tabla cuadrada de n x n (donde n = número de nodos).
# Cada posición (i, j) vale:
# 1: si hay conexión entre los nodos i y j.
# 0: si no hay conexión.
# Para nuestro ejemplo (A, B, C, D en ese orden):
matriz_adyacencia = [[0]*len(nodos) for _ in range(len(nodos))]
for u, v in aristas:
    i, j = nodos.index(u), nodos.index(v)
    matriz_adyacencia[i][j] = 1
    matriz_adyacencia[j][i] = 1  # porque es grafo no dirigido

print("Matriz de Adyacencia:")
for fila in matriz_adyacencia:
    print(fila)


# 2. Lista de adyacencia
# Para cada nodo, se guarda una lista con sus vecinos.
# Ej:
# A → [B, C]
# B → [A, C]
# C → [A, B, D]
# D → [C]
lista_adyacencia = {nodo: [] for nodo in nodos}
for u, v in aristas:
    lista_adyacencia[u].append(v)
    lista_adyacencia[v].append(u)

print("\nLista de Adyacencia:")
for k, v in lista_adyacencia.items():
    print(f"{k}: {v}")


# 3. Matriz de incidencia
# Ahora las columnas representan aristas y las filas representan nodos.
# Si un nodo participa en una arista, se marca con 1.
# Teniamos que las aristas: e1=(A,B), e2=(A,C), e3=(B,C), e4=(C,D)
matriz_incidencia = [[0]*len(aristas) for _ in range(len(nodos))]
for idx, (u, v) in enumerate(aristas):
    matriz_incidencia[nodos.index(u)][idx] = 1
    matriz_incidencia[nodos.index(v)][idx] = 1

print("\nMatriz de Incidencia:")
for fila in matriz_incidencia:
    print(fila)

# 4. Lista de aristas
# representación las conexiones, simplemente una colección de pares (o tuplas).
# [(A,B), (A,C), (B,C), (C,D)]
print("\nLista de Aristas:")
print(aristas)
